package com.example.rss.presentation.itemDetail.adapter;


import com.bumptech.glide.RequestManager;
import com.example.rss.presentation.itemList.adapter.ItemModel;

import java.util.List;

public class ViewDetailPresenter {
    private List<ItemModel> items;
    private final RequestManager glide;
    private int resId;

    public ViewDetailPresenter(RequestManager glide, List<ItemModel> items, int resId) {
        this.glide = glide;
        this.items = items;
        this.resId = resId;
    }

    void onBindRepositoryRowViewAtPosition(int position, DetailRowView rowView) {
        ItemModel item = items.get(position);
        rowView.setTitle(item.getTitle());
        rowView.setDescription(item.getDescription());
        rowView.setDate(item.getPubDate());
        rowView.setLogo(glide, item.getEnclosure());
        rowView.setStar((item.getStar() == null)?false:item.getStar());
    }

    int getResourceId(){
        return resId;
    }

    int getRepositoriesRowsCount() {
        return items.size();
    }
}
