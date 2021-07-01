package blq.ssnb.snbutil

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2016/9/18
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 2
 * 描述:
 * 加密工具
 * 主要实现
 * 1 MD5加密
 * 2 SHA加密
 * 3 增加混淆因子的加密
 * ================================================
</pre> *
 */
object SnbEncryptionUtil {
    private const val MD5: String = "MD5"
    private const val SHA: String = "SHA"
    private val digits: CharArray = charArrayOf(
        '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'a', 'b',
        'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z'
    )

    /**
     * MD5加密
     * @param message 待加密数据
     * @return 加密完成后的数据 27:52:6F:FE:2D:3C:4A:34:6E:E3:A3:08:82:36:35:E1
     */
    @JvmStatic
    fun encryptMD5(message: String): String {
        return encryptByStyle(MD5, message, null)
    }

    /**
     * SHA加密
     * @param message 待加密数据
     * @return 加密完成后的数据 5D:23:15:F5:E2:E1:73:92:68:6E:78:0D:C6:50:02:DA:0A:B8:24:59
     */
    @JvmStatic
    fun encryptSHA(message: String): String {
        return encryptByStyle(SHA, message, null)
    }

    /**
     * MD5加密 同时添加混淆因子
     * @param message 待加密数据
     * @param confusion 混淆因子
     * @return 加密完成后的数据 27:52:6F:FE:2D:3C:4A:34:6E:E3:A3:08:82:36:35:E1
     */
    @JvmStatic
    fun encryptMD5Confusion(message: String, confusion: IntArray?): String {
        return encryptByStyle(MD5, message, confusion)
    }

    /**
     * SHA加密 同时添加混淆因子
     * @param message 待加密数据
     * @param confusion 混淆因子
     * @return 加密完成后的数据 5D:23:15:F5:E2:E1:73:92:68:6E:78:0D:C6:50:02:DA:0A:B8:24:59
     */
    @JvmStatic
    fun encryptSHAWithConfusion(message: String, confusion: IntArray?): String {
        return encryptByStyle(SHA, message, confusion)
    }

    private fun encryptByStyle(algorithm: String, message: String, confusion: IntArray?): String {
        var nMessage = message
        val value: String
        try {
            val md: MessageDigest = MessageDigest.getInstance(algorithm)
            nMessage = confusionMessage(nMessage, confusion)
            md.update(nMessage.toByteArray())
            value = byte2hex(md.digest())
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            throw IllegalAccessError("加密出错")
        }
        return value
    }

    /**
     * 获得混淆后的字符串
     * @param message 字符串
     * @param confusion 混淆因子
     * @return 如果混淆因子为空，就返回原来的字符串
     */
    private fun confusionMessage(message: String, confusion: IntArray?): String {
        if ((confusion != null && confusion.isNotEmpty())) {
            //如果混淆因子不为空
            val builder: StringBuilder = StringBuilder(message)
            val mask: Int = 0xF //1111
            var buf: Char //中间变量
            var index: Int
            for (c: Int in confusion) { //从混淆因子中获得数据
                val mc = if (c < 0) -c else c
                buf = digits[mc and mask] //获得字符
                index = mc % builder.length
                builder.insert(index, buf)
            }
            return builder.toString()
        } else {
            return message
        }
    }

    /**
     * 将加密后的数据转换为16位格式输出
     * @param b 加密后的数据
     * @return 格式化后的数据
     */
    private fun byte2hex(b: ByteArray): String {
        val hsBuilder: StringBuilder = StringBuilder()
        for (by: Byte in b) {
            val temp = by.toInt().and(0xFF)
            if (temp < 0x10) {
                hsBuilder.append("0")
            }
            hsBuilder.append(Integer.toHexString(temp)).append(":")
        }
        if (b.isNotEmpty()) {
            hsBuilder.deleteCharAt(hsBuilder.length - 1)
        }
        return hsBuilder.toString().toUpperCase()
    }
}
