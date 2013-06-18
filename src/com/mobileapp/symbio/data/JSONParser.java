package com.mobileapp.symbio.data;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: marko
 * Date: 15.06.13
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class JSONParser {

    private List<MenuItem> items;

    JSONParser(BufferedInputStream input)
    {
        items = new ArrayList<MenuItem>();
    }

    public List<MenuItem> getItems() {
        return items;
    }
}
