package com.cv.model;

import com.cv.util.InputValidatorLoop;
import com.cv.engine.Input;
import com.cv.engine.Screen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cv {
    private long userID;

    private String firstName;
    private String lastName;
    private String email;
    private String website;
    private String about;

    private List<Skill> skills = new ArrayList<>();
    private List<Experience> experience = new ArrayList<>();
    private List<Education> education = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    public transient Input input;
    public transient Screen screen;

    public Cv setComponents(Input input, Screen screen) {
        this.input = input;
        this.screen = screen;
        return this;
    }

    public String validateName() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Enter your name:");

        return new InputValidatorLoop<String>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNext)
                .withInputAllocator(input::getInput)
                .withInputValidator(StringUtils::isAlpha)
                .withInvalidInputMessage("Invalid name supplied!")
                .runLoop();
    }

    public String validateEmail() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Enter your e-mail:");

        return new InputValidatorLoop<String>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNext)
                .withInputAllocator(input::getInput)
                .withInputValidator(e -> e.matches("[a-zA-Z0-9._]*@[a-z]*.[a-z]*"))
                .withInvalidInputMessage("Invalid e-mail supplied!")
                .runLoop();
    }

    public String validateWebsite() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Enter your website:");

        return new InputValidatorLoop<String>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNext)
                .withInputAllocator(input::getInput)
                .withInputValidator(e -> e.matches("www.[a-zA-Z0-9-]*.[a-z/]*"))
                .withInvalidInputMessage("Invalid website supplied!")
                .runLoop();
    }

    public String validateAbout() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Enter your description:");

        return new InputValidatorLoop<String>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNext)
                .withInputAllocator(input::getDescription)
                .runLoop();
    }
}
