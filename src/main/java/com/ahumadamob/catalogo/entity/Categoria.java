package com.ahumadamob.catalogo.entity;

import com.ahumadamob.catalogo.entity.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorias", uniqueConstraints = @UniqueConstraint(columnNames = "url_amigable"))
public class Categoria extends BaseEntity {

    @Column(name = "nombre", nullable = false, length = 50)
    @NotBlank(message = "El nombre es obligatorio.")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres.")
    private String nombre;

    @Column(name = "url_amigable", nullable = true, length = 50, unique = true)
    @Size(min = 3, max = 50, message = "La URL amigable debe tener entre 3 y 50 caracteres.")
    @Pattern(regexp = "^[a-z0-9\\-]+$", message = "La URL amigable sólo puede contener minúsculas, dígitos y guiones.")
    private String urlAmigable;

    @Column(name = "descripcion", nullable = true, length = 255)
    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres.")
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    @Column(name = "orden_visualizacion", nullable = true)
    @Min(value = 1, message = "El orden de visualización debe ser mayor a cero.")
    private Integer ordenVisualizacion;

    @Column(name = "icono", nullable = true, length = 100)
    @Size(max = 100, message = "El nombre del icono no puede exceder 100 caracteres.")
    private String icono;

    @Column(name = "visible_en_menu", nullable = false)
    private boolean visibleEnMenu = true;

    @Column(name = "destacada", nullable = false)
    private boolean destacada = false;

    @Column(name = "color_hex", nullable = true, length = 7)
    @Pattern(regexp = "^#([A-Fa-f0-9]{6})$", message = "El valor de colorHex debe ser un código hexadecimal válido.")
    private String colorHex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Categoria padre;

    @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Categoria> hijos = new HashSet<>();
}
