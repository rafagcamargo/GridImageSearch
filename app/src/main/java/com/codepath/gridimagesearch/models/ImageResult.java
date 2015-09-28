package com.codepath.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageResult {

    private String fullUrl;
    private String thumbUrl;
    private String title;
    private int height;
    private int thumbnailHeight;
    private int thumbnailWidth;

    private ImageResult(JSONObject jsonObject) {
        try {
            this.fullUrl = jsonObject.getString("link");
            JSONObject image = jsonObject.getJSONObject("image");
            this.thumbUrl = image.getString("thumbnailLink");
            this.thumbnailHeight = image.getInt("thumbnailHeight");
            this.thumbnailWidth = image.getInt("thumbnailWidth");
            this.height = image.getInt("height");
            this.title = jsonObject.getString("htmlTitle");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJsonArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonObject = array.getJSONObject(i);
                results.add(new ImageResult(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
    }

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "ImageResult{" +
                "fullUrl='" + fullUrl + '\'' +
                ", thumbUrl='" + thumbUrl + '\'' +
                ", title='" + title + '\'' +
                ", thumbnailHeight=" + thumbnailHeight +
                ", thumbnailWidth=" + thumbnailWidth +
                '}';
    }
}
