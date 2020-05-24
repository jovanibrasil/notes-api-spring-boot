package com.notes.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.notes.exception.ExceptionMessages;
import com.notes.exception.NotFoundException;
import com.notes.model.ColorPallet;
import com.notes.repository.ColorPalletRepository;
import com.notes.service.ColorPalletService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorPalletServiceImpl implements ColorPalletService {

    private final ColorPalletRepository colorPalletRepository;

    /**
     * Returns a color pallet for the current authenticated user.
     * 
     */
    @Override
    public ColorPallet getColorPalletByCurrentUserName() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return colorPalletRepository.findByUserName(currentUserName)
        		.orElseThrow(() -> new NotFoundException(ExceptionMessages.PALLET_NOT_FOUND));
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
