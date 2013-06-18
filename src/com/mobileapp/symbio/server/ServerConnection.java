package com.mobileapp.symbio.server;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: marko
 * Date: 16.06.13
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class ServerConnection {

    public static final String COMMAND_GET_ALL_MENU_ITEMS = "show";

    public static String getHttpRequestContent(String url) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
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

}
