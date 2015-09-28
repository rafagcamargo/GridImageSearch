package com.codepath.gridimagesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchFilter implements Parcelable {

    private String imageSize;
    private int imageSizePosition;
    private String imageColorType;
    private int imageColorTypePosition;
    private String imageType;
    private int imageTypePosition;
    private String resultSize;
    private int resultSizePosition;
    private String siteFilter;
    private String searchExpression;

    public SearchFilter() {
        this.imageSize = null;
        this.imageSizePosition = 0;
        this.imageColorType = null;
        this.imageColorTypePosition = 0;
        this.imageType = null;
        this.imageTypePosition = 0;
        this.resultSize = "8";
        this.resultSizePosition = 7;
        this.siteFilter = null;
        this.searchExpression = null;
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public int getImageSizePosition() {
        return imageSizePosition;
    }

    public void setImageSizePosition(int imageSizePosition) {
        this.imageSizePosition = imageSizePosition;
    }

    public String getImageColorType() {
        return imageColorType;
    }

    public void setImageColorType(String imageColorType) {
        this.imageColorType = imageColorType;
    }

    public int getImageColorTypePosition() {
        return imageColorTypePosition;
    }

    public void setImageColorTypePosition(int imageColorTypePosition) {
        this.imageColorTypePosition = imageColorTypePosition;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public int getImageTypePosition() {
        return imageTypePosition;
    }

    public void setImageTypePosition(int imageTypePosition) {
        this.imageTypePosition = imageTypePosition;
    }

    public String getResultSize() {
        return resultSize;
    }

    public void setResultSize(String resultSize) {
        this.resultSize = resultSize;
    }

    public int getResultSizePosition() {
        return resultSizePosition;
    }

    public void setResultSizePosition(int resultSizePosition) {
        this.resultSizePosition = resultSizePosition;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }

    public String getSearchExpression() {
        return searchExpression;
    }

    public void setSearchExpression(String searchExpression) {
        this.searchExpression = searchExpression;
    }

    @Override
    public String toString() {
        return "SearchFilter{" +
                "imageSize='" + imageSize + '\'' +
                ", imageColorType='" + imageColorType + '\'' +
                ", imageType='" + imageType + '\'' +
                ", resultSize='" + resultSize + '\'' +
                ", siteFilter='" + siteFilter + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageSize);
        dest.writeString(this.imageColorType);
        dest.writeString(this.imageType);
        dest.writeString(this.resultSize);
        dest.writeString(this.siteFilter);
    }

    protected SearchFilter(Parcel in) {
        this.imageSize = in.readString();
        this.imageColorType = in.readString();
        this.imageType = in.readString();
        this.resultSize = in.readString();
        this.siteFilter = in.readString();
    }

    public static final Parcelable.Creator<SearchFilter> CREATOR = new Parcelable.Creator<SearchFilter>() {
        public SearchFilter createFromParcel(Parcel source) {
            return new SearchFilter(source);
        }

        public SearchFilter[] newArray(int size) {
            return new SearchFilter[size];
        }
    };
}
