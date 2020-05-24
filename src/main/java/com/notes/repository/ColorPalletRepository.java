package com.notes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.notes.model.ColorPallet;

import java.util.Optional;

@Repository
public interface ColorPalletRepository extends MongoRepository<ColorPallet, String> {
    Optional<ColorPallet> findByUserName(String userName);
}
