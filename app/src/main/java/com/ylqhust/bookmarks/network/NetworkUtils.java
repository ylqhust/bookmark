package com.ylqhust.bookmarks.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ylqhust.bookmarks.presenter.Listener.OnMainFinishListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

/**
 * Created by apple on 15/11/1.
 */
public class NetworkUtils {

    private static NetworkUtils networkUtils;
    private AsyncHttpClient mClient;

    public synchronized static NetworkUtils getInstance(){
        if (networkUtils == null){
            networkUtils = new NetworkUtils();
        }
        return networkUtils;
    }

    /**
     * 获取title和shortcuticon
     */
    public void getTS(final OnMainFinishListener listener,final String url){
        AsyncHttpClient client = getClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String title = getTitle(responseBody);
                    String shortcuturl = getShortCutUrl(responseBody,url);
                    listener.onTitleIconGetFinished(title,shortcuturl);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    /**
     * 获取title
     * @return
     */
    private String getTitle(byte[] response) throws UnsupportedEncodingException {
        String tmp = new String(response,"UTF-8");
        String regx = "<meta.*charset=(\"|'|)([0-9a-zA-Z-]*)(\"|'|)\\s*/>";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(tmp);
        while(matcher.find()){
            String ma = matcher.group();
            if (ma.contains("gbk") || ma.contains("GBK")){
                tmp = new String(response,"GBK");
                break;
            }
        }
        Document doc = Jsoup.parse(tmp);
        return doc.title();
    }

    /**
     * 获取shortcuturl
     * @param responseString
     * @param url
     * @return
     */
    private String getShortCutUrl(byte[] responseString, String url) {
        return "";
    }

    private AsyncHttpClient getClient(){
        if (mClient == null) {
            mClient = new AsyncHttpClient();
            mClient.setEnableRedirects(false);
            mClient.addHeader("Cache-Control", "max-age=0");
            mClient.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            mClient.addHeader("Accept-Charset", "utf-8, iso-8859-1, utf-16, *;q=0.7");
            mClient.addHeader("Accept-Language", "zh-CN, en-US");
            mClient.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
        }
        return mClient;
    }
}
