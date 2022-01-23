package com.example.configurableui.universal;

import android.os.Parcel;
import android.os.Parcelable;

public class Options implements Parcelable {

    private String QA;
    private String UAT;
    private String Mock;
    private String Manual;

    protected Options(Parcel in) {
        QA = in.readString();
        UAT = in.readString();
        Mock = in.readString();
        Manual = in.readString();
    }

    public static final Creator<Options> CREATOR = new Creator<Options>() {
        @Override
        public Options createFromParcel(Parcel in) {
            return new Options( in );
        }

        @Override
        public Options[] newArray(int size) {
            return new Options[size];
        }
    };

    public String getQA() {
        return QA;
    }

    public void setQA(String QA) {
        this.QA = QA;
    }

    public String getUAT() {
        return UAT;
    }

    public void setUAT(String UAT) {
        this.UAT = UAT;
    }

    public String getMock() {
        return Mock;
    }

    public void setMock(String mock) {
        this.Mock = mock;
    }

    public String getManual() {
        return Manual;
    }

    public void setManual(String manual) {
        this.Manual = manual;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString( QA );
        dest.writeString( UAT );
        dest.writeString( Mock );
        dest.writeString( Manual );
    }
}
