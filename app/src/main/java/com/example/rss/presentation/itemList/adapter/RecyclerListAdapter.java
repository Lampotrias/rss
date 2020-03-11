package com.example.rss.presentation.itemList.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.rss.R;
import com.example.rss.databinding.CardListItemRowBinding;

import java.util.List;
import java.util.Objects;

public class RecyclerListAdapter extends RecyclerView.Adapter<ListViewHolder> {
    public static final int SWIPE_FAVORITE = 0;
    public static final int SWIPE_READ = 1;
    private final AsyncListDiffer<ItemModel> mDiffer;
    private final RequestManager glide;
    private final Context context;
    private CardListItemRowBinding itemPersonBinding;

    public RecyclerListAdapter(RequestManager glide, Context context) {
        this.mDiffer = new AsyncListDiffer<>(this, DIFF_CALLBACK);
        this.context = context;
        this.glide = glide;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        itemPersonBinding = CardListItemRowBinding.inflate(layoutInflater, parent, false);
        return new ListViewHolder(itemPersonBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ItemModel item = mDiffer.getCurrentList().get(position);
        holder.setTitle(item.getTitle());
        holder.setDescription(item.getDescription());
        holder.setDate(item.getPubDate());
        holder.setLogo(glide, item.getEnclosure());
        holder.setStar((item.getStar() == null) ? false : item.getStar());
        holder.setRead((item.getRead() == null) ? false : item.getRead());
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public void submitList(@Nullable List<ItemModel> list) {
        mDiffer.submitList(list);
    }

    public ItemModel get(int position) {
        return mDiffer.getCurrentList().get(position);
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

    public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

        private final SwipeCallback callback;
        private final Drawable icoRead;
        private final Drawable icoFavorites;

        public SwipeHelper(SwipeCallback callback, Drawable icoFavorites, Drawable icoRead) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.callback = callback;
            this.icoRead = icoRead;
            this.icoFavorites = icoFavorites;
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                int horizontalPos = viewHolder.itemView.getTop() + (viewHolder.itemView.getHeight() / 2) - (icoRead.getIntrinsicHeight() / 2);
                int paddingStart = viewHolder.itemView.getWidth() / 10;
                if (dX > 0) {
                    c.clipRect(0, viewHolder.itemView.getTop(), dX, viewHolder.itemView.getBottom());
                    /*if (dX < c.getWidth())
                        c.drawColor(context.getResources().getColor(R.color.swipe_favorites));
                    else*/
                    c.drawColor(context.getResources().getColor(R.color.swipe_read));

                    icoRead.setBounds(new Rect(paddingStart, horizontalPos, paddingStart + icoRead.getIntrinsicWidth(), horizontalPos + icoRead.getIntrinsicHeight()));
                    icoRead.draw(c);
                } else {
                    c.clipRect(
                            viewHolder.itemView.getWidth() + dX,
                            viewHolder.itemView.getTop(),
                            viewHolder.itemView.getRight(),
                            viewHolder.itemView.getBottom());
                    c.drawColor(context.getResources().getColor(R.color.swipe_favorites));

                    icoFavorites.setBounds(new Rect(
                            viewHolder.itemView.getWidth() - paddingStart - icoFavorites.getIntrinsicWidth(),
                            horizontalPos,
                            viewHolder.itemView.getWidth() - paddingStart,
                            horizontalPos + icoFavorites.getIntrinsicHeight()));
                    icoFavorites.draw(c);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            ItemModel itemModel = mDiffer.getCurrentList().get(position);
            if (direction == ItemTouchHelper.LEFT) {
                itemModel.setStar(!itemModel.getStar());
                callback.onSwiped(itemModel.getItemId(), RecyclerListAdapter.SWIPE_FAVORITE, itemModel.getStar());

            } else if (direction == ItemTouchHelper.RIGHT) {
                itemModel.setRead(!itemModel.getRead());
                callback.onSwiped(itemModel.getItemId(), RecyclerListAdapter.SWIPE_READ, itemModel.getRead());
            }

            RecyclerListAdapter.this.notifyItemChanged(position);
        }
    }

    public interface SwipeCallback {
        void onSwiped(Long itemId, int direction, Boolean value);
    }
}
