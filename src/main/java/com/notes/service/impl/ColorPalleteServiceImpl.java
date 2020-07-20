package com.notes.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.notes.exception.ExceptionMessages;
import com.notes.exception.NotFoundException;
import com.notes.model.ColorPallete;
import com.notes.repository.ColorPalleteRepository;
import com.notes.service.ColorPalleteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorPalleteServiceImpl implements ColorPalleteService {

    private final ColorPalleteRepository colorPalleteRepository;

    /**
     * Returns a color pallete for the current authenticated user.
     * 
     */
    @Override
    public ColorPallete getColorPalleteByCurrentUserName() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        return colorPalleteRepository.findByUserName(currentUserName)
        		.orElseThrow(() -> new NotFoundException(ExceptionMessages.PALLETE_NOT_FOUND));
    }

    /**
     * Saves a color pallete if it does not exists, otherwise only 
     * updates the pallete.
     * 
     */
    @Override
    public void saveColorPallete(ColorPallete colorPallete) {
        colorPalleteRepository.findByUserName(colorPallete.getUserName()).ifPresentOrElse(
    		savedColorPallete -> {
				savedColorPallete.setColors(colorPallete.getColors());
				colorPalleteRepository.save(savedColorPallete);
    		}, 
    		() -> {
    			log.info("Saving color pallet for {}", colorPallete.getUserName());
                colorPalleteRepository.save(colorPallete);
    		});
    }
}
