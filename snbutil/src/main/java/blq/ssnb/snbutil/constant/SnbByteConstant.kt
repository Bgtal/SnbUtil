package blq.ssnb.snbutil.constant

/**
 * <pre>
 * ================================================
 * 作者: SSNB
 * 日期: 2017/6/26
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 存储单位常量值(基础单位为Byte)
 * ================================================
</pre> *
 */
object SnbByteConstant {
    private const val MULTIPLE: Long = 1024
    //            1B（byte，字节）= 8 bit(位)；
    /**
     * 1Byte = 1 Byte
     */
    const val ONE_B: Long = 1

    /**
     * 1KB = 1024 Byte
     */
    const val ONE_KB = MULTIPLE * ONE_B

    /**
     * 1MB = 1024 KB
     */
    const val ONE_MB = MULTIPLE * ONE_KB

    /**
     * 1G = 1024 MB
     */
    const val ONE_GB = MULTIPLE * ONE_MB

    /**
     * 1TB = 1024 G
     */
    const val ONE_TB = MULTIPLE * ONE_GB

    /**
     * 1PB = 1024 TB
     */
    const val ONE_PB = MULTIPLE * ONE_TB
}