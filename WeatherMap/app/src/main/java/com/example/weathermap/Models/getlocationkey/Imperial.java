package com.example.weathermap.Models.getlocationkey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Imperial implements Parcelable {

    @SerializedName("Value")
    @Expose
    private Integer value;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("UnitType")
    @Expose
    private Integer unitType;

    private Imperial(Parcel in) {
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readInt();
        }
        unit = in.readString();
        if (in.readByte() == 0) {
            unitType = null;
        } else {
            unitType = in.readInt();
        }
    }

    public static final Creator<Imperial> CREATOR = new Creator<Imperial>() {
        @Override
        public Imperial createFromParcel(Parcel in) {
            return new Imperial(in);
        }

        @Override
        public Imperial[] newArray(int size) {
            return new Imperial[size];
        }
    };

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (value == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(value);
        }
        dest.writeString(unit);
        if (unitType == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(unitType);
        }
    }
}
