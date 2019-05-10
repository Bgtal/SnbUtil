package com.blq.ssnb.snbutil.adapter

import blq.ssnb.snbutil.SnbTimeUtil
import com.blq.ssnb.snbutil.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.io.File

/**
 *
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2019/5/9
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 *      添加描述
 * ================================================
 * </pre>
 */
class FileListAdapter : BaseQuickAdapter<File, BaseViewHolder>(R.layout.item_file_list) {
    override fun convert(helper: BaseViewHolder?, item: File?) {

        val subInfo: String = if (item?.isDirectory!!) {
            helper?.setImageResource(R.id.img_file_type_icon, R.drawable.icon_file_dir)
            helper?.setVisible(R.id.img_arrow_icon, true)
            "文件:" + item.listFiles().size
        } else {
            helper?.setImageResource(R.id.img_file_type_icon, R.drawable.icon_file)
            helper?.setGone(R.id.img_arrow_icon, false)
            SnbTimeUtil.milliseconds2String("yyyy-MM-dd hh:mm:ss", item.lastModified())
        }

        helper?.setText(R.id.tv_file_name, item.name)
        helper?.setText(R.id.tv_file_info, subInfo)
    }

}