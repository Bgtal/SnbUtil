package com.blq.ssnb.snbutil.demo

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import blq.ssnb.baseconfigure.BaseActivity
import blq.ssnb.snbutil.SnbFileUtil
import com.blq.ssnb.snbutil.R
import com.blq.ssnb.snbutil.adapter.FileListAdapter
import com.blq.ssnb.snbutil.adapter.FileNavAdapter
import com.blq.ssnb.snbutil.bean.FileNavBean
import java.io.File
import kotlin.collections.ArrayList

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
class SnbFileActivity : BaseActivity() {

    val navAdapter = FileNavAdapter()
    val fileListAdapter = FileListAdapter()

    private lateinit var navRvContent: RecyclerView
    private lateinit var fileRvContent: RecyclerView

    private lateinit var rootFile: File

    override fun contentView(): Int = R.layout.activity_snb_file_util

    override fun initView() {
        navRvContent = findViewById(R.id.rv_file_nav_view)
        fileRvContent = findViewById(R.id.rv_file_view)
    }

    override fun bindData() {
        navRvContent.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        navRvContent.adapter = navAdapter
        fileRvContent.layoutManager = LinearLayoutManager(context)
        fileRvContent.adapter = fileListAdapter
    }

    override fun bindEvent() {
        navAdapter.setOnItemClickListener { _, _, position ->
            val navBean = navAdapter.getItem(position)
            val lastIndex = navAdapter.itemCount - 1
            if (lastIndex == position) {//如果点击的是最后一个
                //直接返回
            } else {//否者就跳转到对应的目录
                for (index in lastIndex downTo (position + 1)) {
                    navAdapter.remove(index)
                }
                updateList(navBean?.fileDir!!)
            }
        }

        fileListAdapter.setOnItemClickListener { _, _, position ->
            val bean = fileListAdapter.getItem(position)
            if (bean?.isDirectory!!) {
                loading(bean)
            } else {
//                TODO("文件需要打开")
            }
        }
        fileListAdapter.setOnItemLongClickListener { adapter, view, position ->
            true
        }
    }

    override fun operation() {
        super.operation()
        rootFile = SnbFileUtil.getFile(SnbFileUtil.getSDCardPath())
        navAdapter.addData(FileNavBean("根目录", rootFile))
        updateList(rootFile)
        rootFile.listFiles()

    }


    fun loading(file: File) {
        navAdapter.addData(FileNavBean(file.name, file))
        updateList(file)
    }

    fun onBack(): Boolean {
        val lastIndex = navAdapter.data.size - 1;
        if (lastIndex <= 0) {//如果小于0
            return false
        }
        navAdapter.remove(lastIndex)
        val navBean = navAdapter.getItem(navAdapter.data.size - 1)
        updateList(navBean?.fileDir!!)

        return true
    }

    fun updateList(file: File) {
        val list = ArrayList<File>()
        list.addAll(file.listFiles())
        fileListAdapter.replaceData(list)
    }

    override fun onBackPressed() {
        if (!onBack()) {
            super.onBackPressed()
        }
    }

}