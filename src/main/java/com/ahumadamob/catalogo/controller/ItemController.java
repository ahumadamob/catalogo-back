package com.ahumadamob.catalogo.controller;

import com.ahumadamob.catalogo.dto.ApiResponse;
import com.ahumadamob.catalogo.entity.Item;
import com.ahumadamob.catalogo.dto.ItemDto;
import com.ahumadamob.catalogo.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import com.ahumadamob.catalogo.dto.ApiError;
import java.util.List;


@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private IItemService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Item>>> all() {
        List<Item> items = service.findAll();

        ApiResponse<List<Item>> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setStatus(HttpStatus.OK.value());
        response.setData(items);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Item>> create(@Valid @RequestBody ItemDto itemDto, BindingResult bindingResult) {
        ApiResponse<Item> response = new ApiResponse<>();

        if (bindingResult.hasErrors()) {
            response.setSuccess(false);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            java.util.List<ApiError> errors = bindingResult.getFieldErrors().stream()
                    .map(err -> new ApiError(err.getField(), err.getDefaultMessage()))
                    .toList();
            response.setErrors(errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Item item = new Item();
        item.setNombre(itemDto.getNombre());
        Item created = service.create(item);
        response.setSuccess(true);
        response.setStatus(HttpStatus.OK.value());
        response.setData(created);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Item>> update(@PathVariable Long id,
                                                    @Valid @RequestBody ItemDto itemDto,
                                                    BindingResult bindingResult) {
        ApiResponse<Item> response = new ApiResponse<>();

        if (bindingResult.hasErrors()) {
            response.setSuccess(false);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            List<ApiError> errors = bindingResult.getFieldErrors().stream()
                    .map(err -> new ApiError(err.getField(), err.getDefaultMessage()))
                    .toList();
            response.setErrors(errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Item item = new Item();
        item.setNombre(itemDto.getNombre());
        Item updated = service.update(id, item);
        if (updated == null) {
            response.setSuccess(false);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setErrors(java.util.List.of(new ApiError("id", "Item no encontrado")));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response.setSuccess(true);
        response.setStatus(HttpStatus.OK.value());
        response.setData(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        ApiResponse<Void> response = new ApiResponse<>();

        Item existing = service.findById(id);
        if (existing == null) {
            response.setSuccess(false);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setErrors(java.util.List.of(new ApiError("id", "Item no encontrado")));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        service.delete(id);

        response.setSuccess(true);
        response.setStatus(HttpStatus.NO_CONTENT.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
