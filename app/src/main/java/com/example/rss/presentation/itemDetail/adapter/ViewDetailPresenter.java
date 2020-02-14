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
        rowView.setLogo(glide, "https://icdn.lenta.ru/images/2020/02/13/21/20200213210621185/pic_989b7e9c88f529c0bbd59d22e36b79e8.png");
        rowView.setStar((item.getStar() == null)?false:item.getStar());
    }

    int getResourceId(){
        return resId;
    }

    int getRepositoriesRowsCount() {
        return items.size();
    }
}
