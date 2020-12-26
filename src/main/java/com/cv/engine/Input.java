package com.cv.engine;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Input {
    private final Scanner scanner = new Scanner(System.in);

    public String getInput() {
        return scanner.next();
    }

    public int getInteger() {
        return scanner.nextInt();
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }

    public boolean hasNextInt() {
        return scanner.hasNextInt();
    }

    public String getDescription() {
        scanner.nextLine();
        return scanner.nextLine();
    }
}
