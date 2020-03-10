package com.example.rss.presentation.itemDetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.rss.databinding.CardDetailItemRowBinding;
import com.example.rss.presentation.itemList.adapter.ItemModel;

import java.util.List;

public class ViewDetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {

    private CardDetailItemRowBinding binding;
    private final RequestManager glide;
    private List<ItemModel> items;

    public ViewDetailAdapter(List<ItemModel> items, RequestManager glide) {
        this.glide = glide;
        this.items = items;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = CardDetailItemRowBinding.inflate(layoutInflater, parent, false);
        return new DetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        ItemModel item = items.get(position);
        holder.setTitle(item.getTitle());
        holder.setDescription(item.getDescription());
        holder.setDate(item.getPubDate());
        holder.setLogo(glide, item.getEnclosure());
        holder.setStar((item.getStar() == null) ? false : item.getStar());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
