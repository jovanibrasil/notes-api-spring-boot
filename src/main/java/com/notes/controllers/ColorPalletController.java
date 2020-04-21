package com.notes.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.models.ColorPallet;
import com.notes.services.ColorPalletService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/colorpallet")
public class ColorPalletController {

    private final ColorPalletService colorPalletService;

    @GetMapping
    public ResponseEntity<List<String>> getColorPallet(){
        ColorPallet colorPallet = colorPalletService.getColorPalletByCurrentUserName();
        return ResponseEntity.ok(colorPallet.getColors());
    }

    @PostMapping
    public ResponseEntity<?> saveColorPallet(@RequestBody List<String> colors){
        ColorPallet colorPallet = new ColorPallet();
        colorPallet.setColors(colors);
        colorPalletService.saveColorPallet(colorPallet);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
