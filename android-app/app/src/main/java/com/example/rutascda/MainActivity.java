package com.example.rutascda;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutascda.v2.Utils.AuthHelper;
import com.example.rutascda.v2.Utils.MyDialogs;
import com.example.rutascda.v2.activities.ActividadesActivity;
import com.example.rutascda.v2.activities.LogInActivity;
import com.example.rutascda.v2.activities.ScanQRActivity;
import com.example.rutascda.v2.entities.ResponseData;
import com.example.rutascda.v2.entities.Usuario;
import com.example.rutascda.v2.populaters.Populated;
import com.example.rutascda.v2.retrofit.ApiService;
import com.example.rutascda.v2.retrofit.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private AuthHelper authHelper;

    public static String cosas;
    BottomNavigationView bottomNavView;
    private ImageButton imageButtonFitness, imageButtonAcertijos, imageButtonCuentos, imageButtonMatematica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        authHelper=new AuthHelper(context);
        Populated.instance();

        bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setSelectedItemId(R.id.itemInicio);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intento = null;
                int selecionado = item.getItemId();
                if (selecionado==R.id.itemVolver) {
                    finish();
                }
                if (selecionado==R.id.itemInfo) {
                    intento = new Intent(context, ActivityInfo.class);
                }
                if (intento!=null) {
                    startActivity(intento);
                }
                return false;
            }
        });

        imageButtonAcertijos = findViewById(R.id.imageButtonAcertijos);
        imageButtonFitness = findViewById(R.id.imageButtonFitness);
        imageButtonCuentos = findViewById(R.id.imageButtonCuentos);
        imageButtonMatematica = findViewById(R.id.imageButtonMatematica);

        imageButtonFitness.setOnClickListener(view -> {
            logINRuta(1);
        });
        imageButtonCuentos.setOnClickListener(view -> {
            logINRuta(2);
        });
        imageButtonAcertijos.setOnClickListener(view -> {
            logINRuta(3);
        });
        imageButtonMatematica.setOnClickListener(view -> {
            logINRuta(4);
        });

    }
    private void logINRuta(Integer indexRuta){
        if (authHelper.isRememberOn()){

            String nickName = authHelper.getUserid();
            String pass = authHelper.getPassword();

            ApiService apiService = RetrofitClient.getApiService();
            Call<ResponseData> call = apiService.checkLogin(nickName, pass);

            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ResponseData resultado = response.body();
                        if (resultado.isResponse()){
                            Intent intentAct = new Intent(context, ActividadesActivity.class);
                            intentAct.putExtra("indexRuta", indexRuta);
                            startActivity(intentAct);
                            return;
                        }
                        MyDialogs.dialogOk(context, "Fallo al tratar de Iniciar Sesion", resultado.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable throwable) {
                    MyDialogs.dialogOk(context, "Fallo al tratar de Iniciar Sesion", "Hubo problemas al intentar Iniciar Sesion, intentelo más tarde.");
                }
            });
        } else {
            Intent intentAct = new Intent(context, LogInActivity.class);
            intentAct.putExtra("indexRuta", indexRuta);
            startActivity(intentAct);
        }
    }
}