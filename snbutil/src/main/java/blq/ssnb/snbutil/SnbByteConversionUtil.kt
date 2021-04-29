package blq.ssnb.snbutil

import blq.ssnb.snbutil.constant.SnbByteConstant

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
</pre> *
 */
object SnbByteConversionUtil {
    /**
     * 字节单位
     */
    enum class ByteUnit {
        B, KB, MB, GB, TB, PB
    }

    /**
     * 单位互相转换
     * 例如 将M单位 的 转化为 KB单位
     * @param size 大小
     * @param type 原始单位
     * @param toType 目标单位
     * @return 转换后的单位
     */
    @JvmStatic
    fun unitConversion(size: Double, type: ByteUnit, toType: ByteUnit): Double {
        val fromTypeUnit: Long = getUnit(type)
        val toTypeUnit: Long = getUnit(toType)
        return size * fromTypeUnit / toTypeUnit
    }

    private fun getUnit(type: ByteUnit): Long {
        return when (type) {
            ByteUnit.B -> SnbByteConstant.ONE_B
            ByteUnit.KB -> SnbByteConstant.ONE_KB
            ByteUnit.MB -> SnbByteConstant.ONE_MB
            ByteUnit.GB -> SnbByteConstant.ONE_GB
            ByteUnit.TB -> SnbByteConstant.ONE_TB
            ByteUnit.PB -> SnbByteConstant.ONE_PB
        }
    }
}
