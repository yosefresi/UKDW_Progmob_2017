package com.yosefresi.testdb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yosef on 11-May-17.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.MyViewHolder> {
    private List<User> friendList;
    private Context context;

    public FriendAdapter(List<User> friendList, Context context){
        this.friendList = friendList;
        this.context = context;
    }

    //mengubah layout xml ke bentuk object (inflate)
    @Override
    public FriendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.datafriend, parent, false);
        return new FriendAdapter.MyViewHolder(view);
    }

    //memasukkan data dari database ke layout spy ditampilkan di item yg ada di recyclerview
    @Override
    public void onBindViewHolder(FriendAdapter.MyViewHolder holder, int position) {
        User user = friendList.get(position);
        holder.txtFriendName.setText(user.getNama());
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtFriendName;

        public MyViewHolder(View view){
            super(view);
            txtFriendName = (TextView) view.findViewById(R.id.txtFriendName);
        }
    }
}
