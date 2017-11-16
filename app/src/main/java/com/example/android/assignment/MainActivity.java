package com.example.android.assignment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<ConfigItems> {
    ArrayList<Config>lis ;
    ProgressDialog loading;
    String search;
    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView)findViewById(R.id.textView);
        tv2 = (TextView)findViewById(R.id.textView2);





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(false);
        LinearLayout linearLayoutOfSearchView = (LinearLayout) searchView.getChildAt(0);
        Button yourButton = new Button(getApplicationContext());// and do whatever to your button
        yourButton.setText("Go");

        linearLayoutOfSearchView.addView(yourButton);

        yourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             search =  searchView.getQuery().toString();
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                downloadata(search);

            }
        });

        return true;
    }
    public void downloadata(String search){
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
        loading = new ProgressDialog(MainActivity.this);
        loading.setMessage("Please Wait");
        loading.setIndeterminate(false);
        loading.setCancelable(false);
        loading.show();


    }



    @Override
    public void onResponse(Call<ConfigItems> call, Response<ConfigItems> response) {

        lis = response.body().getPayoutCharges();

        for (Config c:lis
             ) {
            Log.d("******",c.getFullname());
            Log.d("******",c.getId());

        }
        loading.dismiss();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ArrayList<Config> albumList = new ArrayList<>();
        AlbumsAdapter adapter = new AlbumsAdapter(this, albumList,search);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        for(int i=0;i<9;i++) {
            albumList.add(lis.get(i));

        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onFailure(Call<ConfigItems> call, Throwable t) {

    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public  void opening(String a, Context co, String searches){

            try {
                Intent intent = new Intent(co, Details.class);
                intent.putExtra("ID",a);
                intent.putExtra("SEARCH", searches);
                co.startActivity(intent);
            }catch (Exception e){e.printStackTrace();}



    }



}
