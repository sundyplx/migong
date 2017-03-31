package com.peng.migong.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.peng.migong.R;
import com.peng.migong.constants.ImageType;
/**
 * 主页面选择图片
 * @author penglingxiao
 * @date 2017/3/31 
 */
public class MainActivity extends Activity {

    private static final int RESULT_IMAGE = 60;
    private static final String IMAGE_TYPE = "image/*";

    private GridView gridView;
    private int[] resIds = {R.drawable.default_1, R.drawable.default_2, R.drawable.default_3, R.drawable.default_4, R.drawable.default_more};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter());
        setListener();
    }

    private void setListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == resIds.length - 1) {
                    //选择本地图库
                    showCustomDrawableDialog();
                } else {
                    jumpPuzzleActivity(resIds[i] + "", ImageType.ID_SOURCE.getType());
                }
            }
        });
    }

    private void showCustomDrawableDialog() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
        startActivityForResult(intent, RESULT_IMAGE);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return resIds.length;
        }

        @Override
        public Object getItem(int i) {
            return resIds[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(MainActivity.this).inflate(R.layout.main_gridview_item_layout, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
                view.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) view.getTag();

            viewHolder.imageView.setImageResource(resIds[i]);

            return view;
        }

    }

    class ViewHolder {
        ImageView imageView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_IMAGE && data != null) {
                Cursor cursor = this.getContentResolver().query(data.getData(), null, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                jumpPuzzleActivity(imagePath, ImageType.STRING_SOURCE.getType());
            }
        }
    }

    private void jumpPuzzleActivity(String imagePath, String imageType) {
        Intent intent = new Intent(this, null);
        intent.putExtra("imagePath", imagePath);
        intent.putExtra("imageType", imageType);
        startActivity(intent);
    }
}
