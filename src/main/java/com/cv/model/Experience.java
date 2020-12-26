package com.cv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Experience {
    private String jobTitle;
    private String company;
    private float yearsWorked;
    private boolean stillWorking;
    private String jobDescription;
}
