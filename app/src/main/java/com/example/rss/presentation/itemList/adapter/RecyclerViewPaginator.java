package com.example.rss.presentation.itemList.adapter;

import com.example.rss.domain.paginator.Paginator;

public class RecyclerViewPaginator extends Paginator<ItemModel> {

    public RecyclerViewPaginator(Paginator.Page<ItemModel> dataSource, ViewController<ItemModel> viewController) {
        super(dataSource, viewController);
    }
}