package com.byelex.newsparser.Parser;

import java.util.HashMap;
import java.util.Map;

public class ListOfUrls {
    private Map<String, String> listOfUrls;

    public ListOfUrls() {
        listOfUrls = new HashMap<String, String>();
    }
    public void addUrlToListOfUrls(String url, String eventName){
        listOfUrls.put(eventName, url);
    }
    public void clearListOfUrls(){
        listOfUrls.clear();
    }

    public Map<String, String> getListOfUrls() {
        return listOfUrls;
    }

    public void setListOfUrls(Map<String, String> listOfUrls) {
        this.listOfUrls = listOfUrls;
    }
}
