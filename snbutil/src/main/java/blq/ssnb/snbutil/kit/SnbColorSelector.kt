package blq.ssnb.snbutil.kit

import android.content.res.ColorStateList
import android.widget.TextView
import androidx.annotation.ColorInt
import java.util.*
import kotlin.collections.HashMap

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2016/11/2
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 代码设置代码设置[TextView]不同状态下的颜色
 * 目前只支持设置单一模式下的颜色改变，比如需要 设置 focus 情况下按下的效果代码是实现不了的
 * ================================================
</pre> *
 */
class SnbColorSelector private constructor(private val mColorBeanList: Array<ColorBean?>) {
    private val colorStateList: ColorStateList?

    init {
        initStates(mColorBeanList)
        colorStateList = initColorStateList()
    }

    private fun initStates(colorBeans: Array<ColorBean?>) {
        var k = 0
        for (bean in colorBeans) {
            //当状态不为默认的时候，循环其他状态
            bean?.let {
                if (bean.selectState != SnbSelectState.NORMAL) {
                    for (item in colorBeans) {//再次循环
                        item?.let {//如果这个状态没设置就不弄了
                            //这里主要做的是将设置的状态填写到
                            //目前只支持设置单一模式下的颜色改变，比如需要 设置 focus 情况下按下的效果代码是实现不了的
                            val value: Int = if (item == bean) bean.selectState.stateValue else bean.selectState.unStateValue
                            if (item.stateArray == null) {
                                //因为状态为normal的不参与配置
                                item.stateArray = IntArray(colorBeans.size - 1)
                            }
                            item.stateArray!![k] = value
                        }
                    }
                    k++
                }
            }
        }
    }

    private fun initColorStateList(): ColorStateList {
        //states 中的对象相当于 xml 中的 item标签
        // <selector>
        //      <item/>
        //</selector>
        val states = arrayOfNulls<IntArray>(mColorBeanList.size)
        val colors = IntArray(mColorBeanList.size)
        /**
         * @note 这里的方法千万不能改变顺序，否者就会显示有问题
         * 主要原因是，select 的是按顺序匹配的，如果normal排在前面，那么后面的都匹配不到，所以不能随便改顺序
         */
        for (i in mColorBeanList.indices) {//循环数组
            val bean = mColorBeanList[i]//得到颜色状态对象
            bean?.let {
                //当为默认值的时候
                if (bean.selectState == SnbSelectState.NORMAL) {
                    //添加一个空数组
                    states[i] = intArrayOf()
                } else {
                    //当非默认的时候，将对应item下的 state_xxx 配置
                    states[i] = bean.stateArray
                }
                colors[i] = bean.color
            }
        }
        return ColorStateList(states, colors)
    }

    fun setTextColor(v: TextView) {
        if (colorStateList != null) {
            v.setTextColor(colorStateList)
        }
    }

    class Builder @JvmOverloads constructor(@ColorInt normalColor: Int = -0xa2a2a3) {
        //用于存储 状态和该状态下颜色的样式
        private val mColorBeanHashMap: HashMap<SnbSelectState, ColorBean> = HashMap()

        /**
         * 默认状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun normal(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.NORMAL, color)
            return this
        }

        /**
         * 选中状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun checked(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.CHECKED, color)
            return this
        }

        /**
         * 获得焦点状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun focused(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.FOCUSED, color)
            return this
        }

        /**
         * 光标停留在组件上的状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun hovered(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.HOVERED, color)
            return this
        }

        /**
         * 按下状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun pressed(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.PRESSED, color)
            return this
        }

        /**
         * 不能被选中状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun disabled(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.DISABLED, color)
            return this
        }

        /**
         * 在item中选中的状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun selected(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.SELECTED, color)
            return this
        }

        /**
         * 组件激活状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun activated(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.ACTIVATED, color)
            return this
        }

        /**
         * 组件能被选中状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun checkable(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.CHECKABLE, color)
            return this
        }

        /**
         * 组件所在界面失去焦点状态下的文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        fun windowUnFocused(@ColorInt color: Int): Builder {
            addColor(SnbSelectState.WINDOW_UN_FOCUSED, color)
            return this
        }

        /**
         * 设置颜色
         */
        private fun addColor(state: SnbSelectState, @ColorInt color: Int) {
            var bean = mColorBeanHashMap[state]
            if (bean == null) {
                bean = ColorBean(color, state)
                mColorBeanHashMap[state] = bean
            }
            bean.color = color
            bean.selectState = state
        }

        /**
         * 移除对应状态的颜色
         */
        fun removeStateColor(state: SnbSelectState): Builder {
            require(state != SnbSelectState.NORMAL) { "默认状态不可移除" }
            mColorBeanHashMap.remove(state)
            return this
        }

        fun build(): SnbColorSelector {
            val colorBeans = arrayOfNulls<ColorBean>(mColorBeanHashMap.size)
            var k = 0
            /**
             * @note 这里按状态顺序添加到列表中，不能随意改变
             */
            for (state in SnbSelectState.selectStatesList) {
                val bean = mColorBeanHashMap[state]
                bean?.let {
                    colorBeans[k] = bean.copy()
                    k++
                }
            }
            return SnbColorSelector(colorBeans)
        }

        /**
         * 传入默认状态下的文字颜色
         *
         * @param normalColor 默认状态下的文字颜色
         */
        init {
            normal(normalColor)
        }
    }

    private class ColorBean constructor(var color: Int, var selectState: SnbSelectState) {
//        var color //颜色
//        var selectState: SnbSelectState //状态

        //该属性用于生成状态
        var stateArray: IntArray? = null//

        fun copy(): ColorBean {
            return ColorBean(color, selectState)
        }

    }
}