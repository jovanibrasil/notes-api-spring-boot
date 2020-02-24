package com.notes.repositories;

import com.notes.models.ColorPallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorPalletRepository extends CrudRepository<ColorPallet, String> {
    Optional<ColorPallet> findByUserName(String userName);
}
