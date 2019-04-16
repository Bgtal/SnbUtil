package blq.ssnb.snbutil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * ================================================
 * 作者: BLQ_SSNB
 * 日期: 2016/9/18
 * 邮箱: blq_ssnb@outlook.com
 * 修改次数: 2
 * 描述:
 * 加密工具
 * 主要实现
 * 1 MD5加密
 * 2 SHA加密
 * 3 增加混淆因子的加密
 * ================================================
 * </pre>
 */

public class SnbEncryptionUtil {
    private static String MD5 = "MD5";
    private static String SHA = "SHA";

    private final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };

    private SnbEncryptionUtil(){
        throw new SnbIllegalInstantiationException();
    }

    /**
     * MD5加密
     * @param message 待加密数据
     * @return 加密完成后的数据 27:52:6F:FE:2D:3C:4A:34:6E:E3:A3:08:82:36:35:E1
     */
    public static String encryptMD5(String message){
        return encryptByStyle(MD5,message,null);
    }

    /**
     * SHA加密
     * @param message 待加密数据
     * @return 加密完成后的数据 5D:23:15:F5:E2:E1:73:92:68:6E:78:0D:C6:50:02:DA:0A:B8:24:59
     */
    public static String encryptSHA(String message){
        return encryptByStyle(SHA,message,null);
    }

    /**
     * MD5加密 同时添加混淆因子
     * @param message 待加密数据
     * @param confusion 混淆因子
     * @return 加密完成后的数据 27:52:6F:FE:2D:3C:4A:34:6E:E3:A3:08:82:36:35:E1
     */
    public static String encryptMD5Confusion(String message, int[] confusion){
        return encryptByStyle(MD5,message,confusion);
    }

    /**
     * SHA加密 同时添加混淆因子
     * @param message 待加密数据
     * @param confusion 混淆因子
     * @return 加密完成后的数据 5D:23:15:F5:E2:E1:73:92:68:6E:78:0D:C6:50:02:DA:0A:B8:24:59
     */
    public static String encryptSHAWithConfusion(String message, int[] confusion){
        return encryptByStyle(SHA,message,confusion);
    }

    private static String encryptByStyle(String algorithm , String message, int[] confusion){
        if(message==null){
            throw new IllegalAccessError("message is null");
        }
        String value;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            message = confusionMessage(message,confusion);
            md.update(message.getBytes());
            value = byte2hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new IllegalAccessError("加密出错");
        }
        return value;
    }

    /**
     * 获得混淆后的字符串
     * @param message 字符串
     * @param confusion 混淆因子
     * @return 如果混淆因子为空，就返回原来的字符串
     */
    private static String confusionMessage(String message, int[] confusion) {
        if((confusion!=null&&confusion.length>0)){
            //如果混淆因子不为空
            StringBuilder builder = new StringBuilder(message);
            int mask = 0xF;//1111
            char buf ;//中间变量
            int index;
            for (int c : confusion){//从混淆因子中获得数据
                if(c<0){
                    c = -c;
                }
                buf = digits[c&mask];//获得字符
                index = c%builder.length();
                builder.insert(index,buf);
            }
            return builder.toString();
        }else{
            return message;
        }
    }

    /**
     * 将加密后的数据转换为16位格式输出
     * @param b 加密后的数据
     * @return 格式化后的数据
     */
    private static String byte2hex(byte[] b){
        StringBuilder hsBuilder = new StringBuilder();
        for (byte by : b){
            if ((by & 0xFF) < 0x10){
                hsBuilder.append("0");
            }
            hsBuilder.append(Integer.toHexString(by & 0xFF)).append(":");
        }
        if(b.length>0){
            hsBuilder.deleteCharAt(hsBuilder.length()-1);
        }
        return hsBuilder.toString().toUpperCase();
    }
}