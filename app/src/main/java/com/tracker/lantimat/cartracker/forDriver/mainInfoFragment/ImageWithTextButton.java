package com.tracker.lantimat.cartracker.forDriver.mainInfoFragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tracker.lantimat.cartracker.R;

/**
 * Created by GabdrakhmanovII on 23.03.2018.
 */

public class ImageWithTextButton extends LinearLayout {
    LinearLayout layout = null;
    ImageView imageView = null;
    TextView textView = null;
    Context mContext = null;

    public ImageWithTextButton(Context context) {
        super(context);
    }

    public ImageWithTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageWithTextButton);

        Drawable drawable = a.getDrawable(R.styleable.ImageWithTextButton_image);
        String text = a.getString(R.styleable.ImageWithTextButton_text);
        int color = a.getColor(R.styleable.ImageWithTextButton_tint, ContextCompat.getColor(getContext(), R.color.md_white_1000));
        text = text == null ? "" : text;

        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(service);

        layout = (LinearLayout) li.inflate(R.layout.image_with_text, this, true);

        imageView = (ImageView) layout.findViewById(R.id.imageView);
        textView = (TextView) layout.findViewById(R.id.tvName);

        imageView.setImageDrawable(drawable);
        textView.setText(text);

        textView.setTextColor(color);
        imageView.setColorFilter(color);

        a.recycle();

    }
}
