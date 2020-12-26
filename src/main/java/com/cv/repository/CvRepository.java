package com.cv.repository;

import com.cv.model.Cv;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CvRepository {
    private final Gson gson;

    private final String stringDirectoryPath = "./my-work/work-5.hw/src/main/resources/cvs/";
    private List<Cv> cvsList = new ArrayList<>();

    @Autowired
    public CvRepository(Gson gson) {
        this.gson = gson;
    }

    @PostConstruct
    private void loadCVsList() throws IOException {
        File dir = new File(stringDirectoryPath);
        FileFilter fileFilter = new WildcardFileFilter("*.json");
        File[] files = dir.listFiles(fileFilter);

        for (File file : files) {
            String str = FileUtils.readFileToString(file, "UTF-8");
            cvsList.add(gson.fromJson(str, Cv.class));
        }
    }

    public Optional<Cv> getCV(long userID) {
        return cvsList.stream()
                .filter(cv -> cv.getUserID() == userID)
                .findFirst();
    }

    public boolean existsByUsername(long userID) {
        return getCV(userID).isPresent();
    }

    public List<Cv> getAllCVs() {
        return cvsList;
    }

    public void addCV(Cv cv) throws IOException {
        Writer writer = new FileWriter(stringDirectoryPath + "CV_" +
                cv.getFirstName() + "_" +
                cv.getLastName() + "_" +
                cv.getUserID() + ".json");
        gson.toJson(cv, writer);
        writer.close();
        cvsList = cvsList.stream().filter(listCV -> listCV.getUserID() != cv.getUserID())
                .collect(Collectors.toList());
        cvsList.add(cv);
    }
}
