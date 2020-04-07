package com.example.rss.presentation.itemDetail.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.rss.databinding.CardDetailItemRowBinding;
import com.example.rss.domain.adapter.DiffCallback;
import com.example.rss.presentation.itemList.adapter.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ViewDetailAdapter extends RecyclerView.Adapter<DetailViewHolder> {

    private CardDetailItemRowBinding binding;
    private final RequestManager glide;
    private List<ItemModel> data;
    private final DetailViewHolder.onClick onClick;

    public ViewDetailAdapter(List<ItemModel> data, RequestManager glide, DetailViewHolder.onClick onClick) {
        this.glide = glide;
        this.data = data;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = CardDetailItemRowBinding.inflate(layoutInflater, parent, false);
        return new DetailViewHolder(binding);
    }

    public void insertItemBefore(List<ItemModel> newData){
        if (!newData.isEmpty()){
            newData.addAll(this.data);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(newData, this.data));
            this.data.clear();
            this.data.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    public void insertItemAfter(List<ItemModel> newData){
        if (!newData.isEmpty()){
            List<ItemModel> tmpList = new ArrayList<>();
            tmpList.addAll(this.data);
            tmpList.addAll(newData);

            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(tmpList, this.data));
            this.data.clear();
            this.data.addAll(tmpList);

            diffResult.dispatchUpdatesTo(this);
        }
    }

    public ItemModel getItem(int position){
        return this.data.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        ItemModel item = data.get(position);
        holder.setTitle(item.getTitle());
        holder.setDescription(item.getDescription());
        holder.setDate(item.getPubDate());
        holder.setLogo(glide, item.getEnclosure());
        holder.setStar((item.getStar() == null) ? false : item.getStar(), onClick);
        holder.showLink(item.getLink());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
