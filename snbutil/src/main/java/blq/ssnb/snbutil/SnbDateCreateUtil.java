package blq.ssnb.snbutil;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/2/20
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *  一个简单的数据创建工具
 * ================================================
 * </pre>
 */
public class SnbDateCreateUtil {

    public interface DataCreateFactory<T> {
        /**
         * 具体创建的数据
         *
         * @param index 当前创建第几个
         * @return 创建好的数据
         */
        T createData(int index);

        /**
         * 最小延迟时间，用于模仿网络请求
         *
         * @return 最小延迟时间
         */
        long minDelayTime();


        /**
         * 创建数据的个数
         *
         * @return 数据个数
         */
        int dataSize();
    }

    public interface DataCallBack<T> {
        void onFinish(T data);
    }

    public static <T> void asyCreateListData(DataCreateFactory<T> createFactory, final DataCallBack<List<T>> callBack) {
        new CreateTask<>(createFactory, callBack).execute(createFactory.dataSize());
    }

    public static <T> List<T> createListData(DataCreateFactory<T> createFactory) {
        try {
            Thread.sleep(createFactory.minDelayTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<T> array = new ArrayList<>();
        for (int i = 0; i < createFactory.dataSize(); i++) {
            array.add(createFactory.createData(i));
        }
        return array;
    }


    private static class CreateTask<T> extends AsyncTask<Integer, Integer, List<T>> {
        private DataCreateFactory<T> mCreateFactory;
        private DataCallBack<List<T>> mDataCallBack;

        public CreateTask(DataCreateFactory<T> createFactory, DataCallBack<List<T>> callBack) {
            this.mCreateFactory = createFactory;
            this.mDataCallBack = callBack;
        }

        @Override
        protected List<T> doInBackground(Integer... integers) {
            int length = 20;
            if (integers.length > 0) {
                length = integers[0];
            }
            try {
                Thread.sleep(mCreateFactory.minDelayTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<T> array = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                array.add(mCreateFactory.createData(i));
            }
            return array;
        }

        @Override
        protected void onPostExecute(List<T> ts) {
            super.onPostExecute(ts);
            if (mDataCallBack != null) {
                mDataCallBack.onFinish(ts);
            }
        }
    }

    public static DataCreateFactory<String> simpleStringCreateFactory() {
        return new DataCreateFactory<String>() {
            @Override
            public String createData(int index) {
                return "data:" + index;
            }

            @Override
            public long minDelayTime() {
                return 1500;
            }

            @Override
            public int dataSize() {
                return 20;
            }
        };
    }

}
