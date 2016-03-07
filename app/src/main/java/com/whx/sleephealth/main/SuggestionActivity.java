package com.whx.sleephealth.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

import com.whx.sleephealth.R;
import com.whx.sleephealth.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whx on 2016/1/20.
 */
public class SuggestionActivity extends Activity {

    private ListView foodList, sportsList;
    private List<Map<String, Object>> foodData, sportsData;
    private TextView title;
    public WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.suggestion);
        title = (TextView) findViewById(R.id.title);
        title.setText("建议");

        webview = (WebView) findViewById(R.id.webview1);
        //设置webview属性
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的页面
        webview.loadUrl("http://www.51cto.com/");
        //设置webview视图
        webview.setWebViewClient(new HelloWebViewClient());

    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)&&webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面

            return true;
        }else{
            finish();
            return true;
        }

    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


        /*foodList = (ListView)findViewById(R.id.s_food_list);
        sportsList = (ListView)findViewById(R.id.s_sports_list);

        foodData = getFoodData();
        sportsData = getSportsData();

        ListViewAdapter foodAdapter = new ListViewAdapter(this,foodData);
        ListViewAdapter sportsAdapter = new ListViewAdapter(this,sportsData);

        foodList.setAdapter(foodAdapter);
        sportsList.setAdapter(sportsAdapter);
    }

    private List<Map<String,Object>> getFoodData(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        for(int i=0;i<5;i++)
        {
            map = new HashMap<>();
            map.put("img", R.mipmap.food);
            map.put("title", "睡得少该怎么吃");
            map.put("info", "昨晚又熬夜了，好困啊。。来看看吃点什么让你精神焕发，减轻疲劳吧");
            list.add(map);
        }
        return list;
    }

    private List<Map<String,Object>> getSportsData(){
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        for(int i=0;i<5;i++)
        {
            map = new HashMap<>();
            map.put("img", R.mipmap.sports);
            map.put("title", "生命在于运动");
            map.put("info", "睡眠不规律，导致精神状态不好？来一起锻炼下吧");
            list.add(map);
        }
        return list;
    }
    */

}
