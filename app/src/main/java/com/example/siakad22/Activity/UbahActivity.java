package com.example.siakad22.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siakad22.API.APIRequestData;
import com.example.siakad22.API.RetroServer;
import com.example.siakad22.Model.ResponseModel;
import com.example.siakad22.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbahActivity extends AppCompatActivity {
    private EditText etNama,etNomor,etStatus;
    private TextView tv_id_ed;
    private String nama,status,nomor,id;
   // private int id;
    private Button btnUbah;
    private String namaset,nomorset,idset,statusset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);
        etNama = findViewById(R.id.et_nama_ed);
        etNomor = findViewById(R.id.et_nomor_ed);
        etStatus = findViewById(R.id.et_status_ed);
        tv_id_ed = findViewById(R.id.tv_id_ed);
        btnUbah = findViewById(R.id.btn_Ubah);
        Intent i2 = getIntent();
        namaset= i2.getStringExtra("nama");
        nomorset = i2.getStringExtra("nomor");
        statusset = i2.getStringExtra("status");
        idset = i2.getStringExtra("id");

        etNama.setText(namaset);
        etNomor.setText(nomorset);
        etStatus.setText(statusset);
        tv_id_ed.setText(idset);

        Toast.makeText(this,"Pesan : "+namaset,Toast.LENGTH_SHORT).show();

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama=etNama.getText().toString();
                status = etStatus.getText().toString();
                nomor = etNomor.getText().toString();
                id = tv_id_ed.getText().toString();
                if(nama.trim().equals("")){
                    etNama.setError("Nama Harus di isi");
                }else if(nomor.trim().equals("")){
                    etNomor.setError("Nomor Harus di isi");
                }else if (status.trim().equals("")){
                    etStatus.setError("Status Harus di isi");
                }else {
                    ubah_data();
                }

            }
        });

        //show_data();
    }

    private void ubah_data(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> ubahData = ardData.ardUpdateData(nama,nomor,status,id);
        ubahData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String str = response.body().toString();
                Log.e("Tag","onResponse"+str);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UbahActivity.this,"Gagal Menghubungi Server"+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
