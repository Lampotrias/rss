package com.example.rss.data.database.mapper;

import com.example.rss.data.database.dto.CategoryDTO;
import com.example.rss.data.database.dto.ChannelDTO;
import com.example.rss.data.database.dto.FavoritesDTO;
import com.example.rss.data.database.dto.FileDTO;
import com.example.rss.data.database.dto.ItemDTO;
import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FavoriteEntity;
import com.example.rss.data.entity.FileEntity;
import com.example.rss.data.entity.ItemEntity;

import java.util.ArrayList;
import java.util.List;

public class ChannelDatabaseMapper {

	public static ChannelDTO transform (ChannelEntity channelEntity){
		ChannelDTO channelDTO = null;

		if (channelEntity != null){
			channelDTO = new ChannelDTO();
			channelDTO.setChannelId(channelEntity.getId());
			channelDTO.setTitle(channelEntity.getTitle());
			channelDTO.setDescription(channelEntity.getDescription());
			channelDTO.setCategoryId(channelEntity.getCategoryId());
			channelDTO.setFileId(channelEntity.getFileId());
			channelDTO.setLink(channelEntity.getLink());
			channelDTO.setSourceLink(channelEntity.getSourceLink());
			channelDTO.setCacheImage(channelEntity.getCacheImage());
			channelDTO.setDownloadFullText(channelEntity.getDownloadFullText());
			channelDTO.setOnlyWifi(channelEntity.getOnlyWifi());
			channelDTO.setLastBuild(channelEntity.getLastBuildDate());
			channelDTO.setNextSyncDate(channelEntity.getNextSyncDate());
		}
		return channelDTO;
	}

	public static ChannelEntity transform(ChannelDTO channelDTO){
		ChannelEntity channelEntity = null;
		if (channelDTO != null) {
			channelEntity = new ChannelEntity();
			channelEntity.setChannelId(channelDTO.getChannelId());
			channelEntity.setTitle(channelDTO.getTitle());
			channelEntity.setFileId(channelDTO.getFileId());
			channelEntity.setDescription(channelDTO.getDescription());
			channelEntity.setCategoryId(channelDTO.getCategoryId());
			channelEntity.setLink(channelDTO.getLink());
			channelEntity.setSourceLink(channelDTO.getSourceLink());
			channelEntity.setCacheImage(channelDTO.getCacheImage());
			channelEntity.setDownloadFullText(channelDTO.getDownloadFullText());
			channelEntity.setOnlyWifi(channelDTO.getOnlyWifi());
			channelEntity.setLastBuildDate(channelDTO.getLastBuild());
			channelEntity.setNextSyncDate(channelDTO.getNextSyncDate());
		}
		return channelEntity;
	}

	public static List<ChannelEntity> transformChannels (List<ChannelDTO> channelDTOS) {
		List<ChannelEntity> channelEntities = new ArrayList<>();

		for (ChannelDTO channelDTO : channelDTOS) {
			if (channelDTO != null) {
				channelEntities.add(transform(channelDTO));
			}
		}
		return channelEntities;
	}

	public static CategoryDTO transform (CategoryEntity categoryEntity){
		CategoryDTO categoryDTO = null;
		if (categoryEntity != null) {
			categoryDTO = new CategoryDTO();
			categoryDTO.setCategoryId(categoryEntity.getId());
			categoryDTO.setName(categoryEntity.getName());
			categoryDTO.setType(categoryEntity.getType());
		}

		return categoryDTO;
	}

	public static CategoryEntity transform (CategoryDTO categoryDTO){
		CategoryEntity categoryEntity = null;
		if (categoryDTO != null) {
			categoryEntity = new CategoryEntity();
			categoryEntity.setCategoryId(categoryDTO.getCategoryId());
			categoryEntity.setName(categoryDTO.getName());
			categoryEntity.setType(categoryDTO.getType());
		}

		return categoryEntity;
	}


	public static List<CategoryEntity> transformCategories(List<CategoryDTO> categoryDTOS){
		List<CategoryEntity> categoryEntities = new ArrayList<>();
		for (CategoryDTO categoryDTO: categoryDTOS) {
			if (categoryDTO != null){
				categoryEntities.add(transform(categoryDTO));
			}
		}
		return categoryEntities;
	}

	public static FileDTO transform (FileEntity fileEntity){
		FileDTO fileDTO = null;
		if (fileEntity != null) {
			fileDTO = new FileDTO();
			fileDTO.setFileId(fileEntity.getFileId());
			fileDTO.setPath(fileEntity.getPath());
			fileDTO.setDescription((fileEntity.getDescription()));
			fileDTO.setExternal(fileEntity.getExternal());
			fileDTO.setType(fileEntity.getType());
		}

		return fileDTO;
	}

	public static FileEntity transform (FileDTO fileDTO){
		FileEntity fileEntity = null;
		if (fileDTO != null) {
			fileEntity = new FileEntity();
			fileEntity.setFileId(fileDTO.getFileId());
			fileEntity.setPath(fileDTO.getPath());
			fileEntity.setDescription((fileDTO.getDescription()));
			fileEntity.setExternal(fileDTO.getExternal());
			fileEntity.setType(fileDTO.getType());
		}
		return fileEntity;
	}

	public static ItemDTO transform (ItemEntity itemEntity){
		ItemDTO itemDTO = null;
		if (itemEntity != null) {
			itemDTO = new ItemDTO();
			itemDTO.setItemId(itemEntity.getItemId());
			itemDTO.setTitle(itemEntity.getTitle());
			itemDTO.setDescription(itemEntity.getDescription());
			itemDTO.setEnclosure(itemEntity.getEnclosure());
			itemDTO.setGuid(itemEntity.getGuid());
			itemDTO.setLink(itemEntity.getLink());
			itemDTO.setChannelId(itemEntity.getChannelId());
			itemDTO.setPubDate(itemEntity.getPubDate());
			itemDTO.setRead(itemEntity.getRead());
			itemDTO.setFavorite(itemEntity.getFavorite());
		}

		return itemDTO;
	}

	public static ItemEntity transform (ItemDTO itemDTO){
		ItemEntity itemEntity = null;
		if (itemDTO != null) {
			itemEntity = new ItemEntity();
			itemEntity.setItemId(itemDTO.getItemId());
			itemEntity.setTitle(itemDTO.getTitle());
			itemEntity.setDescription(itemDTO.getDescription());
			itemEntity.setEnclosure(itemDTO.getEnclosure());
			itemEntity.setChannelId(itemDTO.getChannelId());
			itemEntity.setGuid(itemDTO.getGuid());
			itemEntity.setLink(itemDTO.getLink());
			itemEntity.setPubDate(itemDTO.getPubDate());
			itemEntity.setRead(itemDTO.getRead());
			itemEntity.setFavorite(itemDTO.getFavorite());
		}

		return itemEntity;
	}

	public static List<ItemEntity> transformItemsDtoToEntity(List<ItemDTO> itemDTOS){
		List<ItemEntity> itemEntities = new ArrayList<>();
		for (ItemDTO categoryDTO: itemDTOS) {
			if (categoryDTO != null){
				itemEntities.add(transform(categoryDTO));
			}
		}
		return itemEntities;
	}

	public static List<ItemDTO> transformItems(List<ItemEntity> itemEntities){
		List<ItemDTO> itemDTOS = new ArrayList<>();
		for (ItemEntity itemEntity: itemEntities) {
			if (itemEntity != null){
				itemDTOS.add(transform(itemEntity));
			}
		}
		return itemDTOS;
	}

	public static FavoriteEntity transform (FavoritesDTO favoritesDTO){
		FavoriteEntity favoriteEntity = null;
		if (favoritesDTO != null) {
			favoriteEntity = new FavoriteEntity();
			favoriteEntity.setItemId(favoritesDTO.getItemId());
			favoriteEntity.setCategoryId(favoritesDTO.getCategoryId());
			favoriteEntity.setFavId(favoritesDTO.getFavId());
		}
		return favoriteEntity;
	}

	public static FavoritesDTO transform (FavoriteEntity favoriteEntity){
		FavoritesDTO favoritesDTO = null;
		if (favoriteEntity != null) {
			favoritesDTO = new FavoritesDTO();
			favoritesDTO.setItemId(favoriteEntity.getItemId());
			favoritesDTO.setCategoryId(favoriteEntity.getCategoryId());
			favoritesDTO.setFavId(favoriteEntity.getFavId());
		}
		return favoritesDTO;
	}
}
