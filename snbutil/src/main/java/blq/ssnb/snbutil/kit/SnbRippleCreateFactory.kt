package blq.ssnb.snbutil.kit

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/2/23
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 添加描述
 * ================================================
</pre> *
 */
object SnbRippleCreateFactory {
    /**
     * [.noMask]
     */
    @JvmStatic
    fun noMask(context: Context, @ColorRes colorRes: Int): Drawable? {
        return noMask(context.resources.getColor(colorRes))
    }

    /**
     * [.noMask]
     */
    @JvmStatic
    fun noMask(@ColorInt color: Int): Drawable? {
        return noMask(ColorStateList.valueOf(color))
    }

    /**
     * 没有设置边界的水波纹drawable
     *
     * @param colorStateList colorStateList
     */
    @JvmStatic
    fun noMask(colorStateList: ColorStateList): Drawable? {
        return backgroundAndMask(colorStateList, null, null)
    }
    /**
     * 设置 有边界的水波纹,边界大小为mask的大小
     *
     * @param rippleColor 水波纹颜色
     * @param mask        边界对象
     */
    /**
     * [.mask]
     */
    @JvmStatic
    @JvmOverloads
    fun mask(@ColorInt rippleColor: Int, mask: Drawable? = ColorDrawable(0x7f000000)): Drawable? {
        return backgroundAndMask(ColorStateList.valueOf(rippleColor), null, mask)
    }

    /**
     * 设置背景，且水波纹在背景图片中显示
     *
     * @param colorStateList
     * @param background
     * @return
     */
    @JvmStatic
    fun backgroundMask(colorStateList: ColorStateList, background: Drawable?): Drawable? {
        return backgroundAndMask(colorStateList, background, null)
    }

    /**
     * 设置背景 和 边界 的水波纹效果 (背景一直显示，只有触发水波纹的时候会显示背景drawable)
     * 如果mask 为null 效果和 [.backgroundMask]一样
     * 如果background 为null 效果和 [.mask] 一样
     * 如果两个都未设置，效果为 不设边界的水波纹效果
     *
     * @param colorStateList
     * @param background
     * @param mask
     * @return
     */
    @JvmStatic
    fun backgroundAndMask(colorStateList: ColorStateList, background: Drawable?, mask: Drawable?): Drawable? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Builder(colorStateList)
                    .setContent(background)
                    .setMask(mask)
                    .build()
        } else {
            val builder = SnbDrawableSelector.Builder()
            if (background != null) {
                builder.normal(background)
            } else {
                builder.normal(ColorDrawable())
            }
            if (mask != null) {
                builder.pressed(mask)
            } else {
                builder.pressed(ColorDrawable(colorStateList.defaultColor))
            }
            builder.build().drawable
        }
    }

    class Builder(private val mColorStateList: ColorStateList) {
        var mContent: Drawable? = null
        private var mMask: Drawable? = null
        private var mRadius = -1
        private var mRect: Rect? = null

        fun setContent(content: Drawable?): Builder {
            mContent = content
            return this
        }

        fun setMask(mask: Drawable?): Builder {
            mMask = mask
            return this
        }

        fun setRadius(radius: Int): Builder {
            var radius = radius
            if (radius < 0) {
                radius = -1
            }
            mRadius = radius
            return this
        }

        fun setHotspotBounds(left: Int, top: Int, right: Int, bottom: Int): Builder {
            mRect = Rect(left, top, right, bottom)
            return this
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        fun build(): RippleDrawable {
            val rippleDrawable = RippleDrawable(mColorStateList, mContent, mMask)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rippleDrawable.radius = mRadius
            }
            if (mRect != null) {
                rippleDrawable.setHotspotBounds(mRect!!.left, mRect!!.top, mRect!!.right, mRect!!.bottom)
            }
            return rippleDrawable
        }
    }
}
