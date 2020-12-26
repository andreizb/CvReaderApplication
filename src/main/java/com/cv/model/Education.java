package com.cv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Education {
    private String schoolName;
    private String studyLevel;
    private String profile;

    private int startYear;
    private int endYear;

    private String educationDescription;
}
