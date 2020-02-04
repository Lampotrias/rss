package com.example.rss.presentation.itemList.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rss.R;

public class RepositoriesRecyclerAdapter  extends RecyclerView.Adapter<RepositoryViewHolder>{
    private final RepositoriesListPresenter presenter;

    public RepositoriesRecyclerAdapter(RepositoriesListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        presenter.onBindRepositoryRowViewAtPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return presenter.getRepositoriesRowsCount();
    }
}
