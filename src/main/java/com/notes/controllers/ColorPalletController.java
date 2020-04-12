package com.notes.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.models.ColorPallet;
import com.notes.services.ColorPalletService;
import com.notes.services.models.Response;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/colorpallet")
public class ColorPalletController {

    private final ColorPalletService colorPalletService;

    @GetMapping
    public ResponseEntity<Response<List<String>>> getColorPallet(){
        ColorPallet colorPallet = colorPalletService.getColorPalletByCurrentUserName();
        Response<List<String>> response = new Response<>();
        response.setData(colorPallet.getColors());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> saveColorPallet(@RequestBody List<String> colors){
        ColorPallet colorPallet = new ColorPallet();
        colorPallet.setColors(colors);
        colorPalletService.saveColorPallet(colorPallet);
        return ResponseEntity.ok(new Response<>());
    }

}
