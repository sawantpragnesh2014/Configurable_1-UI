package com.example.configurableui.universal;

import android.os.Parcel;
import android.os.Parcelable;

public class Url implements Parcelable {

    private String type;
    private String value;
    private String identifier;
    private String title;

    public Url(String type, String value, String identifier, String title) {
        this.type = type;
        this.value = value;
        this.identifier = identifier;
        this.title = title;
    }

    protected Url(Parcel in) {
        type = in.readString();
        value = in.readString();
        identifier = in.readString();
        title = in.readString();
    }

    public static final Creator<Url> CREATOR = new Creator<Url>() {
        @Override
        public Url createFromParcel(Parcel in) {
            return new Url( in );
        }

        @Override
        public Url[] newArray(int size) {
            return new Url[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( type );
        dest.writeString( value );
        dest.writeString( identifier );
        dest.writeString( title );
    }
}
