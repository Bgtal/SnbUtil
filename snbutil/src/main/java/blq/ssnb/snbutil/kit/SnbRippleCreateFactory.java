package blq.ssnb.snbutil.kit;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;

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

    /**
     * {@link #noMask(ColorStateList)}
     */
    public static Drawable noMask(Context context, @ColorRes int colorRes) {
        return noMask(context.getResources().getColor(colorRes));
    }

    /**
     * {@link #noMask(ColorStateList)}
     */
    public static Drawable noMask(@ColorInt int color) {
        return noMask(ColorStateList.valueOf(color));
    }

    /**
     * 没有设置边界的水波纹drawable
     *
     * @param colorStateList colorStateList
     */
    public static Drawable noMask(ColorStateList colorStateList) {
        return backgroundAndMask(colorStateList, null, null);
    }

    /**
     * {@link #mask(int, Drawable)}
     */
    public static Drawable mask(@ColorInt int rippleColor) {
        return mask(rippleColor, new ColorDrawable(0x7f000000));
    }

    /**
     * 设置 有边界的水波纹,边界大小为mask的大小
     *
     * @param rippleColor 水波纹颜色
     * @param mask        边界对象
     */
    public static Drawable mask(@ColorInt int rippleColor, Drawable mask) {
        return backgroundAndMask(ColorStateList.valueOf(rippleColor), null, mask);
    }

    /**
     * 设置背景，且水波纹在背景图片中显示
     *
     * @param colorStateList
     * @param background
     * @return
     */
    public static Drawable backgroundMask(ColorStateList colorStateList, Drawable background) {
        return backgroundAndMask(colorStateList, background, null);
    }

    /**
     * 设置背景 和 边界 的水波纹效果 (背景一直显示，只有触发水波纹的时候会显示背景drawable)
     * 如果mask 为null 效果和 {@link #backgroundMask(ColorStateList, Drawable)}一样
     * 如果background 为null 效果和 {@link #mask(int, Drawable)} 一样
     * 如果两个都未设置，效果为 不设边界的水波纹效果
     *
     * @param colorStateList
     * @param background
     * @param mask
     * @return
     */
    public static Drawable backgroundAndMask(ColorStateList colorStateList, Drawable background, Drawable mask) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new Builder(colorStateList)
                    .setContent(background)
                    .setMask(mask)
                    .build();
        } else {
            SnbDrawableSelector.Builder builder = new SnbDrawableSelector.Builder();
            if (background != null) {
                builder.normal(background);
            } else {
                builder.normal(new ColorDrawable());
            }
            if (mask != null) {
                builder.pressed(mask);
            } else if (colorStateList != null) {
                builder.pressed(new ColorDrawable(colorStateList.getDefaultColor()));
            }
            return builder.build().getDrawable();
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
