package com.ahumadamob.catalogo.service.jpa;

import com.ahumadamob.catalogo.entity.Item;
import com.ahumadamob.catalogo.repository.ItemRepository;
import com.ahumadamob.catalogo.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private ItemRepository repository;

    @Override
    public List<Item> findAll() {
        return repository.findAll();
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
