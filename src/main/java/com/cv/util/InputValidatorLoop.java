package com.cv.util;

import com.cv.engine.Input;
import com.cv.engine.Screen;

import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InputValidatorLoop<T> {
    private BooleanSupplier blockingCondition = () -> true;
    private String conditionFailMessage;
    private Supplier<T> inputAllocator;
    private Predicate<T> inputValidator = x -> true;
    private String invalidInputMessage;

    private Input input;
    private Screen screen;

    public InputValidatorLoop<T> setComponents(Input input, Screen screen) {
        this.input = input;
        this.screen = screen;

        return this;
    }

    public InputValidatorLoop<T> withBlockingCondition(BooleanSupplier blockingCondition) {
        this.blockingCondition = blockingCondition;
        return this;
    }

    public InputValidatorLoop<T> withConditionFailMessage(String conditionFailMessage) {
        this.conditionFailMessage = conditionFailMessage;
        return this;
    }

    public InputValidatorLoop<T> withInputAllocator(Supplier<T> inputAllocator) {
        this.inputAllocator = inputAllocator;
        return this;
    }

    public InputValidatorLoop<T> withInputValidator(Predicate<T> inputValidator) {
        this.inputValidator = inputValidator;
        return this;
    }

    public InputValidatorLoop<T> withInvalidInputMessage(String invalidInputMessage) {
        this.invalidInputMessage = invalidInputMessage;
        return this;
    }

    public T runLoop() {
        if (inputAllocator == null) {
            return null;
        }

        T myInput = null;

        BooleanSupplier hasNextCondition = input::hasNext;

        while (true) {
            while (!blockingCondition.getAsBoolean()) {
                if (conditionFailMessage != null)
                    screen.displayMessage(conditionFailMessage);

                if (blockingCondition != hasNextCondition)
                    input.getInput();
            }
            myInput = inputAllocator.get();
            if (inputValidator.test(myInput))
                return myInput;
            if (invalidInputMessage != null)
                screen.displayMessage(invalidInputMessage);
        }
    }
}
