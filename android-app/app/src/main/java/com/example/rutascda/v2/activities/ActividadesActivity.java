package com.example.rutascda.v2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutascda.ItemClickListener;
import com.example.rutascda.MyRecyclerViewAdapter;
import com.example.rutascda.R;
import com.example.rutascda.v2.Utils.AuthHelper;
import com.example.rutascda.v2.Utils.MyDialogs;
import com.example.rutascda.v2.entities.Actividad;
import com.example.rutascda.v2.entities.UsuarioActividad;
import com.example.rutascda.v2.populaters.Populated;
import com.example.rutascda.v2.retrofit.ApiService;
import com.example.rutascda.v2.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadesActivity extends AppCompatActivity implements ItemClickListener {

    MyRecyclerViewAdapter adapter;
    ArrayList<Actividad> actividades;
    ArrayList<UsuarioActividad> usuarioActividades;
    private Context context;
    private Integer indexRuta;
    private AuthHelper authHelper;

    private TextView textViewActName;
    private RecyclerView spinnerActividades;
    private Button buttonActComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actividades);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context=this;
        authHelper=new AuthHelper(context);
        indexRuta = getIntent().getIntExtra("indexRuta", 1);

        buttonActComentarios = findViewById(R.id.buttonActComentarios);
        spinnerActividades = findViewById(R.id.spinnerActividades);
        textViewActName = findViewById(R.id.textViewActName);

        textViewActName.setText(Populated.instance().get(indexRuta-1).getNombre());

        buttonActComentarios.setOnClickListener(view -> {
            Intent intent = new Intent(context, ComentariosActivity.class);
            intent.putExtra("indexRuta", indexRuta);
            startActivity(intent);
        });

        spinnerActividades.setLayoutManager(new LinearLayoutManager(this));
        adapter= new MyRecyclerViewAdapter(context, new ArrayList<>(Populated.instance().get(indexRuta-1).getActividades()));
        adapter.setClickListener(this);
        spinnerActividades.setAdapter(adapter);

        prepareSpinnerData();

    }

    private void prepareSpinnerData() {
        String userID = authHelper.getUserid();
        Integer rutaID = indexRuta;

        ApiService apiService = RetrofitClient.getApiService();
        Call<List<UsuarioActividad>> call = apiService.getUserActividades(userID, rutaID);

        call.enqueue(new Callback<List<UsuarioActividad>>() {
            @Override
            public void onResponse(Call<List<UsuarioActividad>> call, Response<List<UsuarioActividad>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    usuarioActividades = new ArrayList<>(response.body());
                    usuarioActividades.forEach(usuarioActividad -> {
                        Integer idActividad = usuarioActividad.getActividadID();
                        if (spinnerActividades == null)
                            return;
                        RecyclerView.ViewHolder view = spinnerActividades.findViewHolderForAdapterPosition(idActividad > 4 ? ((idActividad-1) % 4) : idActividad-1);
                        if (usuarioActividad.isCompletada()) {
                            view.itemView.setBackgroundResource(R.drawable.bordergreen);
                        } else if (usuarioActividad.isEscaneada()) {
                            view.itemView.setBackgroundResource(R.drawable.borderyellow);
                        } else {
                            view.itemView.setBackgroundResource(R.drawable.borderred);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioActividad>> call, Throwable throwable) {
                MyDialogs.dialogOk(context, "Fallo al tratar de obtener datos del Usuario y las Actividades", "Hubo problemas al tratar de hacer la conexion, intentelo más tarde."+throwable.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(View activisa, int position) {
        if (usuarioActividades.get(position).isCompletada()){
            return;
        }
        if (usuarioActividades.get(position).isEscaneada()){
            Intent intento = new Intent(activisa.getContext(), EnigmaActivity.class);
            intento.putExtra("indexRuta", indexRuta);
            intento.putExtra("indexActividad", position);
            startActivity(intento);
            return;
        }
        Intent intento = new Intent(activisa.getContext(), ScanQRActivity.class);
        intento.putExtra("indexRuta", indexRuta);
        intento.putExtra("indexActividad", position);
        startActivity(intento);
    }


    public void onMenuItemClick(MenuItem menuItem, int position) {
        //TODO
    }
    @Override
    protected void onResume() {
        super.onResume();
        prepareSpinnerData();
    }

}