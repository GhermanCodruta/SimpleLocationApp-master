package com.sample.foo.simplelocationapp;

/**
 * Created by Latitude on 04-Sep-16.
 */
public class CoordsPair {
    private double mLatitude;
    private double mLongitude;

    public CoordsPair(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }
    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
}
