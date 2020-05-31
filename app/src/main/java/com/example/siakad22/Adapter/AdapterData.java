package com.example.siakad22.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siakad22.API.APIRequestData;
import com.example.siakad22.API.RetroServer;
import com.example.siakad22.Activity.MainActivity;
import com.example.siakad22.Activity.UbahActivity;
import com.example.siakad22.Model.DataModel;
import com.example.siakad22.Model.ResponseModel;
import com.example.siakad22.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listDataPendaftar;
    private int idPendaftar;
    private String nomor,nama,id,status;
    public AdapterData(Context ctx, List<DataModel> listDataPendaftar) {
        this.ctx = ctx;
        this.listDataPendaftar = listDataPendaftar;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent,false);
        HolderData holder = new HolderData(layout);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listDataPendaftar.get(position);

        holder.tvId.setText(dm.getId());
        holder.tvNama.setText(dm.getNama());
        holder.tvNomor.setText(dm.getNomor());
        holder.tvStatus.setText(dm.getStatus());
    }

    @Override
    public int getItemCount() {
        return listDataPendaftar.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvNama,tvNomor,tvId,tvStatus;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tv_nama);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNomor= itemView.findViewById(R.id.tv_nomor);
            tvStatus = itemView.findViewById(R.id.tv_status);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih Operasi yang akan dilakukan");
                    dialogPesan.setCancelable(true);
                    idPendaftar = Integer.parseInt(tvId.getText().toString());
                    //dialogPesan.setIcon()
                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteData();
                            dialogInterface.dismiss();
                            ((MainActivity) ctx).retrieveData();
                        }
                    });
                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialogPesan.show();
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {




                @Override
                public void onClick(View view) {
                    nama = tvNama.getText().toString();
                    nomor = tvNomor.getText().toString();
                    id = tvId.getText().toString();
                    status = tvStatus.getText().toString();
                    Intent i = new Intent(ctx, UbahActivity.class);
                    i.putExtra("nama",nama);
                    i.putExtra("nomor",nomor);
                    i.putExtra("id",id);
                    i.putExtra("status",status);
                    ctx.startActivity(i);
                }
            });


        }

        private void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(idPendaftar);
            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    String msg = response.body().getMsg();
                    Toast.makeText(ctx,"Pesan : "+msg,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx,"Gagal Menghubungi Server : "+t.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });


        }


    }
}
