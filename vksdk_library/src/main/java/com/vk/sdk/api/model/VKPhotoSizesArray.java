package com.vk.sdk.api.model;

import android.os.Parcel;

public class VKPhotoSizesArray extends VKApiArray<VKPhotoSize> {
    @Override
    protected VKPhotoSize createObject() {
        return new VKPhotoSize();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
