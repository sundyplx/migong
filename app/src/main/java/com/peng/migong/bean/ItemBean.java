package com.peng.migong.bean;

import android.graphics.Bitmap;

/**
 * 将每小格图片抽象成实体类
 * Created by Penglingxiao on 2017/3/31.
 */

public class ItemBean {

    private int itemId;
    private int bitmapId;
    private Bitmap bitmap;

    public ItemBean() {

    }

    public ItemBean(int itemId, int bitmapId, Bitmap bitmap) {
        this.itemId = itemId;
        this.bitmapId = bitmapId;
        this.bitmap = bitmap;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getBitmapId() {
        return bitmapId;
    }

    public void setBitmapId(int bitmapId) {
        this.bitmapId = bitmapId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
