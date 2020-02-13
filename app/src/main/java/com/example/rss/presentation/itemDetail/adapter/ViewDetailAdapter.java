package com.example.rss.presentation.itemDetail.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewDetailAdapter extends RecyclerView.Adapter<DetailViewHolder>{
    private final ViewDetailPresenter presenter;

    public ViewDetailAdapter(ViewDetailPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(presenter.getResourceId(), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        presenter.onBindRepositoryRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getRepositoriesRowsCount();
    }
}
