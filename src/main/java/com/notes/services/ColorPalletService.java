package com.notes.services;

import com.notes.model.ColorPallet;

public interface ColorPalletService {
    ColorPallet getColorPalletByCurrentUserName();
    void saveColorPallet(ColorPallet colorPallet);
}
