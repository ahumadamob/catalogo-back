package com.example.catalogo.controller;

import com.example.catalogo.dto.ApiResponse;
import com.example.catalogo.entity.Item;
import com.example.catalogo.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private IItemService service;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Item>>> all(
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<Item> page = service.findAll(pageable);

        ApiResponse<Page<Item>> response = ApiResponse.<Page<Item>>builder()
                .success(true)
                .status(HttpStatus.OK.value())
                .data(page)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return service.create(item);
    }
}
