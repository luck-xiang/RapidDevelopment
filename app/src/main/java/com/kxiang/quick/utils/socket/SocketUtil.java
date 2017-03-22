package com.kxiang.quick.utils.socket;


import com.kxiang.quick.utils.HEX;

public class SocketUtil {
	/*
	 *order 协议标示
	 *orderContent 协议内容
	 *deviceId 设备号
	 */
	public static String getCmd(String order, String orderContent, String deviceId)
	{
		String mid = "00"+order+deviceId+orderContent;
		String mid_check =  HEX.HeiStringAdd(mid); //校验命令内容
		mid = mid+mid_check;
		String mid_len= Integer.toHexString(mid.length()/2);
		if(mid_len.length() == 1)
		{
			mid_len = "0"+mid_len;
		}
		mid ="AA"+mid_len+mid;
		String all_check = HEX.HeiStringAdd(mid); //校验命令内容
		mid = mid+all_check;
		int head_len = Integer.parseInt(mid_len,16)+3;
		String head_len_str = Integer.toHexString(head_len);
		int len = head_len_str.length();
		for(int i=0;i<4-len;i++)
		{
			head_len_str = "0"+head_len_str;
		}
		String head = "AA"+head_len_str+"000000000000";
		String head_check = HEX.HeiStringAdd(head);
		head +=head_check;
		String cmd = head+mid;
		return cmd;
	}
	
	public static String fun(String m) //日时分秒
	{
		String[] h=m.split(":");
		String all="";
		String day = Integer.toBinaryString(Integer.parseInt(h[0]));
		int day_len = day.length();
		for(int i=0;i<5-day_len;i++)
		{
			day ="0"+day;
		}
		String hour = Integer.toBinaryString(Integer.parseInt(h[1]));
		int hour_len = hour.length();
		for(int i=0;i<5-hour_len;i++)
		{
			hour ="0"+hour;
		}
		String fen = Integer.toBinaryString(Integer.parseInt(h[2]));
		int fen_len = fen.length();
		for(int i=0;i<6-fen_len;i++)
		{
			fen ="0"+fen;
		}
		String miao = Integer.toBinaryString(Integer.parseInt(h[3]));
		int miao_len = miao.length();
		for(int i=0;i<6-miao_len;i++)
		{
			miao ="0"+miao;
		}
		all+=day;
		all+=hour;
		all+=fen;
		all+=miao;
		return getHex(all+"00");
	}
	
	public static String getHex(String binary)
	{
		String ret="";
		String t1 = binary.substring(0, 8);
		String t2 = binary.substring(8, 16);
		String t3 = binary.substring(16, 24);
		String h1 = Integer.toHexString(Integer.valueOf(t1,2));
		String h2 = Integer.toHexString(Integer.valueOf(t2,2));
		String h3 = Integer.toHexString(Integer.valueOf(t3,2));
		System.out.println(h1);
		System.out.println(h2);
		System.out.println(h3);
		int h1_len = h1.length();
		int h2_len = h2.length();
		int h3_len = h3.length();
		for(int i=0;i<2-h1_len;i++)
		{
			h1 ="0"+h1;
		}
		for(int i=0;i<2-h2_len;i++)
		{
			h2 ="0"+h2;
		}
		for(int i=0;i<2-h3_len;i++)
		{
			h3 ="0"+h3;
		}
		System.out.println(h1);
		System.out.println(h2);
		System.out.println(h3);
		ret =h1+h2+h3;
		return ret;
	}
	
	public static String doYearMonth(String src)
	{
		String data = src.substring(2); //取后4位
		String year = data.substring(0, 2);
		String month = data.substring(2, 4);
		
		
		String hex_year = Integer.toHexString(Integer.parseInt(year));
		String hex_month = Integer.toHexString(Integer.parseInt(month));
		int h1_len = hex_year.length();
		int h2_len = hex_month.length();
		for(int i=0;i<2-h1_len;i++)
		{
			hex_year ="0"+hex_year;
		}
		for(int i=0;i<2-h2_len;i++)
		{
			hex_month ="0"+hex_month;
		}
		return hex_year+hex_month;
	}
}
