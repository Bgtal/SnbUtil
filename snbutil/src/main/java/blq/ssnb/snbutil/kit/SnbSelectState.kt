package blq.ssnb.snbutil.kit

import android.R

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/5/16
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 添加描述
 * ================================================
</pre> *
 */
enum class SnbSelectState(var stateValue: Int) {
    NORMAL(0), CHECKED(R.attr.state_checked), FOCUSED(R.attr.state_focused), HOVERED(R.attr.state_hovered), PRESSED(R.attr.state_pressed), DISABLED(-R.attr.state_enabled), SELECTED(R.attr.state_selected), ACTIVATED(R.attr.state_activated), CHECKABLE(R.attr.state_checkable), WINDOW_UN_FOCUSED(-R.attr.state_window_focused);

    val unStateValue: Int
        get() = -stateValue

    companion object {
        /**
         * @note 这里的状态列表不要轻易的改变，这个涉及到select的状态显示
         * @return 状态的列表
         */
        val selectStatesList: Array<SnbSelectState> = arrayOf(FOCUSED, HOVERED, CHECKED, PRESSED, DISABLED, SELECTED, ACTIVATED, CHECKABLE, WINDOW_UN_FOCUSED, NORMAL)
    }
}