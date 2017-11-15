package com.tracker.lantimat.cartracker.reportActivity;

import android.net.Uri;

/**
 * Created by GabdrakhmanovII on 14.11.2017.
 */

public class UploadImage {
    private Uri uri;
    private boolean isLoaded;
    private int progress = 0;

    public UploadImage(Uri uri, boolean isLoaded) {
        this.uri = uri;
        this.isLoaded = isLoaded;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
