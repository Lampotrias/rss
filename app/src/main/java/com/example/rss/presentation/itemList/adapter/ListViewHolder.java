package com.example.rss.presentation.itemList.adapter;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.rss.R;
import com.example.rss.databinding.CardListItemRowBinding;

public class ListViewHolder extends RecyclerView.ViewHolder implements ListRowView {

    private CardListItemRowBinding binding;

    public ListViewHolder(CardListItemRowBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @Override
    public void setTitle(String title) {
        binding.txTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        binding.txDescription.setText(description);
    }

    @Override
    public void setDate(String date) {
        binding.txDate.setText(date);
    }

    @Override
    public void setLogo(RequestManager glide, String logoPath) {
        if (logoPath == null || logoPath.equals("")) {
            binding.imgChannelLogo.setVisibility(View.GONE);
        } else {
            glide.load(logoPath).into(binding.imgChannelLogo);
        }
    }

    @Override
    public void setStar(@NonNull Boolean isStar) {
        if (!isStar) {
            binding.imgStar.setImageResource(R.drawable.ic_star_border_24dp);
        } else {
            binding.imgStar.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
    }

    @Override
    public void setRead(Boolean isRead) {
        if (isRead) {
            binding.txTitle.setTextColor(Color.parseColor("#d1e0e0"));
            binding.txDescription.setTextColor(Color.parseColor("#d1e0e0"));
        } else {
            binding.txTitle.setTextColor(Color.BLACK);
            binding.txDescription.setTextColor(Color.BLACK);
        }
    }
}
