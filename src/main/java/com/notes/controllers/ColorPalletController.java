package com.notes.controllers;

import com.notes.models.ColorPallet;
import com.notes.services.ColorPalletService;
import com.notes.services.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/colorpallet")
public class ColorPalletController {

    private final ColorPalletService colorPalletService;

    @GetMapping
    public ResponseEntity<Response<List<String>>> getColorPallet(){
        ColorPallet colorPallet = colorPalletService.getColorPalletByCurrentUserName();
        Response response = new Response<>();
        response.setData(colorPallet.getColors());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> saveColorPallet(@Valid @NotNull @NotEmpty List<String> colors){
        ColorPallet colorPallet = new ColorPallet();
        colorPallet.setColors(colors);
        colorPalletService.saveColorPallet(colorPallet);
        return ResponseEntity.ok(new Response<>());
    }

}
