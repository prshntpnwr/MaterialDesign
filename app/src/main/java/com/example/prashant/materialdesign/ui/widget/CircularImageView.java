package com.example.prashant.materialdesign.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.prashant.materialdesign.util.ViewUtils;

/**
 * Created by prashant on 18/3/18.
 */

public class CircularImageView extends AppCompatImageView {

    public CircularImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOutlineProvider(ViewUtils.CIRCULAR_OUTLINE);
        setClipToOutline(true);
    }
}
