package com.example.rss.data.entity.mapper;

import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;
import com.example.rss.domain.Category;
import com.example.rss.domain.Channel;
import com.example.rss.domain.File;
import com.example.rss.domain.Item;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RepositoryEntityDataMapper {

    @Inject
    public RepositoryEntityDataMapper() {
    }

    //ToDO доделать
    public ChannelEntity transform (Channel channel){
        ChannelEntity channelEntity = null;
        if (channel != null) {
            channelEntity = new ChannelEntity();
            channelEntity.setChannelId(channel.getChannelId());
            channelEntity.setTitle(channel.getTitle());
            channelEntity.setFileId(channel.getFileId());
            channelEntity.setDescription(channel.getDescription());
            channelEntity.setLink(channel.getLink());
            channelEntity.setSourceLink(channel.getSourceLink());
            channelEntity.setCategoryId(channel.getCategoryId());
            channelEntity.setCacheImage(channel.getCacheImage());
            channelEntity.setDownloadFullText(channel.getDownloadFullText());
            channelEntity.setOnlyWifi(channel.getOnlyWifi());
            channelEntity.setLastBuild(channel.getLastBuild());
            channelEntity.setNextSyncDate(channel.getNextSyncDate());
        }
        return channelEntity;
    }

    public Channel transform(ChannelEntity channelEntity){
        Channel channel = null;

        if (channelEntity != null){
            channel = new Channel();
            channel.setChannelId(channelEntity.getChannelId());
            channel.setTitle(channelEntity.getTitle());
            channel.setFileId(channelEntity.getFileId());
            channel.setLink(channelEntity.getLink());
            channel.setSourceLink(channelEntity.getSourceLink());
            channel.setDescription(channelEntity.getDescription());
            channel.setCategoryId(channelEntity.getCategoryId());
            channel.setCacheImage(channelEntity.getCacheImage());
            channel.setDownloadFullText(channelEntity.getDownloadFullText());
            channel.setOnlyWifi(channelEntity.getOnlyWifi());
            channel.setLastBuild(channelEntity.getLastBuild());
            channel.setNextSyncDate(channelEntity.getNextSyncDate());
        }
        return channel;
    }

    public List<Channel> transformChannels(List<ChannelEntity> channelEntities){
        List<Channel> channels = new ArrayList<>();
        for (ChannelEntity channelEntity: channelEntities) {
            if (channelEntity != null){
                channels.add(this.transform(channelEntity));
            }
        }
        return channels;
    }

    public File transform (FileEntity fileEntity){
        File file = null;
        if (fileEntity != null) {
            file = new File();
            file.setFileId(fileEntity.getFileId());
            file.setPath(fileEntity.getPath());
            file.setDescription((fileEntity.getDescription()));
            file.setExternal(fileEntity.getExternal());
            file.setType(fileEntity.getType());
        }
        return file;
    }

    public FileEntity transform (File file){
        FileEntity fileEntity = null;
        if (file != null) {
            fileEntity = new FileEntity();
            fileEntity.setFileId(file.getFileId());
            fileEntity.setPath(file.getPath());
            fileEntity.setDescription((file.getDescription()));
            fileEntity.setExternal(file.getExternal());
            fileEntity.setType(file.getType());
        }
        return fileEntity;
    }



    public CategoryEntity transform (Category category){
        CategoryEntity categoryEntity = null;
        if (category != null) {
            categoryEntity = new CategoryEntity();
            categoryEntity.setCategoryId(category.getCategoryId());
            categoryEntity.setName(category.getName());
            categoryEntity.setType(category.getType());
        }
        return categoryEntity;
    }

    public Category transform (CategoryEntity categoryEntity){
        Category category = null;
        if (categoryEntity != null) {
            category = new Category();
            category.setCategoryId(categoryEntity.getCategoryId());
            category.setName(categoryEntity.getName());
            category.setType(categoryEntity.getType());
        }
        return category;
    }

    public List<Category> transformCategories(List<CategoryEntity> categoryEntities){
        List<Category> categories = new ArrayList<>();
        for (CategoryEntity categoryEntity: categoryEntities) {
            if (categoryEntity != null){
                categories.add(this.transform(categoryEntity));
            }
        }
        return categories;
    }

    public Item transform (ItemEntity itemEntity){
        Item item = null;
        if (itemEntity != null) {
            item = new Item();
            item.setItemId(itemEntity.getItemId());
            item.setTitle(itemEntity.getTitle());
            item.setDescription(itemEntity.getDescription());
            item.setEnclosure(itemEntity.getEnclosure());
            item.setGuid(itemEntity.getGuid());
            item.setChannelId(itemEntity.getChannelId());
            item.setLink(itemEntity.getLink());
            item.setPubDate(itemEntity.getPubDate());
            item.setRead(itemEntity.getRead());
            item.setFavorite(itemEntity.getFavorite());
        }

        return item;
    }

    public ItemEntity transform (Item item){
        ItemEntity itemEntity = null;
        if (item != null) {
            itemEntity = new ItemEntity();
            itemEntity.setItemId(item.getItemId());
            itemEntity.setTitle(item.getTitle());
            itemEntity.setDescription(item.getDescription());
            itemEntity.setEnclosure(item.getEnclosure());
            itemEntity.setGuid(item.getGuid());
            itemEntity.setChannelId(item.getChannelId());
            itemEntity.setLink(item.getLink());
            itemEntity.setPubDate(item.getPubDate());
            itemEntity.setRead(item.getRead());
            itemEntity.setFavorite(item.getFavorite());
        }

        return itemEntity;
    }

    public List<Item> transformEntityToItems(List<ItemEntity> itemEntities){
        List<Item> items = new ArrayList<>();
        for (ItemEntity itemEntity: itemEntities) {
            if (itemEntity != null){
                items.add(transform(itemEntity));
            }
        }
        return items;
    }

    public List<ItemEntity> transformItemsToEntity(List<Item> items){
        List<ItemEntity> itemEntities = new ArrayList<>();
        for (Item item: items) {
            if (items != null){
                itemEntities.add(transform(item));
            }
        }
        return itemEntities;
    }
}
