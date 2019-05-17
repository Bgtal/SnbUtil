package blq.ssnb.snbutil.kit;

import blq.ssnb.snbutil.R;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/5/16
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public enum SnbSelectState {
    NORMAL(0),
    CHECKED(android.R.attr.state_checked),
    FOCUSED(android.R.attr.state_focused),
    HOVERED(android.R.attr.state_hovered),
    PRESSED(android.R.attr.state_pressed),
    DISABLED(-android.R.attr.state_enabled),
    SELECTED(android.R.attr.state_selected),
    ACTIVATED(android.R.attr.state_activated),
    CHECKABLE(android.R.attr.state_checkable),
    WINDOW_UN_FOCUSED(-android.R.attr.state_window_focused),;

    int stateValue;

    SnbSelectState(int stateValue) {
        this.stateValue = stateValue;
    }

    public int getStateValue() {
        return stateValue;
    }

    public int getUnStateValue() {
        return -stateValue;
    }

    private static SnbSelectState[] selectStatesList;

    /**
     * @note 这里的状态列表不要轻易的改变，这个涉及到select的状态显示
     * @return 状态的列表
     */
    public static SnbSelectState[] getSelectStatesList() {
        if (selectStatesList == null) {
            selectStatesList = new SnbSelectState[]{
                    FOCUSED,
                    HOVERED,
                    CHECKED,
                    PRESSED,
                    DISABLED,
                    SELECTED,
                    ACTIVATED,
                    CHECKABLE,
                    WINDOW_UN_FOCUSED,
                    NORMAL,
            };
        }
        return selectStatesList;
    }
}
