package com.blq.ssnb.snbutil.demo

import android.content.res.ColorStateList
import android.graphics.drawable.*
import android.widget.TextView
import blq.ssnb.baseconfigure.BaseActivity
import blq.ssnb.snbutil.kit.SnbRippleCreateFactory
import com.blq.ssnb.snbutil.R

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
class SnbRippleActivity : BaseActivity() {

    private lateinit var oneBtn: TextView
    private lateinit var twoBtn: TextView
    private lateinit var threeBtn: TextView
    private lateinit var fourBtn: TextView
    private lateinit var fiveBtn: TextView

    override fun contentView(): Int = R.layout.activity_snb_ripple

    override fun bindEvent() {
        oneBtn.setOnClickListener { }
        twoBtn.setOnClickListener { }
        threeBtn.setOnClickListener { }
        fourBtn.setOnClickListener { }
        fiveBtn.setOnClickListener { }
    }

    override fun bindData() {
        oneBtn.background = SnbRippleCreateFactory.noMask(resources.getColor(R.color.sview_blue_500))
        twoBtn.background = SnbRippleCreateFactory.mask(resources.getColor(R.color.sview_blue_500))
        threeBtn.background = SnbRippleCreateFactory.mask(resources.getColor(R.color.sview_blue_500), getDrawable(R.drawable.icon_file))
        fourBtn.background = SnbRippleCreateFactory.backgroundMask(ColorStateList.valueOf(resources.getColor(R.color.sview_blue_500)), getDrawable(R.drawable.icon_file))
        fiveBtn.background = SnbRippleCreateFactory.backgroundAndMask(ColorStateList.valueOf(resources.getColor(R.color.sview_blue_500)), getDrawable(R.drawable.icon_file), getDrawable(R.drawable.icon_file_dir))
    }

    override fun initView() {
        oneBtn = findViewById(R.id.tv_1_btn)
        twoBtn = findViewById(R.id.tv_2_btn)
        threeBtn = findViewById(R.id.tv_3_btn)
        fourBtn = findViewById(R.id.tv_4_btn)
        fiveBtn = findViewById(R.id.tv_5_btn)
    }

}