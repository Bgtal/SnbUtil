package blq.ssnb.snbutil;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
 * </pre>
 */

public class SnbPreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String fileName;

    /**
     * @param context  上下文对象
     * @param fileName 存储数据xml的名称
     */
    public SnbPreferences(Context context, String fileName) {
        this.fileName = fileName;
        sharedPreferences = context.getSharedPreferences(
                fileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    /**
     * 保存boolean参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 boolean 类型
     */
    public void save(String name, boolean content) {
        editor.putBoolean(name, content);
        editor.commit();
    }

    /**
     * 保存float参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 float 类型
     */
    public void save(String name, float content) {
        editor.putFloat(name, content);
        editor.commit();
    }

    /**
     * 保存 int 参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 int 类型
     */
    public void save(String name, int content) {
        editor.putInt(name, content);
        editor.commit();
    }

    /**
     * 保存 long 参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 long 类型
     */
    public void save(String name, long content) {
        editor.putLong(name, content);
        editor.commit();
    }

    /**
     * 保存 String 参数
     *
     * @param name    保存参数的key名称
     * @param content 保存参数的值 String 类型
     */
    public void save(String name, String content) {
        editor.putString(name, content);
        editor.commit();
    }

    /**
     * 保存 set
     *
     * @param name   保存参数
     * @param values 保存参数的值 Set 类型
     */
    public void save(String name, Set<String> values) {
        editor.putStringSet(name, values).commit();
    }

    /**
     * 读取保存的boolean参数，
     *
     * @param name 需要读取的参数的key名称
     * @return boolean类型的参数值
     * 如果该参数不存在返回false
     */
    public boolean readBoolean(String name) {
        return readBoolean(name, false);
    }

    /**
     * 读取保存的boolean参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return boolean类型的参数
     * 如果该参数不存在就返回 defValue
     */
    public boolean readBoolean(String name, boolean defValue) {
        return sharedPreferences.getBoolean(name, defValue);
    }

    /**
     * 读取保存的float参数
     *
     * @param name 需要读取的参数的key名称
     * @return float类型的参数值
     * 如果该参数不存在就返回 -1f
     */
    public float readFloat(String name) {
        return readFloat(name, -1f);
    }

    /**
     * 读取保存的float参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return float类型的参数值
     * 如果该参数不存在就返回 defValue
     */
    public float readFloat(String name, float defValue) {
        return sharedPreferences.getFloat(name, defValue);
    }

    /**
     * 读取保存的int参数
     *
     * @param name 需要读取的参数的key名称
     * @return int类型的参数值
     * 如果该参数不存在返回-1
     */
    public int readInt(String name) {
        return readInt(name, -1);
    }

    /**
     * 读取保存的int参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return int类型的参数值
     * 如果该参数不存在就返回 defValue
     */
    public int readInt(String name, int defValue) {
        return sharedPreferences.getInt(name, defValue);
    }

    /**
     * 读取保存的long参数
     *
     * @param name 需要读取的参数的key名称
     * @return long类型的参数值
     * 如果该参数不存在就返回-1
     */
    public long readLong(String name) {
        return readLong(name, -1);
    }

    /**
     * 读取保存的long参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return long类型的参数值
     * 如果该参数不存在就返回 defValue
     */
    public long readLong(String name, long defValue) {
        return sharedPreferences.getLong(name, defValue);
    }

    /**
     * 读取保存的String参数，
     *
     * @param name 需要读取的参数的key名称
     * @return String类型的参数值
     * 如果该参数不存在返回""
     */
    public String readString(String name) {
        return readString(name, "");
    }

    /**
     * 读取保存的StringSet
     *
     * @param name key
     * @return Set
     */
    public Set<String> readStringSet(String name) {
        return readStringSet(name, null);
    }

    /**
     * 读取保存的StringSet
     *
     * @param name     key
     * @param defValue 默认
     * @return Set
     */
    public Set<String> readStringSet(String name, Set<String> defValue) {
        return sharedPreferences.getStringSet(name, defValue);
    }

    /**
     * 读取保存的String参数
     *
     * @param name     需要读取的参数的key名称
     * @param defValue 如果读取失败默认返回值
     * @return String 类型的参数
     * 如果该参数不存在就返回 defValue
     */
    public String readString(String name, String defValue) {
        return sharedPreferences.getString(name, defValue);
    }

    /**
     * 读取所有存储在xml文件中的key-value
     *
     * @return 存储的所有key-value的map
     */
    public Map<String, ?> readAll() {
        return sharedPreferences.getAll();
    }

    /**
     * 获取当前存入文件的名字
     *
     * @return 当前存入文件的名字
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 移除key
     *
     * @param key 需要移除的key对象
     */
    public void remove(String key) {
        editor.remove(key).commit();
        editor.commit();
    }

    /**
     * 清理所有的内容
     */
    public void clear() {
        editor.clear().commit();
    }

}
