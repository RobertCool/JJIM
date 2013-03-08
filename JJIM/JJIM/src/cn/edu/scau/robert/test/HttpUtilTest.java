/**
 * 
 */
package cn.edu.scau.robert.test;

/**
 * @author robert
 *
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

/**
 *
 * @author robert
 */
public class HttpUtilTest {

    public static String doGet(String url, HttpClient httpClient) throws IOException {
        StringBuilder sb = new StringBuilder();
        HttpGet getMethod = new HttpGet(url);
        HttpResponse response = httpClient.execute(getMethod);
        HttpEntity entity = response.getEntity();
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));

        String result = "";
        while ((result = br.readLine()) != null) {
            sb.append(result);
        }

        br.close();
        getMethod.releaseConnection();

        return sb.toString();
    }

    public static String doPost(String url, HttpClient httpClient, List<NameValuePair> nvps) throws IOException {
        StringBuilder sb = new StringBuilder();
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        HttpResponse response = httpClient.execute(post);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String result = "";
        while ((result = br.readLine()) != null) {
            sb.append(result);
        }

        br.close();
        post.releaseConnection();
        return sb.toString();

    }
    
    public static String doPut(String url, HttpClient httpClient, List<NameValuePair> nvps) throws IOException{
        StringBuilder sb = new StringBuilder();
        HttpPut put = new HttpPut(url);
        put.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        HttpResponse response = httpClient.execute(put);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        
        String result = "";
        while ((result = br.readLine()) != null) {
            sb.append(result);
        }

        br.close();
        put.releaseConnection();
        return sb.toString();
    }
}

