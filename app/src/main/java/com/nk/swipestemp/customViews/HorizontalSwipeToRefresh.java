package com.nk.swipestemp.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HorizontalSwipeToRefresh extends SwipeRefreshLayout {

    private static final String TAG = "Test_code";
    private float startX;
    private float startY;
    private boolean isHorizontalSwipe;
    private int touchSlop;


    private int mTouchSlop;
    private float mPrevX;
    // Indicate if we've already declined the move event
    private boolean mDeclined;

    public HorizontalSwipeToRefresh(@NonNull Context context) {
        super(context);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public HorizontalSwipeToRefresh(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.d(TAG, "onInterceptTouchEvent: Action: " + event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                isHorizontalSwipe = false;
                break;

            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - startX;
                float dy = event.getY() - startY;
                if (Math.abs(dx) > Math.abs(dy)) {
//                    Log.d(TAG, "onInterceptTouchEvent: Horizontal Left");
                    isHorizontalSwipe = true;
                }
                break;
        }

        boolean result = isHorizontalSwipe && super.onInterceptTouchEvent(event);
        Log.d(TAG, "onInterceptTouchEvent: Return: " + result + "\nisHorizontal: " + isHorizontalSwipe + "\nSuper: " + super.onInterceptTouchEvent(event));

        return isHorizontalSwipe && super.onInterceptTouchEvent(event);
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
