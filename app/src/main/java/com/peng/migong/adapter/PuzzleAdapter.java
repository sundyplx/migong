package com.peng.migong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.peng.migong.R;
import com.peng.migong.util.GameUtil;

/**
 * Created by Penglingxiao on 2017/4/1.
 */

public class PuzzleAdapter extends RecyclerView.Adapter<PuzzleAdapter.MyRecyclerViewHolder> {

    private Context mContext;

    public PuzzleAdapter(Context context) {
        this.mContext = context;
    }


    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.puzzle_gridview_item_layout, null));
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolder holder, int position) {
        holder.imageView.setImageBitmap(GameUtil.mBitmaps.get(position));
    }

    @Override
    public int getItemCount() {
        return GameUtil.mBitmaps.size();
    }

    class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyRecyclerViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }

    }

}
