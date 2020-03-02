package com.example.rss.data.database.dto;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "favorite", primaryKeys = {"item_id"})
public class FavoritesDTO {
    @NonNull
    @ColumnInfo(name = "id")
    private Long favId;

    @NonNull
    @ColumnInfo(name = "item_id")
    private Long itemId;

    @NonNull
    @ColumnInfo(name = "category_id")
    private Long categoryId;

    @NonNull
    public Long getFavId() {
        return favId;
    }

    public void setFavId(@NonNull Long favId) {
        this.favId = favId;
    }

    @NonNull
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(@NonNull Long itemId) {
        this.itemId = itemId;
    }

    @NonNull
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull Long categoryId) {
        this.categoryId = categoryId;
    }
}
