package com.example.rss.data.database.mapper;

import com.example.rss.data.database.dto.CategoryDTO;
import com.example.rss.data.database.dto.ChannelDTO;
import com.example.rss.data.database.dto.FileDTO;
import com.example.rss.data.entity.CategoryEntity;
import com.example.rss.data.entity.ChannelEntity;
import com.example.rss.data.entity.FileEntity;

public class ChannelDatabaseMapper {

	public static ChannelDTO transform (ChannelEntity channelEntity){
		ChannelDTO channelDTO = null;

		if (channelEntity != null){
			channelDTO = new ChannelDTO();
			channelDTO.setChannelId(channelEntity.getChannelId());
			channelDTO.setTitle(channelEntity.getTitle());
			channelDTO.setDescription(channelEntity.getDescription());
			channelDTO.setCategoryId(channelEntity.getCategory());
			channelDTO.setCacheImage(channelEntity.getCacheImage());
			channelDTO.setDownloadFullText(channelEntity.getDownloadFullText());
			channelDTO.setLastBuild(channelEntity.getLastBuild());
		}
		return channelDTO;
	}

	public static ChannelEntity transform (ChannelDTO dto){
		ChannelEntity channelEntity = null;
		if (dto != null) {
			channelEntity = new ChannelEntity();
			channelEntity.setChannelId(dto.getChannelId());
			channelEntity.setTitle(dto.getTitle());
			channelEntity.setDescription(dto.getDescription());
			channelEntity.setCategory(dto.getCategoryId());
			channelEntity.setCacheImage(dto.getCacheImage());
			channelEntity.setDownloadFullText(dto.getDownloadFullText());
			channelEntity.setLastBuild(dto.getLastBuild());
		}
		return channel;
	}

	public static CategoryDTO transform (CategoryEntity categoryEntity){
		CategoryDTO dto = null;
		if (categoryEntity != null) {
			dto = new CategoryDTO();
			dto.setName(categoryEntity.getName());
			dto.setType(categoryEntity.getType());
		}

		return dto;
	}

	public static CategoryEntity transform (CategoryDTO dto){
		CategoryEntity categoryEntity = null;
		if (dto != null) {
			categoryEntity = new CategoryEntity();
			categoryEntity.setName(dto.getName());
			categoryEntity.setType(dto.getType());
		}

		return categoryEntity;
	}

	public static FileDTO transform (FileEntity fileEntity){
		FileDTO dto = null;
		if (fileEntity != null) {
			dto = new FileDTO();
			dto.setPath(fileEntity.getPath());
			dto.setDescription((fileEntity.getDescription()));
			dto.setExternal(fileEntity.getExternal());
			dto.setType(fileEntity.getType());
		}

		return dto;
	}

	public static FileEntity transform (FileDTO dto){
		FileEntity fileEntity = null;
		if (dto != null) {
			fileEntity = new FileEntity();
			fileEntity.setPath(dto.getPath());
			fileEntity.setDescription((dto.getDescription()));
			fileEntity.setExternal(dto.getExternal());
			fileEntity.setType(dto.getType());
		}
		return fileEntity;
	}
}
