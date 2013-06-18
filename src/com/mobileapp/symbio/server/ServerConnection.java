package com.mobileapp.symbio.server;

import android.os.Bundle;
import android.util.Log;
import com.mobileapp.symbio.SymbioApp;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marko
 * Date: 16.06.13
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class ServerConnection {

    public static final String COMMAND_GET_ALL_MENU_ITEMS = "show";
    public static final String COMMAND_MY_ORDERS          = "my_orders";
    public static final String COMMAND_LOGIN              = "users/sign_in";

    public static String getHttpRequestContent(HttpClient client, String url) {
        StringBuilder builder = new StringBuilder();
        HttpGet httpGet = new HttpGet(url);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));

                String line = null;

                int counter = 0;

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    Log.v("ServerConnection", "read new line number " + counter);
                    counter++;
                }

            } else {
                Log.e(ServerConnection.class.toString(),
                        "Failed to download file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String returnString = builder.toString();

        return returnString;
    }

    public static void tryToLogin(HttpClient httpClient, String url, String username, String password)
    {
        // Create a new HttpClient and Post Header
        HttpPost httppost = new HttpPost(url + "/" + COMMAND_LOGIN);
        HttpResponse response;
        HttpEntity entity;
        StringBuilder builder = new StringBuilder();
        try {
            // Add your data

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("user[email]", username));
            nameValuePairs.add(new BasicNameValuePair("user[password]", password));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            response = httpClient.execute(httppost);

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if (statusCode == 200) {
                entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));

                String line = null;

                int counter = 0;

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    Log.v("ServerConnection", "read new line number " + counter);
                    counter++;
                }

                String returnString = builder.toString();

                System.out.println(returnString);

            } else {
                Log.e(ServerConnection.class.toString(),
                        "Failed to download file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
