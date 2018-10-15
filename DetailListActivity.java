package testproject.com.app.testproject;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import testproject.com.app.testproject.utility.OkHttp3Connection;

public class DetailListActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);

        progressBar=findViewById(R.id.progressBar);
        webView=findViewById(R.id.webView);

        String id=getIntent().getStringExtra("PAGE_ID");

        if(id!=null){
            progressBar.setVisibility(View.VISIBLE);
            getWebLink(id);
        }

    }

    private void getWebLink(final String id){
        String url="https://en.wikipedia.org/w/api.php?action=query&prop=info&pageids="+id+"&inprop=url&format=json";
        OkHttp3Connection.doOkHttp3Connection(url, OkHttp3Connection.Request_type.GET, new JSONObject(), new OkHttp3Connection.OkHttp3RequestCallback() {
            @Override
            public void onSuccess(String result) {
                Log.e("WIKI_RES", result.toString());
                try {
                    JSONObject responseJO=new JSONObject(result);
                    JSONObject jsonObject=responseJO.getJSONObject("query").getJSONObject("pages").getJSONObject(id);
                    String link=jsonObject.getString("fullurl");
                    Log.e("WIKI_RES2", link);

                    startWebView(link);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*Gson gson=new Gson();
                SearchResponse searchResponse=gson.fromJson(result,SearchResponse.class);
                if(searchResponse.getQuery().getPages().size()>0){
                    pagesList.clear();
                    pagesList.addAll(searchResponse.getQuery().getPages());
                    searchAdapter.notifyDataSetChanged();
                }*/
            }

            @Override
            public void onError(String error) {
                Log.e("WIKI_RES", error.toString());
            }
        });
    }



    private void startWebView(String url) {

        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(false);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                progressBar.setVisibility(View.GONE);
            }

        });
        webView.loadUrl(url);

    }
}
