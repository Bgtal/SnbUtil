package blq.ssnb.snbutil.kit;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2016/9/20
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      代码设置Selector，免去写xml，增加灵活性
 * ================================================
 * </pre>
 */

public final class SnbDrawableSelector {

    private StateListDrawable mDrawable;

    private DrawableBean[] mDrawableBeans;

    private SnbDrawableSelector(DrawableBean[] drawableBeans) {
        mDrawableBeans = drawableBeans;
        initStates(drawableBeans);

        mDrawable = getStateListDrawable();
    }

    private void initStates(DrawableBean[] drawableBeans) {
        int k = 0;
        for (DrawableBean bean : drawableBeans) {
            if (bean.mSelectState.equals(SnbSelectState.NORMAL)) {
                continue;
            }
            for (DrawableBean item : drawableBeans) {
                int vale = item.equals(bean) ? bean.mSelectState.getStateValue() : bean.mSelectState.getUnStateValue();
                if (item.stateArray == null) {
                    item.stateArray = new int[drawableBeans.length - 1];
                }
                item.stateArray[k] = vale;
            }
            k++;
        }

    }

    private StateListDrawable getStateListDrawable() {
        StateListDrawable drawable = new StateListDrawable();
        for (DrawableBean bean : mDrawableBeans) {
            if (bean.mSelectState == SnbSelectState.NORMAL) {
                drawable.addState(new int[]{}, bean.mDrawable);
            } else {
                drawable.addState(bean.stateArray, bean.mDrawable);
            }
        }
        return drawable;
    }

    /**
     * 获得设置好的Selector Drawable
     *
     * @return Drawable
     */
    public Drawable getDrawable() {
        return mDrawable;
    }

    /**
     * 设置背景
     *
     * @param view 待设置的view
     */
    public void setBackground(View view) {
        if (mDrawable != null) {
            view.setBackground(mDrawable);
        }
    }

    public Drawable getNormalDraw() {
        return getDrawable(SnbSelectState.NORMAL);
    }

    public Drawable getCheckedDraw() {
        return getDrawable(SnbSelectState.CHECKED);
    }

    public Drawable getFocusedDraw() {
        return getDrawable(SnbSelectState.FOCUSED);
    }

    public Drawable getHoveredDraw() {
        return getDrawable(SnbSelectState.HOVERED);
    }

    public Drawable getPressedDraw() {
        return getDrawable(SnbSelectState.PRESSED);
    }

    public Drawable getDisabledDraw() {
        return getDrawable(SnbSelectState.DISABLED);
    }

    public Drawable getSelectedDraw() {
        return getDrawable(SnbSelectState.SELECTED);
    }

    public Drawable getActivatedDraw() {
        return getDrawable(SnbSelectState.ACTIVATED);
    }

    public Drawable getCheckableDraw() {
        return getDrawable(SnbSelectState.CHECKABLE);
    }

    public Drawable getWindowUnFocusedDraw() {
        return getDrawable(SnbSelectState.WINDOW_UN_FOCUSED);
    }

    private Drawable getDrawable(SnbSelectState state) {
        for (DrawableBean bean : mDrawableBeans) {
            if (bean.mSelectState.equals(state)) {
                return bean.mDrawable;
            }
        }
        return null;
    }

    public static class Builder {


        private Map<SnbSelectState, DrawableBean> mDrawableMap = new HashMap<>();

        public Builder() {
            this(new ColorDrawable());
        }

        /**
         * 传入默认状态下的图片
         *
         * @param normalDraw 默认状态下的背景
         */
        public Builder(@NonNull Drawable normalDraw) {
            normal(normalDraw);
        }

        /**
         * 默认状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder normal(Drawable drawable) {
            setDrawable(SnbSelectState.NORMAL, drawable);
            return this;
        }

        /**
         * 选中状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder checked(Drawable drawable) {
            setDrawable(SnbSelectState.CHECKED, drawable);
            return this;
        }

        /**
         * 获得焦点状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder focused(Drawable drawable) {
            setDrawable(SnbSelectState.FOCUSED, drawable);
            return this;
        }

        /**
         * 光标停留在组件上的状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder hovered(Drawable drawable) {
            setDrawable(SnbSelectState.HOVERED, drawable);
            return this;
        }

        /**
         * 按下状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder pressed(Drawable drawable) {
            setDrawable(SnbSelectState.PRESSED, drawable);
            return this;
        }

        /**
         * 不能被选中状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder disabled(Drawable drawable) {
            setDrawable(SnbSelectState.DISABLED, drawable);
            return this;
        }

        /**
         * 在item中选中的状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder selected(Drawable drawable) {
            setDrawable(SnbSelectState.SELECTED, drawable);
            return this;
        }

        /**
         * 组件激活状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder activated(Drawable drawable) {
            setDrawable(SnbSelectState.ACTIVATED, drawable);
            return this;
        }

        /**
         * 组件能被选中状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder checkable(Drawable drawable) {
            setDrawable(SnbSelectState.CHECKABLE, drawable);
            return this;
        }

        /**
         * 组件所在界面失去焦点状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder windowUnFocused(Drawable drawable) {
            setDrawable(SnbSelectState.WINDOW_UN_FOCUSED, drawable);
            return this;
        }

        private void setDrawable(SnbSelectState state, Drawable drawable) {
            DrawableBean bean = mDrawableMap.get(state);
            if (bean == null) {
                bean = new DrawableBean();
                mDrawableMap.put(state, bean);
            }
            bean.setDrawable(drawable);
            bean.setSelectState(state);
        }

        public Builder removeStateDrawable(SnbSelectState state) {
            if (state == SnbSelectState.NORMAL) {
                throw new IllegalArgumentException("默认状态不可移除");
            }
            mDrawableMap.remove(state);
            return this;
        }

        public SnbDrawableSelector build() {
            DrawableBean[] drawableBeans = new DrawableBean[mDrawableMap.size()];
            int k = 0;
            for (SnbSelectState state : SnbSelectState.getSelectStatesList()) {
                DrawableBean bean = mDrawableMap.get(state);
                if (bean != null) {
                    drawableBeans[k] = bean.copy();
                    k++;
                }
            }

            return new SnbDrawableSelector(drawableBeans);
        }
    }

    private static class DrawableBean {

        private Drawable mDrawable;
        private SnbSelectState mSelectState;
        private int[] stateArray;

        DrawableBean copy() {
            DrawableBean colorBean = new DrawableBean();
            colorBean.mDrawable = mDrawable;
            colorBean.mSelectState = mSelectState;
            return colorBean;
        }

        /**
         * 设置颜色
         */
        void setDrawable(Drawable drawable) {
            this.mDrawable = drawable;
        }

        /**
         * 状态类型
         */
        void setSelectState(SnbSelectState selectState) {
            this.mSelectState = selectState;
        }
    }
}
