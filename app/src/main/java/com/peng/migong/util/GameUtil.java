package com.peng.migong.util;

import android.graphics.Bitmap;

import com.peng.migong.bean.ItemBean;
import com.peng.migong.ui.PuzzleActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Penglingxiao on 2017/3/31.
 */

public class GameUtil {

    public static List<ItemBean> itemBeanList = new ArrayList<>();

    public static Bitmap mLastBitmap;

    public static ItemBean mBlankBitmapBean;


    /**
     * 交换点击图片和空白图片
     * @param from
     * @param blank
     */
    public static void swapItems(ItemBean from, ItemBean blank) {
        ItemBean tempItemBean = new ItemBean();

        //交换bitmapId;
        tempItemBean.setBitmapId(from.getBitmapId());
        from.setBitmapId(blank.getBitmapId());
        blank.setBitmapId(tempItemBean.getBitmapId());
        //交换Bitmap
        tempItemBean.setBitmap(from.getBitmap());
        from.setBitmap(blank.getBitmap());
        blank.setBitmap(tempItemBean.getBitmap());

        GameUtil.mBlankBitmapBean = from;
    }

    /**
     * 数据是否有解
     * @param data
     * @return
     */
    public static boolean canSolve(List<Integer> data) {
        int blankId = GameUtil.mBlankBitmapBean.getItemId();

        if (data.size() % 2 == 1) {
            return getInversions(data) % 2 == 0;
        } else {
            if (((blankId - 1) / PuzzleActivity.TYPE) % 2 == 1) {
                return getInversions(data) % 2 == 0;
            } else {
                return getInversions(data) % 2 == 1;
            }
        }
    }

    /**
     * 计算倒置和算法
     * @param data
     * @return
     */
    public static int getInversions(List<Integer> data) {
        int inversions = 0;
        int inversionCount = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                int index = data.get(i);
                if (data.get(j) != 0 && data.get(j) < index) {
                    inversionCount++;
                }
            }
            inversions += inversionCount;
            inversionCount = 0;
        }
        return inversions;
    }

}
