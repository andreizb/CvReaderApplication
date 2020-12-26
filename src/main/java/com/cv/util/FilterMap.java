package com.cv.util;

import com.cv.model.Cv;
import com.cv.model.Experience;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class FilterMap {
    private static final Map<String, Predicate<Cv>> map = new HashMap<>();

    static {
        map.put("with-github", cv -> {
            return cv.getWebsite().contains("github");
        });

        map.put("last-year-graduates", cv -> {
            return cv.getEducation().stream()
                    .anyMatch(ed -> ed.getEndYear() == Calendar.getInstance().get(Calendar.YEAR));
        });

        map.put("over-3-years", cv -> {
            return !cv.getExperience().isEmpty() && cv.getExperience().stream()
                    .map(Experience::getYearsWorked)
                    .reduce(Float::sum).get() >= 3.0;
        });

        map.put("java-programmer", cv -> {
            return !cv.getSkills().isEmpty() && cv.getSkills().stream()
                    .anyMatch(skill -> skill.getSkillName().toLowerCase().contains("java"));
        });

        map.put("worked-at-bank", cv -> {
            return cv.getExperience().size() > 0 && cv.getExperience().stream()
                    .anyMatch(experience -> experience.getCompany().toLowerCase().contains("bank"));
        });
    }

    public static Predicate<Cv> getFilter(String filter) {
        return map.get(filter);
    }
}
