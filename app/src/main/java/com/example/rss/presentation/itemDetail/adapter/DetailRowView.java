package com.example.rss.presentation.itemDetail.adapter;

import com.bumptech.glide.RequestManager;

public interface DetailRowView {
    void setTitle(String title);
    void setDescription(String description);
    void setDate(String date);
    void setLogo(RequestManager glide, String logoPath);
    void setStar(Boolean isStar, DetailViewHolder.onClick onClick);
}
