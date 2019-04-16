package blq.ssnb.snbutil.kit;

import android.util.LongSparseArray;

import java.util.Locale;

import blq.ssnb.snbutil.constant.SnbTimeConstant;
import blq.ssnb.snbutil.constant.SnbTimeConstant.TimeUtil;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 时间差的format 用于自定义不同时间差的显示内容
 * 目前请直接使用 {@link SimpleTimeDifferenceFormat}
 * ================================================
 * </pre>
 */

public class TimeDifferenceFormat {
    private static final String PLACE_HOLDER = "%d";
    private LongSparseArray<String> formatKeyValue;
    private String normalMsg;
    private long normalLimit;

    TimeDifferenceFormat(){
        this("%ds",SnbTimeConstant.ONE_SECONDS);
    }

    TimeDifferenceFormat(String normalMsg, long normalLimit){
        formatKeyValue = new LongSparseArray<>();
        this.normalMsg = normalMsg;
        this.normalLimit = normalLimit;
    }

    void setNormalMsg(String normalMsg, long normalLimit){
        this.normalMsg = normalMsg;
        this.normalLimit = normalLimit;
    }

    /**
     * 添加判断条件
     * 例：添加 addFormat(SnbTimeConstant.ONE_MINUTES,"%d分钟前");
     *    addFormat(SnbTimeConstant.HALF_AN_HOUR,"半小时前");
     * 当时间为8分钟的时候会显示8分钟前，当时间为38分钟的时候会显示半小时前
     * @param upLimit 时间上限
     * @param intervalMsg 时间区间内返回的内容,msg 中可以包含且只能包含一个"%d" 并且%d 的值是当前时间上线的单位个数
     *                    如果时间上限为30分钟 输入的时间是36分钟如果{intervalMsg} 中包含%d 那么format  的值为36，
     *                    如果时间上限为60分钟(即1小时) 输入的时间是36分钟如果{intervalMsg} 中包含%d 那么format  的值为0,
     * @return TimeDifferenceFormat
     */
    <T extends TimeDifferenceFormat>  T addFormat(@TimeUtil long upLimit , String intervalMsg){
        if(intervalMsg.split(PLACE_HOLDER).length > 2){
            throw new IllegalAccessError("提示中最多只能出现一次"+PLACE_HOLDER);
        }
        formatKeyValue.put(upLimit,intervalMsg);
        return (T)this;
    }

    /**
     * 移除判断时间
     * @param upLimit 上限时间
     * @param <T> TimeDifferenceFormat 的继承类 目前只有{@link SimpleTimeDifferenceFormat}
     * @return TimeDifferenceFormat
     */
    <T extends TimeDifferenceFormat>  T removeFormat(long upLimit){
        formatKeyValue.remove(upLimit);
        return (T)this;
    }

    public String format(long differenceTime){
        if(formatKeyValue.size() == 0 ){
            return formatBorder(normalLimit,differenceTime,normalMsg);
        }
        String formatStr = null;
        for (int i = formatKeyValue.size()-1;i>=0;i--){
            //获取边界值
            long limit = formatKeyValue.keyAt(i);
            String format = formatKeyValue.valueAt(i);
            if(differenceTime>=limit){
                formatStr = formatBorder(limit,differenceTime,format);
                break;
            }
        }
        if(formatStr == null){
            formatStr = formatBorder(normalLimit,differenceTime,normalMsg);
        }

        return formatStr;
    }

    private String formatBorder(long borderLimit, long differenceTime, String format){
        if(format.contains(PLACE_HOLDER)){
            //获得边界单位级别
            long unit = getLimitUnit(borderLimit);
            int count = (int) (differenceTime/unit);
            return String.format(Locale.getDefault(),format,count);
        }else{
            return format;
        }
    }

    private long getLimitUnit(long limit){
        //除以1000<1 表示毫秒级别
        int temp = (int)(limit/SnbTimeConstant.ONE_SECONDS);
        if( temp < 1){
            return 1;
        }

        //除以60*1000 <1 表示秒级别
        temp = (int) (limit/SnbTimeConstant.ONE_MINUTES);
        if( temp < 1){
            return SnbTimeConstant.ONE_SECONDS;
        }

        //除以60*60*1000 <1 表示分钟级别
        temp = (int) (limit/SnbTimeConstant.ONE_HOURS);
        if(temp < 1){
            return SnbTimeConstant.ONE_MINUTES;
        }

        //除以24*60*60*1000 <1 表示小时级别
        temp = (int) (limit/SnbTimeConstant.ONE_DAY);
        if(temp < 1){
            return SnbTimeConstant.ONE_HOURS;
        }
        //除以7*24*60*60*1000 <1 表示天级别
        temp = (int) (limit/SnbTimeConstant.ONE_WEEK);
        if(temp < 1){
            return SnbTimeConstant.ONE_DAY;
        }

        //除以30*24*60*60*1000 < 1 表示周级别
        temp = (int) (limit/SnbTimeConstant.ONE_MONTH);
        if(temp < 1){
            return SnbTimeConstant.ONE_WEEK;
        }

        //除以365*24*60*60*1000 < 1 表示月级别
        temp = (int) (limit/SnbTimeConstant.ONE_YEAR);
        if( temp < 1){
            return SnbTimeConstant.ONE_MONTH;
        }

        return SnbTimeConstant.ONE_YEAR;
    }

}
