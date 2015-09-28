package com.codepath.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResult {

    private String title;
    private String totalResults;
    private int count;
    private int startIndex;
    private ArrayList<ImageResult> imageResults;

    public SearchResult(JSONObject jsonObject) {
        try {
            JSONObject queries = jsonObject.getJSONObject("queries");

            JSONObject nextPage = queries.getJSONArray("nextPage").getJSONObject(0);
            title = nextPage.getString("title");
            totalResults = nextPage.getString("totalResults");
            count = nextPage.getInt("count");
            startIndex = nextPage.getInt("startIndex");

            JSONArray items = jsonObject.getJSONArray("items");
            imageResults = new ArrayList<>(ImageResult.fromJsonArray(items));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public int getCount() {
        return count;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public ArrayList<ImageResult> getImageResults() {
        return imageResults;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "title='" + title + '\'' +
                ", totalResults='" + totalResults + '\'' +
                ", count=" + count +
                ", startIndex=" + startIndex +
                ", imageResults=" + imageResults +
                '}';
    }
}
