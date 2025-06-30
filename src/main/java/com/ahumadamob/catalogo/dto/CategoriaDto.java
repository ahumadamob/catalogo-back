package com.ahumadamob.catalogo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaDto {
    private Long id;
    private String nombre;
    private String urlAmigable;
    private String descripcion;
    private Boolean activo;
    private Integer ordenVisualizacion;
    private String icono;
    private Boolean visibleEnMenu;
    private Boolean destacada;
    private String colorHex;
    private Long padreId;
    private List<CategoriaDto> hijos;
}
