package com.cv.engine;

import org.springframework.stereotype.Component;

@Component
public class Screen {
    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayCVs(String message) {
        System.out.println(message);
        System.out.println();
    }
}
