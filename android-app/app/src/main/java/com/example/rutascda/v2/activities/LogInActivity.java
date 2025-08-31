package com.example.rutascda.v2.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rutascda.R;
import com.example.rutascda.v2.Utils.AuthHelper;
import com.example.rutascda.v2.Utils.MyDialogs;
import com.example.rutascda.v2.entities.ResponseData;
import com.example.rutascda.v2.retrofit.ApiService;
import com.example.rutascda.v2.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    private Context context;
    private AuthHelper authHelper;
    private Integer indexRuta;

    private Button buttonLogLogIn, buttonLogRegister;
    private EditText editTextLogNick, editTextLogPass;
    private CheckBox checkBoxLogRemember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context=this;
        buttonLogLogIn=findViewById(R.id.buttonLogLogIn);
        buttonLogRegister=findViewById(R.id.buttonLogRegister);
        editTextLogNick=findViewById(R.id.editTextLogNick);
        editTextLogPass=findViewById(R.id.editTextLogPass);
        checkBoxLogRemember=findViewById(R.id.checkBoxLogRemember);
        authHelper = new AuthHelper(this);

        indexRuta = getIntent().getIntExtra("indexRuta", 1);


        buttonLogLogIn.setOnClickListener(view -> {

            String nickName = editTextLogNick.getText().toString();
            String pass = editTextLogPass.getText().toString();

            ApiService apiService = RetrofitClient.getApiService();
            Call<ResponseData> call = apiService.checkLogin(nickName, pass);

            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ResponseData resultado = response.body();
                        if (resultado.isResponse()){
                            authHelper.saveCredentials(nickName, pass, checkBoxLogRemember.isChecked());

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

        });

        buttonLogRegister.setOnClickListener(view -> {
            startActivity(new Intent(context, RegisterActivity.class));
        });

    }
}