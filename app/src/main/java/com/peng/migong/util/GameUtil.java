package com.peng.migong.util;

import android.graphics.Bitmap;

import com.peng.migong.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Penglingxiao on 2017/3/31.
 */

public class GameUtil {

    public static List<Bitmap> mBitmaps = new ArrayList<>();
    public static List<ItemBean> mItemBeans = new ArrayList<>();

    public static Bitmap mLastBitmap;

    public static ItemBean mBlankBitmapBean;

    public static int TYPE = 3; //3, 4, 5


    /**
     * 交换点击图片和空白图片
     *
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
     *
     * @param data
     * @return
     */
    public static boolean canSolve(List<Integer> data) {
        int blankId = GameUtil.mBlankBitmapBean.getItemId();

        if (data.size() % 2 == 1) {
            return getInversions(data) % 2 == 0;
        } else {
            if (((blankId - 1) / TYPE) % 2 == 1) {
                return getInversions(data) % 2 == 0;
            } else {
                return getInversions(data) % 2 == 1;
            }
        }
    }

    /**
     * 计算倒置和算法
     *
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

    /**
     * 判断点击item能否移动
     *
     * @param position
     * @return
     */
    public static boolean isMoveable(int position) {
        int blankId = GameUtil.mBlankBitmapBean.getItemId() - 1;
        //不同行相差type
        if (Math.abs(blankId - position) == TYPE) {
            return true;
        } else {
            //同行相差1
            if (Math.abs(blankId - position) == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 判断是否完成
     *
     * @return
     */
    public static boolean isSuccess() {
        for (ItemBean itemBean : GameUtil.mItemBeans) {
            if (itemBean.getItemId() != 0 && itemBean.getItemId() == itemBean.getBitmapId()) {
                continue;
            } else if (itemBean.getBitmapId() == 0 && itemBean.getItemId() == TYPE * TYPE) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 随机打散图片
     */
    public static void getPuzzleGenerator() {

        for (int i = 0; i < mItemBeans.size(); i++) {
            int index = (int) (Math.random() * (TYPE * TYPE));
            swapItems(mItemBeans.get(index), mBlankBitmapBean);
        }

        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < mItemBeans.size(); i++) {
            datas.add(mItemBeans.get(i).getBitmapId());
        }

        if (canSolve(datas)) {
            return;
        } else {
            getPuzzleGenerator();
        }

    }

    public static void reset() {
        mBitmaps.clear();
        mItemBeans.clear();
        if (mLastBitmap != null && !mLastBitmap.isRecycled()) {
            mLastBitmap.recycle();
            mLastBitmap = null;
        }

        mBlankBitmapBean = null;
    }

}
