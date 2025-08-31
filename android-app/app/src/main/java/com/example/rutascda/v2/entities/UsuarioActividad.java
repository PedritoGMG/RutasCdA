package com.example.rutascda.v2.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioActividad {
    private String userID;
    private Integer actividadID;
    private boolean completada;
    private boolean escaneada;
}
