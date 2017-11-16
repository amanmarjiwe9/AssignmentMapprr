package com.example.android.assignment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserDetails extends AppCompatActivity {
    ImageView imageView;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar().setTitle("User Repositories");
        imageView  =(ImageView)findViewById(R.id.userImage);

        String username= getIntent().getExtras().getString("USER");
        String url= getIntent().getExtras().getString("URL");
        Picasso.with(this).load(url).into(imageView);

        getRepos(username);
    }
    public void getRepos(final String fullname) {
        class Download extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UserDetails.this, "Please Wait", "Fetching Info", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Log.d("*****",s);
                Log.d("&&&&",fullname);
                jsonParsed(s);

            }
            @Override
            protected String doInBackground(Void... params) {
                JSONParserSync jsonParserSync = new JSONParserSync();
                String jsonObject =  jsonParserSync.sendGetRequest("https://api.github.com/users/"+fullname+"/repos");
                return jsonObject;
            }
        }
        Download download = new Download();
        download.execute();
    }
    ArrayList<UserRepos>userDetailses;
    public void jsonParsed(String input){
        try {
            userDetailses = new ArrayList<>();

            JSONArray jArr = new JSONArray(input);
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject obj1 = (JSONObject) jArr.get(i);
                UserRepos contributors = new UserRepos();
                String login = obj1.getString("name");
                String id = obj1.getString("id");
                contributors.setRepos(login);
                contributors.setId(id);
                userDetailses.add(contributors);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<UserRepos> albumList = new ArrayList<>();

        UserDetailsAdapter adapter = new UserDetailsAdapter(albumList,this);
       RecyclerView gridView= (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,1);
        gridView.setLayoutManager(mLayoutManager);
        albumList.addAll(userDetailses);
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
