package com.kxiang.quick.utils;

import android.util.Log;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * @author houj
 *
 */
public final class HEX {
	/**金额为分的格式 */    
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";
	/**
	 * HEX字符串转换为字节数组
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] hexToBytes(String s) {
		s = s.toUpperCase();
		int len = s.length() / 2;
		int ii = 0;
		byte[] bs = new byte[len];
		char c;
		int h;
		for (int i = 0; i < len; i++) {
			c = s.charAt(ii++);
			if (c <= '9') {
				h = c - '0';
			} else {
				h = c - 'A' + 10;
			}
			h <<= 4;
			c = s.charAt(ii++);
			if (c <= '9') {
				h |= c - '0';
			} else {
				h |= c - 'A' + 10;
			}
			bs[i] = (byte) h;
		}
		return bs;
	}

	private final static char[] CS = "0123456789ABCDEF".toCharArray();

	/**
	 * 字节数组转换为HEX字符�?
	 * 
	 * @param bs
	 * @return
	 */
	public static String bytesToHex(byte[] bs) {
		char[] cs = new char[bs.length * 2];
		int io = 0;
		for (int n : bs) {
			cs[io++] = CS[(n >> 4) & 0xF];
			cs[io++] = CS[(n >> 0) & 0xF];
		}
		return new String(cs);
	}

	public static String bytesToHex(byte[] bs, int pos, int len) {
		char[] cs = new char[len * 2];
		int io = 0;
		len += pos;
		for (int i = pos; i < len; i++) {
			int n = bs[i];
			cs[io++] = CS[(n >> 4) & 0xF];
			cs[io++] = CS[(n >> 0) & 0xF];
		}
		return new String(cs);
	}

	public static String bytesToHex(byte[] bs, char gap) {
		char[] cs = new char[bs.length * 3];
		int io = 0;
		for (int n : bs) {
			cs[io++] = CS[(n >> 4) & 0xF];
			cs[io++] = CS[(n >> 0) & 0xF];
			cs[io++] = gap;
		}
		return new String(cs);
	}

	/**
	 * 字节数组转换为CPP数组格式
	 * 
	 * @param bs
	 * @param bytePerLine
	 * @return
	 */
	public static String bytesToCppHex(byte[] bs, int bytePerLine) {

		if (bytePerLine <= 0 || bytePerLine >= 65536) {
			bytePerLine = 65536;
		}
		int lines = 0;
		if (bytePerLine < 65536) {
			lines = (bs.length + bytePerLine - 1) / bytePerLine;
		}

		char[] cs = new char[bs.length * 5 + lines * 3];
		int io = 0;
		int ic = 0;
		for (int n : bs) {
			cs[io++] = '0';
			cs[io++] = 'x';
			cs[io++] = CS[(n >> 4) & 0xF];
			cs[io++] = CS[(n >> 0) & 0xF];
			cs[io++] = ',';
			if (bytePerLine < 65536) {
				if (++ic >= bytePerLine) {
					ic = 0;
					cs[io++] = '/';
					cs[io++] = '/';
					cs[io++] = '\n';
				}
			}
		}
		if (bytePerLine < 65536) {
			if (io < cs.length) {
				cs[io++] = '/';
				cs[io++] = '/';
				cs[io++] = '\n';
			}
		}
		return new String(cs);
	}

	public static String toLeHex(int n, int byteCount) {
		char[] rs = new char[byteCount * 2];
		int io = 0;
		for (int i = 0; i < byteCount; i++) {
			rs[io++] = CS[(n >> 4) & 0xF];
			rs[io++] = CS[(n >> 0) & 0xF];
			n >>>= 8;
		}
		return new String(rs);
	}

	public static String toBeHex(int n, int byteCount) {
		char[] rs = new char[byteCount * 2];
		int io = 0;
		n <<= (32 - byteCount * 8);
		for (int i = 0; i < byteCount; i++) {
			rs[io++] = CS[(n >> 28) & 0xF];
			rs[io++] = CS[(n >> 24) & 0xF];
			n <<= 8;
		}
		return new String(rs);
	}
	
	public static String intToHexString(int n)
	{
		String ret = Integer.toHexString(n);
		int retLen = ret.length();
		for(int i=0;i<8-retLen;i++)
		{
			ret = "0"+ret;
		}
		return ret;
	}
	public static String HeiStringAdd(String n)
	{
		String[] heix = new String[n.length()/2];
		int[] x10 = new int[n.length()/2];
		int total = 0;
		for(int i=0;i<n.length();i++)
		{
			if(i<n.length()/2)
			{
				heix[i] = n.substring(i*2, i*2+2); //0--2  2--4  4--6
			}
		}
		
		for(int i=0;i<heix.length;i++)
		{
			x10[i] = Integer.parseInt(heix[i],16);
			total += x10[i];
		}
		
		
		String hei_total = Integer.toHexString(total);
		
		return hei_total.substring(hei_total.length()-2, hei_total.length());
	}
	
	public static String toHexString(String hei)
	{
		if(hei.length() == 1)
		{
			return "0"+hei;
		}
		else
		{
			return hei;
		}
	}
	
//	public static String getNameInit(String name)
//	{
//		for()
//	}
	//将16进制数转换为汉字
	public static String deUnicode(String content){
		 String enUnicode=null;
		 String deUnicode=null;
		 for(int i=0;i<content.length();i++)
		 {
		     if(enUnicode==null){
		      enUnicode= String.valueOf(content.charAt(i));
		     }else{
		      enUnicode=enUnicode+content.charAt(i);
		     }
		     if(i%4==3){
		      if(enUnicode!=null){
		       if(deUnicode==null){
		        deUnicode= String.valueOf((char) Integer.valueOf(enUnicode, 16).intValue());
		       }else{
		        deUnicode=deUnicode+ String.valueOf((char) Integer.valueOf(enUnicode, 16).intValue());
		       }
		      }
		      enUnicode=null;
		     }
		     
		    }
		 return deUnicode;
	}
	public static String changeF2Y(String amount){
        if(!amount.matches(CURRENCY_FEN_REGEX)) {    
           Log.i("HyActivity","金额格式有误");
        }    
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }

	//16进制转2进制
	public static String hexString2binaryString(String hexString)
	{
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++)
		{
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(hexString
					.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		char ar[] = bString.toCharArray();
		for(int i=0;i<ar.length;i++)
		{
			ar[i] = ar[i] == '1' ? '0':'1';
		}
		String ret = String.valueOf(ar);
		return ret;
	}
	//2进制转16进制
	public static String binaryString2hexString(String bString)
	{
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4)
		{
			iTmp = 0;
			for (int j = 0; j < 4; j++)
			{
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}
	/*
	  小端转大端
	 */
	public static String littleToBig(String n)
	{
		int len = n.length()/2;
		String[] littles = new String[len];
		String ret="";
		for(int i =0;i<=len-1;i++)
		{
			littles[i] = n.substring(i*2,(i+1)*2);
		}
		for(int i=len-1;i>=0;i--)
		{
			ret+=littles[i];
		}
		return ret;
	}
	/*

	  次数转16进制小端

	 */
	public static String freToLittle(int le)
	{
		if(le == 0)
		{
			return "00000000";
		}
		else
		{
			String ret=intToHexString(le);
			return littleToBig(ret);
		}
	}

	/**
	 *
	 * 次数转反码16进制小端
	 *
     * @return
     */
	public static String freToFan(int le)
	{
		if(le == 0)
		{
			return "FFFFFFFF";
		}
		else
		{
			String ret=intToHexString(le);
			ret= littleToBig(ret); //转小端
			ret = hexString2binaryString(ret);
			ret = binaryString2hexString(ret);
			return ret;
		}
	}
	public static String freCishu(String src)
	{
		int ci = Integer.parseInt(src.substring(16,20),16);
		ci+=1;
		String hei_ci = Integer.toHexString(ci);
		int ci_len = hei_ci.length();
		for(int i=0;i<4-ci_len;i++)
		{
			hei_ci = "0"+hei_ci;
		}
		return hei_ci;
	}
	public static String freDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String ret =formatter.format(date);
		return ret.substring(2);
	}

}
