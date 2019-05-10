package com.blq.ssnb.snbutil.adapter

import com.blq.ssnb.snbutil.R
import com.blq.ssnb.snbutil.bean.FileNavBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

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
class FileNavAdapter : BaseQuickAdapter<FileNavBean, BaseViewHolder>(R.layout.item_file_nav) {

    override fun convert(helper: BaseViewHolder?, item: FileNavBean?) {
        helper?.setText(R.id.tv_file_nav_dir, item?.navName)
    }
}