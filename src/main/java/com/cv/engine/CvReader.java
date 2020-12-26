package com.cv.engine;

import com.cv.model.*;
import com.cv.util.InputValidatorLoop;
import com.cv.exception.InstanceAlreadyExistsException;
import com.cv.exception.NotFoundException;
import com.cv.service.CvService;
import com.cv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CvReader {
    private User authenticatedUser = null;

    @Autowired
    private Input input;

    @Autowired
    private Screen screen;

    @Autowired
    private CvService cvService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void run() throws IOException {
        while (true) {
            if (this.authenticatedUser == null) {
                authenticate();
            } else {
                executeAction();
            }
        }
    }

    private void authenticate() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Welcome, please select one of the following options:");
        screen.displayMessage("\t1. Login");
        screen.displayMessage("\t2. Register");

        int option = new InputValidatorLoop<Integer>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNextInt)
                .withConditionFailMessage("Invalid argument supplied!")
                .withInputAllocator(input::getInteger)
                .withInputValidator(e -> e == 1 || e == 2)
                .withInvalidInputMessage("Invalid argument supplied!")
                .runLoop();

        if (option == 1) {
            login();
        } else {
            register();
        }
    }

    private void login() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Please enter your username or type <exit> to go back:");

        new InputValidatorLoop<String>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNext)
                .withInputAllocator(input::getInput)
                .withInputValidator(e -> {
                    try {
                        if (e.equals("exit")) {
                            return true;
                        }
                        authenticatedUser = userService.getByUsername(e);
                        return authenticatedUser != null;
                    } catch (NotFoundException exception) {
                        screen.displayMessage(exception.getMessage());
                        return false;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
                })
                .runLoop();
    }

    private void register() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Please enter your desired username or type <exit> to go back:");

        new InputValidatorLoop<String>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNext)
                .withInputAllocator(input::getInput)
                .withInputValidator(e -> {
                    try {
                        if (e.equals("exit")) {
                            return true;
                        }
                        userService.addByUsername(e);
                        return true;
                    } catch (InstanceAlreadyExistsException exception) {
                        screen.displayMessage(exception.getMessage());
                        return false;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
                })
                .runLoop();
    }

    private void executeAction() throws IOException {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Please select one of the following options:");
        screen.displayMessage("\t1. Insert your CV in the database");
        screen.displayMessage("\t2. Read my CV from the database");
        screen.displayMessage("\t3. Get list of CVs");
        screen.displayMessage("\t4. Exit");

        int option = new InputValidatorLoop<Integer>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNextInt)
                .withConditionFailMessage("Invalid option supplied!")
                .withInputAllocator(input::getInteger)
                .withInputValidator(e -> e == 1 || e == 2 || e == 3 || e == 4)
                .withInvalidInputMessage("Invalid option supplied!")
                .runLoop();

        if (option == 1) {
            insertCV();
        } else if (option == 2) {
            readCV();
        } else if (option == 3) {
            readMoreCVs();
        } else {
            authenticatedUser = null;
        }
    }

    private void insertCV() throws IOException {
        Cv cv = new Cv().setComponents(input, screen);
        cv.setUserID(authenticatedUser.getId());

        cv.setFirstName(cv.validateName());
        cv.setLastName(cv.validateName());
        cv.setEmail(cv.validateEmail());
        cv.setWebsite(cv.validateWebsite());
        cv.setAbout(cv.validateAbout());

        cv.setSkills(validateSkills());
        cv.setExperience(validateExperience());
        cv.setEducation(validateEducation());
        cv.setProjects(validateProjects());

        cvService.addCV(cv);
    }

    private List<Project> validateProjects() {
        List<Project> projects = new ArrayList<>();

        while (true) {
            screen.displayMessage("-------------------------------------------------------");
            screen.displayMessage("Please enter anything to add one more project or <exit> to stop:");

            String option = input.getInput();
            if (option.contains("exit")) {
                break;
            }

            screen.displayMessage("");
            screen.displayMessage("Please enter project infos:");

            screen.displayMessage("Title:");
            String title = input.getInput();
            screen.displayMessage("");

            screen.displayMessage("Still working (true / false):");
            boolean work = Boolean.parseBoolean(input.getInput());
            screen.displayMessage("");

            screen.displayMessage("Description:");
            String description = input.getDescription();
            screen.displayMessage("");

            projects.add(new Project(title, work, description));
        }

        return projects;
    }

    private List<Education> validateEducation() {
        List<Education> education = new ArrayList<>();

        while (true) {
            screen.displayMessage("-------------------------------------------------------");
            screen.displayMessage("Please enter anything to add one more education or <exit> to stop:");

            String option = input.getInput();
            if (option.contains("exit")) {
                break;
            }

            screen.displayMessage("");
            screen.displayMessage("Please enter education infos:");

            screen.displayMessage("Name:");
            String title = input.getInput();
            screen.displayMessage("");

            screen.displayMessage("Study level:");
            String level = input.getInput();
            screen.displayMessage("");

            screen.displayMessage("Profile:");
            String profile = input.getInput();
            screen.displayMessage("");

            screen.displayMessage("Start year:");
            int start = input.getInteger();
            screen.displayMessage("");

            screen.displayMessage("End year:");
            int end = input.getInteger();
            screen.displayMessage("");

            screen.displayMessage("Description:");
            String description = input.getDescription();
            screen.displayMessage("");

            education.add(new Education(title, level, profile, start, end, description));
        }

        return education;
    }

    private List<Experience> validateExperience() {
        List<Experience> experience = new ArrayList<>();

        while (true) {
            screen.displayMessage("-------------------------------------------------------");
            screen.displayMessage("Please enter anything to add one more experience or <exit> to stop:");

            String option = input.getInput();
            if (option.contains("exit")) {
                break;
            }

            screen.displayMessage("");
            screen.displayMessage("Please enter experience infos:");

            screen.displayMessage("Name:");
            String title = input.getInput();
            screen.displayMessage("");

            screen.displayMessage("Company:");
            String company = input.getInput();
            screen.displayMessage("");

            screen.displayMessage("Years worked:");
            float profile = Float.parseFloat(input.getInput());
            screen.displayMessage("");

            screen.displayMessage("Still working (true / false):");
            boolean work = Boolean.parseBoolean(input.getInput());
            screen.displayMessage("");

            screen.displayMessage("Description:");
            String description = input.getDescription();
            screen.displayMessage("");

            experience.add(new Experience(title, company, profile, work, description));
        }

        return experience;
    }

    private List<Skill> validateSkills() {
        List<Skill> skills = new ArrayList<>();

        while (true) {
            screen.displayMessage("-------------------------------------------------------");
            screen.displayMessage("Please enter anything to add one more skill or <exit> to stop:");

            String option = input.getInput();
            if (option.contains("exit")) {
                break;
            }

            screen.displayMessage("");
            screen.displayMessage("Please enter skills infos:");

            screen.displayMessage("Title:");
            String title = input.getInput();
            screen.displayMessage("");

            screen.displayMessage("Description:");
            String description = input.getDescription();
            screen.displayMessage("");

            skills.add(new Skill(title, description));
        }

        return skills;
    }

    private void readCV() {
        try {
            screen.displayMessage(cvService.getCV(authenticatedUser.getId()));
        } catch (NotFoundException e) {
            screen.displayMessage(e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void readMoreCVs() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Please select one of the following options:");
        screen.displayMessage("\t1. Display all CVs");
        screen.displayMessage("\t2. Filter current CVs");
        screen.displayMessage("\t3. Sort CVs based on job requirements");

        int option = new InputValidatorLoop<Integer>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNextInt)
                .withConditionFailMessage("Invalid option supplied!")
                .withInputAllocator(input::getInteger)
                .withInputValidator(e -> e == 1 || e == 2 || e == 3)
                .withInvalidInputMessage("Invalid option supplied!")
                .runLoop();

        if (option == 1) {
            cvService.getAllCVs().forEach(screen::displayCVs);
        } else if (option == 2) {
            getFilteredCVs();
        } else {
            getSortedCVs();
        }
    }

    private void getSortedCVs() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Please select one of the following sorting options:");
        screen.displayMessage("\t* full-stack");
        screen.displayMessage("\t* data-analysis");

        String option = new InputValidatorLoop<String>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNext)
                .withInputAllocator(input::getInput)
                .withInputValidator(e -> {
                    return e.equals("full-stack") || e.equals("data-analysis");
                })
                .withInvalidInputMessage("Invalid option supplied!")
                .runLoop();

        cvService.getSortedCVs(option).forEach(screen::displayCVs);
    }

    private void getFilteredCVs() {
        screen.displayMessage("-------------------------------------------------------");
        screen.displayMessage("Please select one of the following filter options:");
        screen.displayMessage("\t* with-github");
        screen.displayMessage("\t* last-year-graduates");
        screen.displayMessage("\t* over-3-years");
        screen.displayMessage("\t* java-programmer");
        screen.displayMessage("\t* worked-at-bank");

        String option = new InputValidatorLoop<String>()
                .setComponents(input, screen)
                .withBlockingCondition(input::hasNext)
                .withInputAllocator(input::getInput)
                .withInputValidator(e -> {
                    return e.equals("with-github") || e.equals("last-year-graduates") ||
                            e.equals("over-3-years") || e.equals("java-programmer") ||
                            e.equals("worked-at-bank");
                })
                .withInvalidInputMessage("Invalid option supplied!")
                .runLoop();

        cvService.getFilteredCVs(option).forEach(screen::displayCVs);
    }
}
