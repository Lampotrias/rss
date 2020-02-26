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

import java.util.List;
import java.util.Objects;

public class RecyclerListAdapter extends RecyclerView.Adapter<ListViewHolder>{

    private final AsyncListDiffer<ItemModel> mDiffer;
    private final RequestManager glide;
    private final Context context;
    private final int resId;

    public RecyclerListAdapter(RequestManager glide, int resId, Context context) {
        this.mDiffer = new AsyncListDiffer<>(this, DIFF_CALLBACK);
        this.context = context;
        this.glide = glide;
        this.resId = resId;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(resId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ItemModel item = mDiffer.getCurrentList().get(position);
        holder.setTitle(item.getTitle());
        holder.setDescription(item.getDescription());
        holder.setDate(item.getPubDate());
        holder.setLogo(glide, item.getEnclosure());
        holder.setStar((item.getStar() == null)?false:item.getStar());
        holder.setRead((item.getRead() == null)?false:item.getRead());
    }

    @Override
    public int getItemCount() {
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

    public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

        private final SwipeCallback callback;
        private final Drawable ico;

        public SwipeHelper(SwipeCallback callback, Drawable ico) {
            super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.callback = callback;
            this.ico = ico;
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            c.clipRect(0f, viewHolder.itemView.getTop(), dX, viewHolder.itemView.getBottom());
            if (dX < c.getWidth())
                c.drawColor(context.getResources().getColor(R.color.swipe_favorites));
            else
                c.drawColor(context.getResources().getColor(R.color.swipe_favorites));
            int paddingStart = viewHolder.itemView.getWidth() / 10;
            int topStarPos = viewHolder.itemView.getTop() + (viewHolder.itemView.getHeight() / 2) - (ico.getIntrinsicHeight() / 2);
            ico.setBounds(new Rect(paddingStart, topStarPos, paddingStart + ico.getIntrinsicWidth(), topStarPos + ico.getIntrinsicHeight()));
            ico.draw(c);

//            Log.e("logo",   "iVW: " + viewHolder.itemView.getWidth() + " iVH: " + viewHolder.itemView.getHeight()
//                    + " iVT: " + viewHolder.itemView.getTop() + " iVB: " + viewHolder.itemView.getBottom());
//            Log.e("logo", "iW: " + ico.getIntrinsicWidth() + " iH: " + ico.getIntrinsicHeight());

        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            RecyclerListAdapter.this.notifyItemChanged(position);
            callback.onSwiped(position, direction);
        }
    }
    public interface SwipeCallback {
        void onSwiped (int position, int direction);
    }
}
