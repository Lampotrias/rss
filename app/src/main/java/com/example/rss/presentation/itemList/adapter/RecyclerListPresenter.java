package com.example.rss.presentation.itemList.adapter;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

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

public class RecyclerListPresenter implements ListPresenter {
    private final RequestManager glide;
    private final Context context;
    private int resId;
    private AsyncListDiffer<ItemModel> mDiffer;

    private RecyclerView.Adapter adapter;

    public RecyclerListPresenter(RequestManager glide, int resId, Context context) {
        this.glide = glide;
        this.resId = resId;
        this.context = context;
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
            adapter.notifyItemChanged(position);
            callback.onSwiped(position, direction);
        }
    }

    public interface SwipeCallback {
        void onSwiped (int position, int direction);
    }
}
