package com.blq.ssnb.snbutil.demo

import android.graphics.Color
import android.text.method.ScrollingMovementMethod
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import blq.ssnb.baseconfigure.BaseActivity
import blq.ssnb.snbutil.SnbToast
import blq.ssnb.snbutil.kit.SnbColorSelector
import blq.ssnb.snbutil.kit.SnbDrawableSelector
import com.blq.ssnb.snbutil.R

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/5/13
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
class SnbColorAndDrawableSelectorActivity : BaseActivity() {

    private lateinit var demoView: TextView
    private lateinit var demoEdit: EditText
    private lateinit var demoCheckBox: CheckBox

    private lateinit var styleCheckBox: CheckBox

    private lateinit var enableBtn: TextView
    private lateinit var focused: TextView
    private lateinit var colorNote: TextView
    private lateinit var drawableNote: TextView

    override fun contentView(): Int = R.layout.activity_snb_color_and_drawable_selector

    override fun initView() {
        demoView = findViewById(R.id.tv_demo_view)
        demoEdit = findViewById(R.id.edit_demo_view)
        demoCheckBox = findViewById(R.id.cb_demo_view)
        styleCheckBox = findViewById(R.id.cb_select_type_check)

        enableBtn = findViewById(R.id.tv_enable_btn)
        focused = findViewById(R.id.tv_focused_btn)

        colorNote = findViewById(R.id.tv_note_color_selector)
        drawableNote = findViewById(R.id.tv_note_drawable_selector)
    }

    override fun bindEvent() {
        demoView.setOnClickListener { SnbToast.showSmart("demo") }
        styleCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.text = if (isChecked) {
                changeColorSelector()
                "colorSelector"
            } else {
                changeDrawableSelector()
                "drawableSelector"
            }

        }

        enableBtn.setOnClickListener {
            demoView.isEnabled = !demoView.isEnabled
            demoEdit.isEnabled = !demoEdit.isEnabled
            demoCheckBox.isEnabled = !demoCheckBox.isEnabled
        }

        focused.setOnClickListener {
            if (demoView.isFocused) {
                demoEdit.requestFocus()
            } else {
                demoView.requestFocus()
            }
        }
    }

    override fun bindData() {
        colorNote.movementMethod = ScrollingMovementMethod()
        drawableNote.movementMethod = ScrollingMovementMethod()
    }

    private fun changeDrawableSelector() {
        val builder = SnbDrawableSelector.Builder()
                .windowUnFocused(resources.getDrawable(R.drawable.icon_drawable_window_un_focused))
                .checkable(resources.getDrawable(R.drawable.icon_drawable_checked))
                .activated(resources.getDrawable(R.drawable.icon_drawable_activated))
                .selected(resources.getDrawable(R.drawable.icon_drawable_selected))
                .disabled(resources.getDrawable(R.drawable.icon_drawable_disabled))
                .pressed(resources.getDrawable(R.drawable.icon_drawable_pressed))
                .hovered(resources.getDrawable(R.drawable.icon_drawable_hovered))
//                .focused(resources.getDrawable(R.drawable.icon_drawable_focused))
                .checked(resources.getDrawable(R.drawable.icon_drawable_checked))
                .normal(resources.getDrawable(R.drawable.icon_drawable_normal));
//                .build()

        demoView.setTextColor(Color.BLACK)
        demoEdit.setTextColor(Color.BLACK)
        demoCheckBox.setTextColor(Color.BLACK)

        builder.build().setBackground(demoView)
        builder.build().setBackground(demoEdit)
        builder.build().setBackground(demoCheckBox)

    }

    private fun changeColorSelector() {

        val builder = SnbColorSelector.Builder()
                .windowUnFocused(resources.getColor(R.color.windowUnFocused))
                .checkable(resources.getColor(R.color.checkable))
                .activated(resources.getColor(R.color.activated))
                .selected(resources.getColor(R.color.selected))
                .disabled(resources.getColor(R.color.disabled))
                .pressed(resources.getColor(R.color.pressed))
                .hovered(resources.getColor(R.color.hovered))
//                .focused(resources.getColor(R.color.focused))
                .checked(resources.getColor(R.color.checked))
                .normal(resources.getColor(R.color.normal))
                .build()
        demoView.setBackgroundColor(Color.WHITE)
        demoEdit.setBackgroundColor(Color.WHITE)
        demoCheckBox.setBackgroundColor(Color.WHITE)

        builder.setTextColor(demoView)
        builder.setTextColor(demoEdit)
        builder.setTextColor(demoCheckBox)
    }

}