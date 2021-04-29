package blq.ssnb.snbutil

import android.util.Log
import androidx.annotation.IntDef
import java.util.*

/**
 * <pre>
 * ================================================
 * 作者: SSNB
 * 日期：2017/4/4
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 4
 * 1.Log打印工具
 * 2.增加Log打印位置的输出
 * 3.增加Log的Builder，控制Log的打印规则
 * 4.增加了自定义tag，主要是用于区分不同项目下的Log打印(需要自己管理Builder的TAG及时关闭，防止Log的信息泄露)
 * 描述:自定义的日志工具类.
 * 1.可以全局设置是否打印Log
 * 2.可以设置是否在log中添加开始和结束的分界线
 * 3.可以设置是否显示调用log的位置(在Logcat 中可以直接点击跳转到调用位置)
 * 4.直接打印错误信息
 * 5.可以自定义特定的tag，可以用于区分在不同项目下的LOG
 * ================================================
</pre> *
 */
object SnbLog {
    @IntDef(V, D, I, W, E, A, ERROR)
    @Retention(AnnotationRetention.SOURCE)
    annotation class TYPE

    /**
     * Log 打印的构造器 用来配置打印参数
     */
    class Builder {
        var mTag: String
        var isOpen: Boolean
        var isShowLocation: Boolean
        var isShowBorderLine: Boolean

        /**
         * 设置Log 的tag
         * @param tag tag
         * @return Builder
         */
        fun setTag(tag: String): Builder {
            require(!tag.trim { it <= ' ' }.isEmpty()) { "设置的全局TAG为空，请检查" }
            mTag = tag
            return this
        }

        /**
         * 是否开启当前构造器的Log 打印（全局打印关闭的话无论是否开启都不会打印）
         * @param isOpen true 开启
         * @return Builder
         */
        fun isOpen(isOpen: Boolean): Builder {
            this.isOpen = isOpen
            return this
        }

        /**
         * 是否显示Log打印的位子
         * @param isShow true 显示
         * @return Builder
         */
        fun isShowLocation(isShow: Boolean): Builder {
            isShowLocation = isShow
            return this
        }

        /**
         * 是否显示 Log 的上限边界
         * @param isShow true 显示
         * @return Builder
         */
        fun isShowBorderLine(isShow: Boolean): Builder {
            isShowBorderLine = isShow
            return this
        }

        init {
            mTag = DEFAULT_TAG
            isOpen = false
            isShowLocation = false
            isShowBorderLine = false
        }
    }

    private val buildCache: MutableMap<String, Builder> = HashMap()
    private const val DEFAULT_TAG = "SnbLog"
    private const val V = 0x01
    private const val D = 0x02
    private const val I = 0x03
    private const val W = 0x04
    private const val E = 0x05
    private const val A = 0x06
    private const val ERROR = 0X07
    private const val START_LINE = "====================Start===================="
    private const val END_LINE = "---------------------End---------------------"
    private var GLOBAL_LOG_PRINT = true

    /**
     * 请务必在发布的时候调用该方法设置为false，防止log信息泄露
     * @param isOpen 全局开启或关闭log打印，
     */
    fun setGlobalLogPrint(isOpen: Boolean) {
        GLOBAL_LOG_PRINT = isOpen
    }


    @JvmStatic
    fun v(content: String?) {
        v(globalBuilder!!.mTag, content)
    }

    @JvmStatic
    fun v(tag: String?, content: String?) {
        printLog(V, globalBuilder, tag, content)
    }

    @JvmStatic
    fun sv(buildTag: String, content: String?) {
        printLog(V, getBuilder(buildTag), null, content)
    }

    @JvmStatic
    fun d(content: String?) {
        d(globalBuilder!!.mTag, content)
    }

    @JvmStatic
    fun d(tag: String?, content: String?) {
        printLog(D, globalBuilder, tag, content)
    }

    @JvmStatic
    fun sd(buildTag: String, content: String?) {
        printLog(D, getBuilder(buildTag), null, content)
    }

    @JvmStatic
    fun i(content: String?) {
        i(globalBuilder.mTag, content)
    }

    @JvmStatic
    fun i(tag: String?, content: String?) {
        printLog(I, globalBuilder, tag, content)
    }

    @JvmStatic
    fun si(buildTag: String, content: String?) {
        printLog(I, getBuilder(buildTag), null, content)
    }

    @JvmStatic
    fun w(content: String?) {
        w(globalBuilder.mTag, content)
    }

    @JvmStatic
    fun w(tag: String?, content: String?) {
        printLog(W, globalBuilder, tag, content)
    }

    @JvmStatic
    fun sw(buildTag: String, content: String?) {
        printLog(W, getBuilder(buildTag), null, content)
    }

    @JvmStatic
    fun e(content: String?) {
        e(globalBuilder!!.mTag, content)
    }

    @JvmStatic
    fun e(tag: String?, content: String?) {
        printLog(E, globalBuilder, tag, content)
    }

    @JvmStatic
    fun se(buildTag: String, content: String?) {
        printLog(E, getBuilder(buildTag), null, content)
    }

    @JvmStatic
    fun a(content: String?) {
        a(globalBuilder!!.mTag, content)
    }

    @JvmStatic
    fun a(tag: String?, content: String?) {
        printLog(A, globalBuilder, tag, content)
    }

    @JvmStatic
    fun sa(buildTag: String, content: String?) {
        printLog(A, getBuilder(buildTag), null, content)
    }

    @JvmStatic
    fun error(e: Throwable?) {
        error(globalBuilder!!.mTag, e)
    }

    @JvmStatic
    fun error(tag: String?, e: Throwable?) {
        printLog(ERROR, globalBuilder, tag, Log.getStackTraceString(e))
    }

    /**
     * 打印log
     * @param type log 类型 [TYPE]
     * @param buildTag 打印的tag 如果为空或者null 那就设置为 TAG
     * @param contentMsg log 内容
     */
    private fun printLog(@TYPE type: Int, builder: Builder?, buildTag: String?, contentMsg: String?) {
        var tag = buildTag
        var content = contentMsg
        if (!GLOBAL_LOG_PRINT || builder?.isOpen != true) {
            //如果全局打印关闭 或者 builder 关闭那么就返回
            return
        }
        content = isEmpty(content)
        if (tag == null || tag.trim { it <= ' ' }.isEmpty()) {
            tag = builder.mTag
        }
        if (builder.isShowLocation) {
            content = head + content
        }
        when (type) {
            V -> if (builder.isShowBorderLine) {
                Log.v(tag, START_LINE)
                Log.v(tag, content)
                Log.v(tag, END_LINE)
            } else {
                Log.v(tag, content)
            }
            D -> if (builder.isShowBorderLine) {
                Log.d(tag, START_LINE)
                Log.d(tag, content)
                Log.d(tag, END_LINE)
            } else {
                Log.d(tag, content)
            }
            I -> if (builder.isShowBorderLine) {
                Log.i(tag, START_LINE)
                Log.i(tag, content)
                Log.i(tag, END_LINE)
            } else {
                Log.i(tag, content)
            }
            W -> if (builder.isShowBorderLine) {
                Log.w(tag, START_LINE)
                Log.w(tag, content)
                Log.w(tag, END_LINE)
            } else {
                Log.w(tag, content)
            }
            E -> if (builder.isShowBorderLine) {
                Log.e(tag, START_LINE)
                Log.e(tag, content)
                Log.e(tag, END_LINE)
            } else {
                Log.e(tag, content)
            }
            A -> if (builder.isShowBorderLine) {
                Log.wtf(tag, START_LINE)
                Log.wtf(tag, content)
                Log.wtf(tag, END_LINE)
            } else {
                Log.wtf(tag, content)
            }
            ERROR -> {
                Log.e(tag, START_LINE)
                Log.e(tag, content)
                Log.e(tag, END_LINE)
            }
            else -> {
                Log.e(tag, START_LINE)
                Log.e(tag, content)
                Log.e(tag, END_LINE)
            }
        }
    }

    /**
     * 判断打印内容是否为空
     * @param content 待检测的内容
     * @return 检查后的文本内容
     */
    private fun isEmpty(content: String?): String {
        var msg = content
        if (content == null) {
            msg = "msg is null"
        } else if (content.trim { it <= ' ' }.isEmpty()) {
            msg = "msg is empty"
        }
        return msg!!
    }

    /**
     * 获取头部信息 当前线程和调用位置
     * @return 格式化后的头部信息
     */
    private val head: String
        get() = try {
            val elements = Thread.currentThread().stackTrace
            var element = elements[5]
            if (element.className == SnbLog::class.java.name) {
                element = elements[6]
            }
            Formatter()
                    .format("Thread: %s, at %s\n",
                            Thread.currentThread().name,
                            element.toString())
                    .toString()
        } catch (ignored: Exception) {
            ""
        }
    private var mBuilder: Builder? = null
    private const val GLOBAL_BUILDER_TAG = "snb_global_tag"

    /**
     * 获取全局LOG打印的构建器
     * @return log构建器
     */
    @JvmStatic
    val globalBuilder: Builder
        get() {
            if (mBuilder == null) {
                mBuilder = getBuilder(GLOBAL_BUILDER_TAG)
            }
            return mBuilder as Builder
        }

    /**
     * 单独设置一个标记为buildTag 的构建器
     * @param buildTag 构建器标识
     * @return 构建器
     */
    @JvmStatic
    fun getBuilder(buildTag: String): Builder {
        var builder = buildCache[buildTag]
        if (builder == null) {
            builder = Builder()
            buildCache[buildTag] = builder
        }
        return builder
    }
}