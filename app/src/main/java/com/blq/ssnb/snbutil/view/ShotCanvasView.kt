package com.blq.ssnb.snbutil.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import blq.ssnb.snbutil.SnbLog.e

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/4/15
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 用来截图用
 * ================================================
</pre> *
 */
class ShotCanvasView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val mPaint: Paint
    private val mPath: Path
    private val outPath: Path
    var w = 0
    var h = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        w = MeasureSpec.getSize(widthMeasureSpec)
        h = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        outPath.reset()
        outPath.addRect(0f, 0f, w.toFloat(), h.toFloat(), Path.Direction.CW)
        mPath.reset()
        mPath.addRect(sx, sy, ex, ey, Path.Direction.CW)
        mPath.close()
        outPath.op(mPath, Path.Op.DIFFERENCE)
        canvas.drawPath(outPath, mPaint)
        e(">>>>>>onDraw:($sx,$sy);当前位置:($ex,$ey)")
    }

    private var isInShot = false
    private var sx = 0f
    private var sy = 0f
    private var ex = 0f
    private var ey = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isInShot) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    sx = event.x
                    sy = event.y
                    if (mOnActionListener != null) {
                        mOnActionListener!!.onActionDown(sx, sy)
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    ex = event.x
                    ey = event.y
                    if (mOnActionListener != null) {
                        mOnActionListener!!.onActionMove(sx, sy, ex, ey)
                    }
                    invalidate()
                    e(">>>>>>截屏范围:($sx,$sy);当前位置:($ex,$ey)")
                }
                MotionEvent.ACTION_UP -> {
                    ex = event.x
                    ey = event.y
                    if (mOnActionListener != null) {
                        mOnActionListener!!.onActionUp(sx, sy, ex, ey)
                    }
                    closeShot()
                }
            }
            return true
        }
        return false
    }

    fun openShot() {
        visibility = VISIBLE
        isInShot = true
        ey = 0f
        ex = ey
        sy = ex
        sx = sy
    }

    fun closeShot() {
        isInShot = false
        visibility = GONE
    }

    private var mOnActionListener: OnActionListener? = null
    fun setOnActionListener(listener: OnActionListener?) {
        mOnActionListener = listener
    }

    interface OnActionListener {
        fun onActionDown(sx: Float, sy: Float)
        fun onActionMove(sx: Float, sy: Float, ex: Float, ey: Float)
        fun onActionUp(sx: Float, sy: Float, ex: Float, ey: Float)
    }

    init {
        mPaint = Paint()
        mPaint.color = -0x78666667
        mPath = Path()
        outPath = Path()
    }
}