package com.example.rss.data.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {

    OkHttpClient okHttpClient;
    OkHttpClient eagerClient;

    Request request = null;

    public HttpClient(OkHttpClient okHttpClient, String url) {
        this(okHttpClient, makeRequestFromUrl(url));
    }

    public HttpClient(OkHttpClient okHttpClient, Request request) {
        this.request = request;
        eagerClient = okHttpClient.newBuilder()
                .readTimeout(500, TimeUnit.MILLISECONDS)
                .build();
    }

    static Request makeRequestFromUrl(String url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    public Observable<Response> run() {
        if (request != null){
            try (Response response = eagerClient.newCall(request).execute()) {
                return Observable.just(response);
            } catch (IOException e) {
                Observable.error(e);
            }
        }
        return Observable.empty();
    }
}
