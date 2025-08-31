package com.example.rutascda.v2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rutascda.R;
import com.example.rutascda.Ubicacion;
import com.example.rutascda.v2.Utils.AuthHelper;
import com.example.rutascda.v2.Utils.MyDialogs;
import com.example.rutascda.v2.entities.Enigma;
import com.example.rutascda.v2.entities.ResponseData;
import com.example.rutascda.v2.populaters.Populated;
import com.example.rutascda.v2.retrofit.ApiService;
import com.example.rutascda.v2.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnigmaActivity extends AppCompatActivity {

    private Context context;
    private AuthHelper authHelper;
    private Enigma enigma;
    private Integer indexRuta;
    private Integer indexActividad;

    private TextView textViewEnigNombre, textViewEnigDescripcion;
    private Button buttonEnigResponder;
    private EditText editTextEnigRespuesta;
    private LinearLayout layoutEnigma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enigma);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context = this;
        authHelper = new AuthHelper(this);
        textViewEnigNombre = findViewById(R.id.textViewEnigNombre);
        textViewEnigDescripcion = findViewById(R.id.textViewEnigDescripcion);
        buttonEnigResponder = findViewById(R.id.buttonEnigResponder);
        editTextEnigRespuesta = findViewById(R.id.editTextEnigRespuesta);
        layoutEnigma = findViewById(R.id.layoutEnigma);

        Intent intentoResultado = getIntent();

        indexRuta = intentoResultado.getIntExtra("indexRuta", 1);
        indexActividad = intentoResultado.getIntExtra("indexActividad", 1);

        enigma = Populated.instance().get(indexRuta-1).getActividades().get(indexActividad).getEnigma();

        textViewEnigNombre.setText(enigma.getName());
        textViewEnigDescripcion.setText(enigma.getDescription());

        buttonEnigResponder.setOnClickListener(view -> {
            if (enigma.getAnswer().equals(editTextEnigRespuesta.getText().toString())) {
                layoutEnigma.setBackgroundResource(R.drawable.bordergreen);
                MyDialogs.dialogOk(context, "Respuesta Correcta", "Has respondido correctamente.");
                ApiService apiService = RetrofitClient.getApiService();
                Call<ResponseData> call = apiService.updateCompletada(authHelper.getUserid(), Populated.instance().get(indexRuta-1).getActividades().get(indexActividad).getActividadID());

                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ResponseData resultado = response.body();
                            if (resultado.isResponse()){
                                layoutEnigma.setBackgroundResource(R.drawable.bordergreen);
                                MyDialogs.dialogOk(context, "Respuesta Correcta", "Has respondido correctamente.");
                                return;
                            }
                            MyDialogs.dialogOk(context, "Fallo de Conexion", "Algo sucedio mal, intentelo más tarde");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable throwable) {
                        MyDialogs.dialogOk(context, "Fallo de Conexion", "Algo sucedio mal, intentelo más tarde");
                    }
                });
            } else {
                layoutEnigma.setBackgroundResource(R.drawable.borderred);
                MyDialogs.dialogOk(context, "Respuesta Incorrecta", "Intentalo otra vez...");
            }
        });
    }
}