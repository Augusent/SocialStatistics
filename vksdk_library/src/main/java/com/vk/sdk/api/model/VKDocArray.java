package com.vk.sdk.api.model;

import android.os.Parcel;

/**
 * Array of VKDoc
 * Created by alex_xpert on 29.01.14.
 */
public class VKDocArray extends VKApiArray<VKDoc> {
    @Override
    protected VKDoc createObject() {
        return new VKDoc();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
