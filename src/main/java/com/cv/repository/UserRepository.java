package com.cv.repository;

import com.cv.model.User;
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

@Repository
public class UserRepository {
    private final Gson gson;

    private final String stringDirectoryPath = "./my-work/work-5.hw/src/main/resources/users/";
    private final List<User> usersList = new ArrayList<>();
    ;

    @Autowired
    public UserRepository(Gson gson) {
        this.gson = gson;
    }

    @PostConstruct
    private void loadUserList() throws IOException {
        File dir = new File(stringDirectoryPath);
        FileFilter fileFilter = new WildcardFileFilter("*.json");
        File[] files = dir.listFiles(fileFilter);

        for (File file : files) {
            String str = FileUtils.readFileToString(file, "UTF-8");
            usersList.add(gson.fromJson(str, User.class));
        }
    }

    public long getIDSequence() {
        return usersList.size();
    }

    public void addUser(User user) throws IOException {
        Writer writer = new FileWriter(stringDirectoryPath +
                user.getUsername() + ".json");
        gson.toJson(user, writer);
        writer.close();
        usersList.add(user);
    }

    public Optional<User> getByUsername(String username) {
        return usersList.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public boolean existsByUsername(String username) {
        return getByUsername(username).isPresent();
    }
}
