package com.nk.swipestemp.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.reflect.Field;

public class HorizontalSwipeToRefresh extends SwipeRefreshLayout {

    private static final String TAG = "HorizontalSwipeToRefresh";
    private float startX;
    private float startY;
    private boolean isHorizontalSwipe;
    private int touchSlop;

    private int mTouchSlop;
    private float mPrevX;
    // Indicate if we've already declined the move event
    private boolean mDeclined;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private boolean transparentAnimation = false;
    public void setTransparentAnimation(boolean transparentAnimation) {
        this.transparentAnimation = transparentAnimation;
    }
    public void setHideAnimation(boolean isHide) {
        try {
            Field circleViewField = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            circleViewField.setAccessible(true);
            ImageView circleView = (ImageView) circleViewField.get(this);
            if (circleView != null) {
                if (isHide) circleView.setAlpha(0f);
                else circleView.setAlpha(1f);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    //private View targetView; // Used in base class for determine the target

    public HorizontalSwipeToRefresh(@NonNull Context context) {
        super(context);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public HorizontalSwipeToRefresh(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private MotionEvent swapTouchEvent(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float swappedX = (ev.getY() / height) * width;
        float swappedY = (ev.getX() / width) * height;

        ev.setLocation(swappedX, swappedY);

        return ev;
    }

    // Used in base class for determine the target
    /*
    private void ensureTarget() {
        if (targetView == null) {
            if (getChildCount() > 1) {
                targetView = getChildAt(1);
            }
        }
    }
     */
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(ev));
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d(TAG, "onInterceptTouchEvent: Action: " + event.getAction());

        ////////////////////////////////////////////////////////////////////////////////////////////

        if (transparentAnimation) {
            this.setProgressBackgroundColorSchemeColor(getResources().getColor(android.R.color.transparent, this.getContext().getTheme()));
            this.setColorSchemeColors(getResources().getColor(android.R.color.transparent, this.getContext().getTheme()));
            this.setColorSchemeResources(android.R.color.transparent);
        }

        //ensureTarget(); //Used in base class for determine the target
        boolean intercepted = super.onInterceptTouchEvent(swapTouchEvent(event));
        swapTouchEvent(event); // Restore the event coordinates
        return intercepted;

        ////////////////////////////////////////////////////////////////////////////////////////////

        /*
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                isHorizontalSwipe = false;
                Log.d(TAG, "onInterceptTouchEvent: Vertical Top | Bottom");
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - startX;
                float dy = event.getY() - startY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    Log.d(TAG, "onInterceptTouchEvent: Horizontal Left | Right");
                    isHorizontalSwipe = true;
                }
                break;
        }

        boolean result = isHorizontalSwipe && super.onInterceptTouchEvent(event);
        Log.d(TAG, "onInterceptTouchEvent: Return: " + result + "\nisHorizontal: " + isHorizontalSwipe + "\nSuper: " + super.onInterceptTouchEvent(event));

        return result; //isHorizontalSwipe && super.onInterceptTouchEvent(event);
         */
//        return true;


//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mPrevX = MotionEvent.obtain(event).getX();
//                mDeclined = false; // New action
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                final float eventX = event.getX();
//                float xDiff = Math.abs(eventX - mPrevX);
//
//                if (mDeclined || xDiff > mTouchSlop) {
//                    mDeclined = true; // Memorize
//                    return false;
//                }
//        }
//
//        Log.d(TAG, "onInterceptTouchEvent: Return: " + super.onInterceptTouchEvent(event));
//
//        return super.onInterceptTouchEvent(event);


//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startX = event.getX();
//                startY = event.getY();
//                isHorizontalSwipe = false;
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                float dx = event.getX() - startX;
//                float dy = event.getY() - startY;
//                if (Math.abs(dx) > touchSlop && Math.abs(dx) > Math.abs(dy)) {
//                    isHorizontalSwipe = true;
//                }
//                break;
//        }
//
//        return isHorizontalSwipe ? super.onInterceptTouchEvent(event) : false;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
////        switch (ev.getAction()) {
////            case MotionEvent.ACTION_DOWN:
////                startX = ev.getX();
////                startY = ev.getY();
////                isHorizontalSwipe = false;
////                break;
////
////            case MotionEvent.ACTION_MOVE:
////                float dx = ev.getX() - startX;
////                float dy = ev.getY() - startY;
////                if (Math.abs(dx) > touchSlop && Math.abs(dx) > Math.abs(dy)) {
////                    isHorizontalSwipe = true;
////                }
////                break;
////        }
//
////        switch (ev.getAction()) {
////            case MotionEvent.ACTION_DOWN:
////                startX = ev.getX();
////                startY = ev.getY();
////                isHorizontalSwipe = false;
////                break;
////
////            case MotionEvent.ACTION_MOVE:
////                float dx = ev.getX() - startX;
////                float dy = ev.getY() - startY;
////                if (Math.abs(dx) > Math.abs(dy)) {
//////                    Log.d(TAG, "onInterceptTouchEvent: Horizontal Left");
////                    isHorizontalSwipe = true;
////                }
////                break;
////        }
////
////        boolean result = isHorizontalSwipe && super.onInterceptTouchEvent(ev);
////        Log.d(TAG, "onInterceptTouchEvent: Return: " + result + "\nisHorizontal: " + isHorizontalSwipe + "\nSuper: " + super.onInterceptTouchEvent(ev));
//
////        return isHorizontalSwipe && super.onInterceptTouchEvent(event);
//
//        return isHorizontalSwipe && super.onTouchEvent(ev);
//    }
}
