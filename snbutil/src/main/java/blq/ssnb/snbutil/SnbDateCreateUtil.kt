package blq.ssnb.snbutil

import android.os.AsyncTask
import java.util.*

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/2/20
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 一个简单的数据创建工具
 * ================================================
</pre> *
 */
object SnbDateCreateUtil {
    //异步创建数据
    @JvmStatic
    fun <T> asyCreateListData(createFactory: DataCreateFactory<T?>, callBack: DataCallBack<List<T?>?>?) {
        CreateTask(createFactory, callBack).execute(createFactory.dataSize())
    }

    //同步创建数据
    @JvmStatic
    fun <T> createListData(createFactory: DataCreateFactory<T>): List<T> {
        if (createFactory.minDelayTime() != 0L) {
            //如果延迟时间等于0 那就直接跳过休眠
            try {
                Thread.sleep(createFactory.minDelayTime())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        val array: MutableList<T> = ArrayList()
        for (i in 0 until createFactory.dataSize()) {
            array.add(createFactory.createData(i))
        }
        return array
    }

    @JvmStatic
    fun simpleStringCreateFactory(): DataCreateFactory<String> {
        return object : DataCreateFactory<String> {
            public override fun createData(index: Int): String {
                return "data:$index"
            }

            public override fun minDelayTime(): Long {
                return 1500
            }
        }
    }

    interface DataCreateFactory<T> {
        /**
         * 具体创建的数据
         *
         * @param index 当前创建第几个
         * @return 创建好的数据
         */
        fun createData(index: Int): T

        /**
         * 最小延迟时间，用于模仿网络请求
         *
         * @return 最小延迟时间
         */
        fun minDelayTime(): Long = 1000L

        /**
         * 创建数据的个数
         *
         * @return 数据个数
         */
        fun dataSize() = 20
    }

    interface DataCallBack<T> {
        fun onFinish(data: T)
    }

    private class CreateTask<T> constructor(
            private val mCreateFactory: DataCreateFactory<T>,
            private val mDataCallBack: DataCallBack<List<T>?>?) : AsyncTask<Int?, Int?, List<T>>() {

        override fun doInBackground(vararg integers: Int?): List<T> {
            if (mCreateFactory.minDelayTime() != 0L) {
                try {
                    Thread.sleep(mCreateFactory.minDelayTime())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            val array: MutableList<T> = ArrayList()
            for (length in integers) {
                length?.let {
                    for (i in 0 until it) {
                        array.add(mCreateFactory.createData(i))
                    }
                }
            }
            return array
        }

        override fun onPostExecute(ts: List<T>) {
            super.onPostExecute(ts)
            mDataCallBack?.onFinish(ts)
        }
    }
}