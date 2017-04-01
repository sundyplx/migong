package com.peng.migong.ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.peng.migong.R;
import com.peng.migong.adapter.PuzzleAdapter;
import com.peng.migong.bean.ItemBean;
import com.peng.migong.constants.ImageType;
import com.peng.migong.util.GameUtil;
import com.peng.migong.util.ImageUtil;

/**
 * 拼图页面
 *
 * @author penglingxiao
 * @date 2017/3/31
 */
public class PuzzleActivity extends Activity {

    private TextView steperView;
    private Button artworkBtn, resetBtn, backBtn;
    private GridView gridView;
    //    private RecyclerView recyclerView;
    private PuzzleAdapter puzzleAdapter;
    private ImageView imgViewStub;
    private MyAdapter myAdapter;
    private Bitmap mBitmap;

    private int stepTotal = 0;

    private boolean isArtworkVisible = false;
    private ObjectAnimator artworkVisibleAnimator;
    private ObjectAnimator artworkInvisibleAnimator;

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
            ImageUtil.createInitBitmaps(this, GameUtil.TYPE, mBitmap);
            GameUtil.getPuzzleGenerator();
        }
        initView();
        initData();
        setListener();
    }

    private void initView() {
        steperView = (TextView) findViewById(R.id.steper);
        artworkBtn = (Button) findViewById(R.id.artwork);
        resetBtn = (Button) findViewById(R.id.reset);
        backBtn = (Button) findViewById(R.id.back);
        imgViewStub = (ImageView) findViewById(R.id.artwork_viewstub);
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        puzzleAdapter = new PuzzleAdapter(this);
//        recyclerView.setAdapter(puzzleAdapter);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setNumColumns(GameUtil.TYPE);
        myAdapter = new MyAdapter();
        gridView.setAdapter(myAdapter);
    }

    private void initData() {

        imgViewStub.setImageBitmap(mBitmap);
        artworkVisibleAnimator = ObjectAnimator.ofFloat(imgViewStub, "alpha", 0f, 1f);
        artworkVisibleAnimator.setDuration(500);
        artworkInvisibleAnimator = ObjectAnimator.ofFloat(imgViewStub, "alpha", 1f, 0f);
        artworkInvisibleAnimator.setDuration(500);
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return GameUtil.mBitmaps.size();
        }

        @Override
        public Object getItem(int i) {
            return GameUtil.mBitmaps.get(i);
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
                GridView.LayoutParams layoutParams = new GridView.LayoutParams(viewGroup.getWidth()/GameUtil.TYPE, viewGroup.getHeight()/GameUtil.TYPE);
                view.setLayoutParams(layoutParams);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
                GridView.LayoutParams layoutParams = new GridView.LayoutParams(viewGroup.getWidth()/GameUtil.TYPE, viewGroup.getHeight()/GameUtil.TYPE);
                view.setLayoutParams(layoutParams);
            }

            viewHolder.imageView.setImageBitmap(GameUtil.mItemBeans.get(i).getBitmap());
            Log.e("test", "position: " + i + ",  " + GameUtil.mBitmaps.get(i));

            return view;
        }
    }

    class ViewHolder {
        ImageView imageView;
    }

    ViewTreeObserver.OnGlobalLayoutListener layoutListener;

    private void setListener() {
        artworkBtn.setOnClickListener(btnOnClickListener);
        resetBtn.setOnClickListener(btnOnClickListener);
        backBtn.setOnClickListener(btnOnClickListener);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemBean itemBean = GameUtil.mItemBeans.get(i);
                if (GameUtil.isMoveable(i)) {
                    GameUtil.swapItems(itemBean, GameUtil.mBlankBitmapBean);
                    stepTotal++;
                    myAdapter.notifyDataSetChanged();
                    refreshStep(stepTotal);
                    isDone();
                }
            }
        });
        ImageUtil.resizeView(PuzzleActivity.this, gridView, mBitmap);
        layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(gridView.getWidth(), gridView.getHeight());
                imgViewStub.setLayoutParams(layoutParams);
                gridView.getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
            }
        };
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        imgViewStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isArtworkVisible) {
                    showArtWork();
                }
            }
        });
        //初始化放开事件
        imgViewStub.setClickable(false);
    }

    private void isDone() {
        gridView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (GameUtil.isSuccess()) {
                    Toast.makeText(PuzzleActivity.this, "恭喜您拼图成功!", Toast.LENGTH_LONG).show();
                }
            }
        }, 50);
    }

    private void refreshStep(int t) {
        if (t < 0) {
            steperView.setText(getString(R.string.puzzle_steper));
        } else {
            steperView.setText(t + "");
        }
    }

    private View.OnClickListener btnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.artwork:
                    showArtWork();
                    break;
                case R.id.reset:
                    GameUtil.reset();
                    resetStatus();
                    ImageUtil.createInitBitmaps(PuzzleActivity.this, GameUtil.TYPE, mBitmap);
                    GameUtil.getPuzzleGenerator();
                    myAdapter.notifyDataSetChanged();
                    break;
                case R.id.back:
                    PuzzleActivity.this.finish();
                    break;
            }
        }
    };

    //显示原图
    private void showArtWork() {
        if (isArtworkVisible) {
            artworkInvisibleAnimator.start();
            imgViewStub.setClickable(false);
        } else {
            artworkVisibleAnimator.start();
            imgViewStub.setClickable(true);
        }
        isArtworkVisible = !isArtworkVisible;
    }

    private void resetStatus() {
        refreshStep(-1);
        stepTotal = 0;
    }

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
