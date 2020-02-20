package com.example.rss.presentation.itemList.adapter;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import java.util.List;
import java.util.Objects;

public class RecyclerListPresenter implements ListPresenter {
    private final RequestManager glide;
    private int resId;
    private AsyncListDiffer<ItemModel> mDiffer;

    private RecyclerView.Adapter adapter;

    public RecyclerListPresenter(RequestManager glide, int resId) {
        this.glide = glide;
        this.resId = resId;
    }

    void onBindRepositoryRowViewAtPosition(int position, ListRowView rowView) {
        ItemModel item = mDiffer.getCurrentList().get(position);
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
        return mDiffer.getCurrentList().size();
    }

    public void submitList(@Nullable List<ItemModel> list) {
        mDiffer.submitList(list);
    }

    private static final DiffUtil.ItemCallback<ItemModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<ItemModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull ItemModel oldUser, @NonNull ItemModel newUser) {
            return Objects.equals(oldUser.getItemId(), newUser.getItemId());
        }
        @Override
        public boolean areContentsTheSame(@NonNull ItemModel oldUser, @NonNull ItemModel newUser) {
            return oldUser.equals(newUser);
        }
    };

    @Override
    public void setAdapter(RecyclerView.Adapter adapter){
        this.adapter = adapter;
        mDiffer = new AsyncListDiffer<>(this.adapter, DIFF_CALLBACK);
    }
}
