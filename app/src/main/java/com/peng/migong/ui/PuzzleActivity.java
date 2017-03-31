package com.peng.migong.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.peng.migong.R;
import com.peng.migong.constants.ImageType;

/**
 * 拼图页面
 *
 * @author penglingxiao
 * @date 2017/3/31
 */
public class PuzzleActivity extends Activity {

    public static int TYPE = 3;

    private TextView timerView, steperView;
    private Button artworkBtn, resetBtn, backBtn;
    private Bitmap mBitmap;

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
        }
        initView();
        setListener();
    }

    private void initView() {
        timerView = (TextView) findViewById(R.id.timer);
        steperView = (TextView) findViewById(R.id.steper);
        artworkBtn = (Button) findViewById(R.id.artwork);
        resetBtn = (Button) findViewById(R.id.reset);
        backBtn = (Button) findViewById(R.id.back);
    }

    private void setListener() {
        artworkBtn.setOnClickListener(btnOnClickListener);
        resetBtn.setOnClickListener(btnOnClickListener);
        backBtn.setOnClickListener(btnOnClickListener);
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
    }
}
