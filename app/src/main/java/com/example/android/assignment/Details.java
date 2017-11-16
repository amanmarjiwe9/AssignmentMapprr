package com.example.android.assignment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Details extends AppCompatActivity implements Callback<ConfigItems>, View.OnClickListener {
    ProgressDialog loading;
    String id,search;
    TextView name,url,user,description;
    String JSON_STRING;
    RecyclerView gridView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name =(TextView)findViewById(R.id.detailsName);
        url =(TextView)findViewById(R.id.detailsurl);
        url.setOnClickListener(this);

        user =(TextView)findViewById(R.id.detailsuserName);
        description =(TextView)findViewById(R.id.detailsDescription);
        imageView = (ImageView)findViewById(R.id.repoImage);

        id= getIntent().getExtras().getString("ID");
        search = getIntent().getExtras().getString("SEARCH");



        downloadata(search);


    }
    public void downloadata(String search){;
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1200, TimeUnit.SECONDS).readTimeout(1200,TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)

                .baseUrl("https://api.github.com/")

                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // prepare call in Retrofit 2.0
        GettingApi stackOverflowAPI = retrofit.create(GettingApi.class);
        Call<ConfigItems> call1 = stackOverflowAPI.loadRepos(search,"watchers","desc");
        call1.enqueue(this);
        loading = new ProgressDialog(Details.this);
        loading.setMessage("Please Wait");
        loading.setIndeterminate(false);
        loading.setCancelable(false);
        loading.show();


    }
String link;
    @Override
    public void onResponse(Call<ConfigItems> call, Response<ConfigItems> response) {
       /* ArrayList <Config>owner =response.body().getOwnerDetails();*/
        ArrayList <Config>items =response.body().getPayoutCharges();
        for (Config c:items
                ) {
          if(c.getRepoid().equals(id)) {
              Log.d("******", c.owner.getAvatarurl());
              Log.d("******", c.owner.getUrl());
              name.setText(c.getName());
              url.setText(c.owner.getHtml_url());
              description.setText(c.getDescription());
              user.setText(c.owner.getUser());
              link=c.owner.getHtml_url();
              getContributors(c.fullname);
              Picasso.with(this).load(c.owner.getAvatarurl()).into(imageView);
          }


        }
        loading.dismiss();


    }


    @Override
    public void onFailure(Call<ConfigItems> call, Throwable t) {

    }
    public void getContributors(final String fullname) {
        class Download extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Details.this, "Please Wait", "Fetching Info", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                Log.d("*****",s);
                Log.d("&&&&",fullname);
                jsonParsed(s);

            }
            @Override
            protected String doInBackground(Void... params) {
                JSONParserSync jsonParserSync = new JSONParserSync();
               String jsonObject =  jsonParserSync.sendGetRequest("https://api.github.com/repos/"+fullname+"/contributors");
                return jsonObject;
            }
        }
        Download download = new Download();
        download.execute();
    }
    ArrayList<Contributors>contributorses;
    public void jsonParsed(String input){
        contributorses =new ArrayList<>();
        try {

            JSONArray jArr = new JSONArray(input);
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject obj1 = (JSONObject) jArr.get(i);
                Contributors contributors = new Contributors();
                String login = obj1.getString("login");
                String  avatar = obj1.getString("avatar_url");
                contributors.setName(login);
                contributors.setUrl(avatar);
                contributorses.add(contributors);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Contributors> albumList = new ArrayList<>();

        ContributorsAdapter adapter = new ContributorsAdapter(albumList,this);
        gridView= (RecyclerView) findViewById(R.id.gridview);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        gridView.setLayoutManager(mLayoutManager);
        albumList.addAll(contributorses);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();





    }

    @Override
    public void onClick(View v) {
        SpannableString content = new SpannableString(link);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        url.setText(content);

    }
}
