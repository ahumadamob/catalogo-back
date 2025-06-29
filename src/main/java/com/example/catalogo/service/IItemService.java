package com.example.catalogo.service;

import com.example.catalogo.entity.Item;

import java.util.List;

/**
 * Servicio que define operaciones CRUD para {@link Item}.
 */
public interface IItemService {

    List<Item> findAll();

    Item findById(Long id);

    Item create(Item item);

    Item update(Long id, Item item);

    void delete(Long id);
}
