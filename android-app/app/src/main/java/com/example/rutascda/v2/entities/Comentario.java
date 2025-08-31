package com.example.rutascda.v2.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {
    private String name;
    private String texto;
    private Integer valoracion;
    private String fecha;
}
