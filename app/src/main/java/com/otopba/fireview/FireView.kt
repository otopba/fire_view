package com.otopba.fireview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.createBitmap
import java.util.*
import kotlin.math.max
import kotlin.math.min

class FireView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private lateinit var temp: Array<IntArray>
    private val paint = Paint()
    private lateinit var bitmap: Bitmap
    private val random = Random()
    private val scale = 5

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val scaledW = w / scale
        val scaledH = h / scale

        temp = Array(scaledH) { IntArray(scaledW) }

        for (x in 0 until scaledW) {
            temp[scaledH - 1][x] = firePalette.size - 1
        }

        bitmap = createBitmap(scaledW, scaledH)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (y in 0 until temp.size - 1) {
            for (x in temp[y].indices) {
                val dx = random.nextInt(3) - 1
                val dy = random.nextInt(6)
                val dt = random.nextInt(2)

                val x1 = min(temp[y].size - 1, max(0, x + dx))
                val y1 = min(temp.size - 1, y + dy)

                temp[y][x] = max(0, temp[y1][x1] - dt)
            }
        }


        for (y in temp.indices) {
            for (x in temp[y].indices) {
                val color = firePalette[temp[y][x]]
                paint.color = color
                bitmap.setPixel(x, y, color)
            }
        }

        canvas.scale(scale.toFloat(), scale.toFloat())

        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        invalidate()
    }

    private companion object {
        private val firePalette = intArrayOf(
            -0xf8f8f9,
            -0xe0f8f9,
            -0xd0f0f9,
            -0xb8f0f9,
            -0xa8e8f9,
            -0x98e0f9,
            -0x88e0f9,
            -0x70d8f9,
            -0x60d0f9,
            -0x50c0f9,
            -0x40b8f9,
            -0x38b8f9,
            -0x20b0f9,
            -0x20a8f9,
            -0x20a8f9,
            -0x28a0f9,
            -0x28a0f9,
            -0x2898f1,
            -0x3090f1,
            -0x3088f1,
            -0x3080f1,
            -0x3078e9,
            -0x3878e9,
            -0x3870e9,
            -0x3868e1,
            -0x4060e1,
            -0x4060e1,
            -0x4058d9,
            -0x4058d9,
            -0x4050d1,
            -0x4850d1,
            -0x4848d1,
            -0x4848c9,
            -0x303091,
            -0x202061,
            -0x101039,
            -0x1,
        )
    }
}