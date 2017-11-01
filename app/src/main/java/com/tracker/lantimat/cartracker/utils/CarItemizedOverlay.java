package com.tracker.lantimat.cartracker.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.tracker.lantimat.cartracker.R;
import com.tracker.lantimat.cartracker.mapActivity.MapActivity;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.List;

/**
 * Created by GabdrakhmanovII on 25.10.2017.
 */

public class CarItemizedOverlay extends ItemizedIconOverlay<OverlayItem> {
    protected Context mContext;
    OverlayItem lasItem;
    List<OverlayItem> aList;

    public CarItemizedOverlay(final Context context, final List<OverlayItem> aList) {
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
        this.aList = aList;
    }

    @Override
    protected boolean onSingleTapUpHelper(final int index, final OverlayItem item, final MapView mapView) {
        /*AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(item.getStopTime());
        dialog.setMessage(item.getSnippet());
        dialog.show();*/

        //Drawable onSelectIcon = mContext.getResources().getDrawable(R.drawable.ic_apple_keyboard_control_grey600_36dp);
        Drawable icon = mContext.getResources().getDrawable(R.drawable.car);

        Drawable onSelectIcon = mContext.getResources().getDrawable(R.drawable.car_red);

        for (OverlayItem oIt: aList
             ) {
            oIt.setMarker(icon);
        }

        Toast.makeText(mContext, "Item " + index + " has been tapped!", Toast.LENGTH_SHORT).show();
        ((MapActivity)mContext).mapPresenter.carMarkerClick(index);



        item.setMarker(onSelectIcon);
        lasItem = item;
        mapView.invalidate();
        return true;
    }
}