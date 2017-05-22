package com.yosefresi.testdb;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by yosef on 03-May-17.
 */

public class SoalAdapter extends RecyclerView.Adapter<SoalAdapter.MyViewHolder>{
    private List<Soal> soalList;
    private Context context;
    private List<String> keyList;
    String nama;

    public SoalAdapter(List<Soal> soalList, Context context, List<String> keyList, String nama){
        this.soalList = soalList;
        this.context = context;
        this.keyList = keyList;
        this.nama = nama;
    }

    //mengubah layout xml ke bentuk object (inflate)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_soal, parent, false);
        return new MyViewHolder(view);
    }

    //memasukkan data dari database ke layout spy ditampilkan di item yg ada di recyclerview
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Soal soal = soalList.get(position);
        holder.txtJudul.setText(soal.getJudul());
        holder.txtTanggal.setText(soal.getTanggal());
        holder.txtMapel.setText(soal.getMapel());
        holder.txtCommentNum.setText(String.valueOf(soal.getJumlahComment()));
    }

    @Override
    public int getItemCount() {
        return soalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtJudul, txtTanggal, txtMapel, txtCommentNum;

        public MyViewHolder(View view){
            super(view);
            context = view.getContext();
            view.setOnClickListener(this);
            txtJudul = (TextView) view.findViewById(R.id.txtJudul);
            txtTanggal = (TextView) view.findViewById(R.id.txtTanggal);
            txtMapel = (TextView) view.findViewById(R.id.txtMapel);
            txtCommentNum = (TextView) view.findViewById(R.id.txtCommentNum);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            String judul = soalList.get(pos).getJudul();
            String ket = soalList.get(pos).getKeterangan();
            String key = keyList.get(pos);
            goToDetails(judul,ket,key);
        }
    }

    public void goToDetails(String judul, String ket, String key){
        Intent intent = new Intent(context,ItemSoalActivity.class);
        intent.putExtra("JUDUL_SOAL",judul);
        intent.putExtra("KETERANGAN",ket);
        intent.putExtra("KEY_SOAL",key);
        intent.putExtra("USERNAME",nama);
//        Toast.makeText(context, String.valueOf(key), Toast.LENGTH_SHORT).show();
        context.startActivity(intent);
    }
}
