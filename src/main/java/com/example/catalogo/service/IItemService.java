package com.example.catalogo.service;

import com.example.catalogo.entity.Item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Servicio que define operaciones CRUD para {@link Item}.
 */
public interface IItemService {

    Page<Item> findAll(Pageable pageable);

    Item findById(Long id);

    Item create(Item item);

    Item update(Long id, Item item);

    void delete(Long id);
}
