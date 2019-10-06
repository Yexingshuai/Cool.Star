package com.example.myapp.myapp.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;

public class ExpandTextView extends AppCompatTextView {

    private String mText = "";

    /**
     * 最多展示的行数
     */
    final int maxLineCount = 6;

    /**
     * 省略文字
     */
    final String ellipsizeText = "...";

    /**
     * true：展开，false：收起
     */
    boolean mExpanded;

    /**
     * 状态回调
     */
    Callback mCallback;


    public ExpandTextView(Context context) {
        super(context);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //文字辅助工具
        StaticLayout sl = new StaticLayout(mText, getPaint(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight()
                , Layout.Alignment.ALIGN_CENTER, 1, 0, true);

        // 总计行数
        int lineCount = sl.getLineCount();

        if (lineCount > maxLineCount) {
            mCallback.onPrepare();
            if (mExpanded) {
                setText(mText);
                mCallback.onExpand();
            } else {
                lineCount = maxLineCount;

                // 省略文字的宽度
                float dotWidth = getPaint().measureText(ellipsizeText);

                // 找出第 showLineCount 行的文字
                int start = sl.getLineStart(lineCount - 1);
                int end = sl.getLineEnd(lineCount - 1);
                String lineText = mText.substring(start, end);

                // 将第 showLineCount 行最后的文字替换为 ellipsizeText
                int endIndex = 0;
                for (int i = lineText.length() - 1; i >= 0; i--) {
                    String str = lineText.substring(i, lineText.length());
                    // 找出文字宽度大于 ellipsizeText 的字符
                    if (getPaint().measureText(str) >= dotWidth) {
                        endIndex = i;
                        break;
                    }
                }

                // 新的第 showLineCount 的文字
                String newEndLineText = lineText.substring(0, endIndex) + ellipsizeText;
                // 最终显示的文字
                setText(mText.substring(0, start) + newEndLineText);

                mCallback.onCollapse();
            }
        } else {
            mCallback.onLoss(); //不需要
            setText(mText);
        }

        // 重新计算高度
        int lineHeight = 0;
        for (int i = 0; i < lineCount; i++) {
            Rect lineBound = new Rect();
            sl.getLineBounds(i, lineBound);
            lineHeight += lineBound.height();
        }
        lineHeight += getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(getMeasuredWidth(), lineHeight);
    }


    /**
     * 设置要显示的文字以及状态
     *
     * @param text
     * @param expanded true：展开，false：收起
     * @param callback
     */
    public void setText(String text, boolean expanded, Callback callback) {
        mText = text;
        mExpanded = expanded;
        mCallback = callback;

        // 设置要显示的文字，这一行必须要，否则 onMeasure 宽度测量不正确
        setText(text);
    }

    /**
     * 展开收起状态变化
     *
     * @param expanded
     */
    public void setChanged(boolean expanded) {
        mExpanded = expanded;
        requestLayout();
    }


    public interface Callback {
        /**
         * 展开状态
         */
        void onExpand();

        /**
         * 收起状态
         */
        void onCollapse();

        /**
         * 行数小于最小行数，不满足展开或者收起条件
         */
        void onLoss();

        /**
         * 满足条件
         */
        void onPrepare();
    }
}
