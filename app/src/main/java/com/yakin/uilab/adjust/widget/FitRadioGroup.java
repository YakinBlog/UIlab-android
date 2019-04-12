package com.yakin.uilab.adjust.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

public class FitRadioGroup extends RadioGroup {

    public FitRadioGroup(Context context) {
        super(context);
    }

    public FitRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int x = 0;
        int y = 0;
        int row = 1;

        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                MarginLayoutParams childMargin = (MarginLayoutParams) child.getLayoutParams();
                int childTotalWidth = child.getMeasuredWidth() + childMargin.leftMargin + childMargin.rightMargin;
                int childTotalHeight = child.getMeasuredHeight() + childMargin.topMargin + childMargin.bottomMargin;

                if(x + childTotalWidth > widthSize - paddingLeft - paddingRight) {
                    x = childTotalWidth;
                    row ++;
                    y = row * childTotalHeight;
                } else {
                    x += childTotalWidth;
                    y = row * childTotalHeight;
                }
            }
        }

        setMeasuredDimension(widthSize, y + paddingTop + paddingBottom);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int maxWidth = r - l;
        int x = 0;int y = 0;int row = 1;

        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                MarginLayoutParams childMargin = (MarginLayoutParams) child.getLayoutParams();
                int childTotalWidth = child.getMeasuredWidth() + childMargin.leftMargin + childMargin.rightMargin;
                int childTotalHeight = child.getMeasuredHeight() + childMargin.topMargin + childMargin.bottomMargin;

                if(x + childTotalWidth > maxWidth) {
                    x = childTotalWidth + getPaddingLeft(); // 新行都要留出左填充
                    row ++;
                    y = row * childTotalHeight;
                } else {
                    x += childTotalWidth;
                    y = row * childTotalHeight;
                }
                if(index == 0) { // 第一行需要留出左上填充
                    x += getPaddingLeft();
                    y += getPaddingTop();
                }
                child.layout(x - childTotalWidth, y - childTotalHeight, x, y);
            }
        }
    }
}
