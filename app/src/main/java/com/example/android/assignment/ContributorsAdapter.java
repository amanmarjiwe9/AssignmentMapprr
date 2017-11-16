package com.example.android.assignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.resource;



public class ContributorsAdapter extends  RecyclerView.Adapter<ContributorsAdapter.RecordHolder> {
    private  int POsition=0;
    ArrayList<Contributors> contributorses;
    Context context;
    Bitmap bitmap;
    private Bitmap myBitmap;

    public ContributorsAdapter(ArrayList<Contributors>  contributorses, Context context) {
        this.contributorses = contributorses;
        this.context = context;
    }

    @Override
    public RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contri, parent, false);

        return new ContributorsAdapter.RecordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecordHolder holder, int position) {
        final Contributors album = contributorses.get(position);
        holder.name.setText(album.getName());
        Picasso.with(context).load(contributorses.get(position).getUrl()).into(holder.avatar);
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UserDetails.class);
                intent.putExtra("USER",album.getName());
                intent.putExtra("URL",album.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return contributorses.size();
    }
    public class RecordHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView avatar;

        public RecordHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.contriName);
            avatar = (ImageView) view.findViewById(R.id.contriImage);

        }
    }

}
