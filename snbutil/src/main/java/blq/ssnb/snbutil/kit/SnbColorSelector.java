package blq.ssnb.snbutil.kit;

import android.content.res.ColorStateList;

import androidx.annotation.ColorInt;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2016/11/2
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 代码设置代码设置{@link TextView}不同状态下的颜色
 * ================================================
 * </pre>
 */

public final class SnbColorSelector {
    private ColorStateList mTextColor;
    private ColorBean[] mColorBeanList;

    private SnbColorSelector(ColorBean[] colorBeans) {
        mColorBeanList = colorBeans;
        initStates(colorBeans);
        mTextColor = initColorStateList();
    }

    private void initStates(ColorBean[] colorBeans) {
        int k = 0;
        for (ColorBean bean : colorBeans) {
            if (bean.mSelectState.equals(SnbSelectState.NORMAL)) {
                continue;
            }
            for (ColorBean item : colorBeans) {
                int value = item.equals(bean) ? bean.mSelectState.getStateValue() : bean.mSelectState.getUnStateValue();
                if (item.stateArray == null) {
                    item.stateArray = new int[colorBeans.length - 1];
                }
                item.stateArray[k] = value;
            }
            k++;
        }
    }

    private ColorStateList initColorStateList() {

        int[][] states = new int[mColorBeanList.length][];
        int[] colors = new int[mColorBeanList.length];

        /**
         * @note 这里的方法千万不能改变顺序，否者就会显示有问题
         * 主要原因是，select 的是按顺序匹配的，如果normal排在前面，那么后面的都匹配不到，所以不能随便改顺序
         */
        for (int i = 0; i < mColorBeanList.length; i++) {
            ColorBean bean = mColorBeanList[i];
            if (bean.mSelectState == SnbSelectState.NORMAL) {
                states[i] = new int[]{};
            } else {
                states[i] = bean.stateArray;
            }
            colors[i] = bean.color;
        }
        return new ColorStateList(states, colors);
    }

    public void setTextColor(TextView v) {
        if (mTextColor != null) {
            v.setTextColor(mTextColor);
        }
    }

    public ColorStateList getColorStateList() {
        return mTextColor;
    }

    public static class Builder {

        private Map<SnbSelectState, ColorBean> mColorBeanHashMap = new HashMap<>();

        public Builder() {
            this(0xFF5D5D5D);
        }

        /**
         * 传入默认状态下的文字颜色
         *
         * @param normalColor 默认状态下的文字颜色
         */
        public Builder(@ColorInt int normalColor) {
            normal(normalColor);
        }

        /**
         * 默认状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder normal(@ColorInt int color) {
            setColor(SnbSelectState.NORMAL, color);
            return this;
        }

        /**
         * 选中状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder checked(@ColorInt int color) {
            setColor(SnbSelectState.CHECKED, color);
            return this;
        }

        /**
         * 获得焦点状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder focused(@ColorInt int color) {
            setColor(SnbSelectState.FOCUSED, color);
            return this;
        }

        /**
         * 光标停留在组件上的状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder hovered(@ColorInt int color) {
            setColor(SnbSelectState.HOVERED, color);
            return this;
        }

        /**
         * 按下状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder pressed(@ColorInt int color) {
            setColor(SnbSelectState.PRESSED, color);
            return this;
        }

        /**
         * 不能被选中状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder disabled(@ColorInt int color) {
            setColor(SnbSelectState.DISABLED, color);
            return this;
        }

        /**
         * 在item中选中的状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder selected(@ColorInt int color) {
            setColor(SnbSelectState.SELECTED, color);
            return this;
        }

        /**
         * 组件激活状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder activated(@ColorInt int color) {
            setColor(SnbSelectState.ACTIVATED, color);
            return this;
        }

        /**
         * 组件能被选中状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder checkable(@ColorInt int color) {
            setColor(SnbSelectState.CHECKABLE, color);
            return this;
        }

        /**
         * 组件所在界面失去焦点状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder windowUnFocused(@ColorInt int color) {
            setColor(SnbSelectState.WINDOW_UN_FOCUSED, color);
            return this;
        }

        /**
         * 设置颜色
         */
        private void setColor(SnbSelectState state, @ColorInt int color) {
            ColorBean bean = mColorBeanHashMap.get(state);
            if (bean == null) {
                bean = new ColorBean();
                mColorBeanHashMap.put(state, bean);
            }
            bean.setColor(color);
            bean.setSelectState(state);
        }

        /**
         * 移除对应状态的颜色
         */
        public Builder removeStateColor(SnbSelectState state) {
            if (state == SnbSelectState.NORMAL) {
                throw new IllegalArgumentException("默认状态不可移除");
            }
            mColorBeanHashMap.remove(state);
            return this;
        }

        public SnbColorSelector build() {
            ColorBean[] colorBeans = new ColorBean[mColorBeanHashMap.size()];
            int k = 0;
            /**
             * @note 这里按状态顺序添加到列表中，不能随意改变
             */
            for (SnbSelectState state : SnbSelectState.getSelectStatesList()) {
                ColorBean bean = mColorBeanHashMap.get(state);
                if (bean != null) {
                    colorBeans[k] = bean.copy();
                    k++;
                }
            }
            return new SnbColorSelector(colorBeans);
        }
    }

    private static class ColorBean {

        private int color;
        private SnbSelectState mSelectState;
        private int[] stateArray;

        ColorBean copy() {
            ColorBean colorBean = new ColorBean();
            colorBean.color = color;
            colorBean.mSelectState = mSelectState;
            return colorBean;
        }

        /**
         * 设置颜色
         */
        void setColor(@ColorInt int color) {
            this.color = color;
        }

        /**
         * 状态类型
         */
        void setSelectState(SnbSelectState selectState) {
            this.mSelectState = selectState;
        }
    }
}
