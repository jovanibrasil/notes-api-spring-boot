package com.notes.service;

import com.notes.model.ColorPallete;

public interface ColorPalleteService {
    ColorPallete getColorPalleteByCurrentUserName();
    void saveColorPallete(ColorPallete colorPallete);
}
