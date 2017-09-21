package com.smartfridge.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.smartfridge.R;

/**
 * Created by admin on 2017/9/20.
 */

public class HorizonVerticalViewPager extends MyViewPager {
    private boolean isVertical = false;
    private boolean isCanScroll = true;

    public HorizonVerticalViewPager(Context context) {
        super(context);
        init();
    }

    public HorizonVerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initIsVertical(attrs, 0);
        init();
    }

    public HorizonVerticalViewPager(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initIsVertical(attrs, defStyle);
        init();
    }

    private void initIsVertical(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyViewPager, defStyle, 0);
        isVertical = a.getBoolean(R.styleable.MyViewPager_isVertical, false);
        System.out.println("isVertical=>" + isVertical);
        a.recycle();
    }


    private void init() {
        if (isVertical) {
            // The majority of the magic happens here
            setPageTransformer(true, new VerticalPageTransformer());
            // The easiest way to get rid of the overscroll drawing that happens on the left and right
            setOverScrollMode(OVER_SCROLL_NEVER);
        }
    }

    private class VerticalPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View view, float position) {

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);

                //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);

        return ev;
    }

    /**
     * 设置其是否能滑动换页
     *
     * @param isCanScroll false 不能换页， true 可以滑动换页
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll == false)
            return isCanScroll;
        if (isVertical) {
            boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
            swapXY(ev); // return touch coordinates to original reference frame for any child views
            return intercepted;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll == false)
            return isCanScroll;

        if (isVertical) {
            return super.onTouchEvent(swapXY(ev));
        } else {
            return super.onTouchEvent(ev);
        }
    }
}
