package com.blq.ssnb.snbutil.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import blq.ssnb.snbutil.SnbLog;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/4/15
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class ShotCanvasView extends View {

    private Paint mPaint;
    private Path mPath;
    private Path outPath;

    public ShotCanvasView(Context context) {
        this(context, null);
    }

    public ShotCanvasView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShotCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(0x87999999);
        mPath = new Path();
        outPath = new Path();
    }

    int w;
    int h;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        w = MeasureSpec.getSize(widthMeasureSpec);
        h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(w, h);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        outPath.reset();
        outPath.addRect(0, 0, w, h, Path.Direction.CW);

        mPath.reset();
        mPath.addRect(sx, sy, ex, ey, Path.Direction.CW);
        mPath.close();
        outPath.op(mPath, Path.Op.DIFFERENCE);
        canvas.drawPath(outPath, mPaint);
        SnbLog.e(">>>>>>onDraw:(" + sx + "," + sy + ");当前位置:(" + ex + "," + ey + ")");
    }

    private boolean isInShot = false;
    private float sx;
    private float sy;
    private float ex;
    private float ey;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isInShot) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    sx = event.getX();
                    sy = event.getY();
                    if(mOnActionListener != null){
                        mOnActionListener.onActionDown(sx,sy);
                    }
//                    updateInfo("起始位置:(" + sx + "," + sy + ")");
                    break;
                case MotionEvent.ACTION_MOVE:
                    ex = event.getX();
                    ey = event.getY();
                    if(mOnActionListener != null){
                        mOnActionListener.onActionMove(sx,sy,ex,ey);
                    }
                    invalidate();
                    SnbLog.e(">>>>>>截屏范围:(" + sx + "," + sy + ");当前位置:(" + ex + "," + ey + ")");
                    break;

                case MotionEvent.ACTION_UP:
                    ex = event.getX();
                    ey = event.getY();
                    if(mOnActionListener != null){
                        mOnActionListener.onActionUp(sx,sy,ex,ey);
                    }
                    closeShot();
                    break;
            }
            return true;
        }
        return false;
    }

    public void openShot() {
        setVisibility(VISIBLE);
        isInShot = true;
        sx = sy = ex = ey = 0;
    }

    public void closeShot() {
        isInShot = false;
        setVisibility(GONE);
    }

    private OnActionListener mOnActionListener;

    public void setOnActionListener(OnActionListener listener) {
        this.mOnActionListener = listener;
    }

    public interface OnActionListener {
        void onActionDown(float sx, float sy);

        void onActionMove(float sx, float sy, float ex, float ey);

        void onActionUp(float sx, float sy, float ex, float ey);

    }

}
