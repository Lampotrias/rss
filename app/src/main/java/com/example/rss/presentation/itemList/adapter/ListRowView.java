package com.example.rss.presentation.itemList.adapter;

import com.bumptech.glide.RequestManager;

public interface ListRowView {
    void setTitle(String title);
    void setDescription(String description);
    void setDate(String date);
    void setLogo(RequestManager glide, String logoPath);
    void setStar(Boolean isStar);
    void setRead(Boolean isRead);
}
