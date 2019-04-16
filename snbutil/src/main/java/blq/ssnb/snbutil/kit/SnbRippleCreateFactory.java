package blq.ssnb.snbutil.kit;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/2/23
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SnbRippleCreateFactory {

    public static LayerDrawable noMask(@ColorInt int color) {
        return noMask(color, -1);
    }

    public static LayerDrawable noMask(Context context, @ColorRes int colorRes) {
        return noMask(context, colorRes, -1);
    }

    public static LayerDrawable noMask(ColorStateList colorStateList) {
        return noMask(colorStateList, -1);
    }

    public static LayerDrawable noMask(@ColorInt int color, int radius) {
        return noMask(ColorStateList.valueOf(color), radius);
    }

    public static LayerDrawable noMask(Context context, @ColorRes int colorRes, int radius) {
        return noMask(context.getResources().getColorStateList(colorRes), radius);
    }

    /**
     * 没有设置边界的水波纹drawable
     *
     * @param colorStateList colorStateList
     * @param radius         按下后水波的半径 <0 表示使用系统默认的半径
     */
    public static LayerDrawable noMask(ColorStateList colorStateList, int radius) {
        return backgroundAndMask(colorStateList, null, null);
    }

    /**
     * 设置背景，且水波纹在背景图片中显示
     *
     * @param colorStateList
     * @param background
     * @return
     */
    public static LayerDrawable backgroundMask(ColorStateList colorStateList, Drawable background) {
        return backgroundAndMask(colorStateList, background, null);
    }

    /**
     * 设置 有边界的水波纹
     *
     * @param colorStateList
     * @param mask           边界对象
     * @return
     */
    public static LayerDrawable mask(ColorStateList colorStateList, Drawable mask) {
        return backgroundAndMask(colorStateList, null, mask);
    }

    /**
     * 设置背景 和 边界 的水波纹效果 (背景不会显示，只有触发水波纹的时候显示)
     * 如果mask 为null 效果和 {@link #backgroundMask(ColorStateList, Drawable)}一样
     * 如果background 为null 效果和 {@link #mask(ColorStateList, Drawable)} 一样
     * 如果两个都未设置，效果为 不设边界的水波纹效果
     *
     * @param colorStateList
     * @param background
     * @param mask
     * @return
     */
    public static LayerDrawable backgroundAndMask(ColorStateList colorStateList, Drawable background, Drawable mask) {
        return backgroundAndMask(colorStateList, background, mask, -1);
    }

    public static LayerDrawable backgroundAndMask(ColorStateList colorStateList, Drawable background, Drawable mask, int radius) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new Builder(colorStateList)
                    .setContent(background)
                    .setRadius(radius)
                    .setMask(mask)
                    .build();
        } else {
//            LayerDrawable dd = new LayerDrawable(new Drawable[]{});

            return null;
        }
    }


    public static class Builder {

        private ColorStateList mColorStateList;
        private Drawable mContent;
        private Drawable mMask;
        private int mRadius = -1;
        private Rect mRect;

        public Builder(ColorStateList colorStateList) {
            mColorStateList = colorStateList;
        }


        public Builder setContent(Drawable content) {
            this.mContent = content;
            return this;
        }

        public Builder setMask(Drawable mask) {
            this.mMask = mask;
            return this;
        }

        public Builder setRadius(int radius) {
            if (radius < 0) {
                radius = -1;
            }
            this.mRadius = radius;
            return this;
        }

        public Builder setHotspotBounds(int left, int top, int right, int bottom) {
            mRect = new Rect(left, top, right, bottom);
            return this;
        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public RippleDrawable build() {
            RippleDrawable rippleDrawable = new RippleDrawable(mColorStateList, mContent, mMask);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rippleDrawable.setRadius(mRadius);
            }

            if (mRect != null) {
                rippleDrawable.setHotspotBounds(mRect.left, mRect.top, mRect.right, mRect.bottom);
            }
            return rippleDrawable;
        }

    }

}
