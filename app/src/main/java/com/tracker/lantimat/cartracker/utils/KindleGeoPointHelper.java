package com.tracker.lantimat.cartracker.utils;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

/**
 * Created by GabdrakhmanovII on 03.11.2017.
 */

public class KindleGeoPointHelper {
    public static GeoPoint fromIGeoPointToRoverGeoPoint(final IGeoPoint iGeoPoint) {
        return new GeoPoint(iGeoPoint.getLatitude(), iGeoPoint.getLongitude());
    }
}
