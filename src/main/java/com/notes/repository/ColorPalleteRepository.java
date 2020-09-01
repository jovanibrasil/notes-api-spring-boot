package com.notes.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.notes.model.ColorPallete;

import java.util.Optional;

@Repository
public interface ColorPalleteRepository extends MongoRepository<ColorPallete, String> {
    Optional<ColorPallete> findByUserName(String userName);
}
