package com.example.rutascda.v2.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutascda.R;
import com.example.rutascda.v2.Utils.AuthHelper;
import com.example.rutascda.v2.Utils.MyDialogs;
import com.example.rutascda.v2.entities.Comentario;
import com.example.rutascda.v2.entities.ComentarioRecyclerViewAdapter;
import com.example.rutascda.v2.entities.ResponseData;
import com.example.rutascda.v2.populaters.Populated;
import com.example.rutascda.v2.retrofit.ApiService;
import com.example.rutascda.v2.retrofit.RetrofitClient;

import android.content.DialogInterface;
import android.view.View;
import android.widget.SeekBar;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentariosActivity extends AppCompatActivity {

    private Context context;
    private AuthHelper authHelper;
    private Integer indexRuta;
    private ComentarioRecyclerViewAdapter adapter;

    private Button buttonAddComent;
    private TextView textViewComentRut;
    private RecyclerView miRecycleViewComents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comentarios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context=this;
        authHelper = new AuthHelper(this);

        Intent intentoResultado = getIntent();
        indexRuta = intentoResultado.getIntExtra("indexRuta", 1);

        buttonAddComent=findViewById(R.id.buttonAddComent);
        textViewComentRut=findViewById(R.id.textViewComentRut);
        miRecycleViewComents=findViewById(R.id.miRecycleViewComents);

        textViewComentRut.setText(Populated.instance().get(indexRuta-1).getNombre());

        buttonAddComent.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Introduce los datos de tu comentario");

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            EditText editTextComentario = new EditText(this);
            editTextComentario.setHint("Escribe un Comentario");
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(250); // Establecer límite de 250 caracteres
            editTextComentario.setFilters(filters);
            layout.addView(editTextComentario);

            // Crear un TextView que mostrará el valor numérico de la SeekBar debajo de la barra
            TextView textValoracion = new TextView(this);
            textValoracion.setText("Valoracion"); // El valor inicial de la SeekBar
            textValoracion.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(textValoracion);

            // Crear la SeekBar
            SeekBar seekBar = new SeekBar(this);
            seekBar.setMax(4); // Establece el máximo valor a 4 para que el rango sea 1-5
            seekBar.setProgress(0); // Establece el valor inicial en 0 (lo cual representará el valor 1)
            seekBar.setTickMark(null); // Opcional, para quitar las marcas de la SeekBar
            layout.addView(seekBar);

            // Crear un TextView que mostrará el valor numérico de la SeekBar debajo de la barra
            TextView numberValue = new TextView(this);
            numberValue.setText("1"); // El valor inicial de la SeekBar
            numberValue.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(numberValue);

            // Cambiar el TextView con el valor de la SeekBar
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Actualizar el valor mostrado en el TextView
                    int value = progress + 1; // Incrementar en 1 para mostrar el rango 1-5
                    numberValue.setText(String.valueOf(value)); // Mostrar el valor como número debajo de la SeekBar
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            builder.setView(layout);

            // Configurar los botones del diálogo
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String nombre = authHelper.getUserid();
                    String comentario = editTextComentario.getText().toString();
                    Integer valoracion = Integer.valueOf(numberValue.getText().toString());

                    if (comentario.isEmpty()) {
                        AlertDialog.Builder subBuilder = new AlertDialog.Builder(context);
                        subBuilder.setTitle("[Error] Falta Contenido");
                        subBuilder.setMessage("Necesitas rellenar los campos para poder enviar un comentario.");
                        subBuilder.show();
                        return;
                    }

                    ApiService apiService = RetrofitClient.getApiService();
                    Call<ResponseData> call = apiService.addComentario(nombre, indexRuta, comentario, valoracion);

                    call.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ResponseData resultado = response.body();
                                if (resultado.isResponse()){
                                    MyDialogs.dialogOk(context, "Se ha añadido el comentario", "Se ha añadido el comentario correctamenta, Gracias por tu aportación!");
                                    cargarComentarios();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseData> call, Throwable throwable) {
                            MyDialogs.dialogOk(context, "Fallo de Conexion", "Algo sucedio mal, intentelo más tarde");
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

        });

        cargarComentarios();

    }

    private void cargarComentarios() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Comentario>> call = apiService.getComentarios(indexRuta);

        call.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                if (response.isSuccessful() && response.body() != null && miRecycleViewComents != null) {
                    miRecycleViewComents.setLayoutManager(new LinearLayoutManager(context));
                    adapter= new ComentarioRecyclerViewAdapter(context, response.body());
                    miRecycleViewComents.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable throwable) {
                MyDialogs.dialogOk(context, "Fallo de Conexion", "Algo sucedio mal, intentelo más tarde");
            }
        });
    }
}