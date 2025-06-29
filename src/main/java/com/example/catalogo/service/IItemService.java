package com.example.catalogo.service;

import com.example.catalogo.entity.Item;

import java.util.List;

public interface IItemService {
    List<Item> findAll();
    Item save(Item item);
}
