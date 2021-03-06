package com.example.realtimedatabase;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView txt_title,txt_comment;

    IItemClickListener iItemClickListener;

    MyRecycleViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_comment = itemView.findViewById(R.id.textName);
        txt_title = itemView.findViewById(R.id.textGenre);

        itemView.setOnClickListener(this);
    }

    public void setiItemClickListener(IItemClickListener iItemClickListener) {
        this.iItemClickListener = iItemClickListener;
    }

    @Override
    public void onClick(View v) {
        iItemClickListener.onClick(v,getAdapterPosition());
    }
}