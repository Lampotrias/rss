package com.example.rss.data.database.mapper;

import com.example.rss.data.database.dto.ChannelDTO;
import com.example.rss.domain.Channel;

public class ChannelDatabaseMapper {

	public static ChannelDTO transform (Channel channel){
		ChannelDTO channelDTO = null;

		if (channel != null){
			channelDTO = new ChannelDTO();
			channelDTO.setChannelId(channel.getChannelId());
			channelDTO.setTitle(channel.getTitle());
			channelDTO.setDescription(channel.getDescription());
			channelDTO.setCategoryId(channel.getCategoryId());
			channelDTO.setCacheImage(channel.getCacheImage());
			channelDTO.setDownloadFullText(channel.getDownloadFullText());
			channelDTO.setLastBuild(channel.getLastBuild());
		}
		return channelDTO;
	}

	public static Channel transform (ChannelDTO dto){
		Channel channel = null;
		if (dto != null) {
			channel = new Channel();
			channel.setChannelId(dto.getChannelId());
			channel.setTitle(dto.getTitle());
			channel.setDescription(dto.getDescription());
			channel.setCategoryId(dto.getCategoryId());
			channel.setCacheImage(dto.getCacheImage());
			channel.setDownloadFullText(dto.getDownloadFullText());
			channel.setLastBuild(dto.getLastBuild());
		}
		return channel;
	}


}
