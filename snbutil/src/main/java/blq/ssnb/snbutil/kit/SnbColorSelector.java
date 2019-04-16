package blq.ssnb.snbutil.kit;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.widget.TextView;

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
    private static final int EMPTY = -1;

    /**
     * 9中变化状态
     */
    private static int checked = android.R.attr.state_checked;
    private static int unChecked = -checked;

    private static int focused = android.R.attr.state_focused;
    private static int unFocused = -focused;

    private static int hovered = android.R.attr.state_hovered;
    private static int unHovered = -hovered;

    private static int pressed = android.R.attr.state_pressed;
    private static int unPressed = -pressed;

    private static int enabled = android.R.attr.state_enabled;
    private static int unEnabled = -enabled;

    private static int selected = android.R.attr.state_selected;
    private static int unSelected = -selected;

    private static int activated = android.R.attr.state_activated;
    private static int unActivated = -activated;

    private static int checkable = android.R.attr.state_checkable;
    private static int unCheckable = -checkable;

    private static int windowFocused = android.R.attr.state_window_focused;
    private static int unWindowFocused = -windowFocused;

    /**
     * 10中状态对应显示的图片
     */
    private int normalColor;
    private int checkedColor;
    private int focusedColor;
    private int hoveredColor;
    private int pressedColor;
    private int disabledColor;
    private int selectedColor;
    private int activatedColor;
    private int checkableColor;
    private int windowUnFocusedColor;

    /**
     * 10中状态对应的匹配规则
     */
    private int[] normalState;
    private int[] checkedState;
    private int[] focusedState;
    private int[] hoveredState;
    private int[] pressedState;
    private int[] disabledState;
    private int[] selectedState;
    private int[] activatedState;
    private int[] checkableState;
    private int[] windowFocusedState;

    private ColorStateList mTextColor;


    private SnbColorSelector(Builder builder) {
        this.normalColor = builder.normalColor;
        this.checkedColor = builder.checkedColor;
        this.focusedColor = builder.focusedColor;
        this.hoveredColor = builder.hoveredColor;
        this.pressedColor = builder.pressedColor;
        this.disabledColor = builder.disabledColor;
        this.selectedColor = builder.selectedColor;
        this.activatedColor = builder.activatedColor;
        this.checkableColor = builder.checkableColor;
        this.windowUnFocusedColor = builder.windowUnFocusedColor;
        mTextColor = getStateListDrawable();
    }

    private ColorStateList getStateListDrawable() {
        int[][] status;
        int[] color;
        int size = initState();
        if (size == 0) {
            return null;
        }
        status = new int[size][];
        color = new int[size];
        size--;
        if (normalColor != EMPTY) {
            status[size] = normalState;
            color[size] = normalColor;
            size--;
        }
        if (checkedColor != EMPTY) {
            status[size] = checkedState;
            color[size] = checkedColor;
            size--;
        }
        if (focusedColor != EMPTY) {
            status[size] = focusedState;
            color[size] = focusedColor;
            size--;
        }
        if (hoveredColor != EMPTY) {
            status[size] = hoveredState;
            color[size] = hoveredColor;
            size--;
        }
        if (pressedColor != EMPTY) {
            status[size] = pressedState;
            color[size] = pressedColor;
            size--;
        }
        if (disabledColor != EMPTY) {
            status[size] = disabledState;
            color[size] = disabledColor;
            size--;
        }
        if (selectedColor != EMPTY) {
            status[size] = selectedState;
            color[size] = selectedColor;
            size--;
        }
        if (activatedColor != EMPTY) {
            status[size] = activatedState;
            color[size] = activatedColor;
            size--;
        }
        if (checkableColor != EMPTY) {
            status[size] = checkableState;
            color[size] = checkableColor;
            size--;
        }
        if (windowUnFocusedColor != EMPTY) {
            status[size] = windowFocusedState;
            color[size] = windowUnFocusedColor;
        }

        return new ColorStateList(status, color);
    }

    private int initState() {
        int size;
        int i = colorCount();
        if (i == 0) {
            return 0;
        }
        size = i;
        //因为normal状态没有true/false 选项所以个数要减一
        i--;
        normalState = new int[i];
        checkedState = new int[i];
        focusedState = new int[i];
        hoveredState = new int[i];
        pressedState = new int[i];
        disabledState = new int[i];
        selectedState = new int[i];
        activatedState = new int[i];
        checkableState = new int[i];
        windowFocusedState = new int[i];
        i--;

        if (checkedColor != EMPTY) {
            normalState[i] = unChecked;
            checkedState[i] = checked;
            focusedState[i] = unChecked;
            hoveredState[i] = unChecked;
            pressedState[i] = unChecked;
            disabledState[i] = unChecked;
            selectedState[i] = unChecked;
            activatedState[i] = unChecked;
            checkableState[i] = unChecked;
            windowFocusedState[i] = unChecked;
            i--;
        }
        if (focusedColor != EMPTY) {
            normalState[i] = unFocused;
            checkedState[i] = unFocused;
            focusedState[i] = focused;
            hoveredState[i] = unFocused;
            pressedState[i] = unFocused;
            disabledState[i] = unFocused;
            selectedState[i] = unFocused;
            activatedState[i] = unFocused;
            checkableState[i] = unFocused;
            windowFocusedState[i] = unFocused;
            i--;
        }

        if (hoveredColor != EMPTY) {
            normalState[i] = unHovered;
            checkedState[i] = unHovered;
            focusedState[i] = unHovered;
            hoveredState[i] = hovered;
            pressedState[i] = unHovered;
            disabledState[i] = unHovered;
            selectedState[i] = unHovered;
            activatedState[i] = unHovered;
            checkableState[i] = unHovered;
            windowFocusedState[i] = unHovered;
            i--;
        }
        if (pressedColor != EMPTY) {
            normalState[i] = unPressed;
            checkedState[i] = unPressed;
            focusedState[i] = unPressed;
            hoveredState[i] = unPressed;
            pressedState[i] = pressed;
            disabledState[i] = unPressed;
            selectedState[i] = unPressed;
            activatedState[i] = unPressed;
            checkableState[i] = unPressed;
            windowFocusedState[i] = unPressed;
            i--;
        }
        if (disabledColor != EMPTY) {
            normalState[i] = enabled;
            checkedState[i] = enabled;
            focusedState[i] = enabled;
            hoveredState[i] = enabled;
            pressedState[i] = enabled;
            disabledState[i] = unEnabled;
            selectedState[i] = enabled;
            activatedState[i] = enabled;
            checkableState[i] = enabled;
            windowFocusedState[i] = enabled;
            i--;
        }
        if (selectedColor != EMPTY) {
            normalState[i] = unSelected;
            checkedState[i] = unSelected;
            focusedState[i] = unSelected;
            hoveredState[i] = unSelected;
            pressedState[i] = unSelected;
            disabledState[i] = unSelected;
            selectedState[i] = selected;
            activatedState[i] = unSelected;
            checkableState[i] = unSelected;
            windowFocusedState[i] = unSelected;
            i--;
        }
        if (activatedColor != EMPTY) {
            normalState[i] = unActivated;
            checkedState[i] = unActivated;
            focusedState[i] = unActivated;
            hoveredState[i] = unActivated;
            pressedState[i] = unActivated;
            disabledState[i] = unActivated;
            selectedState[i] = unActivated;
            activatedState[i] = activated;
            checkableState[i] = unActivated;
            windowFocusedState[i] = unActivated;
            i--;
        }
        if (checkableColor != EMPTY) {
            normalState[i] = unCheckable;
            checkedState[i] = unCheckable;
            focusedState[i] = unCheckable;
            hoveredState[i] = unCheckable;
            pressedState[i] = unCheckable;
            disabledState[i] = unCheckable;
            selectedState[i] = unCheckable;
            activatedState[i] = unCheckable;
            checkableState[i] = checkable;
            windowFocusedState[i] = unCheckable;
            i--;
        }
        if (windowUnFocusedColor != EMPTY) {
            normalState[i] = windowFocused;
            checkedState[i] = windowFocused;
            focusedState[i] = windowFocused;
            hoveredState[i] = windowFocused;
            pressedState[i] = windowFocused;
            disabledState[i] = windowFocused;
            selectedState[i] = windowFocused;
            activatedState[i] = windowFocused;
            checkableState[i] = windowFocused;
            windowFocusedState[i] = unWindowFocused;
        }
        return size;
    }

    /**
     * 获得设置的状态的个数
     *
     * @return 状态个数
     */
    private int colorCount() {
        int i = 0;
        i = normalColor != EMPTY ? ++i : i;
        i = checkedColor != EMPTY ? ++i : i;
        i = focusedColor != EMPTY ? ++i : i;
        i = hoveredColor != EMPTY ? ++i : i;
        i = pressedColor != EMPTY ? ++i : i;
        i = disabledColor != EMPTY ? ++i : i;
        i = selectedColor != EMPTY ? ++i : i;
        i = activatedColor != EMPTY ? ++i : i;
        i = checkableColor != EMPTY ? ++i : i;
        i = windowUnFocusedColor != EMPTY ? ++i : i;
        return i;
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
        private int normalColor = EMPTY;
        private int checkedColor = EMPTY;
        private int focusedColor = EMPTY;
        private int hoveredColor = EMPTY;
        private int pressedColor = EMPTY;
        private int disabledColor = EMPTY;
        private int selectedColor = EMPTY;
        private int activatedColor = EMPTY;
        private int checkableColor = EMPTY;
        private int windowUnFocusedColor = EMPTY;

        public Builder() {
        }

        /**
         * 传入默认状态下的文字颜色
         *
         * @param normalColor 默认状态下的文字颜色
         */
        public Builder(@NonNull int normalColor) {
            this.normalColor = normalColor;
        }

        /**
         * 默认状态下的文字颜色
         *
         * @param normalColor 文字颜色
         * @return Builder
         */
        public Builder normal(int normalColor) {
            this.normalColor = normalColor;
            return this;
        }

        /**
         * 选中状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder checked(int color) {
            this.checkedColor = color;
            return this;
        }

        /**
         * 获得焦点状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder focused(int color) {
            this.focusedColor = color;
            return this;
        }

        /**
         * 光标停留在组件上的状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder hovered(int color) {
            this.hoveredColor = color;
            return this;
        }

        /**
         * 按下状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder pressed(int color) {
            this.pressedColor = color;
            return this;
        }

        /**
         * 不能被选中状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder disabled(int color) {
            this.disabledColor = color;
            return this;
        }

        /**
         * 在item中选中的状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder selected(int color) {
            this.selectedColor = color;
            return this;
        }

        /**
         * 组件激活状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder activated(int color) {
            this.activatedColor = color;
            return this;
        }

        /**
         * 组件能被选中状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder checkable(int color) {
            this.checkableColor = color;
            return this;
        }

        /**
         * 组件所在界面失去焦点状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder windowUnFocused(int color) {
            this.windowUnFocusedColor = color;
            return this;
        }

        public SnbColorSelector build() {
            return new SnbColorSelector(this);
        }
    }
}
