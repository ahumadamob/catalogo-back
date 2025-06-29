package com.example.catalogo.controller;

import com.example.catalogo.dto.ApiResponse;
import com.example.catalogo.dto.Pagination;
import com.example.catalogo.entity.Item;
import com.example.catalogo.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private IItemService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Item>>> all(Pageable pageable) {
        Page<Item> page = service.findAll(pageable);

        Pagination pagination = new Pagination(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        ApiResponse<List<Item>> response = ApiResponse.<List<Item>>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .data(page.getContent())
                .pagination(pagination)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return service.create(item);
    }
}
