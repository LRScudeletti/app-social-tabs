package br.com.lrssoftwares.socialtabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPagerClass extends ViewPager {

    private boolean tabLiberada = true;

    public CustomViewPagerClass(Context context) {
        super(context);
    }

    public CustomViewPagerClass(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.tabLiberada && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.tabLiberada && super.onInterceptTouchEvent(event);
    }

    public void setTabLiberada(boolean tabLiberada) {
        this.tabLiberada = tabLiberada;
    }
}