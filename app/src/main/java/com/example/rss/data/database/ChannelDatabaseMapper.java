package com.example.rss.data.database;

import com.example.rss.domain.Channel;

public class ChannelDatabaseMapper {

	public static ChannelDTO transform (Channel channel){
		ChannelDTO channelDTO = null;

		if (channel != null){
			channelDTO = new ChannelDTO();
			channelDTO.setUrl(channel.getUrl());
			channelDTO.setCategory(channel.getCategory());
			channelDTO.setCacheImage(channel.getCacheImage());
			channelDTO.setDownloadFull(channel.getDownloadFull());
		}
		return channelDTO;
	}


}
