package com.example.myapp.myapp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp.myapp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by dzl on 2016/10/18.
 */

public class FlowLayout extends ViewGroup {

    private int horizotalSpacing = Utils.dp2px(getContext(), 6);
    private int verticalSpacing = Utils.dp2px(getContext(), 6);


    /**
     * 这个集合用于保存多行
     */
    private ArrayList<ArrayList<View>> allLines = new ArrayList<>();

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    /**
     * 测量容器的宽和高，并且所有的子View传入测量规格，子View自己完成自己的测量
     * 这个方法会被调用多次，调用多少次不确定
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        allLines.clear();// 这个方法会被调用多次，调用多少次不确定

        int containerMeasuredWidth = MeasureSpec.getSize(widthMeasureSpec);

        // 测量子View
        ArrayList<View> oneLine = null; // 这个集合用于保存一行
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            // 这里面创建出来的unspecifiedMeasureSpec值是0
            int unspecifiedMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);   // 创建一个未指定的测量规格
            child.measure(unspecifiedMeasureSpec, unspecifiedMeasureSpec);  // 相当于child.measure(0, 0);

            // 如果是第1个View或者子View的宽大于了可用的宽度，则需要使用一个新行来装
            if (i == 0 || child.getMeasuredWidth() > getUsableWidth(containerMeasuredWidth, oneLine, oneLine.size())) {
                oneLine = new ArrayList<>();
                allLines.add(oneLine);
            }
            oneLine.add(child);
        }


        // 测量容器的宽和高
        int lineNumber = allLines.size();

//        int allLinesHeight = getChildAt(0).getMeasuredHeight() * lineNumber;    // 所有行的高
        int allLinesHeight = getChildAt(0)!=null?getChildAt(0).getMeasuredHeight() * lineNumber:0;  // 所有行的高
        int verticalTotalPadding = getPaddingBottom() + getPaddingTop();        // 垂直方向总的adding
        int verticalTotalSpacing = verticalSpacing * (lineNumber - 1);          // 垂直方向总的spacing

        // 容器的高 = 所有行View的高 + 垂直方向总的adding + 垂直方向总的spacing
        int containerMeasuredHeight = allLinesHeight + verticalTotalPadding + verticalTotalSpacing;

        // 通过setMeasuredDemetion（）方法把测量出来的宽和高保存起来，保存之后就可以调用getMeasuredWidth获取测量之后的宽了
        setMeasuredDimension(containerMeasuredWidth, containerMeasuredHeight);
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec); // 这个默认实现压跟就没有做测量的事情，只是获取了一个默认值
    }

    /**
     * 获取可以宽
     *
     * @param containerMeasuredWidth
     * @param oneLine
     * @return
     */
    private int getUsableWidth(int containerMeasuredWidth, ArrayList<View> oneLine, int needSpacingCount) {
        int oneLineWidth = 0;
        for (View view : oneLine) {
            oneLineWidth += view.getMeasuredWidth();
        }

        int horizontalTotalPadding = getPaddingLeft() + getPaddingRight();    // 水平方向总的padding
        int horizontalTotalSpacing = horizotalSpacing * needSpacingCount; // 水平方向总的spacing

        // 可用宽度 = 容器的宽 - 一行View的宽 - 水平方向总的padding -水平方向总的spacing
        int usableWidth = containerMeasuredWidth - oneLineWidth - horizontalTotalPadding - horizontalTotalSpacing;
        return usableWidth;
    }

    /**
     * 对子View的位置进行按排（排版）
     * 这个方法会被调用多次，调用多少次不确定
     * 参数l, t, r, b是FlowLayout相对于容器的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int tempRight = 0;  // 用于保存一行中上一个View的right坐标
        int tempBottom = 0; // 用于保存上一行View中的bottom坐标

        // 遍历多行
        for (int rowIndex = 0; rowIndex < allLines.size(); rowIndex++) {
            ArrayList<View> oneLine = allLines.get(rowIndex);   // 取出一行

            // 计算一行中的每个View可以平均分到的宽度
            int totalUsableWidth = getUsableWidth(getMeasuredWidth(), oneLine, oneLine.size() - 1);
            int averageUsableWidth = totalUsableWidth / oneLine.size();

            // 遍历一行
            for (int columnIndex = 0; columnIndex < oneLine.size(); columnIndex++) {
                View child = oneLine.get(columnIndex);
                int childMeasuredWidth = child.getMeasuredWidth();
                int childMeasuredHeight = child.getMeasuredHeight();

                // 如果是一行中的第1个View，则left坐标排在paddingLeft的位置，否则的话排在上一列的right位置
                int childLeft = columnIndex == 0 ? getPaddingLeft() : tempRight + horizotalSpacing;

                // 如果是第一行的View，则top坐标排在paddingTop的位置，否则的话排在上一行View的bottom位置
                int childTop = rowIndex == 0 ? getPaddingTop() : tempBottom + verticalSpacing;

                int childRight = childLeft + childMeasuredWidth + averageUsableWidth;

                // 如果是一行中的最后一个veiw，则把这个View的right坐标排在容器的paddingRight的位置
                if (columnIndex == oneLine.size() - 1) {
                    childRight = getMeasuredWidth() - getPaddingRight();
                }

                int childBottom = childTop + childMeasuredHeight;
                child.layout(childLeft, childTop, childRight, childBottom); // 按排子View相对于容器（FlowLayout）中的位置

                tempRight = childRight; // 保存当前列的right位置，用于下一列的left位置

                // 因为我们手动把TextView的宽改变了，跟测量时的宽不一样了，文本居中的属性是在测量的时候完成的，
                // 要想居中属性重新计算，则重新调用测量即可
                int widthMeasureSpec = MeasureSpec.makeMeasureSpec(child.getWidth(), MeasureSpec.EXACTLY);
                int heightMeasureSpec = MeasureSpec.makeMeasureSpec(child.getHeight(), MeasureSpec.EXACTLY);
                child.measure(widthMeasureSpec, heightMeasureSpec);
            }

            tempBottom = oneLine.get(0).getBottom();    // 保存当前行的Bottom位置，用于下一行View的top
        }
    }

}
