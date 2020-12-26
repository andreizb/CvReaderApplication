package com.cv.util;

import com.cv.model.Cv;
import com.cv.model.Skill;

import java.util.Comparator;

public class CvComparators {
    public static class FullStackComparator implements Comparator<Cv> {

        @Override
        public int compare(Cv o1, Cv o2) {
            int score1 = 0;
            int score2 = 0;

            score1 += getSkillScore(o1, "html");
            score2 += getSkillScore(o2, "html");

            score1 += getSkillScore(o1, "css");
            score2 += getSkillScore(o2, "css");

            score1 += getSkillScore(o1, "javascript");
            score2 += getSkillScore(o2, "javascript");

            score1 += getSkillScore(o1, "java");
            score2 += getSkillScore(o2, "java");

            return score2 - score1;
        }
    }

    private static int getSkillScore(Cv o, String skill) {
        return o.getSkills().stream().map(Skill::getSkillName)
                .map(s -> s.toLowerCase().contains(skill))
                .reduce(Boolean::logicalOr).get()
                ? 100 : 0;
    }

    public static class DataAnalysisComparator implements Comparator<Cv> {

        @Override
        public int compare(Cv o1, Cv o2) {
            int score1 = 0;
            int score2 = 0;

            score1 += getSkillScore(o1, "python");
            score2 += getSkillScore(o2, "python");

            score1 += getSkillScore(o1, "tensorflow");
            score2 += getSkillScore(o2, "tensorflow");

            score1 += getSkillScore(o1, "matplotlib");
            score2 += getSkillScore(o2, "matplotlib");

            score1 += getSkillScore(o1, "regression");
            score2 += getSkillScore(o2, "regression");

            return score2 - score1;
        }
    }
}
