package com.mobileapp.symbio.data;

import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: marko
 * Date: 15.06.13
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */


public class MenuItem {
    private int itemID;
    private int categoryID;
    private double price;
    private String name;
    GregorianCalendar date;

    public static int SOUP          = 1;
    public static int MAIN_MENU_1   = 2;
    public static int MAIN_MENU_2   = 3;
    public static int MAIN_MENU_3   = 4;
    public static int MAIN_INDIAN   = 5;
    public static int SALAD         = 6;
    public static int DESSERT_1     = 7;
    public static int DESSERT_2     = 8;

        public MenuItem(int itemID, int categoryID, double price, String name, String date) {
            this.itemID = itemID;
            this.categoryID = categoryID;
            this.price = price;
            this.name = name;
            dateToGregorianCalendar(date);
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    private void dateToGregorianCalendar(String date) {
        int year  = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day   = Integer.parseInt(date.substring(8,10));
        this.date = new GregorianCalendar(year, month, day);
    }
}
