package blq.ssnb.manager

import android.Manifest
import android.app.Activity
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import blq.ssnb.snbutil.SnbLog
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.HashSet

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-08-27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 蓝牙的管理器
 * 权限
 * <uses-permission android:name="android.permission.BLUETOOTH"></uses-permission>
 * <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"></uses-permission>
 *
 * android 6.0 以上需要定位权限才能使用蓝牙 ble 功能
 *
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>
 * <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"></uses-permission>
 * ================================================
</pre> *
 */
class SnbBluetoothManager private constructor(application: Application) {
    private val mWeakContext: WeakReference<Application?>?
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private val isWeakExist: Boolean
        get() = mWeakContext?.get() != null

    /**
     * 是否支持蓝牙
     *
     * @return true 支持，false 不支持
     */
    val isSupport: Boolean
        get() = mBluetoothAdapter != null

    /**
     * 蓝牙是否已经启用
     *
     * @return true 启用了，false 未启用
     */
    @get:RequiresPermission(Manifest.permission.BLUETOOTH)
    val isEnabled: Boolean
        get() = mBluetoothAdapter?.isEnabled == true
    
    /**
     * 请求打开蓝牙
     *
     * @return true 执行了操作，false 没有执行
     */
    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN])
    fun openBluetooth() {
        if (isSupport && !isEnabled) {
            mBluetoothAdapter?.enable()
        }
    }

    /**
     * 请求打开蓝牙
     *
     * @param context     请求的activity
     * @param requestCode 请求的code code 必须大于0
     * 请在请求的activity  onActivityResult() 中接收
     * 成功 返回 [Activity.RESULT_OK]
     * 失败 返回 [Activity.RESULT_CANCELED]
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    fun openBluetoothForResult(context: Activity, requestCode: Int) {
        //如果是蓝牙支持且没有开启，那么就开启蓝牙
        if (isSupport && !isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            context.startActivityForResult(enableBtIntent, requestCode)
        }
        //        return false;
    }

    /**
     * 关闭蓝牙
     */
    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN])
    fun closeBluetooth() {
        if (isSupport && isEnabled) {
            mBluetoothAdapter?.disable()
        }
    }

    /**
     * 请求关闭蓝牙
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    fun closeBluetoothForResult(context: Activity, requestCode: Int) {
        //如果蓝牙支持且已经打开了就关闭
        if (isSupport && isEnabled) {
            val enableBtIntent = Intent("android.bluetooth.adapter.action.REQUEST_DISABLE")
            context.startActivityForResult(enableBtIntent, requestCode)
        }
    }

    /**
     * 获取本机的蓝牙名称
     *
     * @return 蓝牙名称，如果设备不支持，无法获得
     */
    val name: String
        get() = if (isSupport) {
            mBluetoothAdapter!!.name
        } else ""

    /**
     * 获取本机的蓝牙地址
     *
     * @return 蓝牙地址，如果设备不支持，无法获得
     */
    @get:RequiresPermission(Manifest.permission.BLUETOOTH)
    val address: String
        get() = if (isSupport) {
            mBluetoothAdapter?.address ?: ""
        } else ""

    /**
     * 是否在扫描
     *
     * @return true 正在扫描
     */
    @get:RequiresPermission(Manifest.permission.BLUETOOTH)
    val isDiscovering: Boolean
        get() = if (isSupport && isEnabled) {
            mBluetoothAdapter!!.isDiscovering
        } else false

    /**
     * 启动发现
     */
    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN])
    fun startDiscover() {
        if (isSupport && isEnabled) {
            mBluetoothAdapter!!.startDiscovery()
        }
    }

    /**
     * 取消发现
     * 当找到需要连接的设备后，一定要关闭discover 否者对连接会有影响
     */
    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN])
    fun cancelDiscovery() {
        if (isSupport && isEnabled && isDiscovering) {
            mBluetoothAdapter!!.cancelDiscovery()
        }
    }

    /**
     * 启动蓝牙设备被发现功能
     *
     * @param seconds 被发现时间
     */
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    fun startBeDiscoverEnable(context: Activity, seconds: Int) {
        if (isSupport && isEnabled) {
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, seconds)
            context.startActivity(discoverableIntent)
        }
    }

    /**
     * 获得已经绑定的蓝牙设备信息
     *
     *
     * 需要打开蓝牙才能获取到设备信息
     *
     * @return 已绑设备信息
     */
    @get:RequiresPermission(Manifest.permission.BLUETOOTH)
    val bondedDevices: Set<BluetoothDevice>
        get() {
            var devices: Set<BluetoothDevice>? = null
            if (isSupport) {
                devices = mBluetoothAdapter?.bondedDevices as Set<BluetoothDevice>
            }
            return devices ?: HashSet()
        }

    /**
     * 获得所有蓝牙相关的广播监听
     *
     * @return 蓝牙相关的广播意图
     */
    val intentFilter: IntentFilter
        get() {
            val filter = IntentFilter()
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED) //蓝牙状态改变的广播
            filter.addAction(BluetoothDevice.ACTION_FOUND) //找到设备的广播
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED) //搜索完成的广播
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED) //开始扫描的广播
            filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED) //状态改变
            return filter
        }
    // <editor-fold defaultstate="collapsed" desc="BLE相关接口">
    /**
     * 是否支持low energy
     *
     * @return true 支持 false
     */
    private var isScanning = false
    private val mScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            SnbLog.e("蓝牙扫描:单个回调")
            for (callBack in mBLEScanCallBacks) {
                callBack?.onScanResult(result)
            }
        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            super.onBatchScanResults(results)
            SnbLog.e("蓝牙扫描:多个回调")
            for (result in results) {
                for (callBack in mBLEScanCallBacks) {
                    callBack?.onScanResult(result)
                }
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            SnbLog.e("蓝牙扫描:失败")
            for (callBack in mBLEScanCallBacks) {
                callBack?.onScanFail(errorCode)
            }
        }
    }
    private val mBLEScanCallBacks: MutableList<OnBLEScanCallBack> = ArrayList()
    val isSupportLE: Boolean
        get() = if (isWeakExist) {
            mWeakContext!!.get()!!.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
        } else false

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN])
    fun startScan() {
        //支持蓝牙，并且可用，并且支持ble 并且 没有扫描经典蓝牙
        if (isSupport && isEnabled && isSupportLE && !isDiscovering && !isScanning) {
            //才可以开启扫描
            mBluetoothAdapter!!.bluetoothLeScanner.startScan(mScanCallback)
            isScanning = true
        }
    }

    @RequiresPermission(allOf = [Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN])
    fun stopScan() {
        if (isSupport && isEnabled && isSupportLE && isScanning) {
            mBluetoothAdapter!!.bluetoothLeScanner.stopScan(mScanCallback)
        }
        isScanning = false
    }

    /**
     * 注册监听
     * 注意不使用的时候一定要移除监听否者可能导致内存泄漏
     *
     * @param callBack 监听对象
     */
    fun registerOnBLEScanCallBack(callBack: OnBLEScanCallBack) {
        mBLEScanCallBacks.add(callBack)
    }

    /**
     * 移除已注册的监听
     *
     * @param callBack
     */
    fun unregisterOnBLEScanCallBack(callBack: OnBLEScanCallBack) {
        mBLEScanCallBacks.remove(callBack)
    }

    interface OnBLEScanCallBack {
        fun onScanResult(result: ScanResult?)
        fun onScanFail(errorCode: Int)
    } // </editor-fold>

    companion object {
        private var SINGLETON: SnbBluetoothManager? = null

        @Synchronized
        fun init(application: Application) {
            if (SINGLETON == null) {
                synchronized(SnbBluetoothManager::class.java) {
                    if (SINGLETON == null) {
                        SINGLETON = SnbBluetoothManager(application)
                    }
                }
            }
        }

        fun singleton(): SnbBluetoothManager? {
            requireNotNull(SINGLETON) { "请先在适当的时机调用 init(application) 方法" }
            return SINGLETON
        }
    }

    init {
        mWeakContext = WeakReference(application)
        val bluetoothManager = application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter
    }
}