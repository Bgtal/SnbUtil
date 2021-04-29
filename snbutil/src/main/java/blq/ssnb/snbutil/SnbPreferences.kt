package blq.ssnb.snbutil

import android.content.Context
import android.content.SharedPreferences

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2018/7/27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 用于存储Sp文件
 * ================================================
</pre> *
 */
class SnbPreferences(context: Context,
                     /**
                      * 获取当前存入文件的名字
                      *
                      * @return 当前存入文件的名字
                      */
                     val fileName: String) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    /**
     * 保存boolean参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 boolean 类型
     */
    fun save(name: String?, content: Boolean) {
        editor.putBoolean(name, content)
        editor.commit()
    }

    /**
     * 保存float参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 float 类型
     */
    fun save(name: String?, content: Float) {
        editor.putFloat(name, content)
        editor.commit()
    }

    /**
     * 保存 int 参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 int 类型
     */
    fun save(name: String?, content: Int) {
        editor.putInt(name, content)
        editor.commit()
    }

    /**
     * 保存 long 参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 long 类型
     */
    fun save(name: String?, content: Long) {
        editor.putLong(name, content)
        editor.commit()
    }

    /**
     * 保存 String 参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 String 类型
     */
    fun save(name: String?, content: String?) {
        editor.putString(name, content)
        editor.commit()
    }

    /**
     * 保存 set
     *
     * @param name   保存参数
     * @param values 保存参数的值 Set 类型
     */
    fun save(name: String?, values: Set<String?>?) {
        editor.putStringSet(name, values).commit()
    }
    /**
     * 读取保存的boolean参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return boolean类型的参数
     * 如果该参数不存在就返回 defValue
     */
    /**
     * 读取保存的boolean参数，
     *
     * @param name 需要读取的参数的key名称
     * @return boolean类型的参数值
     * 如果该参数不存在返回false
     */
    @JvmOverloads
    fun readBoolean(name: String?, defValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(name, defValue)
    }
    /**
     * 读取保存的float参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return float类型的参数值
     * 如果该参数不存在就返回 defValue
     */
    /**
     * 读取保存的float参数
     *
     * @param name 需要读取的参数的key名称
     * @return float类型的参数值
     * 如果该参数不存在就返回 -1f
     */
    @JvmOverloads
    fun readFloat(name: String?, defValue: Float = -1f): Float {
        return sharedPreferences.getFloat(name, defValue)
    }
    /**
     * 读取保存的int参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return int类型的参数值
     * 如果该参数不存在就返回 defValue
     */
    /**
     * 读取保存的int参数
     *
     * @param name 需要读取的参数的key名称
     * @return int类型的参数值
     * 如果该参数不存在返回-1
     */
    @JvmOverloads
    fun readInt(name: String?, defValue: Int = -1): Int {
        return sharedPreferences.getInt(name, defValue)
    }
    /**
     * 读取保存的long参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return long类型的参数值
     * 如果该参数不存在就返回 defValue
     */
    /**
     * 读取保存的long参数
     *
     * @param name 需要读取的参数的key名称
     * @return long类型的参数值
     * 如果该参数不存在就返回-1
     */
    @JvmOverloads
    fun readLong(name: String?, defValue: Long = -1): Long {
        return sharedPreferences.getLong(name, defValue)
    }
    /**
     * 读取保存的StringSet
     *
     * @param name     key
     * @param defValue 默认
     * @return Set
     */
    /**
     * 读取保存的StringSet
     *
     * @param name key
     * @return Set
     */
    @JvmOverloads
    fun readStringSet(name: String?, defValue: Set<String?>? = null): Set<String>? {
        return sharedPreferences.getStringSet(name, defValue)
    }
    /**
     * 读取保存的String参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return String 类型的参数
     * 如果该参数不存在就返回 defValue
     */
    /**
     * 读取保存的String参数，
     *
     * @param name 需要读取的参数的key名称
     * @return String类型的参数值
     * 如果该参数不存在返回""
     */
    @JvmOverloads
    fun readString(name: String?, defValue: String? = ""): String? {
        return sharedPreferences.getString(name, defValue)
    }

    /**
     * 读取所有存储在xml文件中的key-value
     *
     * @return 存储的所有key-value的map
     */
    fun readAll(): Map<String, *> {
        return sharedPreferences.all
    }

    /**
     * 移除key
     *
     * @param key 需要移除的key对象
     */
    fun remove(key: String?) {
        editor.remove(key).commit()
        editor.commit()
    }

    /**
     * 清理所有的内容
     */
    fun clear() {
        editor.clear().commit()
    }

    /**
     * @param context  上下文对象
     * @param fileName 存储数据xml的名称
     */
    init {
        editor.apply()
    }
}