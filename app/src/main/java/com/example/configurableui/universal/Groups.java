package com.example.configurableui.universal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Groups implements Parcelable {

    List<Group> groups = new ArrayList<>();

    public Groups() {

    }

    public List<Group> getGroups() {
        return groups;
    }


    protected Groups(Parcel in) {
        groups = in.createTypedArrayList( Group.CREATOR );
    }

    public static final Creator<Groups> CREATOR = new Creator<Groups>() {
        @Override
        public Groups createFromParcel(Parcel in) {
            return new Groups( in );
        }

        @Override
        public Groups[] newArray(int size) {
            return new Groups[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList( groups );
    }
}
