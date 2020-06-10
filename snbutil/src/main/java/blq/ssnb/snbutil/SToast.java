package blq.ssnb.snbutil;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.Queue;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2020-06-02
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
public class SToast {

    final Context mContext;
    final TN mTN;
    int mDuration;
    View mNextView;
    TextView mTextView;
    Queue<SToast> getS;

    public SToast(Context context) {
        this(context, null);
    }

    public SToast(@NonNull Context context, @Nullable Looper looper) {
        mContext = context;
        mTN = new TN(context.getPackageName(), looper);
        int yId = context.getResources().getIdentifier("toast_y_offset", "dimen", "android");
        int gId = context.getResources().getIdentifier("config_toastDefaultGravity", "integer", "android");
        SnbLog.e(">>>>>:1:"+mTN.mY);
        SnbLog.e(">>>>>:1:"+mTN.mGravity);
        mTN.mY = context.getResources().getDimensionPixelSize(yId);
        mTN.mGravity = context.getResources().getInteger(gId);
        SnbLog.e(">>>>>:2:"+mTN.mY);
        SnbLog.e(">>>>>:2:"+mTN.mGravity);
    }

    public void show() {
        if (mNextView == null) {
            throw new RuntimeException("setView must have been called");
        }

        TN tn = mTN;
        tn.mNextView = mNextView;
        tn.show();
    }

    public void cancel() {
        mTN.cancel();
    }

    public void setView(View view) {
        mNextView = view;
    }

    public View getView() {
        return mNextView;
    }

    public void setDuration(int duration) {
        mDuration = duration;
        mTN.mDuration = duration;
    }

    public int getGravity() {
        return mTN.mGravity;
    }

    public int getXOffset() {
        return mTN.mX;
    }

    public int getYOffset() {
        return mTN.mY;
    }

    public WindowManager.LayoutParams getWindowParams() {
        return mTN.mParams;
    }

    public static SToast makeText(Context context, CharSequence text, int duration) {
        return makeText(context, null, text, duration);
    }

    public static SToast makeText(@NonNull Context context, @Nullable Looper looper,
                                  @NonNull CharSequence text, int duration) {
        SToast result = new SToast(context, looper);

        LayoutInflater inflate = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int vid = context.getResources().getIdentifier("transient_notification", "layout", "android");
        View v = inflate.inflate(vid, null);
        int tvID = context.getResources().getIdentifier("message", "id", "android");
        TextView tv = v.findViewById(tvID);
        tv.setText(text);


        result.mNextView = v;
        result.mTextView = tv;
        result.mDuration = duration;

        return result;
    }

    public static SToast makeText(Context context, @StringRes int resId, int duration)
            throws Resources.NotFoundException {
        return makeText(context, context.getResources().getText(resId), duration);
    }

    public void setText(@StringRes int resId) {
        setText(mContext.getText(resId));
    }

    public void setText(CharSequence s) {
        if (mNextView == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        if (mTextView == null) {
            throw new RuntimeException("This Toast was not created with Toast.makeText()");
        }
        mTextView.setText(s);
    }

    private static class TN {
        private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        private static final int SHOW = 0;
        private static final int HIDE = 1;
        private static final int CANCEL = 2;
        final Handler mHandler;

        int mGravity;
        int mX, mY;
        float mHorizontalMargin;
        float mVerticalMargin;


        View mView;
        View mNextView;
        int mDuration;

        WindowManager mWM;

        String mPackageName;

        static final long SHORT_DURATION_TIMEOUT = 4000;
        static final long LONG_DURATION_TIMEOUT = 7000;

        TN(String packageName, @Nullable Looper looper) {
            final WindowManager.LayoutParams params = mParams;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
//            params.windowAnimations = com.android.internal.R.style.Animation_Toast;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.setTitle("Toast");
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

            mPackageName = packageName;

            if (looper == null) {
                // Use Looper.myLooper() if looper is not specified.
                looper = Looper.myLooper();
                if (looper == null) {
                    throw new RuntimeException(
                            "Can't toast on a thread that has not called Looper.prepare()");
                }
            }
            mHandler = new Handler(looper, null) {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case SHOW: {
                            IBinder token = (IBinder) msg.obj;
                            handleShow(token);
                            break;
                        }
                        case HIDE: {
                            handleHide();
                            mNextView = null;
                            break;
                        }
                        case CANCEL: {
                            handleHide();
                            mNextView = null;
                            break;
                        }
                    }
                }
            };
        }

        public void show() {
            mHandler.obtainMessage(SHOW).sendToTarget();
        }

        public void hide() {
            mHandler.obtainMessage(HIDE).sendToTarget();
        }

        public void cancel() {
            mHandler.obtainMessage(CANCEL).sendToTarget();
        }

        public void handleShow(IBinder windowToken) {
            if (mHandler.hasMessages(CANCEL) || mHandler.hasMessages(HIDE)) {
                return;
            }
            if (mView != mNextView) {
                // remove the old view if necessary
                handleHide();
                mView = mNextView;
                Context context = mView.getContext().getApplicationContext();
                String packageName = mView.getContext().getPackageName();
                if (context == null) {
                    context = mView.getContext();
                }
                mWM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                // We can resolve the Gravity here by using the Locale for getting
                // the layout direction
                final Configuration config = mView.getContext().getResources().getConfiguration();
                final int gravity = Gravity.getAbsoluteGravity(mGravity, config.getLayoutDirection());
                mParams.gravity = gravity;
                if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                    mParams.horizontalWeight = 1.0f;
                }
                if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                    mParams.verticalWeight = 1.0f;
                }
                mParams.x = mX;
                mParams.y = mY;
                mParams.verticalMargin = mVerticalMargin;
                mParams.horizontalMargin = mHorizontalMargin;
                mParams.packageName = packageName;
//                mParams.hideTimeoutMilliseconds = mDuration ==
//                        Toast.LENGTH_LONG ? LONG_DURATION_TIMEOUT : SHORT_DURATION_TIMEOUT;
                mParams.token = windowToken;
                if (mView.getParent() != null) {
                    mWM.removeView(mView);
                }
                try {
                    mWM.addView(mView, mParams);
                } catch (WindowManager.BadTokenException e) {
                    /* ignore */
                }
            }
        }

        public void handleHide() {
            if (mView != null) {
                // note: checking parent() just to make sure the view has
                // been added...  i have seen cases where we get here when
                // the view isn't yet added, so let's try not to crash.
                if (mView.getParent() != null) {
                    mWM.removeViewImmediate(mView);
                }

                mView = null;
            }
        }
    }
}
