package com.cv.service;

import com.cv.exception.NotFoundException;
import com.cv.model.Cv;
import com.cv.util.CvComparators;
import com.cv.util.FilterMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.cv.repository.CvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CvService {
    @Autowired
    private CvRepository cvRepository;

    private static CvService cvService;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String getCV(long userID) throws Exception {
        Cv cv = cvRepository.getCV(userID).orElseThrow(NotFoundException::new);
        return gson.toJson(cv);
    }

    public List<String> getAllCVs() {
        return cvRepository.getAllCVs().stream()
                .map(gson::toJson)
                .collect(Collectors.toList());
    }

    public List<String> getFilteredCVs(String filter) {
        return cvRepository.getAllCVs().stream()
                .filter(FilterMap.getFilter(filter))
                .map(gson::toJson)
                .collect(Collectors.toList());
    }

    public List<String> getSortedCVs(String option) {
        return cvRepository.getAllCVs().stream()
                .sorted(option.equals("full-stack") ?
                        new CvComparators.FullStackComparator() :
                        new CvComparators.DataAnalysisComparator())
                .map(gson::toJson)
                .collect(Collectors.toList());
    }

    public void addCV(Cv cv) throws IOException {
        cvRepository.addCV(cv);
    }
}
