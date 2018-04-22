package com.sh.ethan.sh_loveandroid.appUtils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Author：     ethan
 * CreatTime：  2016/11/28
 * ContactInfo：
 * Description: String tools
 */
public class StringUtils {

    public static final String ALLCHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static boolean isEmpty(String str){
        if(null != str){
            return TextUtils.isEmpty(str);
        }else{
            return true;
        }
    }

    /**
     * 字符串是否相同
     * @param str
     * @param equalStr
     * @return
     */
    public static boolean isEqual(String str , String equalStr){
        if(StringUtils.isEmpty(str)){
            return false;
        }
        return str.equals(equalStr);
    }

    /**
     * 字符串从左向右插入字符
     * @param index
     * @param oldString
     * @param insertString
     * @return
     */
    public static String insertChar(int index,String oldString,String insertString) {
        StringBuffer buffer = new StringBuffer(oldString);
        for (int i = index; i < buffer.length(); i = i + index + 1) {
            buffer.insert(i, insertString);
        }
        return buffer.toString();
    }

    /**
     * 十六进制 ---> 二进制
     *
     * @param hexString 16进制字符串
     * @return
     */
    public static String hex2Binary(String hexString) {
        if (null == hexString || isEmpty(hexString)) {
            return null;
        }
        int decimal = Integer.parseInt(hexString, 16);
        String binaryString = Integer.toBinaryString(decimal);
        int finalLength = hexString.length() * 4;
        int nowLength = binaryString.length();
        if(finalLength > nowLength){
            StringBuilder builder = new StringBuilder();
            for(int i=0; i<(finalLength-nowLength);i++){
                builder.append("0");
            }
            builder.append(binaryString);
            return builder.toString();
        }
        return binaryString;
    }

    /**
     * 二进制 ---> 十六进制
     *
     * @param binaryString 2进制字符串
     * @return
     */
    public static String binary2Hex(String binaryString) {
        if (null == binaryString || isEmpty(binaryString)) {
            return null;
        }
        int decimal = Integer.parseInt(binaryString, 2);
        String hexString = Integer.toHexString(decimal);
        return hexString;
    }

    /**
     * byte 数组 转换成 16进制 字符串
     *
     * @param bytes
     * @return
     */
    public static String bytes2Hex(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        final String Hex = "0123456789ABCDEF";
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(Hex.charAt((b >> 4 & 0x0F)));
            sb.append(Hex.charAt((b & 0x0F)));
        }
        return sb.toString();
    }

    /**
     * 保留count 位小数
     * @param number
     * @param count
     * @return
     */
    public static String retainDecimal( float number,int count){
        DecimalFormat decimalFormat;
        String finalNum = null;
        if(count == 1){
            decimalFormat=new DecimalFormat("##0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            finalNum = decimalFormat.format(number);//format 返回的是字符串
        }else if(count == 2){
            decimalFormat=new DecimalFormat("##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            finalNum = decimalFormat.format(number);//format 返回的是字符串
        }
        return finalNum;
    }
    public static String retainDecimal( double number,int count){
        DecimalFormat decimalFormat;
        String finalNum = null;
        if(count == 1){
            decimalFormat=new DecimalFormat("##0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            finalNum = decimalFormat.format(number);//format 返回的是字符串
        }else if(count == 2){
            decimalFormat=new DecimalFormat("##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            finalNum = decimalFormat.format(number);//format 返回的是字符串
        }
        return finalNum;
    }
    /**
     * 返回一个定长的随机字符串(只包含大小写字母、数字)
     *
     * @param length
     *            随机字符串长度
     * @return 随机字符串
     */
    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }
}
