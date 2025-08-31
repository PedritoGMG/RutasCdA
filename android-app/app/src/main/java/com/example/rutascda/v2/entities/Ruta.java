package com.example.rutascda.v2.entities;

import android.content.Intent;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ruta {
    private Integer rutaID;
    private String nombre;
    private List<Actividad> actividades;
}
