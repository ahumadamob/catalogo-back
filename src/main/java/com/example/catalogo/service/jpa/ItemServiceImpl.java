package com.example.catalogo.service.jpa;

import com.example.catalogo.entity.Item;
import com.example.catalogo.repository.ItemRepository;
import com.example.catalogo.service.IItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements IItemService {

    private final ItemRepository repository;

    public ItemServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Override
    public Item save(Item item) {
        return repository.save(item);
    }
}
