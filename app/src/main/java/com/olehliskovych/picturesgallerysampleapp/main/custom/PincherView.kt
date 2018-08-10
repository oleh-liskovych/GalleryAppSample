

package com.olehliskovych.picturesgallerysampleapp.main.custom

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView

class PincherView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {

        private const val NONE = 0
        private const val DRAG = 1
        private const val ZOOM = 2
        private const val CLICK = 3
    }

    private var mMatrix: Matrix = Matrix()
    private var mode = NONE

    private var last = PointF()
    private var start = PointF()
    private var minScale = 1f
    private var maxScale = 10f
    private var m: FloatArray = FloatArray(9)

    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var saveScale = 1f
    protected var originWidth: Float = 0.toFloat()
    protected var originHeight: Float = 0.toFloat()
    private var oldMeasuredWidth: Int = 0
    private var oldMeasuredHeight: Int = 0

    private var mScaleDetector: ScaleGestureDetector = ScaleGestureDetector(context, ScaleListener())

    init{
        super.setClickable(true)
        imageMatrix = mMatrix
        scaleType = ImageView.ScaleType.MATRIX

        setOnTouchListener { v, event ->
            mScaleDetector.onTouchEvent(event)
            val curr = PointF(event.x, event.y)

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    last.set(curr)
                    start.set(last)
                    mode = DRAG
                }

                MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                    val deltaX = curr.x - last.x
                    val deltaY = curr.y - last.y
                    val fixTransX = getFixDragTrans(deltaX, viewWidth.toFloat(),
                            originWidth * saveScale)
                    val fixTransY = getFixDragTrans(deltaY, viewHeight.toFloat(),
                            originHeight * saveScale)
                    mMatrix.postTranslate(fixTransX, fixTransY)
                    fixTrans()
                    last.set(curr.x, curr.y)
                }

                MotionEvent.ACTION_UP -> {
                    mode = NONE
                    val xDiff = Math.abs(curr.x - start.x).toInt()
                    val yDiff = Math.abs(curr.y - start.y).toInt()
                    if (xDiff < CLICK && yDiff < CLICK)
                        performClick()
                }

                MotionEvent.ACTION_POINTER_UP -> mode = NONE
            }

            imageMatrix = mMatrix
            invalidate()
            true
        }
    }

    private fun fixTrans() {
        mMatrix.getValues(m)
        val transX = m[Matrix.MTRANS_X]
        val transY = m[Matrix.MTRANS_Y]

        val fixTransX = getFixTrans(transX, viewWidth.toFloat(), originWidth * saveScale)
        val fixTransY = getFixTrans(transY, viewHeight.toFloat(), originHeight * saveScale)

        if (fixTransX != 0f || fixTransY != 0f)
            mMatrix.postTranslate(fixTransX, fixTransY)
    }

    private fun getFixTrans(trans: Float, viewSize: Float, contentSize: Float): Float {
        val minTrans: Float
        val maxTrans: Float

        if (contentSize <= viewSize) {
            minTrans = 0f
            maxTrans = viewSize - contentSize
        } else {
            minTrans = viewSize - contentSize
            maxTrans = 0f
        }

        if (trans < minTrans)
            return -trans + minTrans
        return if (trans > maxTrans) -trans + maxTrans else 0f
    }

    private fun getFixDragTrans(delta: Float, viewSize: Float, contentSize: Float): Float {
        return if (contentSize <= viewSize) {
            0f
        } else delta
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight
                || viewWidth == 0 || viewHeight == 0)
            return
        oldMeasuredHeight = viewHeight
        oldMeasuredWidth = viewWidth

        if (saveScale == 1f) {
            val scale: Float

            val drawable = drawable
            if (drawable == null || drawable.intrinsicWidth == 0
                    || drawable.intrinsicHeight == 0)
                return
            val bmWidth = drawable.intrinsicWidth
            val bmHeight = drawable.intrinsicHeight

            val scaleX = viewWidth.toFloat() / bmWidth.toFloat()
            val scaleY = viewHeight.toFloat() / bmHeight.toFloat()
            scale = Math.min(scaleX, scaleY)
            mMatrix.setScale(scale, scale)

            var spareYSpace = viewHeight.toFloat() - scale * bmHeight.toFloat()
            var spareXSpace = viewWidth.toFloat() - scale * bmWidth.toFloat()
            spareYSpace /= 2.toFloat()
            spareXSpace /= 2.toFloat()

            mMatrix.postTranslate(spareXSpace, spareYSpace)

            originWidth = viewWidth - 2 * spareXSpace
            originHeight = viewHeight - 2 * spareYSpace
            imageMatrix = mMatrix
        }
        fixTrans()
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            mode = ZOOM
            return true
        }

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            var mScaleFactor = detector.scaleFactor
            val origScale = saveScale
            saveScale *= mScaleFactor
            if (saveScale > maxScale) {
                saveScale = maxScale
                mScaleFactor = maxScale / origScale
            } else if (saveScale < minScale) {
                saveScale = minScale
                mScaleFactor = minScale / origScale
            }

            if (originWidth * saveScale <= viewWidth || originHeight * saveScale <= viewHeight)
                mMatrix.postScale(mScaleFactor, mScaleFactor, (viewWidth / 2).toFloat(),
                        (viewHeight / 2).toFloat())
            else
                mMatrix.postScale(mScaleFactor, mScaleFactor,
                        detector.focusX, detector.focusY)

            fixTrans()
            return true
        }
    }
}