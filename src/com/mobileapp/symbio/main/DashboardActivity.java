package com.mobileapp.symbio.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.*;
import com.mobileapp.symbio.R;
import com.mobileapp.symbio.SymbioApp;
import com.mobileapp.symbio.data.MenuItem;
import com.mobileapp.symbio.server.ServerConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: marko
 * Date: 15.06.13
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */
public class DashboardActivity extends Activity {

    private List<MenuItem> items;
    public final class CustomComparator implements Comparator<MenuItem> {
        @Override
        public int compare(MenuItem o1, MenuItem o2) {
            int value = 0;

            if (o1.getDate().after(o2.getDate())) {
                value = 1;
            }
            if (o1.getDate().before(o2.getDate())) {
                value = -1;
            }

            return value;
        }
    }

    private List<LinearLayout> mLinearLayoutViews;
    private List<TextView> mTextViewWeekday;
    private List<RelativeLayout.LayoutParams> mLayoutParams;
    private ProgressDialog progressDialog;
    private List<Integer> mOrderId;
    RelativeLayout mLayout;
    private RotateAnimation mAnimation;

    public DashboardActivity() {
        items = new ArrayList<MenuItem>();
        mLinearLayoutViews = new ArrayList<LinearLayout>();
        mLayoutParams = new ArrayList<RelativeLayout.LayoutParams>();
        mTextViewWeekday = new ArrayList<TextView>();
        mOrderId = new ArrayList<Integer>();
    }

    private class DownLoadMenuItemsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            SymbioApp instance = (SymbioApp)getApplication();

            // Login first
            ServerConnection.tryToLogin(instance.getHttpClient(), instance.getUrl(), instance.getUsername(),
                    instance.getPassword());

            String jsonFile = ServerConnection.getHttpRequestContent(instance.getHttpClient(),
                    instance.getUrl()  + "/api/" + ServerConnection.COMMAND_GET_ALL_MENU_ITEMS
            );

            String order_ids = ServerConnection.getHttpRequestContent(instance.getHttpClient(),
                    instance.getUrl() + "/api/" + ServerConnection.COMMAND_MY_ORDERS);

            //String jsonFile = "[{\"menu_item\":{\"created_at\":\"2013-06-16T11:13:00+02:00\",\"date\":\"2013-06-10\",\"id\":1,\"meal_category_id\":\"1\",\"name\":\"Suppe Mo\",\"price\":\"2.0\",\"sort_order\":10,\"updated_at\":\"2013-06-16T11:13:00+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:13:04+02:00\",\"date\":\"2013-06-10\",\"id\":2,\"meal_category_id\":\"2\",\"name\":\"Essen Mo 1\",\"price\":\"6.0\",\"sort_order\":20,\"updated_at\":\"2013-06-16T11:13:17+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:13:09+02:00\",\"date\":\"2013-06-10\",\"id\":3,\"meal_category_id\":\"3\",\"name\":\"Essen Mo 2\",\"price\":\"6.0\",\"sort_order\":22,\"updated_at\":\"2013-06-16T11:13:25+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:13:23+02:00\",\"date\":\"2013-06-10\",\"id\":4,\"meal_category_id\":\"4\",\"name\":\"Essen Mo 3\",\"price\":\"6.0\",\"sort_order\":24,\"updated_at\":\"2013-06-16T11:13:23+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:13:30+02:00\",\"date\":\"2013-06-10\",\"id\":5,\"meal_category_id\":\"5\",\"name\":\"Indisch Mo\",\"price\":\"6.0\",\"sort_order\":30,\"updated_at\":\"2013-06-16T11:13:30+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:13:34+02:00\",\"date\":\"2013-06-10\",\"id\":6,\"meal_category_id\":\"6\",\"name\":\"Salat Mo\",\"price\":\"3.0\",\"sort_order\":40,\"updated_at\":\"2013-06-16T11:13:34+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:13:40+02:00\",\"date\":\"2013-06-10\",\"id\":7,\"meal_category_id\":\"7\",\"name\":\"Nachspeise Mo\",\"price\":\"2.0\",\"sort_order\":50,\"updated_at\":\"2013-06-16T11:13:40+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:13:49+02:00\",\"date\":\"2013-06-17\",\"id\":8,\"meal_category_id\":\"2\",\"name\":\"Essen Mo 2\",\"price\":\"6.0\",\"sort_order\":20,\"updated_at\":\"2013-06-16T11:13:49+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:13:55+02:00\",\"date\":\"2013-06-17\",\"id\":9,\"meal_category_id\":\"3\",\"name\":\"Essen Mo 1\",\"price\":\"6.0\",\"sort_order\":22,\"updated_at\":\"2013-06-16T11:13:55+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:14:04+02:00\",\"date\":\"2013-06-17\",\"id\":10,\"meal_category_id\":\"7\",\"name\":\"Nachspeise Mo 1\",\"price\":\"2.0\",\"sort_order\":50,\"updated_at\":\"2013-06-16T11:14:04+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:14:12+02:00\",\"date\":\"2013-06-19\",\"id\":11,\"meal_category_id\":\"1\",\"name\":\"Suppe Mi\",\"price\":\"2.0\",\"sort_order\":10,\"updated_at\":\"2013-06-16T11:14:12+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:14:15+02:00\",\"date\":\"2013-06-19\",\"id\":12,\"meal_category_id\":\"6\",\"name\":\"Salat Mi\",\"price\":\"3.0\",\"sort_order\":40,\"updated_at\":\"2013-06-16T11:14:15+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T11:14:19+02:00\",\"date\":\"2013-06-19\",\"id\":13,\"meal_category_id\":\"7\",\"name\":\"Nachspeise Mi\",\"price\":\"2.0\",\"sort_order\":50,\"updated_at\":\"2013-06-16T11:14:19+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:08+02:00\",\"date\":\"2013-06-14\",\"id\":14,\"meal_category_id\":\"1\",\"name\":\"Kohlsuppe\",\"price\":\"2.0\",\"sort_order\":10,\"updated_at\":\"2013-06-16T12:00:08+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:11+02:00\",\"date\":\"2013-06-14\",\"id\":15,\"meal_category_id\":\"2\",\"name\":\"Kohlhauptspeise\",\"price\":\"6.0\",\"sort_order\":20,\"updated_at\":\"2013-06-16T12:00:11+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:11+02:00\",\"date\":\"2013-06-14\",\"id\":16,\"meal_category_id\":\"3\",\"name\":\"Kohlreis\",\"price\":\"6.0\",\"sort_order\":22,\"updated_at\":\"2013-06-16T12:00:11+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:12+02:00\",\"date\":\"2013-06-14\",\"id\":17,\"meal_category_id\":\"4\",\"name\":\"Kohlrabi\",\"price\":\"6.0\",\"sort_order\":24,\"updated_at\":\"2013-06-16T12:00:12+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:12+02:00\",\"date\":\"2013-06-14\",\"id\":18,\"meal_category_id\":\"5\",\"name\":\"Indischer Kohl\",\"price\":\"6.0\",\"sort_order\":30,\"updated_at\":\"2013-06-16T12:00:12+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:12+02:00\",\"date\":\"2013-06-14\",\"id\":19,\"meal_category_id\":\"6\",\"name\":\"Kohlsalat\",\"price\":\"3.0\",\"sort_order\":40,\"updated_at\":\"2013-06-16T12:00:12+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:12+02:00\",\"date\":\"2013-06-14\",\"id\":20,\"meal_category_id\":\"7\",\"name\":\"Kohlkuchen\",\"price\":\"2.0\",\"sort_order\":50,\"updated_at\":\"2013-06-16T12:00:12+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:30+02:00\",\"date\":\"2013-06-12\",\"id\":21,\"meal_category_id\":\"1\",\"name\":\"Karrotensuppe\",\"price\":\"2.0\",\"sort_order\":10,\"updated_at\":\"2013-06-16T12:00:30+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:31+02:00\",\"date\":\"2013-06-12\",\"id\":22,\"meal_category_id\":\"2\",\"name\":\"Karottengericht\",\"price\":\"6.0\",\"sort_order\":20,\"updated_at\":\"2013-06-16T12:00:31+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:31+02:00\",\"date\":\"2013-06-12\",\"id\":23,\"meal_category_id\":\"3\",\"name\":\"Karottenreis\",\"price\":\"6.0\",\"sort_order\":22,\"updated_at\":\"2013-06-16T12:00:31+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:31+02:00\",\"date\":\"2013-06-12\",\"id\":24,\"meal_category_id\":\"5\",\"name\":\"Indische Karotten\",\"price\":\"6.0\",\"sort_order\":30,\"updated_at\":\"2013-06-16T12:00:31+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:31+02:00\",\"date\":\"2013-06-12\",\"id\":25,\"meal_category_id\":\"6\",\"name\":\"Indischer Karrotten Salat\",\"price\":\"3.0\",\"sort_order\":40,\"updated_at\":\"2013-06-16T12:00:31+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:32+02:00\",\"date\":\"2013-06-12\",\"id\":26,\"meal_category_id\":\"7\",\"name\":\"Karottenkuchen\",\"price\":\"2.0\",\"sort_order\":50,\"updated_at\":\"2013-06-16T12:00:32+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:49+02:00\",\"date\":\"2013-06-18\",\"id\":27,\"meal_category_id\":\"1\",\"name\":\"Hühnersupper von gester\",\"price\":\"2.0\",\"sort_order\":10,\"updated_at\":\"2013-06-16T12:00:49+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:00:56+02:00\",\"date\":\"2013-06-18\",\"id\":28,\"meal_category_id\":\"2\",\"name\":\"Gebackenes Hendl\",\"price\":\"6.0\",\"sort_order\":20,\"updated_at\":\"2013-06-16T12:00:56+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:01:01+02:00\",\"date\":\"2013-06-18\",\"id\":29,\"meal_category_id\":\"3\",\"name\":\"Hendl gefüllt mit Hendl\",\"price\":\"6.0\",\"sort_order\":22,\"updated_at\":\"2013-06-16T12:01:01+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:01:07+02:00\",\"date\":\"2013-06-18\",\"id\":30,\"meal_category_id\":\"5\",\"name\":\"Indisches Hendl\",\"price\":\"6.0\",\"sort_order\":30,\"updated_at\":\"2013-06-16T12:01:07+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:01:12+02:00\",\"date\":\"2013-06-18\",\"id\":31,\"meal_category_id\":\"6\",\"name\":\"Backhendlsalat\",\"price\":\"3.0\",\"sort_order\":40,\"updated_at\":\"2013-06-16T12:01:12+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:01:19+02:00\",\"date\":\"2013-06-18\",\"id\":32,\"meal_category_id\":\"7\",\"name\":\"Hendlkrallen\",\"price\":\"2.0\",\"sort_order\":50,\"updated_at\":\"2013-06-16T12:01:19+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:01:31+02:00\",\"date\":\"2013-06-19\",\"id\":33,\"meal_category_id\":\"2\",\"name\":\"Mittwochsessen\",\"price\":\"6.0\",\"sort_order\":20,\"updated_at\":\"2013-06-16T12:01:31+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:01:42+02:00\",\"date\":\"2013-06-19\",\"id\":34,\"meal_category_id\":\"5\",\"name\":\"Indischer Mittwoch\",\"price\":\"6.0\",\"sort_order\":30,\"updated_at\":\"2013-06-16T12:01:42+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:01:49+02:00\",\"date\":\"2013-06-20\",\"id\":35,\"meal_category_id\":\"1\",\"name\":\"Kuhsuppe\",\"price\":\"2.0\",\"sort_order\":10,\"updated_at\":\"2013-06-16T12:01:49+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:01:52+02:00\",\"date\":\"2013-06-20\",\"id\":36,\"meal_category_id\":\"2\",\"name\":\"Steak\",\"price\":\"6.0\",\"sort_order\":20,\"updated_at\":\"2013-06-16T12:01:52+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:01:55+02:00\",\"date\":\"2013-06-20\",\"id\":37,\"meal_category_id\":\"3\",\"name\":\"Hüftsteak\",\"price\":\"6.0\",\"sort_order\":22,\"updated_at\":\"2013-06-16T12:01:55+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:02:00+02:00\",\"date\":\"2013-06-20\",\"id\":38,\"meal_category_id\":\"4\",\"name\":\"T-Bone Steak\",\"price\":\"6.0\",\"sort_order\":24,\"updated_at\":\"2013-06-16T12:02:00+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:02:05+02:00\",\"date\":\"2013-06-20\",\"id\":39,\"meal_category_id\":\"5\",\"name\":\"Inder essen kein Ring\",\"price\":\"6.0\",\"sort_order\":30,\"updated_at\":\"2013-06-16T12:02:05+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:02:16+02:00\",\"date\":\"2013-06-20\",\"id\":40,\"meal_category_id\":\"6\",\"name\":\"Rinderzunge mit Rinderaugen garniert\",\"price\":\"3.0\",\"sort_order\":40,\"updated_at\":\"2013-06-16T12:02:16+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T12:02:19+02:00\",\"date\":\"2013-06-20\",\"id\":41,\"meal_category_id\":\"7\",\"name\":\"RInderpenis\",\"price\":\"2.0\",\"sort_order\":50,\"updated_at\":\"2013-06-16T12:02:19+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T14:38:45+02:00\",\"date\":\"2013-06-21\",\"id\":42,\"meal_category_id\":\"1\",\"name\":\"Laurasuppe\",\"price\":\"2.0\",\"sort_order\":10,\"updated_at\":\"2013-06-16T14:38:45+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T14:38:45+02:00\",\"date\":\"2013-06-21\",\"id\":43,\"meal_category_id\":\"2\",\"name\":\"LauraEssen1\",\"price\":\"6.0\",\"sort_order\":20,\"updated_at\":\"2013-06-16T14:38:45+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T14:38:45+02:00\",\"date\":\"2013-06-21\",\"id\":44,\"meal_category_id\":\"3\",\"name\":\"LauraEssen2\",\"price\":\"6.0\",\"sort_order\":22,\"updated_at\":\"2013-06-16T14:38:45+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T14:38:46+02:00\",\"date\":\"2013-06-21\",\"id\":45,\"meal_category_id\":\"4\",\"name\":\"LauraEssen3\",\"price\":\"6.0\",\"sort_order\":24,\"updated_at\":\"2013-06-16T14:38:46+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T14:38:46+02:00\",\"date\":\"2013-06-21\",\"id\":46,\"meal_category_id\":\"5\",\"name\":\"Indische Laura\",\"price\":\"6.0\",\"sort_order\":30,\"updated_at\":\"2013-06-16T14:38:46+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T14:38:46+02:00\",\"date\":\"2013-06-21\",\"id\":47,\"meal_category_id\":\"6\",\"name\":\"Laura Salad\",\"price\":\"3.0\",\"sort_order\":40,\"updated_at\":\"2013-06-16T14:38:46+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T14:38:46+02:00\",\"date\":\"2013-06-21\",\"id\":48,\"meal_category_id\":\"7\",\"name\":\"Laura \",\"price\":\"2.0\",\"sort_order\":50,\"updated_at\":\"2013-06-16T14:38:46+02:00\"}},{\"menu_item\":{\"created_at\":\"2013-06-16T14:38:46+02:00\",\"date\":\"2013-06-21\",\"id\":49,\"meal_category_id\":\"8\",\"name\":\"Laura\",\"price\":\"2.0\",\"sort_order\":52,\"updated_at\":\"2013-06-16T14:38:46+02:00\"}}]";

            System.out.println("json file");

            try {
                JSONArray jsonArray = new JSONArray(order_ids);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject jsonMenuItem = jsonObject.getJSONObject("order");
                    mOrderId.add(new Integer(jsonMenuItem.getInt("menu_item_id")));
                }

                jsonArray = new JSONArray(jsonFile);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject jsonMenuItem = jsonObject.getJSONObject("menu_item");

                    int itemID     = jsonMenuItem.getInt("id");
                    int categoryID = jsonMenuItem.getInt("meal_category_id");
                    double price   = jsonMenuItem.getDouble("price");
                    String name    = jsonMenuItem.getString("name");
                    String date    = jsonMenuItem.getString("date");
                    boolean isOrdered = false;

                    for(int j = 0; j < mOrderId.size(); j++)
                    {
                        if(mOrderId.get(j).intValue() == itemID)
                        {
                            isOrdered = true;
                            break;
                        }
                    }

                    items.add(new MenuItem(itemID, categoryID, price, name, date, isOrdered));
                }

                Collections.sort(items, new CustomComparator());

            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.cancel();
            GregorianCalendar currentDate = new GregorianCalendar();
            int currentId = 1;

            for (int i = 0; i < items.size(); i++) {
                if (i == 0 || items.get(i-1).getDate().compareTo(items.get(i).getDate()) != 0) {
                    mTextViewWeekday.add((TextView)getLayoutInflater().inflate(R.layout.dashboard_text_view_date, null));
                    mTextViewWeekday.get(mTextViewWeekday.size() - 1).setId(currentId);
                    mTextViewWeekday.get(mTextViewWeekday.size() - 1).setText(getWeekdayFromDate(items.get(i).getDate()));
                    mLayoutParams.add(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                    if(i != 0 && currentId != 1)
                        mLayoutParams.get(mLayoutParams.size() - 1).addRule(RelativeLayout.BELOW, currentId - 1);
                    else
                        mLayoutParams.get(mLayoutParams.size() - 1).addRule(RelativeLayout.ALIGN_PARENT_TOP);
                    mTextViewWeekday.get(mTextViewWeekday.size() - 1).setLayoutParams(mLayoutParams.get(mLayoutParams.size() - 1));

                    if(currentDate.compareTo(items.get(i).getDate()) > 0)
                        mTextViewWeekday.get(mTextViewWeekday.size() - 1).setBackgroundColor(Color.GRAY);

                    mLayout.addView(mTextViewWeekday.get(mTextViewWeekday.size() - 1));
                    currentId++;
                }

                mLinearLayoutViews.add((LinearLayout)getLayoutInflater().inflate(R.layout.dashboard_text_view_menu_item, null));
                TextView textView = (TextView)mLinearLayoutViews.get(mLinearLayoutViews.size() - 1).findViewById(R.id.dashboardMenuItemTextView);
                mLinearLayoutViews.get(mLinearLayoutViews.size() - 1).setTag(R.id.tag_menuitem, items.get(i).getItemID());
                mLinearLayoutViews.get(mLinearLayoutViews.size() - 1).setTag(R.id.tag_mlayout_position, mLinearLayoutViews.size() - 1);
                ImageView imageView = (ImageView)mLinearLayoutViews.get(mLinearLayoutViews.size() - 1).findViewById(R.id.dashboardMenuItemImageView);
                mLinearLayoutViews.get(mLinearLayoutViews.size() - 1).setId(currentId);

                if(currentDate.compareTo(items.get(i).getDate()) > 0) {
                    mLinearLayoutViews.get(mLinearLayoutViews.size() - 1).setBackgroundColor(Color.GRAY);
                    textView.setTextColor(Color.WHITE);
                }
                else {
                    mLinearLayoutViews.get(mLinearLayoutViews.size() - 1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            view.setClickable(false);
                            LinearLayout linearView = (LinearLayout) view;
                            String text = linearView.getTag(R.id.tag_menuitem).toString();
                            ImageView image = (ImageView) view.findViewById(R.id.dashboardMenuItemImageView);
                            image.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
                            image.setAnimation(mAnimation);
                            ArrayList<String> passing = new ArrayList<String>();
                            passing.add(text);
                            passing.add(linearView.getTag(R.id.tag_mlayout_position).toString());
                            new OrderItemTask().execute(passing);
                        }
                    });
                }


                textView.setText(items.get(i).getName());
                if(items.get(i).isOrdered()) {
                    imageView.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
                }
                mLayoutParams.add(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                mLayoutParams.get(mLayoutParams.size() - 1).addRule(RelativeLayout.BELOW, currentId - 1);
                mLinearLayoutViews.get(mLinearLayoutViews.size() - 1).setLayoutParams(mLayoutParams.get(mLayoutParams.size() - 1));

                mLayout.addView(mLinearLayoutViews.get(mLinearLayoutViews.size() - 1));
                currentId++;
            }


        }

        private String getWeekdayFromDate(GregorianCalendar date) {
            String value = null;
            int day = date.get(Calendar.DAY_OF_WEEK);

            switch (day) {
                case 2:
                    value = "Montag";
                    break;
                case 3:
                    value = "Dienstag";
                    break;
                case 4:
                    value = "Mittwoch";
                    break;
                case 5:
                    value = "Donnerstag";
                    break;
                case 6:
                    value = "Freitag";
                    break;
            }

            value += ", " + date.get(Calendar.DAY_OF_MONTH) + "." + (date.get(Calendar.MONTH) + 1) + "." + date.get(Calendar.YEAR);

            return value;
        }
    }

    private class OrderItemTask extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {

        private boolean status;
        private ImageView statusImage;
        LinearLayout currentLayout;

        protected ArrayList<String> doInBackground(ArrayList<String>... passing) {

            ArrayList<String> passed = passing[0];

            currentLayout = mLinearLayoutViews.get(Integer.parseInt(passed.get(1)));

            statusImage = (ImageView)currentLayout.findViewById(R.id.dashboardMenuItemImageView);

            SymbioApp instance = (SymbioApp)getApplication();

            status = ServerConnection.orderItem(instance.getHttpClient(), instance.getUrl(),
                                    Integer.parseInt(passed.get(0)));

            return null;
        }

        protected void onPostExecute(ArrayList<String> result) {

            if(status)
            {
                Toast.makeText(getApplicationContext(), "Menü bestellt.", Toast.LENGTH_SHORT).show();
                statusImage.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_online));
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Menü abbestellt.", Toast.LENGTH_SHORT).show();
                statusImage.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_invisible));
            }

            statusImage.setAnimation(null);

            currentLayout.setClickable(true);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Downloading Menu Items...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mLayout = (RelativeLayout)findViewById(R.id.dashboardLayout);

        mAnimation = new RotateAnimation(0f, 350f, 15f, 15f);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setDuration(700);

        new DownLoadMenuItemsTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_logout:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_info:
                intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}