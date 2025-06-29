package com.example.catalogo.service.jpa;

import com.example.catalogo.entity.Item;
import com.example.catalogo.repository.ItemRepository;
import com.example.catalogo.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private ItemRepository repository;

    @Override
    public Page<Item> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Item findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Item create(Item item) {
        return repository.save(item);
    }

    @Override
    public Item update(Long id, Item item) {
        if (!repository.existsById(id)) {
            return null;
        }
        item.setId(id);
        return repository.save(item);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
