package com.example.android.assignment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder>  {
    private Context mContext;
    private List<Config> frontlist;
    String search;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Config album = frontlist.get(position);
        holder.name.setText(album.getName());
        holder.fullname.setText(album.getFullname());
        holder.watchercount.setText(album.getId());
        holder.commitcount.setText(album.getId());
        Picasso.with(mContext).load(frontlist.get(position).owner.getAvatarurl()).into(holder.avatar);
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = new MainActivity();
                mainActivity.opening(album.getRepoid(),mContext,search);
            }
        });



    }

    @Override
    public int getItemCount() {
        return frontlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,fullname,watchercount,commitcount;
        public ImageView avatar;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            watchercount = (TextView) view.findViewById(R.id.watchercount);
            avatar = (ImageView) view.findViewById(R.id.thumbnail);
            fullname = (TextView) view.findViewById(R.id.fullname);
            commitcount = (TextView) view.findViewById(R.id.commitCount);
        }
    }

    public AlbumsAdapter(Context mContext, List<Config> albumList, String search) {
        this.mContext = mContext;
        this.frontlist = albumList;
        this.search = search;
    }

}
