package com.blq.ssnb.snbutil.demo

import android.util.TypedValue
import android.widget.SeekBar
import android.widget.TextView
import blq.ssnb.baseconfigure.BaseActivity
import blq.ssnb.snbutil.SnbDisplayUtil
import com.blq.ssnb.snbutil.R
import org.w3c.dom.Text

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/5/10
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
class SnbDisplayActivity : BaseActivity() {
    override fun contentView(): Int = R.layout.activity_snb_display

    private lateinit var dpShowView: TextView
    private lateinit var dpInfoShowView: TextView
    private lateinit var dpSbView: SeekBar
    private lateinit var spView: TextView
    private lateinit var spShowView: TextView

    private lateinit var subBtn: TextView
    private lateinit var addBtn: TextView
    private lateinit var spInfoView: TextView

    private var spSize: Int = 16

    override fun initView() {
        dpShowView = findViewById(R.id.tv_dp_view)
        dpInfoShowView = findViewById(R.id.tv_db_info)
        dpSbView = findViewById(R.id.sb_dp_change)
        spView = findViewById(R.id.tv_sp_example_view)
        spShowView = findViewById(R.id.tv_sp_show_view)
        subBtn = findViewById(R.id.tv_sub_btn)
        addBtn = findViewById(R.id.tv_add_btn)
        spInfoView = findViewById(R.id.tv_sp_info_view)
    }

    override fun bindData() {
        dpSbView.progress = 80
        updateDpInfo(dpSbView.progress)
        updateSize(spSize)

    }

    override fun bindEvent() {
        dpSbView.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    updateDpInfo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        subBtn.setOnClickListener {
            spSize--
            if (spSize < 12) {
                spSize = 12
            }
            updateSize(spSize)
        }

        addBtn.setOnClickListener {
            spSize++
            if (spSize > 60) {
                spSize = 60
            }
            updateSize(spSize)
        }
    }

    private fun updateDpInfo(progress: Int) {
        val wpx = SnbDisplayUtil.dp2Px(context, progress.toFloat())
        dpInfoShowView.text = progress.toString()
        dpShowView.layoutParams.width = wpx

    }

    private fun updateSize(sp: Int) {
        spInfoView.text = "${sp}sp"
        spView.textSize = sp.toFloat()
        spShowView.setTextSize(TypedValue.COMPLEX_UNIT_PX, SnbDisplayUtil.sp2Px(context, sp.toFloat()).toFloat())
    }

}