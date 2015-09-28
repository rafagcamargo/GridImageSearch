package com.codepath.gridimagesearch.helper;

import android.content.Context;
import android.content.res.Resources;

import com.codepath.gridimagesearch.R;

import static android.text.TextUtils.isEmpty;

public class SearchUrlBuilder {

    private String url;
    private String key;
    private String engineId;

    private String searchExpression;
    private String searchType; //image
    private String numberOfResults; //between 1 and 10
    private String imageSize; //huge, icon, large, medium, small, xlarge, xxlarge
    private String imageColorType; //"color", "gray", "mono"
    private String imageType; //clipart, face, lineart, news, photo
    private String siteSearch; //espn.com


    public static SearchUrlBuilder with(Context context) {
        Resources resources = context.getResources();

        String googleUrl = resources.getString(R.string.google_url);
        String googleKey = resources.getString(R.string.google_key);
        String googleSearchEngineId = resources.getString(R.string.google_custom_search_engine_id);

        return new SearchUrlBuilder()
                .url(googleUrl)
                .key(googleKey)
                .engineId(googleSearchEngineId)
                .searchType("image");
    }

    private SearchUrlBuilder() {
    }

    public SearchUrlBuilder url(String url) {
        this.url = url;
        return this;
    }

    public SearchUrlBuilder key(String key) {
        this.key = key;
        return this;
    }

    public SearchUrlBuilder engineId(String engineId) {
        this.engineId = engineId;
        return this;
    }

    public SearchUrlBuilder searchExpression(String searchExpression) {
        this.searchExpression = searchExpression;
        return this;
    }

    public SearchUrlBuilder searchType(String searchType) {
        this.searchType = searchType;
        return this;
    }

    public SearchUrlBuilder numberOfResults(String numberOfResults) {
        this.numberOfResults = numberOfResults;
        return this;
    }

    public SearchUrlBuilder imageSize(String imageSize) {
        this.imageSize = imageSize;
        return this;
    }

    public SearchUrlBuilder imageColorType(String imageColorType) {
        this.imageColorType = imageColorType;
        return this;
    }

    public SearchUrlBuilder imageType(String imageType) {
        this.imageType = imageType;
        return this;
    }

    public SearchUrlBuilder siteSearch(String siteSearch) {
        this.siteSearch = siteSearch;
        return this;
    }

    public String build() {
        url += "?";
        url += appendParam("key", key);
        url += appendParam("cx", engineId);
        url += appendParam("q", searchExpression);
        url += appendParam("searchType", searchType);
        url += appendParam("num", numberOfResults);
        url += appendParam("imgSize", normalizeParameters(imageSize));
        url += appendParam("imgColorType", normalizeParameters(imageColorType));
        url += appendParam("imgType", normalizeParameters(imageType));
        url += appendParam("siteSearch", siteSearch);

        return url;
    }

    private String normalizeParameters(String value) {
        if (!isEmpty(value)) {
            return value.toLowerCase();
        }
        return value;
    }

    private String appendParam(String key, String value) {
        if (!isEmpty(value)) {
            String appended = url.endsWith("?") ? "" : "&";
            appended += key + "=" + value;
            return appended;
        }

        return "";
    }

}
