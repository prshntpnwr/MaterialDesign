package com.example.prashant.materialdesign.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.prashant.materialdesign.R;
import com.example.prashant.materialdesign.util.AnimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prashant on 2/4/18.
 */

public class ElasticDragDismissFrameLayout extends FrameLayout {

    // configurable attribs
    private float dragDismissDistance = Float.MAX_VALUE;
    private float dragDismissFraction = -1f;
    private float dragDismissScale = 1f;
    private boolean shouldScale = false;
    private float dragElasticity = 0.8f;

    // state
    private float totalDrag;
    private boolean draggingDown = false;
    private boolean draggingUp = false;
    private int mLastActionEvent;

    private List<ElasticDragDismissCallback> callbacks;

    public ElasticDragDismissFrameLayout(Context context) {
        this(context, null, 0, 0);
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attrs,
                                         int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ElasticDragDismissFrameLayout(Context context, AttributeSet attrs,
                                         int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ElasticDragDismissFrameLayout, 0, 0);

        if (a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragDismissDistance)) {
            dragDismissDistance = a.getDimensionPixelSize(R.styleable
                    .ElasticDragDismissFrameLayout_dragDismissDistance, 0);
        } else if (a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragDismissFraction)) {
            dragDismissFraction = a.getFloat(R.styleable
                    .ElasticDragDismissFrameLayout_dragDismissFraction, dragDismissFraction);
        }
        if (a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragDismissScale)) {
            dragDismissScale = a.getFloat(R.styleable
                    .ElasticDragDismissFrameLayout_dragDismissScale, dragDismissScale);
            shouldScale = dragDismissScale != 1f;
        }
        if (a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragElasticity)) {
            dragElasticity = a.getFloat(R.styleable.ElasticDragDismissFrameLayout_dragElasticity,
                    dragElasticity);
        }
        a.recycle();
    }

    public static abstract class ElasticDragDismissCallback {

        /**
         * Called for each drag event.
         *
         * @param elasticOffset       Indicating the drag offset with elasticity applied i.e. may
         *                            exceed 1.
         * @param elasticOffsetPixels The elastically scaled drag distance in pixels.
         * @param rawOffset           Value from [0, 1] indicating the raw drag offset i.e.
         *                            without elasticity applied. A value of 1 indicates that the
         *                            dismiss distance has been reached.
         * @param rawOffsetPixels     The raw distance the user has dragged
         */
        void onDrag(float elasticOffset, float elasticOffsetPixels,
                    float rawOffset, float rawOffsetPixels) { }

        /**
         * Called when dragging is released and has exceeded the threshold dismiss distance.
         */
        void onDragDismissed() { }

    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & View.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // if we're in a drag gesture and the user reverses up the we should take those events
        if (draggingDown && dy > 0 || draggingUp && dy < 0) {
            dragScale(dy);
            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        dragScale(dyUnconsumed);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
        mLastActionEvent = ev.getAction();
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void onStopNestedScroll(View child) {
        if (Math.abs(totalDrag) >= dragDismissDistance) {
            dispatchDismissCallback();
        } else { // settle back to natural position
            if (mLastActionEvent == MotionEvent.ACTION_DOWN) {
                // this is a 'defensive cleanup for new gestures',
                // don't animate here
                // see also https://github.com/nickbutcher/plaid/issues/185
                setTranslationY(0f);
                setScaleX(1f);
                setScaleY(1f);
            } else {
                animate()
                        .translationY(0f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(200L)
                        .setInterpolator(AnimUtils.getFastOutSlowInInterpolator(getContext()))
                        .setListener(null)
                        .start();
            }
            totalDrag = 0;
            draggingDown = draggingUp = false;
            dispatchDragCallback(0f, 0f, 0f, 0f);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (dragDismissFraction > 0f) {
            dragDismissDistance = h * dragDismissFraction;
        }
    }

    public void addListener(ElasticDragDismissCallback listener) {
        if (callbacks == null) {
            callbacks = new ArrayList<>();
        }
        callbacks.add(listener);
    }

    public void removeListener(ElasticDragDismissCallback listener) {
        if (callbacks != null && callbacks.size() > 0) {
            callbacks.remove(listener);
        }
    }

    private void dragScale(int scroll) {
        if (scroll == 0) return;

        totalDrag += scroll;

        // track the direction & set the pivot point for scaling
        // don't double track i.e. if start dragging down and then reverse, keep tracking as
        // dragging down until they reach the 'natural' position
        if (scroll < 0 && !draggingUp && !draggingDown) {
            draggingDown = true;
            if (shouldScale) setPivotY(getHeight());
        } else if (scroll > 0 && !draggingDown && !draggingUp) {
            draggingUp = true;
            if (shouldScale) setPivotY(0f);
        }
        // how far have we dragged relative to the distance to perform a dismiss
        // (0–1 where 1 = dismiss distance). Decreasing logarithmically as we approach the limit
        float dragFraction = (float) Math.log10(1 + (Math.abs(totalDrag) / dragDismissDistance));

        // calculate the desired translation given the drag fraction
        float dragTo = dragFraction * dragDismissDistance * dragElasticity;

        if (draggingUp) {
            // as we use the absolute magnitude when calculating the drag fraction, need to
            // re-apply the drag direction
            dragTo *= -1;
        }
        setTranslationY(dragTo);

        if (shouldScale) {
            final float scale = 1 - ((1 - dragDismissScale) * dragFraction);
            setScaleX(scale);
            setScaleY(scale);
        }

        // if we've reversed direction and gone past the settle point then clear the flags to
        // allow the list to get the scroll events & reset any transforms
        if ((draggingDown && totalDrag >= 0)
                || (draggingUp && totalDrag <= 0)) {
            totalDrag = dragTo = dragFraction = 0;
            draggingDown = draggingUp = false;
            setTranslationY(0f);
            setScaleX(1f);
            setScaleY(1f);
        }
        dispatchDragCallback(dragFraction, dragTo,
                Math.min(1f, Math.abs(totalDrag) / dragDismissDistance), totalDrag);
    }

    private void dispatchDragCallback(float elasticOffset, float elasticOffsetPixels,
                                      float rawOffset, float rawOffsetPixels) {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (ElasticDragDismissCallback callback : callbacks) {
                callback.onDrag(elasticOffset, elasticOffsetPixels,
                        rawOffset, rawOffsetPixels);
            }
        }
    }

    private void dispatchDismissCallback() {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (ElasticDragDismissCallback callback : callbacks) {
                callback.onDragDismissed();
            }
        }
    }

    /**
     * {@link Activity#finishAfterTransition() finishes} the activity when drag dismissed.
     */
    public static class ElasticCallListener extends ElasticDragDismissCallback {

        private final Activity activity;

        public ElasticCallListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onDrag(float elasticOffset, float elasticOffsetPixels,
                           float rawOffset, float rawOffsetPixels) {}

        public void onDragDismissed() {
            activity.finishAfterTransition();
        }
    }
}
