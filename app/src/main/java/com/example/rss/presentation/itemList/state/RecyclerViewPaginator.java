package com.example.rss.presentation.itemList.state;

import com.example.rss.presentation.itemList.adapter.ItemModel;

public class RecyclerViewPaginator extends Paginator<ItemModel> {

    public RecyclerViewPaginator(Paginator.Page<ItemModel> dataSource, ViewController<ItemModel> viewController) {
        super(dataSource, viewController);
    }
}