package com.example.configurableui.universal;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Elements implements Parcelable {

    private String type;
    private String value;
    private String identifier;
    private String title;
    private String color;
    private Boolean dynamic;
    private Boolean isPercentageScale;
    private int minValue;
    private int maxValue;

    private HashMap<String, String> options = new HashMap<>(  );

    public Elements(HashMap<String, String> options) {
        this.options = options;
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

    protected Elements(Parcel in) {
        type = in.readString();
        value = in.readString();
        identifier = in.readString();
        title = in.readString();
        color = in.readString();
        byte tmpDynamic = in.readByte();
        dynamic = tmpDynamic == 0 ? null : tmpDynamic == 1;
        byte tmpIsPercentageScale = in.readByte();
        isPercentageScale = tmpIsPercentageScale == 0 ? null : tmpIsPercentageScale == 1;
        minValue = in.readInt();
        maxValue = in.readInt();
    }

    public static final Creator<Elements> CREATOR = new Creator<Elements>() {
        @Override
        public Elements createFromParcel(Parcel in) {
            return new Elements( in );
        }

        @Override
        public Elements[] newArray(int size) {
            return new Elements[size];
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Boolean getDynamic() {
        return dynamic;
    }

    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }

    public Boolean getPercentageScale() {
        return isPercentageScale;
    }

    public void setPercentageScale(Boolean percentageScale) {
        isPercentageScale = percentageScale;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
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
        dest.writeString( color );
        dest.writeByte( (byte) (dynamic == null ? 0 : dynamic ? 1 : 2) );
        dest.writeByte( (byte) (isPercentageScale == null ? 0 : isPercentageScale ? 1 : 2) );
        dest.writeInt( minValue );
        dest.writeInt( maxValue );
    }
}
