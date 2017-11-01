package com.tracker.lantimat.cartracker.utils;

import android.content.Context;
import android.widget.Toast;

import com.tracker.lantimat.cartracker.mapActivity.MapActivity;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

/**
 * Created by GabdrakhmanovII on 25.10.2017.
 */

public class MyOwnItemizedOverlay extends ItemizedIconOverlay<OverlayItem> {
    protected Context mContext;

    public MyOwnItemizedOverlay(final Context context, final List<OverlayItem> aList) {
        super(context, aList, new OnItemGestureListener<OverlayItem>() {
            @Override public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                return false;
            }
            @Override public boolean onItemLongPress(final int index, final OverlayItem item) {
                return false;
            }
        } );
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    @Override
    protected boolean onSingleTapUpHelper(final int index, final OverlayItem item, final MapView mapView) {
        /*AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(item.getStopTime());
        dialog.setMessage(item.getSnippet());
        dialog.show();*/


        //Toast.makeText(mContext, "Item " + Integer.parseInt(item.getUid()) + " has been tapped!", Toast.LENGTH_SHORT).show();
        try {
            ((MapActivity)mContext).mapPresenter.trackMarkerClick(Integer.parseInt(item.getUid()));
        } catch (Exception e) {

        }
        return true;
    }
}