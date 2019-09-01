package com.jmx.spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 原生api实现爬虫，原理及流程
 * 1. 与对应url的网站建立连接
 * 2. 获取流并将源代码下载存储
 * 3. 解析对应的源代码（正则表达式或其他）
 * 4. 获取想要的数据，此例中利用正则表达式获取163网站内的超链接的地址
 */
public class SpiderDemo {

    /**
     * 获取网站源代码并存储到String字符串中
     * @param webUrl  请求的网站的信息
     * @param charset  请求网站的编码格式
     * @return
     */
    public static String getContent(String webUrl,String charset){
        StringBuilder sb = new StringBuilder();
        try {
            //1. 建立url连接
            URL url = new URL(webUrl);
            InputStream stream = url.openStream();
            //2. 通过IO读取原代码
            BufferedReader bf = new BufferedReader(new InputStreamReader(stream, Charset.forName(charset)));
            String msg = "";
            while((msg = bf.readLine())!=null){
                sb.append(msg);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 使用正则表达式过滤爬取到的源代码内容
     * @param destStr 需解析的目标内容
     * @param pattern 正则表达式
     * @return
     */
    public static List<String> getMatchers(String destStr,String pattern){
        List<String> list = new ArrayList<>();
        // 构建正则表达式
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(destStr);
        // 处理结果
        while(matcher.find()){
            list.add(matcher.group(1));
        }
        return list;
    }


    //运行爬虫程序
    public static void main(String[] args) {
        //1. 获取对应页面的源代码
        String content = getContent("http://www.163.com", "gbk");
        //2. 通过正则表达式获取过滤结果集合
        List<String> result = getMatchers(content, "href=\"([\\w\\s./:]+?)\"");

        //打印list集合
        for (String temp : result) {
            System.out.println(temp);
        }
    }
}
