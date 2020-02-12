package com.example.rss.presentation.exception;

import android.content.Context;
import android.util.Log;

import com.example.rss.R;
import com.example.rss.data.exception.DatabaseConnectionException;
import com.example.rss.data.exception.NetworkConnectionException;
import com.example.rss.domain.exception.XmlParseException;

public class ErrorMessageFactory {

	private ErrorMessageFactory() {
		//empty
	}

	public static String create(Context context, Exception exception) {
		String message = context.getString(R.string.exception_message_generic);

		if (exception instanceof NetworkConnectionException) {
			message = context.getString(R.string.exception_message_no_connection);
		} else if (exception instanceof XmlParseException) {
			message = context.getString(R.string.exception_parse_xml);
		} else if (exception instanceof ChannelNotFoundException) {
			message = context.getString(R.string.exception_channel_not_found);
		} else if (exception instanceof ChannelExistsException) {
				message = context.getString(R.string.exception_channel_exists);
		} else if (exception instanceof DatabaseConnectionException) {
			message = context.getString(R.string.exception_database_connect);
		} else if (exception instanceof ErrorDetailItemException) {
			message = context.getString(R.string.exception_detail_item);
		}else{
			Log.e("MyApp", exception.toString());
		}
		return message;
	}
}
