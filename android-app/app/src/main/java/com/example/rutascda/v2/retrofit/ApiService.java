package com.example.rutascda.v2.retrofit;

import com.example.rutascda.v2.entities.Comentario;
import com.example.rutascda.v2.entities.ResponseData;
import com.example.rutascda.v2.entities.UsuarioActividad;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {

//  FormUrlEncoded: Solo para parámetros simples (texto, números) como en un formulario web.
//  Multipart: Para enviar archivos y datos en partes (ideal para cargar imágenes, videos, etc.).

    @FormUrlEncoded
    @POST("checkLogin.php")
    Call<ResponseData> checkLogin(@Field("userID") String userID, @Field("password") String password);

    @FormUrlEncoded
    @POST("registerUser.php")
    Call<ResponseData> registerUser(@Field("userID") String userID,@Field("name") String name, @Field("password") String password);

    @FormUrlEncoded
    @POST("getUserActividades.php")
    Call<List<UsuarioActividad>> getUserActividades(@Field("userID") String userID, @Field("rutaID") Integer rutaID);

    @FormUrlEncoded
    @POST("updateEscaneada.php")
    Call<ResponseData> updateEscaneada(@Field("userID") String userID, @Field("actividadID") Integer actividadID);

    @FormUrlEncoded
    @POST("updateCompletada.php")
    Call<ResponseData> updateCompletada(@Field("userID") String userID, @Field("actividadID") Integer actividadID);

    @FormUrlEncoded
    @POST("getComentarios.php")
    Call<List<Comentario>> getComentarios(@Field("rutaID") Integer rutaID);

    @FormUrlEncoded
    @POST("addComentario.php")
    Call<ResponseData> addComentario(@Field("userID") String userID, @Field("rutaID") Integer rutaID, @Field("texto") String texto, @Field("valoracion") Integer valoracion );
}
