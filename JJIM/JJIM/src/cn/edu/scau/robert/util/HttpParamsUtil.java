/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.scau.robert.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author robert
 */
public class HttpParamsUtil {
    
    private Map<String, String> data;
    
    
    public HttpParamsUtil(HttpServletRequest request) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String tempStr = "";
        do{
            tempStr = br.readLine();
            sb.append(tempStr);
        }
        while(tempStr!=null&&!tempStr.isEmpty());
        br.close();
        data = new HashMap<String, String>();
        String[] strArray = sb.toString().split("&");
        for(int i = 0 ; i < strArray.length; i++){
            data.put(strArray[i].substring(0, strArray[i].indexOf("=")), strArray[i].substring(strArray[i].indexOf("=")+1));
        }
    }
    
    
    public String getString(String key, String defauleValue){
        String result = data.get(key);
        return result==null?defauleValue:result;
    }
    
    public int getInteger(String key){
        return Integer.parseInt(this.getString(key,"0"));
    }
    
    public Date getDate(String key){
        if(this.getLong(key) == 0){
            return null;
        }else{
            return new Date(this.getLong(key));
        }
    }
    
    public long getLong(String key){
        return Long.parseLong(this.getString(key,"0"));
    }
    
    public boolean getBoolean(String key){
        return Boolean.parseBoolean(this.getString(key,null));
    }
    
}
