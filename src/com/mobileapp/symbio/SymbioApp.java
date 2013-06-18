package com.mobileapp.symbio;

import android.app.Application;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created with IntelliJ IDEA.
 * User: marko
 * Date: 16.06.13
 * Time: 14:44
 * To change this template use File | Settings | File Templates.
 */
public class SymbioApp extends Application{

    private String username;
    private String password;
    private String url;
    private HttpClient httpClient;

    private static SymbioApp instance;

    public SymbioApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        httpClient = new DefaultHttpClient();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
