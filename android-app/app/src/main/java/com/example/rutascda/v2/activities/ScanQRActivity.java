package com.example.rutascda.v2.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanQRActivity extends AppCompatActivity {

    private String nombreActividad;
    private Integer indexRuta;
    private Integer indexActividad;
    private Ubicacion ubicacion;
    private String currentUbicacionMessage = "";
    boolean isIn;
    final double radio = 0.0005;

    private Context context;
    private AuthHelper authHelper;
    private FusedLocationProviderClient fusedLocationClient;
    private Handler handler = new Handler();
    private Runnable runnable;

    private Button buttonScanQR;
    private TextView textViewQRNombreAct;
    private WebView webQRGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_scan_qractivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        context=this;
        authHelper = new AuthHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        buttonScanQR = findViewById(R.id.buttonScanQR);
        textViewQRNombreAct = findViewById(R.id.textViewQRNombreAct);
        webQRGPS = findViewById(R.id.webQRGPS);

        Intent intentoResultado = getIntent();

        indexRuta = intentoResultado.getIntExtra("indexRuta", 1);
        indexActividad = intentoResultado.getIntExtra("indexActividad", 1);

        ubicacion = Populated.instance().get(indexRuta-1).getActividades().get(indexActividad).getUbicacion();
        nombreActividad = Populated.instance().get(indexRuta-1).getActividades().get(indexActividad).getNombre();

        String googleMapsUrl = "https://www.google.com/maps?q=" + ubicacion.getX() + "," + ubicacion.getY();
        webQRGPS.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webQRGPS.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webQRGPS.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        webQRGPS.loadUrl(googleMapsUrl);
        textViewQRNombreAct.setText(nombreActividad);

        // Iniciar actualización automática de ubicación
        runnable = new Runnable() {
            @Override
            public void run() {
                getCurrentLocation();
                handler.postDelayed(this, 7000); // Ejecutar cada 7 segundos
            }
        };
        handler.post(runnable);


        buttonScanQR.setOnClickListener(view -> {
            if (!(buttonScanQR.isEnabled())) {
                return;
            }
            ScanOptions options = new ScanOptions();
            options.setPrompt("Escanea el código QR");
            options.setBeepEnabled(true);
            options.setBarcodeImageEnabled(true);
            qrLauncher.launch(options);
        });


    }

    private final ActivityResultLauncher<ScanOptions> qrLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            if (result.getContents().equals(nombreActividad)){

                ApiService apiService = RetrofitClient.getApiService();
                Call<ResponseData> call = apiService.updateEscaneada(authHelper.getUserid(), Populated.instance().get(indexRuta-1).getActividades().get(indexActividad).getActividadID());

                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ResponseData resultado = response.body();
                            if (resultado.isResponse()){

                                Intent intentQR = new Intent(context, EnigmaActivity.class);
                                intentQR.putExtra("indexRuta", indexRuta);
                                intentQR.putExtra("indexActividad", indexActividad);
                                startActivity(intentQR);
                                finish();

                                return;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseData> call, Throwable throwable) {
                        if (!isFinishing()) {
                            MyDialogs.dialogOk(context, "Fallo de Conexion", "Algo sucedio mal, intentelo más tarde");
                        }
                    }
                });


            } else {
                if (!isFinishing()) {
                    MyDialogs.dialogOk(context, "QR no valido","El QR escaneado no es el deseado.");
                }
            }
        }
    });

    private String getCurrentLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            currentUbicacionMessage = location.getLatitude() + " " + location.getLongitude();
                            isIn = ubicacion.UbicacionisInRadio(new Ubicacion(currentUbicacionMessage), radio);
                            buttonScanQR.setEnabled(isIn);
                        }
                    }
                });
        return currentUbicacionMessage;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // Detener el Handler
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); // Detener el Handler
    }
}