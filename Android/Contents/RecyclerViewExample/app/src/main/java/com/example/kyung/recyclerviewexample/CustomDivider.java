package com.example.kyung.recyclerviewexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by Kyung on 2017-11-20.
 */

public class CustomDivider extends RecyclerView.ItemDecoration {
    public Drawable mDivider;
    private final int verticalSpaceHeight;

    public CustomDivider(Context context, int verticalSpaceHeight){
        mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    /**
     * item의 영역(공백을 추가 시킴)을 늘릴 수 있다.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // 마지막 아이템이 아닌 경우, 공백 추가
        if(parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() -1 ){
//            outRect.set(left, top, right, bottom);
            outRect.bottom = verticalSpaceHeight; // 아래쪽 공백 추가
            outRect.top = 10; // 위쪽 공백 추가
        }
    }

    /**
     *  아이템이 그려지기 전에 먼저 그린다.
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    /**
     *  아이템 위에 덮어서 그린다.
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // parent.getPaddingLeft() : recyclerView의 padding값을 불러온다.
        // parent.getWidth : recyclerView의 width값을 불러온다.
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for(int i=0 ; i<childCount ; i++){
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            // child.getBottom : child까지의 높이, params.bootmMargin : RecyclerView의 item의 최상위 parent에서 결정한 margin값
            int top = child.getBottom() + params.bottomMargin + verticalSpaceHeight;
            // mDivider.getIntrinsicHeight : line.divider.xml에서 정의한 높이값
            int bottom = top + mDivider.getIntrinsicHeight();

            // 밑줄의 사각형 bound(굵기 및 길이)를 결정 (left~right : 길이, top~bottom : 높이)
            mDivider.setBounds(left, top,right,bottom);
            // 밑줄을 그린다.
            mDivider.draw(c);
        }
    }
}
