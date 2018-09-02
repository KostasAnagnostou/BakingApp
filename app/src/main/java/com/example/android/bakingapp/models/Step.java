package com.example.android.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable {
    public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {

        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int i) {
            return new Step[i];
        }
    };
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("shortDescription")
    @Expose
    private String mShortDescription;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("videoURL")
    @Expose
    private String mVideoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String mThumbnailURL;

    // Parcelling Implementation
    public Step(Parcel in) {
        this.mId = in.readInt();
        this.mShortDescription = in.readString();
        this.mDescription = in.readString();
        this.mVideoURL = in.readString();
        this.mThumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mShortDescription);
        parcel.writeString(mDescription);
        parcel.writeString(mVideoURL);
        parcel.writeString(mThumbnailURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.mShortDescription = shortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getVideoURL() {
        return mVideoURL;
    }

    public void setVideoURL(String videoURL) {
        this.mVideoURL = videoURL;
    }

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.mThumbnailURL = thumbnailURL;
    }
}
