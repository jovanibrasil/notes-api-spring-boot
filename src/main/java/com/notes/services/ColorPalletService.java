package com.notes.services;

import com.notes.models.ColorPallet;

public interface ColorPalletService {
    ColorPallet getColorPalletByCurrentUserName();
    void saveColorPallet(ColorPallet colorPallet);
}
