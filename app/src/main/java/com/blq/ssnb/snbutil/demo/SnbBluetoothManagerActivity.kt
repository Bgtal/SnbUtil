package com.blq.ssnb.snbutil.demo

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.Switch
import android.widget.TextView
import blq.ssnb.baseconfigure.BaseActivity
import blq.ssnb.manager.SnbBluetoothManager
import blq.ssnb.snbutil.SnbToast
import com.blq.ssnb.snbutil.R
import com.blq.ssnb.snbutil.adapter.OperationRecordAdapter
import java.lang.StringBuilder

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019-08-27
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */

class SnbBluetoothManagerActivity : BaseActivity() {
    private val REQUEST_CODE_OPEN_BLUETOOTH = 1231;
    private val REQUEST_CODE_CLOSE_BLUETOOTH = 1232;

    private lateinit var mBroadcastReceiver: BroadcastReceiver
    private lateinit var isSupportBtn: View
    private lateinit var isSupportLBEBtn: View
    private lateinit var bluetoothSwitch: Switch
    private lateinit var getNameBtn: View
    private lateinit var getAddressBtn: View
    private lateinit var getAllBoundDevices: View
    private lateinit var isDiscovering: Switch
    private lateinit var makeDiscover: View

    private lateinit var openDiscover: View
    private lateinit var closeDiscover: View


    private lateinit var clearBtn: View
    private lateinit var operationList: androidx.recyclerview.widget.RecyclerView
    private var adapter = OperationRecordAdapter()

    override fun contentView(): Int = R.layout.activity_snb_bluetooth_manager

    override fun initView() {
        isSupportBtn = findViewById(R.id.tv_is_support)
        isSupportLBEBtn = findViewById(R.id.tv_is_support_lbe)
        bluetoothSwitch = findViewById(R.id.switch_bluetooth_status)
        getNameBtn = findViewById(R.id.tv_get_name)
        getAddressBtn = findViewById(R.id.tv_get_address)
        getAllBoundDevices = findViewById(R.id.tv_get_all_bound_device)
        isDiscovering = findViewById(R.id.switch_is_discovering)

        openDiscover = findViewById(R.id.tv_open_discover)
        closeDiscover = findViewById(R.id.tv_close_discover)
        makeDiscover = findViewById(R.id.tv_make_discover)

        clearBtn = findViewById(R.id.tv_clear_record_btn)
        operationList = findViewById(R.id.rv_operation_records)

        operationList.adapter = adapter
        operationList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)

    }

    override fun bindData() {
        mBroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                when (action) {
                    BluetoothAdapter.ACTION_STATE_CHANGED -> {
                        val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)
                        adapter.addData("蓝牙状态改变:$state")
                    }
                    BluetoothDevice.ACTION_FOUND -> {
                        val bluetoothDevice = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                        adapter.addData("找到蓝牙设备:${bluetoothDevice?.name} - ${bluetoothDevice?.address}")
                    }
                    //找到设备的广播
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                        adapter.addData("搜索结束")
                        isDiscovering.isChecked = false
                    }
                    //搜索完成的广播
                    BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                        adapter.addData("搜索开始")
                        isDiscovering.isChecked = true
                    }

                    BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                        adapter.addData("绑定状态改变:")
                    }

                }
            }
        }
        bluetoothSwitch.isChecked = SnbBluetoothManager.singleton()?.isEnabled ?: false
        isDiscovering.isChecked = SnbBluetoothManager.singleton()?.isDiscovering ?: false
    }

    override fun bindEvent() {
        clearBtn.setOnClickListener { adapter.replaceData(ArrayList()) }
        isSupportBtn.setOnClickListener { adapter.addData("是否拥有蓝牙模块:" + SnbBluetoothManager.singleton()?.isSupport) }
        isSupportLBEBtn.setOnClickListener { adapter.addData("是否拥有蓝牙LE模块:" + SnbBluetoothManager.singleton()?.isSupportLE) }
        getNameBtn.setOnClickListener { adapter.addData("获取设备蓝牙名称:" + SnbBluetoothManager.singleton()?.name) }
        getAddressBtn.setOnClickListener { adapter.addData("获取设备蓝牙名称:" + SnbBluetoothManager.singleton()?.address) }
        getAllBoundDevices.setOnClickListener {

            var builder = StringBuilder("获取所有连接设备:")
            val devices = SnbBluetoothManager.singleton()?.bondedDevices
            builder.append(devices?.size).append("\n")

            if (devices != null) {
                for (bl in devices) {
                    builder.append("设备:").append(bl.name).append("\n")
                            .append("地址:").append(bl.address).append("\n")
                            .append("状态:").append(bl.bondState).append("\n")
                }
            }
            adapter.addData(builder.toString())

        }
        bluetoothSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (SnbBluetoothManager.singleton()?.isSupport == true) {
                    SnbBluetoothManager.singleton()?.openBluetoothForResult(activity, REQUEST_CODE_OPEN_BLUETOOTH)
                } else {
                    buttonView.isChecked = false
                }
            } else {
                SnbBluetoothManager.singleton()?.closeBluetoothForResult(activity, REQUEST_CODE_CLOSE_BLUETOOTH)
            }
        }

        openDiscover.setOnClickListener { SnbBluetoothManager.singleton()?.startDiscover() }
        closeDiscover.setOnClickListener { SnbBluetoothManager.singleton()?.cancelDiscovery() }
        makeDiscover.setOnClickListener {
            SnbBluetoothManager.singleton()?.startBeDiscoverEnable(activity, 60)
            adapter.addData("启动设备可发现")
        }
    }

    override fun operation() {
        super.operation()
        registerReceiver(mBroadcastReceiver, SnbBluetoothManager.singleton()?.intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBroadcastReceiver)
    }

    override fun onHandledActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onHandledActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_OPEN_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                adapter.addData("打开蓝牙成功")
            } else {
                adapter.addData("打开蓝牙失败")
                bluetoothSwitch.isChecked = false;
            }

        } else if (requestCode == REQUEST_CODE_CLOSE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                adapter.addData("关闭蓝牙成功")
            } else {
                adapter.addData("关闭蓝牙失败")
                bluetoothSwitch.isChecked = true
            }
        }
    }

}