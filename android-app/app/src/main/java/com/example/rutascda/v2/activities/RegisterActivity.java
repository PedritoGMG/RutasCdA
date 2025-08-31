package com.example.rutascda.v2.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rutascda.R;
import com.example.rutascda.v2.Utils.MyDialogs;
import com.example.rutascda.v2.entities.ResponseData;
import com.example.rutascda.v2.retrofit.ApiService;
import com.example.rutascda.v2.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Context context;

    private EditText editTextRegName, editTextRegUserID, editTextRegPassword, editTextRegPasswordRep;
    private Button buttonRegRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context=this;
        buttonRegRegistrarse = findViewById(R.id.buttonRegRegistrarse);
        editTextRegName = findViewById(R.id.editTextRegName);
        editTextRegUserID = findViewById(R.id.editTextRegUserID);
        editTextRegPassword = findViewById(R.id.editTextRegPassword);
        editTextRegPasswordRep = findViewById(R.id.editTextRegPasswordRep);

        buttonRegRegistrarse.setOnClickListener(view -> {
            String name = editTextRegName.getText().toString();
            String nickName = editTextRegUserID.getText().toString();
            String pass = editTextRegPassword.getText().toString();
            String pass2 = editTextRegPasswordRep.getText().toString();

            if (name.isBlank() || nickName.isBlank() || pass.isBlank()){
                MyDialogs.dialogOk(context, "Rellena todos los campos", "Hay campos vacios, rellenalos todos para poder registrate");
                return;
            }
            if (!(pass.equals(pass2))){
                MyDialogs.dialogOk(context, "Fallo con las Contraseñas", "Ambas contraseñas tienen que ser iguales para poder registrarte");
                return;
            }


            ApiService apiService = RetrofitClient.getApiService();
            Call<ResponseData> call = apiService.registerUser(nickName, name, pass);

            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ResponseData resultado = response.body();
                        if (resultado.isResponse()) {
                            MyDialogs.dialogOk(
                                    context,
                                    "El registro se ha realizado correctamente",
                                    "Necesitas ahora Iniciar Sesion para poder continuar",
                                    () -> {finish();}
                            );
                            return;
                        }
                        MyDialogs.dialogOk(context, "Algo salio mal...",resultado.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable throwable) {
                    MyDialogs.dialogOk(context, "Fallo al tratar de Registrarse", "Hubo problemas al intentar Registrarse, intentelo más tarde.");
                }
            });
        });
    }
}