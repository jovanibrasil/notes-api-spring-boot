package com.notes.services;

import com.notes.models.ColorPallet;
import com.notes.repositories.ColorPalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColorPalletServiceImpl implements  ColorPalletService {

    private final ColorPalletRepository colorPalletRepository;

    @Override
    public ColorPallet getColorPalletByCurrentUserName() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ColorPallet> optColorPallet = colorPalletRepository.findByUserName(currentUserName);
        if(!optColorPallet.isPresent()){
            // todo thrown not found exception
        }
        return optColorPallet.get();
    }

    @Override
    public void saveColorPallet(ColorPallet colorPallet) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ColorPallet> optColorPallet = colorPalletRepository.findByUserName(currentUserName);
        if(optColorPallet.isPresent()){
            ColorPallet savedColorPallet = optColorPallet.get();
            savedColorPallet.setColors(colorPallet.getColors());
            colorPalletRepository.save(savedColorPallet);
        }else{
            colorPallet.setUserName(currentUserName);
            colorPalletRepository.save(colorPallet);
        }
    }
}
