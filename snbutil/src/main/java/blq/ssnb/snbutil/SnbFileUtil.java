package blq.ssnb.snbutil;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 * 没有权限将导致一些操作无效
 * ================================================
 * </pre>
 */
public class SnbFileUtil {

    private SnbFileUtil() {
        throw new SnbIllegalInstantiationException();
    }

    private static String sdCardPath;

    /**
     * 获得sdcard 的路径
     *
     * @return sdCard的路径 一般为"/storage/emulated/0" 如果sdCard未正常挂载将返回null
     */
    public static String getSDCardPath() {
        if (sdCardPath == null) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
        }
        return sdCardPath;
    }

    // <editor-fold defaultstate="collapsed" desc="创建">

    /**
     * 创建文件
     *
     * @param folderPath 文件父路径
     * @param fileName   文件名字
     * @return true:文件创建成功 false: 如果文件已经存在或者创建失败返回false
     */
    public static File createFile(String folderPath, String fileName) {
        return createFile(folderPath + File.separator + fileName);
    }

    /**
     * 创建文件
     *
     * @param filePath 文件路径
     * @return true:文件创建成功 false: 如果文件已经存在或者创建失败返回false
     */
    public static File createFile(String filePath) {
        return createFile(filePath, false);
    }

    /**
     * 创建文件夹
     *
     * @param folderPath 文件夹目录
     * @param fileName   文件名
     * @param isOverride 是否覆盖 true 如果文件已存在将会删除后再创建
     * @return 创建好的文件 创建失败返回null
     */
    public static File createFile(String folderPath, String fileName, boolean isOverride) {
        return createFile(folderPath + File.separator + fileName, isOverride);
    }

    /**
     * 创建文件
     *
     * @param filePath   文件路径
     * @param isOverride 是否覆盖
     * @return 创建好的文件 如果创建失败 返回null
     */
    public static File createFile(String filePath, boolean isOverride) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();//获得父目录对象
        if (parentDir != null && !parentDir.exists()) {//如果父文件夹不存在
            //那就去创建一个
            parentDir = createDirectory(parentDir);
        }
        if (parentDir == null) {//判断父目录是不是在
            return null;//失败说明创建失败了,直接返回null
        }

        if (!file.exists()) {//如果文件不存在
            try {
                if (file.createNewFile()) {//创建文件
                    return file;//成功返回
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (isOverride) {//如果是覆盖的话
            if (file.delete()) {//删除
                //删除成功 创建一个文件
                return createFile(filePath, false);
            }//否则直接返回null
        }
        return null;
    }

    /**
     * 创建文件夹
     *
     * @param address 文件夹位置
     * @return 文件夹对象 创建成功返回File对象，创建失败，返回null
     */
    public static File createDirectory(String address) {
        return createDirectory(new File(address));
    }

    public static File createDirectory(File folderFile) {
        if (folderFile != null
                && folderFile.isDirectory()
                && !folderFile.exists()) {
            //不为空，且为文件夹,且实际未存在
            if (folderFile.mkdirs()) {//那就创建一个文件夹
                return folderFile; //创建成果返回文件夹对象
            }
        }
        return null;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="删除">

    /**
     * 删除文件及文件夹
     * 删除单个文件 建议使用{@link #deleteFile(File)} 方法
     * 防止误删文件夹
     *
     * @param path 文件路径
     * @return true 删除成功 false 删除失败
     */
    public static boolean deleteFileAndFolder(String path) {
        return deleteFileAndFolder(new File(path));
    }

    /**
     * 删除文件或文件夹
     * 删除单个文件 建议使用{@link #deleteFile(File)} 方法
     * 防止误删文件夹
     *
     * @param file 文件对象
     * @return true 删除成功 false 删除失败
     */
    public static boolean deleteFileAndFolder(File file) {
        if (file == null || !file.exists()) {
            //文件为空
            return true;
        }
        boolean isSuccess = true;
        if (file.isDirectory()) {
            //如果是文件夹
            File[] childFiles = file.listFiles();
            if (childFiles != null && childFiles.length > 0) {
                //遍历删除子文件
                for (File child : childFiles) {
                    //只要子文件有一个返回false 九尾false
                    isSuccess = isSuccess && deleteFileAndFolder(child);
                }
            }
            isSuccess = isSuccess && file.delete();
        } else {
            isSuccess = isSuccess && file.delete();
        }
        return isSuccess;
    }

    /**
     * 删除文件
     *
     * @param folderPath 文件父路径
     * @param fileName   文件名字
     * @return true:删除成功 false:文件删除失败,传入路径可能为文件夹路径
     */
    public static boolean deleteFile(String folderPath, String fileName) {
        return deleteFile(folderPath + File.separator + fileName);
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return true:删除成功 false:文件删除失败,传入路径可能为文件夹路径
     */
    public static boolean deleteFile(String filePath) {
        return deleteFile(new File(filePath));
    }

    /**
     * 删除文件
     *
     * @param file 文件对象
     * @return true:删除成功 false:文件删除失败,传入路径可能为文件夹路径
     */
    public static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {//不是文件夹
            return file.delete();
        }
        return false;
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
    public static File shearFile(String oldFilePath, String newFilePath) throws IOException {
        return shearFile(new File(oldFilePath), new File(newFilePath));
    }

    /**
     * 剪切文件
     *
     * @param oldFile 原始文件
     * @param newFile 新文件 如果剪切的是文件夹，那么newFile 必须是一个文件夹对象
     * @return 返回剪切后的文件
     * @throws IOException 文件未找到或写入文件失败
     */
    public static File shearFile(File oldFile, File newFile) throws IOException {
        return moveFileOrDir(oldFile, newFile, true);
    }

    /**
     * 复制文件
     *
     * @param oldFilePath 原始文件
     * @param newFilePath 新文件
     * @return 返回剪切后的文件
     * @throws IOException 文件未找到或写入文件失败
     */
    public static File copy(String oldFilePath, String newFilePath) throws IOException {
        return copy(new File(oldFilePath), new File(newFilePath));
    }

    /**
     * 复制文件
     *
     * @param oldFile 原始文件
     * @param newFile 新文件 如果复制的是文件夹，那么newFile 必须是一个文件夹对象
     * @return 返回剪切后的文件
     * @throws IOException 文件未找到或写入文件失败
     */
    public static File copy(File oldFile, File newFile) throws IOException {
        return moveFileOrDir(oldFile, newFile, false);
    }

    /**
     * @param oldFile       原始文件
     * @param newFile       新文件
     * @param deleteOldFile 是否删除原文件
     * @return 返回移动后的文件
     * @throws IOException 文件未找到或写入文件失败
     */
    private static File moveFileOrDir(File oldFile, File newFile, boolean deleteOldFile) throws IOException {
        if (oldFile != null && oldFile.exists()
                && newFile != null) {
            if (!canMove(oldFile, newFile)) {
                return null;
            }
            if (oldFile.isDirectory()) {
                return moveDirectory(oldFile, newFile, deleteOldFile);
            } else {
                return moveFile(oldFile, newFile, deleteOldFile);
            }
        }
        return null;
    }

    private static boolean canMove(File oldFile, File newFile) {
        if (oldFile != null
                && oldFile.exists()
                && newFile != null) {
            String oldFilePath = oldFile.getAbsolutePath();
            String newFilePath = newFile.getAbsolutePath();
            if (!oldFilePath.equals(newFilePath)
                    && !newFilePath.equals(oldFile.getParentFile().getAbsolutePath())
                    && !newFilePath.startsWith(oldFilePath)) {
                return true;
            }
        }
        return false;
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
    private static File moveDirectory(File oldFile, File newFile, boolean deleteOldFile) throws IOException {
        if (oldFile != null && oldFile.exists() && newFile != null) {
            if (oldFile.isFile()) {
                return null;
            }
            File[] childFiles = oldFile.listFiles();
            boolean isMoveSuccess = true;
            for (File childFile : childFiles) {
                if (childFile.isFile()) {
                    File mf = moveFile(childFile, createFile(newFile.getPath(), childFile.getName()), deleteOldFile);
                    if (isMoveSuccess && mf == null) {
                        isMoveSuccess = false;
                    }
                } else {
                    File mf = moveDirectory(childFile, createDirectory(newFile.getPath() + File.separator + childFile.getName()), deleteOldFile);
                    if (isMoveSuccess && mf == null) {
                        isMoveSuccess = false;
                    }
                }
            }
            if (deleteOldFile && isMoveSuccess) {
                oldFile.delete();
                return newFile;
            }
            return null;
        }
        return null;
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
    private static File moveFile(File oldFile, File newFile, boolean deleteOldFile) throws IOException {
        if (oldFile != null && oldFile.exists()
                && newFile != null) {
            int byteRead;
            FileInputStream fileInputStream = new FileInputStream(oldFile);
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            while ((byteRead = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.close();
            if (deleteOldFile) {
                oldFile.delete();
            }
            return newFile;
        }
        return null;
    }


    /**
     * 重命名
     *
     * @param oldFile 老的文件
     * @param newName 新的名字
     * @return 新命名的文件对象，null 表示命名失败了
     */
    public static File renameFile(File oldFile, String newName) {
        if (oldFile != null && oldFile.exists()) {
            //文件存在
            if (oldFile.getName().equals(newName)) {
                //新旧名字一样，直接返回;
                return oldFile;
            }
            File newFile;
            if (oldFile.isDirectory()) {//如果是目录的话
                //创建目录
                newFile = createDirectory(oldFile.getParentFile().getAbsolutePath() + File.separator + newName);
            } else {
                newFile = createFile(oldFile.getParentFile().getAbsolutePath(), newName);
            }

            if (newFile != null) {
                if (oldFile.renameTo(newFile)) {
                    return newFile;
                }
            }
        }

        return null;
    }


    // <editor-fold defaultstate="collapsed" desc="查">

    /**
     * 获取文件对象
     *
     * @param folderPath 父目录
     * @param fileName   文件名
     * @return File对象 如果文件不存在返回null
     */
    public static File getFile(String folderPath, String fileName) {
        return getFile(folderPath + File.separator + fileName);
    }

    /**
     * 获取文件对象
     *
     * @param filePath 文件路径
     * @return File对象 如果文件不存在返回null
     */
    public static File getFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    // </editor-fold>


}
