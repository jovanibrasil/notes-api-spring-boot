package com.notes.service;

import com.notes.model.ColorPallet;

public interface ColorPalletService {
    ColorPallet getColorPalletByCurrentUserName();
    void saveColorPallet(ColorPallet colorPallet);
}
