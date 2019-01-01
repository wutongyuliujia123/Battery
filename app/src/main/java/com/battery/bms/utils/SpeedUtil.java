package com.battery.bms.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by liujia.json on 2019/1/1.
 */

public class SpeedUtil {



    public static  LocationManager mLocationManager;
    public static  Location getLastKnownLocation(Activity context) {
        mLocationManager = (LocationManager)context.getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            @SuppressLint("MissingPermission")
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

}
