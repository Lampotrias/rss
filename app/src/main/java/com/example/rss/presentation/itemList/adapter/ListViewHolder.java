package com.example.rss.presentation.itemList.adapter;

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

public class ListViewHolder extends RecyclerView.ViewHolder implements ListRowView {

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


    public ListViewHolder(@NonNull View itemView) {
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
        if (logoPath == null || logoPath.equals("")){
         imgChannelLogo.setVisibility(View.GONE);
        }else {
            glide.load(logoPath).into(imgChannelLogo);
        }
    }

    @Override
    public void setStar(@NonNull Boolean isStar) {
        if(!isStar){
            imgStar.setImageResource(R.drawable.ic_star_border_24dp);
        }else {
            imgStar.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }

    @Override
    public void setRead(Boolean isRead) {
        if (isRead) {
            txTitle.setTextColor(Color.parseColor("#d1e0e0"));
            txDescription.setTextColor(Color.parseColor("#d1e0e0"));
        }else{
            txTitle.setTextColor(Color.BLACK);
            txDescription.setTextColor(Color.BLACK);
        }
    }
}
