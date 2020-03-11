package com.example.rss.presentation.itemDetail.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.rss.R;
import com.example.rss.databinding.CardDetailItemRowBinding;

public class DetailViewHolder extends RecyclerView.ViewHolder implements DetailRowView {

    private final CardDetailItemRowBinding binding;

    DetailViewHolder(CardDetailItemRowBinding binding) {
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
    public void setStar(@NonNull Boolean isStar, onClick onClick) {
        if (!isStar) {
            binding.imgStar.setImageResource(R.drawable.ic_star_border_24dp);
        } else {
            binding.imgStar.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
        binding.imgStar.setOnClickListener(v -> {
            onClick.clickStar();
            this.setStar(!isStar, onClick);
        });
    }

    public interface onClick{
        void clickStar();
    }
}