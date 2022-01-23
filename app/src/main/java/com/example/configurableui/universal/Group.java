package com.example.configurableui.universal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Group implements Parcelable {

    private String groupName;
    private String seperatorColor;

    List<Elements> elements = new ArrayList<>();

    protected Group(Parcel in) {
        groupName = in.readString();
        seperatorColor = in.readString();
        elements = in.createTypedArrayList( Elements.CREATOR );
    }

    public static final Creator<Group> CREATOR = new Creator<Group>() {
        @Override
        public Group createFromParcel(Parcel in) {
            return new Group( in );
        }

        @Override
        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    public Group() {

    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSeperatorColor() {
        return seperatorColor;
    }

    public void setSeperatorColor(String seperatorColor) {
        this.seperatorColor = seperatorColor;
    }

    public List<Elements> getElements() {
        return elements;
    }

    public void setElements(List<Elements> elements) {
        this.elements = elements;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( groupName );
        dest.writeString( seperatorColor );
        dest.writeTypedList( elements );
    }
}
