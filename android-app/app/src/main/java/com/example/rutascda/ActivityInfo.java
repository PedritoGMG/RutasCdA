package com.example.rutascda;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rutascda.v2.Utils.AuthHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityInfo extends AppCompatActivity {

    BottomNavigationView bottomNavView;

    TextView textViewDireccion;
    TextView textViewTelefono;
    TextView textViewWeb;
    Button buttonCerrarSesion;

    private AuthHelper authHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Context context = this;
        authHelper=new AuthHelper(this);

        buttonCerrarSesion = findViewById(R.id.buttonCerrarSesion);
        bottomNavView = findViewById(R.id.bottomNavViewInfo);
        bottomNavView.setSelectedItemId(R.id.itemInfo);
        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intento = null;
                int selecionado = item.getItemId();
                if (selecionado==R.id.itemVolver) {
                    finish();
                }
                if (selecionado==R.id.itemInicio) {
                    intento = new Intent(context, MainActivity.class);
                }
                if (intento!=null) {
                    startActivity(intento);
                }
                return false;
            }
        });

        textViewDireccion = findViewById(R.id.textViewDireccion);
        textViewDireccion.setOnClickListener(view -> {
            String latitud = "0";
            String longitud = "0";
            String uri = "geo:"+latitud+","+longitud+"?q=71, Zahínos";
            Intent intento = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intento);
        });
        textViewTelefono = findViewById(R.id.textViewTelefono);
        textViewTelefono.setOnClickListener(view -> {
            Intent intento = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:924281040"));
            startActivity(intento);
        });
        textViewWeb = findViewById(R.id.textViewWeb);
        textViewWeb.setOnClickListener(view -> {
            Intent intento = new Intent(Intent.ACTION_WEB_SEARCH);
            intento.putExtra(SearchManager.QUERY,"https://iesocuatroabril.educarex.es/");
            startActivity(intento);
        });
        buttonCerrarSesion.setOnClickListener(view -> {
            authHelper.clearCredentials();
            buttonCerrarSesion.setEnabled(false);
        });

        buttonCerrarSesion.setEnabled(authHelper.isRememberOn());

    }
}