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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/colorpallets")
public class ColorPalletController {

    private final ColorPalletService colorPalletService;

    @ApiOperation(value = "Busca paleta de cores.", notes = "A paleta é do usuário logado.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Paleta encontrada.", response = String.class, responseContainer = "List"),
		@ApiResponse(code = 404, message = "Paleta não encontrada.")})
	@ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<String> getColorPallet(){
        return colorPalletService.getColorPalletByCurrentUserName().getColors();
    }

    @ApiOperation("Cria uma paleta de cores.")
	@ApiResponses({@ApiResponse(code = 200, message = "Paleta de cores criada com sucesso.", response = Void.class)})
	@PostMapping
    public ResponseEntity<?> saveColorPallet(@RequestBody List<String> colors){
        ColorPallet colorPallet = new ColorPallet();
        colorPallet.setColors(colors);
        colorPalletService.saveColorPallet(colorPallet);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
