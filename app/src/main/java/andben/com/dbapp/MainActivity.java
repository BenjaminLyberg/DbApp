package andben.com.dbapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<Article> mArticles = new ArrayList<>();
    private ArticleAdapter adapter;

    ListView listView;
    ActionBar actionBar;
    ProgressDialog dialog;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.mainList);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.progress_dialog_message));
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();

        getData();
        setActionBar();

        adapter = new ArticleAdapter(this, mArticles);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent refresh = new Intent(MainActivity.this, MainActivity.class);
                startActivity(refresh);
                finish(); //
            }
        });


    }

    private void setActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.crop);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(R.string.menu_option_text_1);

        return true;
    }

    private void getData() {
        String url = getString(R.string.api_url);

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    try {
                        String jsonData = response.body().string();
                        Log.e(TAG, jsonData);
                        if(response.isSuccessful()){
                            JSONArray jsonResponse = new JSONArray(jsonData);

                            parseJsonResponse(jsonResponse);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                    dialog.hide();
                                }
                            });

                        }
                        else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e){
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.network_error_toast_text,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    private void parseJsonResponse(JSONArray jsonResponse) throws JSONException {

        for(int i = 0; i < jsonResponse.length(); i++){
            JSONObject jsonObject = jsonResponse.getJSONObject(i);
            Article article = new Article();
            article.setId(jsonObject.getInt("id"));
            article.setType(jsonObject.getString("type"));
            article.setTitle(jsonObject.getString("title"));
            article.setImage(jsonObject.getJSONObject("image")
                    .getString("image_url")+"&width=600&height=500");
            mArticles.add(article);
        }
    }

    private void updateDisplay() {

        adapter.notifyDataSetChanged();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }






}
