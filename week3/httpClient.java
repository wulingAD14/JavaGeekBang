package week3;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class httpClient {

    public HttpResponse get(String url) throws Exception{
        //创建HttpClient实例
        HttpClient client = HttpClientBuilder.create().build();
        //根据URL创建HttpGet实例
        HttpGet get = new HttpGet(url);
        //执行get请求，得到返回体
        HttpResponse response = client.execute(get);
        //判断是否正常返回
        //Header[] headers = response.getAllHeaders();
        return response;
    }


    public String openweb(String website) throws Exception{
        Scanner input = new Scanner(System.in);
        String val = null;  //记录输入度的字符串
        String url = null;

        if(website.equals("https://www.qq.com/")){
            System.out.print("请输入QQ栏目（新闻、体育，2选1），结束输入#：");
            val = input.next();   //等待输入值
            System.out.println("您输入的是："+val);

            if(!val.equals("#")){
                if(val.equals("新闻")) url = in_filter("news");
                else if(val.equals("体育")) url = in_filter("sports");
                else System.out.println("输入非法，程序已经退出！");
            }else{
                System.out.println("你输入了\"#\"，程序已经退出！");
            }
        }
        input.close(); // 关闭资源
        return url;
    }

    public String in_filter(String column) throws Exception{
        String url = "https://" + column + ".qq.com/";
        return url;
    }

    public int out_filter(HttpResponse response) throws Exception{
        int version = 0;
        String data = response.getFirstHeader("Server").toString();
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(data);
        version = Integer.parseInt(m.replaceAll("").trim());
        return version + 999;
    }


    public static void main(String[] args) throws Exception{
        String website = "https://www.qq.com/";
        String weburl = null;

        httpClient hc = new httpClient();

        weburl = hc.openweb(website);
        if(weburl == null ||"".equals(weburl)) return;

        System.out.println("即将打开：" + weburl);
        HttpResponse res = hc.get(weburl);
        int encrypt_version = hc.out_filter(res);
        System.out.println("返回结果：Server Version = " + encrypt_version);
    }

}


