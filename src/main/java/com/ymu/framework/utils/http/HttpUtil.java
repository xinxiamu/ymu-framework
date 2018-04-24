package com.ymu.framework.utils.http;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public final class HttpUtil {
	
	private HttpUtil() {
	}
   
	public static String doGet(final String url) {
		 return doGet(url, null);
	}
    public static String doGet(final String url, String parameters) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + parameters;
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            } 
        } catch (Exception e) {
        	e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	e2.printStackTrace();
            }
        }
        return result;
    }
    
    public static String doPost(final String url) {
    	return doPost(url, null);
    }
    
    public static String doPost(final String url, String parameters) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(parameters);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        finally{
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }
            catch(IOException ex){
            	ex.printStackTrace();
            }
        }
        return result;
    }    
   /* public static void main(String[] args) {
		String url = "http://120.77.54.72:8072/index.do?provinceCode=440000&signMethod=5690dddfa28ae085d23518a035707282";
//		System.out.println(doGet(url));
		System.out.println(doPost(url));
	}*/
}