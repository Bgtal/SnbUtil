package blq.ssnb.snbutil

import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期：2018/11/8
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 1
 * 描述:
 * 文件管理工具
 * 需要权限：
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
 * 没有权限将导致一些操作无效
 * ================================================
</pre> *
 */
object SnbFileUtil {
    @JvmStatic
    var sdCardPath: String? = null
        get() {
            if (field == null) {
                if ((Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)) {
                    field = Environment.getExternalStorageDirectory().absolutePath
                }
            }
            return field
        }

    /**
     * 获得sdcard 的路径
     *
     * @return sdCard的路径 一般为"/storage/emulated/0" 如果sdCard未正常挂载将返回null
     */
    @JvmStatic
    val sDCardPath: String?
        get() {
            if (sdCardPath == null) {
                if ((Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)) {
                    sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                }
            }
            return sdCardPath
        }
    // <editor-fold defaultstate="collapsed" desc="创建">
    /**
     * 创建文件
     *
     * @param folderPath 文件父路径
     * @param fileName   文件名字
     * @return true:文件创建成功 false: 如果文件已经存在或者创建失败返回false
     */
    @JvmStatic
    fun createFile(folderPath: String, fileName: String): File? {
        return createFile(folderPath + File.separator + fileName)
    }

    /**
     * 创建文件夹
     *
     * @param folderPath 文件夹目录
     * @param fileName   文件名
     * @param isOverride 是否覆盖 true 如果文件已存在将会删除后再创建
     * @return 创建好的文件 创建失败返回null
     */
    @JvmStatic
    fun createFile(folderPath: String, fileName: String, isOverride: Boolean): File? {
        return createFile(folderPath + File.separator + fileName, isOverride)
    }
    /**
     * 创建文件
     *
     * @param filePath   文件路径
     * @param isOverride 是否覆盖
     * @return 创建好的文件 如果创建失败 返回null
     */
    /**
     * 创建文件
     *
     * @param filePath 文件路径
     * @return true:文件创建成功 false: 如果文件已经存在或者创建失败返回false
     */
    @JvmStatic
    @JvmOverloads
    fun createFile(filePath: String?, isOverride: Boolean = false): File? {
        val file: File = File(filePath)
        var parentDir: File? = file.getParentFile() //获得父目录对象
        if (parentDir != null && !parentDir.exists()) { //如果父文件夹不存在
            //那就去创建一个
            parentDir = createDirectory(parentDir)
        }
        if (parentDir == null) { //判断父目录是不是在
            return null //失败说明创建失败了,直接返回null
        }
        if (!file.exists()) { //如果文件不存在
            try {
                if (file.createNewFile()) { //创建文件
                    return file //成功返回
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (isOverride) { //如果是覆盖的话
            if (file.delete()) { //删除
                //删除成功 创建一个文件
                return createFile(filePath, false)
            } //否则直接返回null
        }
        return null
    }

    /**
     * 创建文件夹
     *
     * @param address 文件夹位置
     * @return 文件夹对象 创建成功返回File对象，创建失败，返回null
     */
    @JvmStatic
    fun createDirectory(address: String?): File? {
        return createDirectory(File(address))
    }

    @JvmStatic
    fun createDirectory(folderFile: File?): File? {
        if (((folderFile != null
                        ) && folderFile.isDirectory()
                        && !folderFile.exists())) {
            //不为空，且为文件夹,且实际未存在
            if (folderFile.mkdirs()) { //那就创建一个文件夹
                return folderFile //创建成果返回文件夹对象
            }
        }
        return null
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="删除">
    /**
     * 删除文件及文件夹
     * 删除单个文件 建议使用[.deleteFile] 方法
     * 防止误删文件夹
     *
     * @param path 文件路径
     * @return true 删除成功 false 删除失败
     */
    @JvmStatic
    fun deleteFileAndFolder(path: String?): Boolean {
        return deleteFileAndFolder(File(path))
    }

    /**
     * 删除文件或文件夹
     * 删除单个文件 建议使用[.deleteFile] 方法
     * 防止误删文件夹
     *
     * @param file 文件对象
     * @return true 删除成功 false 删除失败
     */
    @JvmStatic
    fun deleteFileAndFolder(file: File?): Boolean {
        if (file == null || !file.exists()) {
            //文件为空
            return true
        }
        var isSuccess: Boolean = true
        if (file.isDirectory()) {
            //如果是文件夹
            val childFiles: Array<File>? = file.listFiles()
            if (childFiles != null && childFiles.isNotEmpty()) {
                //遍历删除子文件
                for (child: File? in childFiles) {
                    //只要子文件有一个返回false 九尾false
                    isSuccess = isSuccess && deleteFileAndFolder(child)
                }
            }
            isSuccess = isSuccess && file.delete()
        } else {
            isSuccess = isSuccess && file.delete()
        }
        return isSuccess
    }

    /**
     * 删除文件
     *
     * @param folderPath 文件父路径
     * @param fileName   文件名字
     * @return true:删除成功 false:文件删除失败,传入路径可能为文件夹路径
     */
    @JvmStatic
    fun deleteFile(folderPath: String, fileName: String): Boolean {
        return deleteFile(folderPath + File.separator + fileName)
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return true:删除成功 false:文件删除失败,传入路径可能为文件夹路径
     */
    @JvmStatic
    fun deleteFile(filePath: String?): Boolean {
        return deleteFile(File(filePath))
    }

    /**
     * 删除文件
     *
     * @param file 文件对象
     * @return true:删除成功 false:文件删除失败,传入路径可能为文件夹路径
     */
    @JvmStatic
    fun deleteFile(file: File?): Boolean {
        if (file == null || !file.exists()) {
            return true
        }
        if (!file.isDirectory()) { //不是文件夹
            return file.delete()
        }
        return false
    }
    // </editor-fold>
    //---------------------修改---------------------
    /**
     * 剪切文件
     *
     * @param oldFilePath 原始文件
     * @param newFilePath 新文件
     * @return 返回剪切后的文件
     * @throws IOException 文件未找到或写入文件失败
     */
    @Throws(IOException::class)
    @JvmStatic
    fun shearFile(oldFilePath: String?, newFilePath: String?): File? {
        return shearFile(File(oldFilePath), File(newFilePath))
    }

    /**
     * 剪切文件
     *
     * @param oldFile 原始文件
     * @param newFile 新文件 如果剪切的是文件夹，那么newFile 必须是一个文件夹对象
     * @return 返回剪切后的文件
     * @throws IOException 文件未找到或写入文件失败
     */
    @Throws(IOException::class)
    @JvmStatic
    fun shearFile(oldFile: File?, newFile: File?): File? {
        return moveFileOrDir(oldFile, newFile, true)
    }

    /**
     * 复制文件
     *
     * @param oldFilePath 原始文件
     * @param newFilePath 新文件
     * @return 返回剪切后的文件
     * @throws IOException 文件未找到或写入文件失败
     */
    @Throws(IOException::class)
    @JvmStatic
    fun copy(oldFilePath: String?, newFilePath: String?): File? {
        return copy(File(oldFilePath), File(newFilePath))
    }

    /**
     * 复制文件
     *
     * @param oldFile 原始文件
     * @param newFile 新文件 如果复制的是文件夹，那么newFile 必须是一个文件夹对象
     * @return 返回剪切后的文件
     * @throws IOException 文件未找到或写入文件失败
     */
    @Throws(IOException::class)
    @JvmStatic
    fun copy(oldFile: File?, newFile: File?): File? {
        return moveFileOrDir(oldFile, newFile, false)
    }

    /**
     * @param oldFile       原始文件
     * @param newFile       新文件
     * @param deleteOldFile 是否删除原文件
     * @return 返回移动后的文件
     * @throws IOException 文件未找到或写入文件失败
     */
    @Throws(IOException::class)
    private fun moveFileOrDir(oldFile: File?, newFile: File?, deleteOldFile: Boolean): File? {
        if (((oldFile != null) && oldFile.exists()
                        && (newFile != null))) {
            if (!canMove(oldFile, newFile)) {
                return null
            }
            return if (oldFile.isDirectory) {
                moveDirectory(oldFile, newFile, deleteOldFile)
            } else {
                moveFile(oldFile, newFile, deleteOldFile)
            }
        }
        return null
    }

    private fun canMove(oldFile: File?, newFile: File?): Boolean {
        if (((oldFile != null) && oldFile.exists() && (newFile != null))) {
            val oldFilePath: String = oldFile.absolutePath
            val newFilePath: String = newFile.absolutePath

            if (oldFilePath != newFilePath && newFilePath != oldFile.parentFile?.absolutePath && !newFilePath.startsWith(oldFilePath)) {
                return true
            }
        }
        return false
    }

    /**
     * 移动文件夹
     *
     * @param oldFile       原始文件
     * @param newFile       新文件
     * @param deleteOldFile 移动后是否删除文件
     * @return 如果移动失败返回null 否者返回新的文件对象
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun moveDirectory(oldFile: File?, newFile: File?, deleteOldFile: Boolean): File? {
        if ((oldFile != null) && oldFile.exists() && (newFile != null)) {
            if (oldFile.isFile) {
                return null
            }
            val childFiles: Array<File>? = oldFile.listFiles()
            var isMoveSuccess: Boolean = true
            childFiles?.also {
                for (childFile: File in childFiles) {
                    if (childFile.isFile) {
                        val mf: File? = moveFile(childFile, createFile(newFile.path, childFile.name), deleteOldFile)
                        if (isMoveSuccess && mf == null) {
                            isMoveSuccess = false
                        }
                    } else {
                        val mf: File? = moveDirectory(childFile, createDirectory(newFile.path + File.separator + childFile.name), deleteOldFile)
                        if (isMoveSuccess && mf == null) {
                            isMoveSuccess = false
                        }
                    }
                }
            }
            if (deleteOldFile && isMoveSuccess) {
                oldFile.delete()
                return newFile
            }
            return null
        }
        return null
    }

    /**
     * 移动文件
     *
     * @param oldFile       旧文件
     * @param newFile       新文件
     * @param deleteOldFile 是否删除旧文件
     * @return 如果移动失败返回null 否者返回新的文件对象
     * @throws IOException 移动失败
     */
    @Throws(IOException::class)
    private fun moveFile(oldFile: File?, newFile: File?, deleteOldFile: Boolean): File? {
        if (((oldFile != null) && oldFile.exists() && (newFile != null))) {
            var byteRead: Int
            val fileInputStream: FileInputStream = FileInputStream(oldFile)
            val fileOutputStream: FileOutputStream = FileOutputStream(newFile)
            val buffer: ByteArray = ByteArray(1024)
            while ((fileInputStream.read(buffer).also { byteRead = it }) != -1) {
                fileOutputStream.write(buffer, 0, byteRead)
            }
            fileInputStream.close()
            fileOutputStream.close()
            if (deleteOldFile) {
                oldFile.delete()
            }
            return newFile
        }
        return null
    }

    /**
     * 重命名
     *
     * @param oldFile 老的文件
     * @param newName 新的名字
     * @return 新命名的文件对象，null 表示命名失败了
     */
    @JvmStatic
    fun renameFile(oldFile: File?, newName: String): File? {
        if (oldFile != null && oldFile.exists()) {
            //文件存在
            if ((oldFile.name == newName)) {
                //新旧名字一样，直接返回;
                return oldFile
            }
            val newFile: File? = if (oldFile.isDirectory) { //如果是目录的话
                //创建目录
                createDirectory(oldFile.parentFile?.absolutePath + File.separator + newName)
            } else {
                createFile(oldFile.parentFile?.absolutePath ?: "", newName)
            }
            if (newFile != null) {
                if (oldFile.renameTo(newFile)) {
                    return newFile
                }
            }
        }
        return null
    }
    // <editor-fold defaultstate="collapsed" desc="查">
    /**
     * 获取文件对象
     *
     * @param folderPath 父目录
     * @param fileName   文件名
     * @return File对象 如果文件不存在返回null
     */
    @JvmStatic
    fun getFile(folderPath: String, fileName: String): File? {
        return getFile(folderPath + File.separator + fileName)
    }

    /**
     * 获取文件对象
     *
     * @param filePath 文件路径
     * @return File对象 如果文件不存在返回null
     */
    @JvmStatic
    fun getFile(filePath: String): File? {
        val file = File(filePath)
        if (file.exists()) {
            return file
        }
        return null
    } // </editor-fold>

}