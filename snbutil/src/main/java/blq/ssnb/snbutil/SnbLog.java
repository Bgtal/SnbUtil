package blq.ssnb.snbutil;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

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
 *     1.可以全局设置是否打印Log
 *     2.可以设置是否在log中添加开始和结束的分界线
 *     3.可以设置是否显示调用log的位置(在Logcat 中可以直接点击跳转到调用位置)
 *     4.直接打印错误信息
 *     5.可以自定义特定的tag，可以用于区分在不同项目下的LOG
 * ================================================
 * </pre>
 */

public class SnbLog {

    private static Map<String,Builder> buildCache = new HashMap<>();

    private static final String DEFAULT_TAG = "SnbLog";
    private static final int V = 0x01;
    private static final int D = 0x02;
    private static final int I = 0x03;
    private static final int W = 0x04;
    private static final int E = 0x05;
    private static final int A = 0x06;
    private static final int ERROR = 0X07;

    private static final String START_LINE = "====================Start====================";
    private static final String END_LINE =   "---------------------End---------------------";

    @IntDef({V,D,I,W,E,A,ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE{}

    private SnbLog(){
        throw new SnbIllegalInstantiationException();
    }

    private static boolean GLOBAL_LOG_PRINT = true;

    /**
     * 请务必在发布的时候调用该方法设置为false，防止log信息泄露
     * @param isOpen 全局开启或关闭log打印，
     */
    public static void setGlobalLogPrint(boolean isOpen){
        GLOBAL_LOG_PRINT = isOpen;
    }

    public static void v(String content){
        v(getGlobalBuilder().mTag,content);
    }
    public static void v(String tag, String content){
        printLog(V,getGlobalBuilder(),tag,content);
    }
    public static void sv(String buildTag, String content){
        printLog(V,getBuilder(buildTag),null,content);
    }

    public static void d(String content){
        d(getGlobalBuilder().mTag,content);
    }
    public static void d(String tag, String content){
        printLog(D,getGlobalBuilder(),tag,content);
    }
    public static void sd(String buildTag, String content){
        printLog(D,getBuilder(buildTag),null,content);
    }

    public static void i(String content){
        i(getGlobalBuilder().mTag,content);
    }
    public static void i(String tag, String content){
        printLog(I,getGlobalBuilder(),tag,content);
    }
    public static void si(String buildTag, String content){
        printLog(I,getBuilder(buildTag),null,content);
    }

    public static void w(String content){
        w(getGlobalBuilder().mTag,content);
    }
    public static void w(String tag, String content){
        printLog(W,getGlobalBuilder(),tag,content);
    }
    public static void sw(String buildTag, String content){
        printLog(W,getBuilder(buildTag),null,content);
    }

    public static void e(String content){
        e(getGlobalBuilder().mTag,content);
    }
    public static void e(String tag, String content){
        printLog(E,getGlobalBuilder(),tag,content);
    }
    public static void se(String buildTag, String content){
        printLog(E,getBuilder(buildTag),null,content);
    }

    public static void a(String content){
        a(getGlobalBuilder().mTag,content);
    }
    public static void a(String tag, String content){
        printLog(A,getGlobalBuilder(),tag,content);
    }
    public static void sa(String buildTag, String content){
        printLog(A,getBuilder(buildTag),null,content);
    }

    public static void error(Throwable e){
        error(getGlobalBuilder().mTag,e);
    }

    public static void error(String tag, Throwable e){
        printLog(ERROR,getGlobalBuilder(),tag,Log.getStackTraceString(e));
    }

    /**
     * 打印log
     * @param type log 类型 {@link TYPE}
     * @param tag 打印的tag 如果为空或者null 那就设置为 TAG
     * @param content log 内容
     */
    private static void printLog(@TYPE int type, Builder builder, String tag, String content){
        if(!GLOBAL_LOG_PRINT || !builder.isOpen){
            //如果全局打印关闭 或者 builder 关闭那么就返回
            return;
        }

        content = isEmpty(content);
        if(tag==null||tag.trim().isEmpty()){
            tag = builder.mTag;
        }
        if(builder.isShowLocation){
            content = getHead()+content;
        }

        switch (type){
            case V:
                if(builder.isShowBorderLine){
                    Log.v(tag,START_LINE);
                    Log.v(tag,content);
                    Log.v(tag,END_LINE);
                }else{
                    Log.v(tag,content);
                }
                break;
            case D:
                if(builder.isShowBorderLine){
                    Log.d(tag,START_LINE);
                    Log.d(tag,content);
                    Log.d(tag,END_LINE);
                }else{
                    Log.d(tag,content);
                }
                break;
            case I:
                if(builder.isShowBorderLine){
                    Log.i(tag,START_LINE);
                    Log.i(tag,content);
                    Log.i(tag,END_LINE);
                }else{
                    Log.i(tag,content);
                }
                break;
            case W:
                if(builder.isShowBorderLine){
                    Log.w(tag,START_LINE);
                    Log.w(tag,content);
                    Log.w(tag,END_LINE);
                }else{
                    Log.w(tag,content);
                }
                break;
            case E:
                if(builder.isShowBorderLine){
                    Log.e(tag,START_LINE);
                    Log.e(tag,content);
                    Log.e(tag,END_LINE);
                }else{
                    Log.e(tag,content);
                }
                break;
            case A:
                if(builder.isShowBorderLine){
                    Log.wtf(tag,START_LINE);
                    Log.wtf(tag,content);
                    Log.wtf(tag,END_LINE);
                }else{
                    Log.wtf(tag,content);
                }
                break;

            case ERROR:
            default:
                Log.e(tag,START_LINE);
                Log.e(tag,content);
                Log.e(tag,END_LINE);
        }
    }

    /**
     * 判断打印内容是否为空
     * @param content 待检测的内容
     * @return 检查后的文本内容
     */
    private static String isEmpty(String content){
        String msg = content;
        if(content==null){
            msg = "msg is null";
        }else if(content.trim().isEmpty()){
            msg = "msg is empty";
        }
        return msg;
    }

    /**
     * 获取头部信息 当前线程和调用位置
     * @return 格式化后的头部信息
     */
    private static String getHead(){
        try {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            StackTraceElement element = elements[5];
            if(element.getClassName().equals(SnbLog.class.getName())){
                element = elements[6];
            }
            return new Formatter()
                    .format("Thread: %s, at %s\n",
                            Thread.currentThread().getName(),
                            element.toString())
                    .toString();
        }catch (Exception ignored){
            return "";
        }
    }


    /**
     * Log 打印的构造器 用来配置打印参数
     */
    public static class Builder{
        private String mTag;
        private boolean isOpen;
        private boolean isShowLocation;
        private boolean isShowBorderLine;

        private Builder(){

            mTag = DEFAULT_TAG;
            isOpen = false;
            isShowLocation = false;
            isShowBorderLine = false;
        }

        /**
         * 设置Log 的tag
         * @param tag tag
         * @return Builder
         */
        public Builder setTag(@NonNull String tag){
            if(tag.trim().isEmpty()){
                throw new IllegalArgumentException("设置的全局TAG为空，请检查");
            }
            mTag = tag;
            return this;
        }

        /**
         * 是否开启当前构造器的Log 打印（全局打印关闭的话无论是否开启都不会打印）
         * @param isOpen true 开启
         * @return Builder
         */
        public Builder isOpen(boolean isOpen){
            this.isOpen = isOpen;
            return this;
        }

        /**
         * 是否显示Log打印的位子
         * @param isShow true 显示
         * @return Builder
         */
        public Builder isShowLocation(boolean isShow){
            this.isShowLocation = isShow;
            return this;
        }

        /**
         * 是否显示 Log 的上限边界
         * @param isShow true 显示
         * @return Builder
         */
        public Builder isShowBorderLine(boolean isShow){
            this.isShowBorderLine = isShow;
            return this;
        }
    }

    private static Builder mBuilder;
    private static final String GLOBAL_BUILDER_TAG = "snb_global_tag";

    /**
     * 获取全局LOG打印的构建器
     * @return log构建器
     */
    public static Builder getGlobalBuilder(){
        if(mBuilder == null){
            mBuilder = getBuilder(GLOBAL_BUILDER_TAG);
        }
        return mBuilder;
    }

    /**
     * 单独设置一个标记为buildTag 的构建器
     * @param buildTag 构建器标识
     * @return 构建器
     */
    public static Builder getBuilder(String buildTag){
        Builder builder = buildCache.get(buildTag) ;
        if(builder == null){
            builder = new Builder();
            buildCache.put(buildTag,builder);
        }
        return builder;
    }

}
