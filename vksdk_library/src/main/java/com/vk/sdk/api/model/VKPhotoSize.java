package com.vk.sdk.api.model;


import android.os.Parcel;

public class VKPhotoSize extends VKApiModel {
    public String src;
    public int width;
    public int height;
    public String type;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
