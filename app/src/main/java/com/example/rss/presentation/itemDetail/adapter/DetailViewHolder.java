package com.example.rss.presentation.itemDetail.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.rss.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailViewHolder extends RecyclerView.ViewHolder implements DetailRowView {

    @BindView(R.id.txTitle)
    TextView txTitle;

    @BindView(R.id.txDescription)
    TextView txDescription;

    @BindView(R.id.txDate)
    TextView txDate;

    @BindView(R.id.img_star)
    ImageView imgStar;

    @BindView(R.id.img_channel_logo)
    ImageView imgChannelLogo;

    public DetailViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setTitle(String title) {
        txTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        txDescription.setText(description);
    }

    @Override
    public void setDate(String date) {
        txDate.setText(date);
    }

    @Override
    public void setLogo(RequestManager glide, String logoPath) {
        glide.load(logoPath).into(imgChannelLogo);
    }

    @Override
    public void setStar(@NonNull Boolean isStar) {
        if(!isStar){
            imgStar.setImageResource(android.R.drawable.star_big_off);
        }else if (isStar){
            imgStar.setImageResource(android.R.drawable.star_big_on);
        }
    }
}