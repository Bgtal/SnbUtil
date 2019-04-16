package blq.ssnb.snbutil;

import blq.ssnb.snbutil.constant.SnbByteConstant;

/**
 * <pre>
 * ================================================
 * 作者: SSNB
 * 日期: 2017/6/26
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 字节单位间的互相转换
 * ================================================
 * </pre>
 */

public class SnbByteConversionUtil {
    /**
     * 字节单位
     */
    public enum ByteUnit{
        B,KB,MB,GB,TB,PB;
    }

    private SnbByteConversionUtil(){
        throw new SnbIllegalInstantiationException();
    }

    /**
     * 单位互相转换
     * 例如 将M单位 的 转化为 KB单位
     * @param size 大小
     * @param type 原始单位
     * @param toType 目标单位
     * @return 转换后的单位
     */
    public static double unitConversion(double size,ByteUnit type,ByteUnit toType){
        long fromTypeUnit = getUnit(type);
        long toTypeUnit = getUnit(toType);
        return size*fromTypeUnit/toTypeUnit;
    }

    private static long getUnit(ByteUnit type){
        long typeUnit = -1;
        switch (type){
            case B:
                typeUnit = SnbByteConstant.ONE_B;
                break;
            case KB:
                typeUnit = SnbByteConstant.ONE_KB;
                break;
            case MB:
                typeUnit = SnbByteConstant.ONE_MB;
                break;
            case GB:
                typeUnit = SnbByteConstant.ONE_GB;
                break;
            case TB:
                typeUnit = SnbByteConstant.ONE_TB;
                break;
            case PB:
                typeUnit = SnbByteConstant.ONE_PB;
                break;
        }
        return typeUnit;
    }

}
