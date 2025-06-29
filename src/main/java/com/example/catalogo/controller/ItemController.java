package com.example.catalogo.controller;

import com.example.catalogo.entity.Item;
import com.example.catalogo.service.IItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final IItemService service;

    public ItemController(IItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<Item> all() {
        return service.findAll();
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return service.save(item);
    }
}
