package com.example.stanislav.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class HorizontalRulerView extends LinearLayout {

    static final int COLOR = Color.DKGRAY;
    static final int HEIGHT = 2;
    static final int VERTICAL_MARGIN = 10;
    static final int HORIZONTAL_MARGIN = 5;
    static final int TOP_MARGIN = VERTICAL_MARGIN;
    static final int BOTTOM_MARGIN = VERTICAL_MARGIN;
    static final int LEFT_MARGIN = HORIZONTAL_MARGIN;
    static final int RIGHT_MARGIN = HORIZONTAL_MARGIN;

    public HorizontalRulerView(Context context) {
        this(context, null);
    }

    public HorizontalRulerView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public HorizontalRulerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
        View v = new View(context);
        v.setBackgroundColor(COLOR);
        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                HEIGHT
        );
        lp.topMargin = TOP_MARGIN;
        lp.bottomMargin = BOTTOM_MARGIN;
        lp.leftMargin = LEFT_MARGIN;
        lp.rightMargin = RIGHT_MARGIN;
        addView(v, lp);
    }

}
