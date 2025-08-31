package com.example.rutascda.v2.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class AuthHelper {

    private static final String PREFS_NAME = "EncryptedUserPrefs";
    private static final String KEY_USERID = "userid";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "remember";


    private SharedPreferences sharedPreferences;

    public AuthHelper(Context context) {
        try {
            // Crear una clave maestra
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // Crear SharedPreferences cifradas
            sharedPreferences = EncryptedSharedPreferences.create(
                    PREFS_NAME,
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            // Si falla, usa SharedPreferences normales (no recomendado para datos sensibles)
            sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
    }

    // Método para guardar las credenciales del usuario
    public void saveCredentials(String userid, String password, boolean isRememberOn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERID, userid);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_REMEMBER, isRememberOn);
        editor.apply(); // Guarda los datos de forma asíncrona
    }

    // Método para obtener el userid guardado
    public String getUserid() {
        return sharedPreferences.getString(KEY_USERID, null);
    }

    // Método para obtener el password guardado
    public String getPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }
    public boolean isRememberOn() {
        return sharedPreferences.getBoolean(KEY_REMEMBER, false);
    }

    // Método para eliminar las credenciales (logout)
    public void clearCredentials() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_USERID);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_REMEMBER);
        editor.apply();
    }
}
