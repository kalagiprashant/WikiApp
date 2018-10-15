package testproject.com.app.testproject;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;

import testproject.com.app.testproject.adapter.SearchAdapter;
import testproject.com.app.testproject.model.Pages;
import testproject.com.app.testproject.model.SearchResponse;
import testproject.com.app.testproject.utility.MyNetworkChangeListner;
import testproject.com.app.testproject.utility.NetworkChangeReceiver;
import testproject.com.app.testproject.utility.OkHttp3Connection;
import testproject.com.app.testproject.utility.Utility;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvSearchRsults;
    private ArrayList<Pages> pagesList=new ArrayList<>();
    private SearchAdapter searchAdapter;
    private ImageView ivSearch;
    private EditText etSearch;
    private ProgressBar progressBar;
    private LinearLayout llNoResult,llNoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        llNoResult = (LinearLayout) findViewById(R.id.llNoResult);
        llNoInternet = (LinearLayout) findViewById(R.id.llNoInternet);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        etSearch = (EditText) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        rvSearchRsults = (RecyclerView) findViewById(R.id.rvSearchRsults);
        rvSearchRsults.setHasFixedSize(true);
        rvSearchRsults.setLayoutManager(new LinearLayoutManager(this));

        searchAdapter=new SearchAdapter(this,pagesList);
        rvSearchRsults.setAdapter(searchAdapter);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery();
            }
        });

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    searchQuery();
                    return true;
                }
                return false;
            }
        });

        NetworkChangeReceiver.setMyNetworkChangeListner(new MyNetworkChangeListner() {
            @Override
            public void onNetworkStateChanges(boolean nwStatus) {
                /*if(nwStatus){
                    llNoInternet.setVisibility(View.GONE);
                }else{
                    if(!Utility.isNetworkAvailable(MainActivity.this)){
                        llNoInternet.setVisibility(View.VISIBLE);
                    }
                }*/
            }
        });
    }

    private void searchQuery()
    {
        llNoResult.setVisibility(View.GONE);
        if(Utility.isNetworkAvailable(this)){

            llNoInternet.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(etSearch.getText().toString())){
                progressBar.setVisibility(View.VISIBLE);
                hideKeyBoard();
                query(etSearch.getText().toString());
            }
        }else {
            llNoInternet.setVisibility(View.VISIBLE);
        }

    }

    private void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) etSearch.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etSearch.getApplicationWindowToken(), 0);
    }

    private void query(String query){

        query=query.replace(" ","%20");
//        String url="https://en.wikipedia.org/w/api.php?action=query&formatversion=2&generator=prefixsearch&gpssearch=Narendra%20Modi&gpslimit=10&prop=pageimages|pageterms&piprop=thumbnail&pithumbsize=100&pilimit=10&redirects=&wbptterms=description&format=json";
        String url="https://en.wikipedia.org/w/api.php?action=query&formatversion=2&generator=prefixsearch&gpssearch="+query+"&gpslimit=10&prop=pageimages|pageterms&piprop=thumbnail&pithumbsize=100&pilimit=10&redirects=&wbptterms=description&format=json";

        OkHttp3Connection.doOkHttp3Connection(url, OkHttp3Connection.Request_type.GET, new JSONObject(), new OkHttp3Connection.OkHttp3RequestCallback() {
            @Override
            public void onSuccess(String result) {
                progressBar.setVisibility(View.GONE);
                Log.e("WIKI_RES", result.toString());
                Gson gson=new Gson();
                SearchResponse searchResponse=gson.fromJson(result,SearchResponse.class);
                if(searchResponse.getQuery()!=null && searchResponse.getQuery().getPages().size()>0){
                    pagesList.clear();
                    pagesList.addAll(searchResponse.getQuery().getPages());
                    searchAdapter.notifyDataSetChanged();
                }else {
                    pagesList.clear();
                    searchAdapter.notifyDataSetChanged();
                    llNoResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                progressBar.setVisibility(View.GONE);
                Log.e("WIKI_RES", error.toString());
            }
        });
    }
}
