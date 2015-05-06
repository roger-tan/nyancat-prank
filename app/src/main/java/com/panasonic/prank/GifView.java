package com.panasonic.prank;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;

import java.io.InputStream;


/**
 * TODO: document your custom view class.
 */
public class GifView extends WebView {

    public GifView(Context context, String url) {
        super(context);
        loadUrl(url);
    }
}
