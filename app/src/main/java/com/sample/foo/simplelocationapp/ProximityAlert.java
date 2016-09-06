package com.sample.foo.simplelocationapp;

/**
 * Created by Latitude on 04-Sep-16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProximityAlert extends BroadcastReceiver {

    public static final String EVENT_ID_INTENT_EXTRA = "EventIDIntentExtraKey";

    @Override
    public void onReceive(Context context, Intent intent) {
        long eventID = intent.getLongExtra(EVENT_ID_INTENT_EXTRA, -1);
        Log.v("","Proximity Alert Intent Received, eventID = "+eventID);
    }

}
