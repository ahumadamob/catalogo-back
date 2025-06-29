package com.example.catalogo.controller;

import com.example.catalogo.entity.Item;
import com.example.catalogo.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private IItemService service;

    @GetMapping
    public List<Item> all() {
        return service.findAll();
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return service.create(item);
    }
}
