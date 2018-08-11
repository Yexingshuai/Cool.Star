package com.example.myapp.myapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Tools {

	
	@NonNull
	public static String format(Object... v){
    	return format(1,v);
    }
    @NonNull
	public static String formatPlus(Object... v){
    	return format(2,v);
    }
    @NonNull
	public static String format2(Object... v){
    	return format(4,v);
    }
    @NonNull
	public static String formatPlus2(Object... v){
    	return format(8,v);
    }
    @NonNull
	public static String formatNormal(Object... v){
    	return format(16,v);
    }
    @NonNull
	public static String formatNormal2(Object... v){
    	return format(32,v);
    }
    @NonNull
	private static String format(int fType, Object... v){
    	String fm = "";
    	for(Object o:v){
    		int vType = -1; // 0 字符�?1整形,2浮点�?
    		if (o instanceof Float) {
    			vType = 2;
    		} else if (o instanceof Double) {
    			vType = 2;
    		} else if (o instanceof Integer) {
    			vType = 1;
    		} else if (o instanceof Long) {
    			vType = 1;
    		} else if (o instanceof Short) {
    			vType = 1;
    		} else if (o instanceof Byte) {
    			vType = 1;
    		} else if (o instanceof String) {
    			vType = 0;
    		}     		
    		switch(vType){
    			case 2:
    				double dtemp = Double.parseDouble(o.toString());
    				long lf = (long)((dtemp - (long)dtemp) * 1000000);
    				String xType = "";
    				String xLen = "0";
    				if((fType&10)==fType){
						xType = "+";    						
					}
    				if((fType&44)==fType){
						xLen = "2";
					}
    				if((fType&19)==fType){
	    				if(dtemp - (long)dtemp>0){    					
	    					if(Math.abs((lf+1) %10000)<=1){
								xLen = "2";
		    				}else{
		    					xLen = "3";
		    				}
	    				}
    				}
    				fm +=" %"+xType+"."+xLen+"f";
    				break;
    			case 1:
    				if((fType&10)==fType)  
    					fm +=" %+d";
    				else
    					fm +=" %d";
    				break;
    			default:
    				if("%".equals(o)){
    					fm +="%s";
    				}else{
    					fm +=" %s";
    				}
    		}
    	}
    	fm+=" ";
    	if((fType&58)==fType) {
    		return String.format(fm, v).trim();
    	}else{
    		return String.format(fm, v).replaceAll(" [\\+\\-]0[\\.]{0,1}[0]{0,3}[%]{0,1} ", " -- ").trim();   
    	}
    }
	
	public static String getDeviceId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
	}



	public final String encrypt(String s, String encryptKey) {
		try {
			byte[] data = s.getBytes("utf8");
			byte[] key = encryptKey.getBytes("utf8");

			DESKeySpec des = new DESKeySpec(key);
			SecretKey k = SecretKeyFactory.getInstance("DES").generateSecret(
					des);
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, k);

			byte[] enData = cipher.doFinal(data);
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < enData.length; i++) {
				int l = enData[i];
				if (l < 0) {
					l += 256;
				}
				String x = Integer.toHexString(l);
				if (x.length() == 1) {
					x = new StringBuilder().append("0").append(x).toString();
				}
				builder.append(x);
			}
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public final String decrypt(byte[] data, String decryptKey) {
		try {
			byte[] key = decryptKey.getBytes("utf8");
			DESKeySpec des = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey k = keyFactory.generateSecret(des);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, k);
			byte[] deData = cipher.doFinal(data);
			return new String(deData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public final static String convertToHex(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			int halfbyte = data[i] >>> 4 & 0xF;
			int two_halfs = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <= 9))
					buf.append((char) (48 + halfbyte));
				else
					buf.append((char) (97 + (halfbyte - 10)));
				halfbyte = data[i] & 0xF;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}

	public final static String SHA1(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] sha1hash = new byte[40];
			md.update(text.getBytes("iso-8859-1"), 0, text.length());
			sha1hash = md.digest();
			return convertToHex(sha1hash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public final static boolean checkNetworkEnable(Context context) {
		ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo net = conn.getActiveNetworkInfo();
		if ((net != null) && (net.isConnected())) {
			return true;
		}
		return false;
	}
	
	public final int  getNetWorkType(Context context){
		NetworkInfo networkInfo = ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if(networkInfo!=null){
			return networkInfo.getType();
		}
		return -1;
	}

	public static int getInt(String str){
		return getInt(str,-1);
	}
	public static int getInt(String str,int defaultInt){
		if(str!=null)
			try{
				return Integer.parseInt(str);
			}catch(Exception ex){}
		return defaultInt;
	}
	
	public static float getFloat(String str){
		return getFloat(str,-1);
	}
	public static float getFloat(String str,float defaultFloat){
		if(str!=null)
			try{
				return Float.parseFloat(str);
			}catch(Exception ex){}
		return defaultFloat;
	}
	public static double getDouble(String str){
		return getDouble(str,-1);
	}
	public static double getDouble(String str,double defaultDouble){
		if(str!=null)
			try{
				return Double.parseDouble(str);
			}catch(Exception ex){}
		return defaultDouble;
	}
	public static int getTimes(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY)*10000+c.get(Calendar.MINUTE)*100+c.get(Calendar.SECOND);
	}
	public static int getDay(){
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR)*10000+(c.get(Calendar.MONTH)+1)*100+c.get(Calendar.DAY_OF_MONTH);
	}

	public static String getDate(){
		return getDate(0);
	}
	public static String getDate(long time){
		Date d = Calendar.getInstance().getTime();
		if(time>1000){
			d.setTime(time);
		}
		return String.format(Locale.getDefault(),"%tY%tm%td", d,d,d);
	}
	public static String getFormatDate(){
		return getFormatDate(0);
	}
	public static String getFormatDate(long time){
		Date d = Calendar.getInstance().getTime();
		if(time>1000){
			d.setTime(time);
		}
		return String.format(Locale.getDefault(),"%tY-%tm-%td", d,d,d);
	}
	public static String getDateTime(){
		return getDateTime(0);
	}
	public static String getDateTime(long time){
		Date d = Calendar.getInstance().getTime();
		if(time>1000){
			d.setTime(time);
		}
		return String.format(Locale.getDefault(),"%tY%tm%td%tH%tM%tS", d,d,d,d,d,d);
	}
	public static String getFormatDateTime(){
		return getFormatDateTime(0);
	}
	public static String getFormatDateTime(long time){
		Date d = Calendar.getInstance().getTime();
		if(time>1000){
			d.setTime(time);
		}
		return String.format(Locale.getDefault(),"%tY-%tm-%td %tH:%tM:%tS", d,d,d,d,d,d);
	}

	public static String getFormatTime(){
		return getFormatTime(0);
	}
	public static String getFormatTime(long time){
		Date d = Calendar.getInstance().getTime();
		if(time>1000){
			d.setTime(time);
		}
		return String.format(Locale.getDefault(),"%tH:%tM:%tS", d,d,d);
	}
	
	public static String getFormatDate(String date){
		Integer d = Tools.getInt(date);
		return String.format(Locale.getDefault(),"%d-%02d-%02d", d/10000,d/100%100,d%100);
	}

	public static void writeFile(String info){
		writeFile("cobinlog.txt",info);
	}
	public static void writeFile(String fileName,String info){
		writeFile(fileName,info.getBytes());
	}
	public static void writeFile(String fileName,byte[] info){
		boolean sd= Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if(sd){
			File fdir = Environment.getExternalStorageDirectory();
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(new File(fdir, fileName),true);
				fos.write(info);
				fos.flush();
			} catch (Exception e) {
			}finally{
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
					}
					fos = null;
				}
			}
		}
	}
	
	public static final void println(Object... msg){
		System.out.println(Arrays.toString(msg));
	}
	public static final void log(String tag,String msg){
		Log.d(tag, msg);
	}
	public static final void log(String tag,Object... msg){
		Log.d(tag, Arrays.toString(msg));
	}
	public static final void log(String tag,String msg,Throwable t){
		Log.d(tag, msg,t);
	}
	public final static String getStackTrace(Throwable t) {
		String error = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream(2048);
			PrintWriter writer = new PrintWriter(bos);
			t.printStackTrace(writer);
			writer.flush();
			bos.flush();
			writer.close();
			writer = null;
			error = bos.toString();
		} catch (Exception e) {
		} finally {
			if (bos != null){
				try {
					bos.close();
				} catch (Exception e2) {
				}
			}
		}
		return Tools.getFormatDateTime()+" "+error;
	}
	
	public static final void defaultUncaughtException(){
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable ex) {
				try {
					String msg = Tools.getStackTrace(ex);
					System.out.println(msg);
					debugLog(msg);
				} catch (Exception exp) {					
				}
			}
		});
	}
	
	public static final String urlEncode(String str, String enc) {
		if (str != null && str.length() > 0)
		try {
			return java.net.URLEncoder.encode(str, enc);
		} catch (UnsupportedEncodingException e) {
		}
		return "";
	}

	public static final String urlEncode(String str) {
		return urlEncode(str,"UTF-8");
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 *
	 * @param dir 将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful.
	 * If a deletion fails, the method stops attempting to
	 * delete and returns "false".
	 */
	public static boolean deleteDir(File dir) {
		if(dir.exists()) {
			if (dir.isDirectory()) {
				//递归删除目录中的子目录下
				File[] children = dir.listFiles();
				if (children != null) {
					for (File child : children) {
						boolean success = deleteDir(child);
						if (!success) {
							return false;
						}
					}
				}
			}
			// 目录此时为空或文件，可以删除
			return dir.delete();
		}
		return false;
	}

	/**
	 * 用于获得某个文件及文件夹的大小
	 * @param file 文件或文件夹
	 * @return
     */
	public static long getTotalSize(File file) {
		if(file.exists()) {
			if (file.isFile())
				return file.length();
			File[] children = file.listFiles();
			long total = 0;
			if (children != null) {
				for (File child : children) {
					total += getTotalSize(child);
				}
			}
			return total;
		}
		return 0;
	}

	public static final void debugLog(String msg){
		Tools.writeFile("CbErr.txt",getFormatDateTime()+" "+msg+"\r\n");
	}
	public static final String getString(String v,String d){
		return v==null?d:v;
	}

	public static boolean isNull(String val){
		return val==null || val.trim().length()==0;
	}

	public static String trimDuplicate(String val,String splitV){
		String[] sv=val.split(splitV);
		Set<String> set = new HashSet<String>(Arrays.asList(sv));
		String[] am= set.toArray(new String[set.size()]);
		StringBuffer buffer = new StringBuffer();
		for(String s:am){
			buffer.append(s).append(splitV);
		}
		buffer.delete(buffer.length()-splitV.length(),buffer.length());
		return buffer.toString();
	}

	public static String listToString(List<String> list){
		StringBuffer buffer = new StringBuffer();
		for(String s:list){
			if(buffer.length()>0) buffer.append(",");
			buffer.append(s);
		}
		return buffer.toString();
	}
}
