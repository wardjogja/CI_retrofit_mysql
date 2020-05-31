package com.example.siakad22.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siakad22.API.APIRequestData;
import com.example.siakad22.API.RetroServer;
import com.example.siakad22.Model.ResponseModel;
import com.example.siakad22.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama,etNomor,etStatus;
    private Button btnSimpan;
    private String nama,nomor,status,TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        etNama = findViewById(R.id.et_nama);
        etNomor = findViewById(R.id.et_nomor);
        etStatus = findViewById(R.id.et_status);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nama = etNama.getText().toString();
                nomor = etNomor.getText().toString();
                status = etStatus.getText().toString();
                if(nama.trim().equals("")){
                    etNama.setError("Nama Harus diisi");
                }
                else if(nomor.trim().equals("")){
                    etNomor.setError("Nomor Harus di isi");
                }else if(status.trim().equals("")){
                    etStatus.setError("Status harus di isi");

                }else{
                    createData();
                }


            }
        });

    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(nama,nomor,status);
        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String str = response.body().toString();
                Log.e( "TAG","onResponse: " + str);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this,"Gagal Menhubungi Server "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
