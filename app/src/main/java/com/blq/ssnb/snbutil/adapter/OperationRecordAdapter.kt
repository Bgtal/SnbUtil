package com.blq.ssnb.snbutil.adapter

import com.blq.ssnb.snbutil.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

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
class OperationRecordAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_operation_order) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_file_nav_dir, item)
    }

}
