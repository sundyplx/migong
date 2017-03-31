package com.peng.migong.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.peng.migong.R;
import com.peng.migong.constants.ImageType;
import com.peng.migong.util.GameUtil;
import com.peng.migong.util.ImageUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 拼图页面
 *
 * @author penglingxiao
 * @date 2017/3/31
 */
public class PuzzleActivity extends Activity {

    public static int TYPE = 3; //3, 4, 5
    private int mType = 3;

    private TextView timerView, steperView;
    private Button artworkBtn, resetBtn, backBtn;
    private GridView gridView;
    private Bitmap mBitmap;

    private Timer timer;
    private TimerTask timerTask;
    private int timeTotal = 0;

    private int stepTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_layout);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String imageType = bundle.getString("imageType");
            String imagePath = bundle.getString("imagePath");
            if (imageType.equals(ImageType.ID_SOURCE.getType())) {
                mBitmap = BitmapFactory.decodeResource(getResources(), Integer.valueOf(imagePath));
            } else {
                mBitmap = BitmapFactory.decodeFile(imagePath);
            }
            mType = bundle.getInt("type");
            ImageUtil.createInitBitmaps(this, mType, mBitmap);
        }
        initView();
        setListener();
        initTimer();

    }

    Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                timeTotal++;
                refreshTime(timeTotal);
            }
        }
    };

    private void initView() {
        timerView = (TextView) findViewById(R.id.tv_timer);
        steperView = (TextView) findViewById(R.id.steper);
        artworkBtn = (Button) findViewById(R.id.artwork);
        resetBtn = (Button) findViewById(R.id.reset);
        backBtn = (Button) findViewById(R.id.back);
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setNumColumns(mType);
        gridView.setAdapter(new myAdapter());
    }


    private class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return GameUtil.bitmaps.size();
        }

        @Override
        public Object getItem(int i) {
            return GameUtil.bitmaps.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(PuzzleActivity.this).inflate(R.layout.puzzle_gridview_item_layout, null);
                viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.imageView.setImageBitmap(GameUtil.bitmaps.get(i));

            return view;
        }

    }

    class ViewHolder {
        ImageView imageView;
    }

    private void setListener() {
        artworkBtn.setOnClickListener(btnOnClickListener);
        resetBtn.setOnClickListener(btnOnClickListener);
        backBtn.setOnClickListener(btnOnClickListener);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private void refreshStep(int t) {
        if (t < 0) {
            timerView.setText(getString(R.string.puzzle_steper));
        } else {
            timerView.setText(t);
        }
    }

    private void refreshTime(int t) {
        if (t < 0) {
            timerView.setText(getString(R.string.puzzle_timer));
        } else {
            timerView.setText(timeTotal + "");
        }
    }

    private void initTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = 1;
                timeHandler.sendMessage(message);
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.artwork:
                    break;
                case R.id.reset:
                    break;
                case R.id.back:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
        GameUtil.reset();
    }
}
