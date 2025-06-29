package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;

    private List<User> userList;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "../localDB/users.json";

//    constructor - automatically invoked when class is instantiated.
    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        File users = new File(USERS_PATH);
//        DESERIALIZE, What and why is TypeReference used.
        userList = objectMapper.readValue(
                users,
                new TypeReference<List<User>>() {}
        );
    }





}
