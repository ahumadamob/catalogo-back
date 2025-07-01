package com.ahumadamob.catalogo.service;

import com.ahumadamob.catalogo.dto.CategoriaDto;
import com.ahumadamob.catalogo.entity.Categoria;
import com.ahumadamob.catalogo.exception.ResourceNotFoundException;
import com.ahumadamob.catalogo.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoriaRepository repository;

    @Override
    public List<CategoriaDto> getAllCategories() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDto getCategoryById(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        return toDto(categoria);
    }

    @Override
    public CategoriaDto createCategory(CategoriaDto dto) {
        Categoria categoria = toEntity(dto);
        if (dto.getPadreId() != null) {
            Categoria padre = repository.findById(dto.getPadreId())
                    .orElseThrow(() -> new ResourceNotFoundException("Padre no encontrado"));
            categoria.setPadre(padre);
        }
        return toDto(repository.save(categoria));
    }

    @Override
    public CategoriaDto updateCategory(Long id, CategoriaDto dto) {
        Categoria existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        if (dto.getPadreId() != null) {
            Categoria padre = repository.findById(dto.getPadreId())
                    .orElseThrow(() -> new ResourceNotFoundException("Padre no encontrado"));
            if (isDescendant(existing, padre)) {
                throw new IllegalArgumentException("El padre no puede ser descendiente de la categoría");
            }
            existing.setPadre(padre);
        } else {
            existing.setPadre(null);
        }

        updateEntity(existing, dto);
        return toDto(repository.save(existing));
    }

    @Override
    public void deleteCategory(Long id) {
        Categoria existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        repository.delete(existing);
    }

    @Override
    public List<CategoriaDto> getCategoryTree() {
        List<Categoria> categories = repository.findAll();
        return categories.stream()
                .filter(c -> c.getPadre() == null)
                .map(this::toDtoWithChildren)
                .collect(Collectors.toList());
    }

    private CategoriaDto toDto(Categoria entity) {
        return CategoriaDto.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .urlAmigable(entity.getUrlAmigable())
                .descripcion(entity.getDescripcion())
                .activo(entity.isActivo())
                .ordenVisualizacion(entity.getOrdenVisualizacion())
                .icono(entity.getIcono())
                .visibleEnMenu(entity.isVisibleEnMenu())
                .destacada(entity.isDestacada())
                .colorHex(entity.getColorHex())
                .padreId(entity.getPadre() != null ? entity.getPadre().getId() : null)
                .build();
    }

    private CategoriaDto toDtoWithChildren(Categoria entity) {
        CategoriaDto dto = toDto(entity);
        if (entity.getHijos() != null && !entity.getHijos().isEmpty()) {
            dto.setHijos(entity.getHijos().stream()
                    .map(this::toDtoWithChildren)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private Categoria toEntity(CategoriaDto dto) {
        Categoria entity = new Categoria();
        updateEntity(entity, dto);
        return entity;
    }

    private void updateEntity(Categoria entity, CategoriaDto dto) {
        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        entity.setUrlAmigable(dto.getUrlAmigable());
        entity.setDescripcion(dto.getDescripcion());
        if (dto.getActivo() != null) entity.setActivo(dto.getActivo());
        entity.setOrdenVisualizacion(dto.getOrdenVisualizacion());
        entity.setIcono(dto.getIcono());
        if (dto.getVisibleEnMenu() != null) entity.setVisibleEnMenu(dto.getVisibleEnMenu());
        if (dto.getDestacada() != null) entity.setDestacada(dto.getDestacada());
        entity.setColorHex(dto.getColorHex());
    }

    private boolean isDescendant(Categoria origin, Categoria target) {
        if (target == null) return false;
        Categoria current = target.getPadre();
        while (current != null) {
            if (current.equals(origin)) {
                return true;
            }
            current = current.getPadre();
        }
        return false;
    }
}
