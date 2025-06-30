package com.ahumadamob.catalogo.repository;

import com.ahumadamob.catalogo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
