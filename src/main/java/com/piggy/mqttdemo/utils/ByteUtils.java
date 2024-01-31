package com.piggy.mqttdemo.utils;

import org.apache.commons.codec.binary.Hex;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * @Author lvxiulei on 2024-01-23 16:59
 * @Description
 * @Version V1.0
 */
public class ByteUtils {

  /**
   * 将指定字节打印到控制台，16进制。
   * @param b
   */
  public static void printHexString( byte[] b) {
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      System.out.print(hex.toUpperCase() + " ");
    }
    System.out.println();
  }

  /**
   * 将指定字节转为16进制字符串 ,加空格，打印适用
   * @param b
   * @return
   */
  public static String getHexString( byte[] b) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      builder.append(hex).append(" ");
    }
    return builder.toString();
  }

  public static byte bit2byte(String bString){
    byte result=0;
    for(int i=bString.length()-1,j=0;i>=0;i--,j++){
      result+=(Byte.parseByte(bString.charAt(i)+"")*Math.pow(2, j));
    }
    return result;
  }

  /**
   * 将一个单字节的byte转换成32位的int
   * @param b
   *            byte
   * @return convert result
   */
  public static int unsignedByteToInt(byte b) {
    return (int) b & 0xFF;
  }

  /**
   * 将一个单字节的byte转换成32位的int-有符号
   * @param b
   * @return
   */
  public static int byte2Int(byte b){
    return (int)b;
  }

  /**
   * 将一个单字节的Byte转换成十六进制的数
   * @param b
   *            byte
   * @return convert result
   */
  public static String byteToHex(byte b) {
    int i = b & 0xFF;
    return Integer.toHexString(i);
  }

  /**
   * 将一个4byte的数组转换成32位的int
   * @param buf
   *            bytes buffer
   * @param pos byte[]中开始转换的位置
   * @return convert result
   */
  public static long unsigned4BytesToInt(byte[] buf, int pos) {
    int firstByte = 0;
    int secondByte = 0;
    int thirdByte = 0;
    int fourthByte = 0;
    int index = pos;
    firstByte = (0x000000FF & ((int) buf[index]));
    secondByte = (0x000000FF & ((int) buf[index + 1]));
    thirdByte = (0x000000FF & ((int) buf[index + 2]));
    fourthByte = (0x000000FF & ((int) buf[index + 3]));
    index = index + 4;
    return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
  }

  /**
   * 将16位的short转换成byte数组
   * @param s
   *            short
   * @return byte[] 长度为2
   * */
  public static byte[] shortToByteArray(short s) {
    byte[] targets = new byte[2];
    for (int i = 0; i < 2; i++) {
      int offset = (targets.length - 1 - i) * 8;
      targets[i] = (byte) ((s >>> offset) & 0xff);
    }
    return targets;
  }

  /**
   * 将32位整数转换成长度为4的byte数组
   * @param s
   *            int
   * @return byte[]
   * */
  public static byte[] intToByteArray(int s) {
    byte[] targets = new byte[2];
    for (int i = 0; i < 4; i++) {
      int offset = (targets.length - 1 - i) * 8;
      targets[i] = (byte) ((s >>> offset) & 0xff);
    }
    return targets;
  }

  /**
   * long to byte[]
   *
   * @param s
   *            long
   * @return byte[]
   * */
  public static byte[] longToByteArray(long s) {
    byte[] targets = new byte[2];
    for (int i = 0; i < 8; i++) {
      int offset = (targets.length - 1 - i) * 8;
      targets[i] = (byte) ((s >>> offset) & 0xff);
    }
    return targets;
  }

  /**32位int转byte[]*/
  public static byte[] int2byte(int res) {
    byte[] targets = new byte[4];
    targets[0] = (byte) (res & 0xff);// 最低位
    targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
    targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
    targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
    return targets;
  }

  /**
   * 将长度为2的byte数组转换为16位int
   * @param res byte[]
   * @return int
   * */
  public static int byte2int(byte[] res) {
    // res = InversionByte(res);
    // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
    int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00); // | 表示安位或
    return targets;
  }

  /**
   * 转换short为byte
   *
   * @param b
   * @param s 需要转换的short
   * @param index
   */
  public static void putShort(byte b[], short s, int index) {
    b[index + 1] = (byte) (s >> 8);
    b[index + 0] = (byte) (s >> 0);
  }

  /**
   * 通过byte数组取到short
   *
   * @param b
   * @param index 第几位开始取
   * @return
   */
  public static short getShort(byte[] b, int index) {
    return (short) (((b[index + 0] << 8) | b[index + 1] & 0xff));
  }

  /**
   * 转换int为byte数组
   * @param bb
   * @param x
   * @param index
   */
  public static void putInt(byte[] bb, int x, int index) {
    bb[index + 3] = (byte) (x >> 24);
    bb[index + 2] = (byte) (x >> 16);
    bb[index + 1] = (byte) (x >> 8);
    bb[index + 0] = (byte) (x >> 0);
  }

  /**
   * 通过byte数组取到int
   * @param bb
   * @param index 第几位开始
   * @return
   */
  public static int getInt(byte[] bb, int index) {
    return (int) ((((bb[index + 3] & 0xff) << 0)
      | ((bb[index + 2] & 0xff) << 8)
      | ((bb[index + 1] & 0xff) << 16)
      | ((bb[index + 0] & 0xff) << 24)));
  }

  /**
   * 转换long型为byte数组
   *
   * @param bb
   * @param x
   * @param index
   */
  public static void putLong(byte[] bb, long x, int index) {
    bb[index + 7] = (byte) (x >> 56);
    bb[index + 6] = (byte) (x >> 48);
    bb[index + 5] = (byte) (x >> 40);
    bb[index + 4] = (byte) (x >> 32);
    bb[index + 3] = (byte) (x >> 24);
    bb[index + 2] = (byte) (x >> 16);
    bb[index + 1] = (byte) (x >> 8);
    bb[index + 0] = (byte) (x >> 0);
  }

  /**
   * 通过byte数组取到long
   *
   * @param bb
   * @param index
   * @return
   */
  public static long getLong(byte[] bb, int index) {
    return ((((long) bb[index + 7] & 0xff) << 56)
      | (((long) bb[index + 6] & 0xff) << 48)
      | (((long) bb[index + 5] & 0xff) << 40)
      | (((long) bb[index + 4] & 0xff) << 32)
      | (((long) bb[index + 3] & 0xff) << 24)
      | (((long) bb[index + 2] & 0xff) << 16)
      | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
  }
//
//  /**
//   * 字符到字节转换
//   *
//   * @param ch
//   * @return
//   */
//  public static void putChar(byte[] bb, char ch, int index) {
//    int temp = (int) ch;
//    // byte[] b = new byte[2];
//    for (int i = 0; i < 2; i ++ ) {
//      bb[index + i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
//      temp = temp >> 8; // 向右移8位
//    }
//  }

  /**
   * 字节到字符转换
   *
   * @param b
   * @return
   */
  public static char getChar(byte[] b, int index) {
    int s = 0;
    if (b[index + 1] > 0)
      s += b[index + 1];
    else
      s += 256 + b[index + 0];
    s *= 256;
    if (b[index + 0] > 0)
      s += b[index + 1];
    else
      s += 256 + b[index + 0];
    char ch = (char) s;
    return ch;
  }
//
//  /**
//   * float转换byte
//   *
//   * @param bb
//   * @param x
//   * @param index
//   */
//  public static void putFloat(byte[] bb, float x, int index) {
//    // byte[] b = new byte[4];
//    int l = Float.floatToIntBits(x);
//    for (int i = 0; i < 4; i++) {
//      bb[index + i] = new Integer(l).byteValue();
//      l = l >> 8;
//    }
//  }

  /**
   * 通过byte数组取得float
   *
   * @param b
   * @param index
   * @return
   */
  public static float getFloat(byte[] b, int index) {
    int l;
    l = b[index + 0];
    l &= 0xff;
    l |= ((long) b[index + 1] << 8);
    l &= 0xffff;
    l |= ((long) b[index + 2] << 16);
    l &= 0xffffff;
    l |= ((long) b[index + 3] << 24);
    return Float.intBitsToFloat(l);
  }

//  /**
//   * double转换byte
//   *
//   * @param bb
//   * @param x
//   * @param index
//   */
//  public static void putDouble(byte[] bb, double x, int index) {
//    // byte[] b = new byte[8];
//    long l = Double.doubleToLongBits(x);
//    for (int i = 0; i < 4; i++) {
//      bb[index + i] = new Long(l).byteValue();
//      l = l >> 8;
//    }
//  }

  /**
   * 通过byte数组取得float
   *
   * @param b
   * @param index
   * @return
   */
  public static double getDouble(byte[] b, int index) {
    long l;
    l = b[0];
    l &= 0xff;
    l |= ((long) b[1] << 8);
    l &= 0xffff;
    l |= ((long) b[2] << 16);
    l &= 0xffffff;
    l |= ((long) b[3] << 24);
    l &= 0xffffffffl;
    l |= ((long) b[4] << 32);
    l &= 0xffffffffffl;
    l |= ((long) b[5] << 40);
    l &= 0xffffffffffffl;
    l |= ((long) b[6] << 48);
    l &= 0xffffffffffffffl;
    l |= ((long) b[7] << 56);
    return Double.longBitsToDouble(l);
  }

  /**
   * 将byte[]转为各种进制的字符串
   * @param bytes byte[]
   * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
   * @return 转换后的字符串
   */
  public static String binary(byte[] bytes, int radix){
    return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
  }

  public static String binary1Byte(byte[] bytes, int start, int radix){
    byte b = bytes[start];
    return new BigInteger(1, new byte[]{b}).toString(radix);// 这里的1代表正数
  }

  public static String binary(byte b, int radix){
    return new BigInteger(1, new byte[]{b}).toString(radix);// 这里的1代表正数
  }

  public static String binary8Bit(byte b){
    String binary = new BigInteger(1, new byte[]{b}).toString(2);// 这里的1代表正数
    StringBuffer sb = new StringBuffer();
    if (binary.length() < 8) {
      for (int i = 0; i < 8 - binary.length(); i++) {
        sb.append("0");
      }
    }
    sb.append(binary);
    return sb.toString();
  }

  public static String binary8Bit(byte[] b){
    String binary = new BigInteger(1, b).toString(2);// 这里的1代表正数
    StringBuffer sb = new StringBuffer();
    if (binary.length() < b.length * 8) {
      for (int i = 0; i < b.length * 8 - binary.length(); i++) {
        sb.append("0");
      }
    }
    sb.append(binary);
    return sb.toString();
  }

  public static byte[] hexStringToBytes(String hexString) {
    if (hexString == null || hexString.equals("")) {
      return null;
    }
    hexString = hexString.toUpperCase().replace(" ", "");
    int length = hexString.length() / 2;
    char[] hexChars = hexString.toCharArray();
    byte[] d = new byte[length];
    for (int i = 0; i < length; i++) {
      int pos = i * 2;
      d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
    }
    return d;
  }

  /**
   * byte[]转换成16进制字符串
   *
   * @param src
   * @return
   */

  public static String bytesToHexString(byte[] src, int index, int end) {
    StringBuilder stringBuilder = new StringBuilder("");
    if (src == null || src.length <= 0 || src.length < end) {
      return null;
    }
    for (int i = index; i < end; i++) {
      int v = src[i] & 0xFF;
      String hv = Integer.toHexString(v);
      if (hv.length() < 2) {
        stringBuilder.append(0);
      }
      stringBuilder.append(hv);
    }
    return stringBuilder.toString();
  }

  public static byte charToByte(char c) {
    return (byte) "0123456789ABCDEF".indexOf(c);
  }

  /** *//**
   * @函数功能: BCD码转为10进制串(阿拉伯数据)
   * @输入参数: BCD码
   * @输出结果: 10进制串
   */
  public static String bcd2Str(byte[] bytes){
    StringBuffer temp=new StringBuffer(bytes.length*2);

    for(int i=0;i<bytes.length;i++){
      temp.append((byte)((bytes[i]& 0xf0)>>>4));
      temp.append((byte)(bytes[i]& 0x0f));
    }
    return temp.toString().substring(0,1).equalsIgnoreCase("0")?temp.toString().substring(1):temp.toString();
  }

  /**
   * @函数功能: byte[]转为BCD码
   * @输入参数: 10进制串
   * @输出结果: BCD码
   */
  public static byte[] bytes2Bcd(byte[] bytes, int index, int end) {
    return str2Bcd(bytesToHexString(bytes, index, end));
  }
  /**
   * 把16进制字符串转换成字节数组
   * @param hex
   * @return
   */
  public static byte[] hexStringToByte(String hex) {
    int len = (hex.length() / 2);
    byte[] result = new byte[len];
    char[] achar = hex.toCharArray();
    for (int i = 0; i < len; i++) {
      int pos = i * 2;
      result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
    }
    return result;
  }

  /**
   * 把字节数组转换成16进制字符串
   * @param bArray
   * @return
   */
  public static final String bytesToHexString(byte[] bArray) {
    StringBuffer sb = new StringBuffer(bArray.length);
    String sTemp;
    for (int i = 0; i < bArray.length; i++) {
      sTemp = Integer.toHexString(0xFF & bArray[i]);
      if (sTemp.length() < 2)
        sb.append(0);
      sb.append(sTemp.toUpperCase());
    }
    return sb.toString();
  }

  private static byte toByte(char c) {
    byte b = (byte) "0123456789ABCDEF".indexOf(c);
    return b;
  }

  /**
   * @函数功能: 10进制串转为BCD码
   * @输入参数: 10进制串
   * @输出结果: BCD码
   */
  public static byte[] str2Bcd(String asc) {
    int len = asc.length();
    int mod = len % 2;

    if (mod != 0) {
      asc = "0" + asc;
      len = asc.length();
    }

    byte abt[] = new byte[len];
    if (len >= 2) {
      len = len / 2;
    }

    byte bbt[] = new byte[len];
    abt = asc.getBytes();
    int j, k;

    for (int p = 0; p < asc.length()/2; p++) {
      if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
        j = abt[2 * p] - '0';
      } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
        j = abt[2 * p] - 'a' + 0x0a;
      } else {
        j = abt[2 * p] - 'A' + 0x0a;
      }

      if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
        k = abt[2 * p + 1] - '0';
      } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
        k = abt[2 * p + 1] - 'a' + 0x0a;
      }else {
        k = abt[2 * p + 1] - 'A' + 0x0a;
      }

      int a = (j << 4) + k;
      byte b = (byte) a;
      bbt[p] = b;
    }
    return bbt;
  }

  /**
   * 二进制字符串转换为byte数组,每个字节以","隔开
   * **/
  public static byte[] conver2HexToByte(String hex2Str) {
    String[] temp = hex2Str.split(",");
    byte[] b = new byte[temp.length];
    for (int i = 0; i < b.length; i++) {
      b[i] = Long.valueOf(temp[i], 2).byteValue();
    }
    return b;
  }


  /**
   * byte数组转换为二进制字符串,每个字节以","隔开
   * **/
  public static String conver2HexStr(byte [] b) {
    StringBuffer result = new StringBuffer();
    for(int i = 0;i<b.length;i++) {
      result.append(Long.toString(b[i] & 0xff, 2));
    }
    return result.toString().substring(0, result.length()-1);
  }

  public static String stringToAscii(String value) {
    StringBuffer sbu = new StringBuffer();
    char[] chars = value.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if(i != chars.length - 1)
      {
        sbu.append((int)chars[i]).append(",");
      }
      else {
        sbu.append((int)chars[i]);
      }
    }
    return sbu.toString();
  }

  public static String asciiToString(String value) {
    StringBuffer sbu = new StringBuffer();
    String[] chars = value.split(",");
    for (int i = 0; i < chars.length; i++) {
      sbu.append((char) Integer.parseInt(chars[i]));
    }
    return sbu.toString();
  }



  public static String bytesToAscii(byte[] bytes, int offset, int dateLen) {
    if ((bytes == null) || (bytes.length == 0) || (offset < 0) || (dateLen <= 0)) {
      return null;
    }
    if ((offset >= bytes.length) || (bytes.length - offset < dateLen)) {
      return null;
    }

    String asciiStr = null;
    byte[] data = new byte[dateLen];
    System.arraycopy(bytes, offset, data, 0, dateLen);
    asciiStr = new String(data, StandardCharsets.US_ASCII);
    return asciiStr;
  }

  public static String bytesToAscii(byte[] bytes, int dateLen) {
    return bytesToAscii(bytes, 0, dateLen);
  }

  public static String bytesToAscii(byte[] bytes) {
    return bytesToAscii(bytes, 0, bytes.length);
  }

  /**
   * byte数组转化为16进制字符串
   * @param arr 数组
   * @param lowerCase 转换后的字母为是否为小写 可不传默认为true
   * @return
   */
  public static String arr2HexStr(byte[] arr,boolean lowerCase){
    return Hex.encodeHexString(arr, lowerCase);
  }

  /**
   * 将16进制字符串转换为byte数组
   * @param hexItr 16进制字符串
   * @return
   */
  public static byte[] hexItr2Arr(String hexItr) throws Exception {
    return Hex.decodeHex(hexItr);
  }

//  public static void main(String[] args) {
//    byte[] bytes = new byte[5];
//    bytes[0] = 0x11;
//    bytes[1] = 0x21;
//    bytes[2] = 0x31;
//    bytes[3] = 0x2;
//    bytes[4] = 0x7F;
//    byte[] bcd = str2Bcd(bytesToHexString(bytes, 0, 5));
//    for (int i = 0; i < bcd.length; i++) {
//      log.info(String.valueOf(bcd[i]));
//    }
//    log.info("---------------------");
//    byte[] bcd2 = bytes2Bcd(bytes, 0, 5);
//    for (int i = 0; i < bcd.length; i++) {
//      log.info(String.valueOf(bcd[i]));
//    }
//  }

  public static int getUnsignedShort(short data){
    return data & 0xFFFF;
  }
}

