package com.example.rss.presentation.itemList.state;

import java.util.List;

public abstract class State<T> {

    public void restart(){};
    public void refresh(){};
    public void loadNewPage(){};
    public void release(){};
    public void newData(List<T> data){};
    public void fail(Throwable error){};
}
