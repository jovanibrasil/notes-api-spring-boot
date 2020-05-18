package com.notes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.notes.model.ColorPallet;

import java.util.Optional;

@Repository
public interface ColorPalletRepository extends CrudRepository<ColorPallet, String> {
    Optional<ColorPallet> findByUserName(String userName);
}
