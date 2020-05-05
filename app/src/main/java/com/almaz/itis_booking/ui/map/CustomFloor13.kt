package com.almaz.itis_booking.ui.map

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.almaz.itis_booking.R

class CustomFloor13 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val floorScheme = Bitmap.createScaledBitmap(
        BitmapFactory.decodeResource(resources, R.drawable.floor_13),
        1000,
        1500,
        true
    )

    private var mWidth = 0
    private var mHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val cx = (mWidth - floorScheme.width) / 2F
        val cy = (mHeight - floorScheme.height) / 2F
        canvas?.drawBitmap(floorScheme, cx, cy, paint)
    }
}