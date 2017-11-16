package com.example.android.assignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.RecordHolder> {
    ArrayList<UserRepos>userReposes;
    Context context;
    public UserDetailsAdapter(ArrayList<UserRepos> userReposes, Context context) {
        this.userReposes = userReposes;
        this.context = context;
    }

    @Override
    public UserDetailsAdapter.RecordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repos, parent, false);

        return new UserDetailsAdapter.RecordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserDetailsAdapter.RecordHolder holder, int position) {
        final UserRepos album = userReposes.get(position);
        holder.name.setText(album.getRepos());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Details.class);
                intent.putExtra("ID",album.getId());
                intent.putExtra("SEARCH", album.getRepos());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userReposes.size();
    }

    public class RecordHolder extends RecyclerView.ViewHolder {
        public TextView name;


        public RecordHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.repos);


        }
    }
}
