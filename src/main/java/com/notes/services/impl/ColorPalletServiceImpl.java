package com.notes.services.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.notes.exceptions.NotFoundException;
import com.notes.model.ColorPallet;
import com.notes.repositories.ColorPalletRepository;
import com.notes.services.ColorPalletService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorPalletServiceImpl implements  ColorPalletService {

    private final ColorPalletRepository colorPalletRepository;

    /**
     * Returns a color pallet for the current authenticated user.
     * 
     */
    @Override
    public ColorPallet getColorPalletByCurrentUserName() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return colorPalletRepository.findByUserName(currentUserName).orElseThrow(() -> new NotFoundException(""));
    }

    /**
     * Saves a color pallet if it does not exists, otherwise only 
     * updates the pallet.
     * 
     */
    @Override
    public void saveColorPallet(ColorPallet colorPallet) {
        colorPalletRepository.findByUserName(colorPallet.getUserName()).ifPresentOrElse(
    		savedColorPallet -> {
				savedColorPallet.setColors(colorPallet.getColors());
				colorPalletRepository.save(savedColorPallet);
    		}, 
    		() -> {
    			log.info("Saving color pallet for {}", colorPallet.getUserName());
                colorPalletRepository.save(colorPallet);
    		});
    }
}
