package com.notes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.notes.model.ColorPallet;
import com.notes.service.ColorPalletService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/colorpallets")
public class ColorPalletController {

    private final ColorPalletService colorPalletService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<String> getColorPallet(){
        return colorPalletService.getColorPalletByCurrentUserName().getColors();
    }

    @PostMapping
    public ResponseEntity<?> saveColorPallet(@RequestBody List<String> colors){
        ColorPallet colorPallet = new ColorPallet();
        colorPallet.setColors(colors);
        colorPalletService.saveColorPallet(colorPallet);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
