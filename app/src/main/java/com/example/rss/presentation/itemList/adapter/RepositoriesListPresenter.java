package com.example.rss.presentation.itemList.adapter;


import com.bumptech.glide.RequestManager;

import java.util.List;

public class RepositoriesListPresenter {
    private List<ItemModel> items;
    private final RequestManager glide;
    private int resId;

    public RepositoriesListPresenter(RequestManager glide, List<ItemModel> items, int resId) {
        this.glide = glide;
        this.items = items;
        this.resId = resId;
    }

    void onBindRepositoryRowViewAtPosition(int position, RepositoryRowView rowView) {
        ItemModel item = items.get(position);
        rowView.setTitle(item.getTitle());
        rowView.setDescription(item.getDescription());
        rowView.setDate(item.getPubDate());
        rowView.setLogo(glide, item.getEnclosure());
        rowView.setStar((item.getStar() == null)?false:item.getStar());
        rowView.setRead((item.getRead() == null)?false:item.getRead());
    }

    int getResourceId(){
        return resId;
    }

    int getRepositoriesRowsCount() {
        return items.size();
    }
}
