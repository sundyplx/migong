package com.peng.migong.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.peng.migong.R;
import com.peng.migong.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片工具类
 * Created by Penglingxiao on 2017/3/31.
 */

public class ImageUtil {


    /**
     * 初始化小图片和bean类
     *
     * @param context
     * @param type
     * @param bitmapSelected
     */
    public void createInitBitmaps(Context context, int type, Bitmap bitmapSelected) {

        Bitmap bitmap = null;
        int itemWidth = bitmapSelected.getWidth() / type;
        int itemHeight = bitmapSelected.getHeight() / type;
        List<Bitmap> bitmaps = new ArrayList<>();
        for (int i = 1; i <= type; i++) {
            for (int j = 1; j <= type; j++) {
                bitmap = Bitmap.createBitmap(bitmapSelected, (j - 1) * itemWidth, (i - 1) * itemHeight, itemWidth, itemHeight);
                bitmaps.add(bitmap);
                ItemBean itembean = new ItemBean((i - 1) * type + j, (i - 1) * type + j, bitmap);
                GameUtil.itemBeanList.add(itembean);
            }
        }
        GameUtil.mLastBitmap = bitmaps.get(type * type - 1);
        bitmaps.remove(type * type - 1);
        GameUtil.itemBeanList.remove(type * type - 1);
        Bitmap blankBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank);
        blankBitmap = Bitmap.createBitmap(blankBitmap, 0, 0, itemWidth, itemHeight);

        bitmaps.add(blankBitmap);
        GameUtil.itemBeanList.add(new ItemBean(type * type, 0, blankBitmap));
        GameUtil.mBlankBitmapBean = GameUtil.itemBeanList.get(type * type - 1);
    }

    /**
     * 处理图片大小，放大，缩小到合适位置
     * @param newWidth
     * @param newHeight
     * @param sourceBitmap
     */
    public static Bitmap resizeBitmap(int newWidth, int newHeight, Bitmap sourceBitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth / sourceBitmap.getWidth(), newHeight / sourceBitmap.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, newWidth, newHeight, matrix, true);
        return bitmap;
    }

}
