package com.example.rss.presentation.itemDetail.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.rss.presentation.itemList.adapter.RepositoryRowView;

public class DetailViewHolder extends RecyclerView.ViewHolder implements RepositoryRowView {

    public DetailViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public void setDate(String date) {

    }

    @Override
    public void setLogo(RequestManager glide, String logoPath) {

    }

    @Override
    public void setStar(Boolean isStar) {

    }

    @Override
    public void setRead(Boolean isRead) {

    }
}
