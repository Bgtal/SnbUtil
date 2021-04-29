package blq.ssnb.snbutil.kit

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import java.util.*

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2016/9/20
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 代码设置Selector，免去写xml，增加灵活性
 * ================================================
</pre> *
 */
class SnbDrawableSelector private constructor(private val mDrawableBeans: Array<DrawableBean?>) {
    private val mDrawable: StateListDrawable?

    init {
        initStates(mDrawableBeans)
        mDrawable = stateListDrawable
    }

    private fun initStates(drawableBeans: Array<DrawableBean?>) {
        var k = 0
        for (bean in drawableBeans) {
            bean?.let {
                if (bean.selectState != SnbSelectState.NORMAL) {
                    for (item in drawableBeans) {
                        item?.let {
                            val vale = if (item == bean) bean.selectState.stateValue else bean.selectState.unStateValue
                            if (item.stateArray == null) {
                                item.stateArray = IntArray(drawableBeans.size - 1)
                            }
                            item.stateArray!![k] = vale
                        }
                    }
                    k++
                }
            }
        }
    }

    private val stateListDrawable: StateListDrawable
        private get() {
            val drawable = StateListDrawable()
            for (bean in mDrawableBeans) {
                bean?.let {
                    if (bean.selectState == SnbSelectState.NORMAL) {
                        //默认值就加一个空的状态列表
                        drawable.addState(intArrayOf(), bean.drawable)
                    } else {
                        //添加对应的状态列表
                        drawable.addState(bean.stateArray, bean.drawable)
                    }
                }
            }
            return drawable
        }

    /**
     * 获得设置好的Selector Drawable
     *
     * @return Drawable
     */
    val drawable: Drawable?
        get() = mDrawable

    /**
     * 设置背景
     *
     * @param view 待设置的view
     */
    fun setBackground(view: View) {
        if (mDrawable != null) {
            view.background = mDrawable
        }
    }

    val normalDraw: Drawable?
        get() = getDrawable(SnbSelectState.NORMAL)
    val checkedDraw: Drawable?
        get() = getDrawable(SnbSelectState.CHECKED)
    val focusedDraw: Drawable?
        get() = getDrawable(SnbSelectState.FOCUSED)
    val hoveredDraw: Drawable?
        get() = getDrawable(SnbSelectState.HOVERED)
    val pressedDraw: Drawable?
        get() = getDrawable(SnbSelectState.PRESSED)
    val disabledDraw: Drawable?
        get() = getDrawable(SnbSelectState.DISABLED)
    val selectedDraw: Drawable?
        get() = getDrawable(SnbSelectState.SELECTED)
    val activatedDraw: Drawable?
        get() = getDrawable(SnbSelectState.ACTIVATED)
    val checkableDraw: Drawable?
        get() = getDrawable(SnbSelectState.CHECKABLE)
    val windowUnFocusedDraw: Drawable?
        get() = getDrawable(SnbSelectState.WINDOW_UN_FOCUSED)

    private fun getDrawable(state: SnbSelectState): Drawable? {
        for (bean in mDrawableBeans) {
            if (bean?.selectState == state) {
                return bean.drawable
            }
        }
        return null
    }

    class Builder @JvmOverloads constructor(normalDraw: Drawable = ColorDrawable()) {
        private val mDrawableMap: MutableMap<SnbSelectState, DrawableBean> = HashMap()

        /**
         * 默认状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun normal(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.NORMAL, drawable)
            return this
        }

        /**
         * 选中状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun checked(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.CHECKED, drawable)
            return this
        }

        /**
         * 获得焦点状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun focused(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.FOCUSED, drawable)
            return this
        }

        /**
         * 光标停留在组件上的状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun hovered(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.HOVERED, drawable)
            return this
        }

        /**
         * 按下状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun pressed(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.PRESSED, drawable)
            return this
        }

        /**
         * 不能被选中状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun disabled(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.DISABLED, drawable)
            return this
        }

        /**
         * 在item中选中的状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun selected(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.SELECTED, drawable)
            return this
        }

        /**
         * 组件激活状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun activated(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.ACTIVATED, drawable)
            return this
        }

        /**
         * 组件能被选中状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun checkable(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.CHECKABLE, drawable)
            return this
        }

        /**
         * 组件所在界面失去焦点状态下的背景
         *
         * @param drawable 背景
         * @return Builder
         */
        fun windowUnFocused(drawable: Drawable): Builder {
            setDrawable(SnbSelectState.WINDOW_UN_FOCUSED, drawable)
            return this
        }

        private fun setDrawable(state: SnbSelectState, drawable: Drawable) {
            var bean = mDrawableMap[state]
            if (bean == null) {
                bean = DrawableBean(drawable, state)
                mDrawableMap[state] = bean
            }
            bean.drawable = drawable
            bean.selectState = state
        }

        fun removeStateDrawable(state: SnbSelectState): Builder {
            require(state != SnbSelectState.NORMAL) { "默认状态不可移除" }
            mDrawableMap.remove(state)
            return this
        }

        fun build(): SnbDrawableSelector {
            val drawableBeans = arrayOfNulls<DrawableBean>(mDrawableMap.size)
            var k = 0
            for (state in SnbSelectState.selectStatesList) {
                val bean = mDrawableMap[state]
                if (bean != null) {
                    drawableBeans[k] = bean.copy()
                    k++
                }
            }
            return SnbDrawableSelector(drawableBeans)
        }

        /**
         * 传入默认状态下的图片
         *
         * @param normalDraw 默认状态下的背景
         */
        init {
            normal(normalDraw)
        }
    }

    private class DrawableBean constructor(var drawable: Drawable, var selectState: SnbSelectState) {
        var stateArray: IntArray? = null
        fun copy(): DrawableBean {
            return DrawableBean(drawable, selectState)
        }
    }
}