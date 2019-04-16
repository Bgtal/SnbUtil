package blq.ssnb.snbutil.kit;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

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
    private Drawable normalDraw;
    private Drawable checkedDraw;
    private Drawable focusedDraw;
    private Drawable hoveredDraw;
    private Drawable pressedDraw;
    private Drawable disabledDraw;
    private Drawable selectedDraw;
    private Drawable activatedDraw;
    private Drawable checkableDraw;
    private Drawable windowUnFocusedDraw;

    private StateListDrawable mDrawable;

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

    private SnbDrawableSelector(Builder builder) {
        this.normalDraw = builder.normalDraw;
        this.checkedDraw = builder.checkedDraw;
        this.focusedDraw = builder.focusedDraw;
        this.hoveredDraw = builder.hoveredDraw;
        this.pressedDraw = builder.pressedDraw;
        this.disabledDraw = builder.disabledDraw;
        this.selectedDraw = builder.selectedDraw;
        this.activatedDraw = builder.activatedDraw;
        this.checkableDraw = builder.checkableDraw;
        this.windowUnFocusedDraw = builder.windowUnFocusedDraw;
        mDrawable = getStateListDrawable();
    }

    private StateListDrawable getStateListDrawable() {
        if (initState() == 0) {
            return null;
        }
        StateListDrawable drawable = new StateListDrawable();
        if (normalDraw != null) {
            drawable.addState(normalState, normalDraw);
        }
        if (checkedDraw != null) {
            drawable.addState(checkedState, checkedDraw);
        }
        if (focusedDraw != null) {
            drawable.addState(focusedState, focusedDraw);
        }
        if (hoveredDraw != null) {
            drawable.addState(hoveredState, hoveredDraw);
        }
        if (pressedDraw != null) {
            drawable.addState(pressedState, pressedDraw);
        }
        if (disabledDraw != null) {
            drawable.addState(disabledState, disabledDraw);
        }
        if (selectedDraw != null) {
            drawable.addState(selectedState, selectedDraw);
        }
        if (activatedDraw != null) {
            drawable.addState(activatedState, activatedDraw);
        }
        if (checkableDraw != null) {
            drawable.addState(checkableState, checkableDraw);
        }
        if (windowUnFocusedDraw != null) {
            drawable.addState(windowFocusedState, windowUnFocusedDraw);
        }
        return drawable;
    }

    /**
     * 初始化状态
     */
    private int initState() {
        int i = drawCount();
        if (i == 0) {
            return 0;
        }
        int size = i;
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

        if (checkedDraw != null) {
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
        if (focusedDraw != null) {
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

        if (hoveredDraw != null) {
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
        if (pressedDraw != null) {
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
        if (disabledDraw != null) {
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
        if (selectedDraw != null) {
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
        if (activatedDraw != null) {
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
        if (checkableDraw != null) {
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
        if (windowUnFocusedDraw != null) {
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
     * 得到设置的Draw的数量
     *
     * @return 状态个数
     */
    private int drawCount() {
        int i = 0;
        i = normalDraw != null ? ++i : i;
        i = checkedDraw != null ? ++i : i;
        i = focusedDraw != null ? ++i : i;
        i = hoveredDraw != null ? ++i : i;
        i = pressedDraw != null ? ++i : i;
        i = disabledDraw != null ? ++i : i;
        i = selectedDraw != null ? ++i : i;
        i = activatedDraw != null ? ++i : i;
        i = checkableDraw != null ? ++i : i;
        i = windowUnFocusedDraw != null ? ++i : i;
        return i;
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackground(mDrawable);
            } else {
                view.setBackgroundDrawable(mDrawable);
            }
        }
    }

    public Drawable getNormalDraw() {
        return normalDraw;
    }

    public Drawable getCheckedDraw() {
        return checkedDraw;
    }

    public Drawable getFocusedDraw() {
        return focusedDraw;
    }

    public Drawable getHoveredDraw() {
        return hoveredDraw;
    }

    public Drawable getPressedDraw() {
        return pressedDraw;
    }

    public Drawable getDisabledDraw() {
        return disabledDraw;
    }

    public Drawable getSelectedDraw() {
        return selectedDraw;
    }

    public Drawable getActivatedDraw() {
        return activatedDraw;
    }

    public Drawable getCheckableDraw() {
        return checkableDraw;
    }

    public Drawable getWindowUnFocusedDraw() {
        return windowUnFocusedDraw;
    }

    public static class Builder {
        private Drawable normalDraw = null;
        private Drawable checkedDraw = null;
        private Drawable focusedDraw = null;
        private Drawable hoveredDraw = null;
        private Drawable pressedDraw = null;
        private Drawable disabledDraw = null;
        private Drawable selectedDraw = null;
        private Drawable activatedDraw = null;
        private Drawable checkableDraw = null;
        private Drawable windowUnFocusedDraw = null;

        public Builder() {
        }

        /**
         * 传入默认状态下的图片
         *
         * @param normalDraw 默认状态下的背景
         */
        public Builder(@NonNull Drawable normalDraw) {
            this.normalDraw = normalDraw;
        }

        /**
         * 默认状态下的背景
         *
         * @param normalDraw 背景
         * @return Builder
         */
        public Builder normal(Drawable normalDraw) {
            this.normalDraw = normalDraw;
            return this;
        }

        /**
         * 选中状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder checked(Drawable drawable) {
            this.checkedDraw = drawable;
            return this;
        }

        /**
         * 获得焦点状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder focused(Drawable drawable) {
            this.focusedDraw = drawable;
            return this;
        }

        /**
         * 光标停留在组件上的状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder hovered(Drawable drawable) {
            this.hoveredDraw = drawable;
            return this;
        }

        /**
         * 按下状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder pressed(Drawable drawable) {
            this.pressedDraw = drawable;
            return this;
        }

        /**
         * 不能被选中状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder disabled(Drawable drawable) {
            this.disabledDraw = drawable;
            return this;
        }

        /**
         * 在item中选中的状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder selected(Drawable drawable) {
            this.selectedDraw = drawable;
            return this;
        }

        /**
         * 组件激活状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder activated(Drawable drawable) {
            this.activatedDraw = drawable;
            return this;
        }

        /**
         * 组件能被选中状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder checkable(Drawable drawable) {
            this.checkableDraw = drawable;
            return this;
        }

        /**
         * 组件所在界面失去焦点状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        public Builder windowUnFocused(Drawable drawable) {
            this.windowUnFocusedDraw = drawable;
            return this;
        }

        public SnbDrawableSelector build() {
            return new SnbDrawableSelector(this);
        }
    }
}
