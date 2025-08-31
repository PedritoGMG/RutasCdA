package com.example.rutascda.v2.entities;

import com.example.rutascda.Ubicacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Actividad {
    private Integer actividadID;
    private Integer rutaID;
    private String nombre;
    private Ubicacion ubicacion;
    private Enigma enigma;
}
