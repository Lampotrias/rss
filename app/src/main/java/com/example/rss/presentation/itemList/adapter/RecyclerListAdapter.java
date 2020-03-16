package com.example.rss.presentation.itemList.adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.rss.R;
import com.example.rss.databinding.CardListItemRowBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<ListViewHolder> implements IRecyclerListAdapter<ItemModel>{
    public static final int SWIPE_FAVORITE = 0;
    public static final int SWIPE_READ = 1;
    private final RequestManager glide;
    private final Context context;
    private CardListItemRowBinding itemPersonBinding;

    private List<ItemModel> data = new ArrayList<>();

    public interface SwipeCallback {
        void onSwiped(Long itemId, int direction, Boolean value);
    }
    public RecyclerListAdapter(RequestManager glide, Context context) {
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
        ItemModel item = this.data.get(position);
        holder.setTitle(item.getTitle());
        holder.setDescription(item.getDescription());
        holder.setDate(item.getPubDate());
        holder.setLogo(glide, item.getEnclosure());
        holder.setStar((item.getStar() == null) ? false : item.getStar());
        holder.setRead((item.getRead() == null) ? false : item.getRead());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    @Override
    public void submitList(@NonNull List<ItemModel> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(newList, this.data));
        this.data.clear();
        this.data.addAll(newList);

        diffResult.dispatchUpdatesTo(this);
    }

    public ItemModel get(int position) {
        return this.data.get(position);
    }

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

            ItemModel itemModel = RecyclerListAdapter.this.data.get(position);
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

    public class DiffCallback extends DiffUtil.Callback{

        List<ItemModel> oldList;
        List<ItemModel> newList;

        DiffCallback(List<ItemModel> newList, List<ItemModel> oldList) {
            this.newList = newList;
            this.oldList = oldList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getItemId().equals(newList.get(newItemPosition).getItemId())
                    && oldList.get(oldItemPosition).getTitle().equals(newList.get(newItemPosition).getTitle());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            //you can return particular field for changed item.
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
}
