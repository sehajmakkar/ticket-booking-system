package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserBookingService {
    private User user;

    private List<User> userList;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "../localDB/users.json";

    public void loadUsers() throws IOException{
        File users = new File(USERS_PATH);
//        DESERIALIZE, What and why is TypeReference used.
        userList = objectMapper.readValue(
                users,
                new TypeReference<List<User>>() {}
        );
    }

    public UserBookingService() throws IOException{
        loadUsers();
    }

//    constructor - automatically invoked when class is instantiated.
    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public Boolean loginUser(){
//        optional is used to eliminate null pointer exception (NPE)
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user){
        try{
            userList.add(user);
            // insert in db
            saveUserListToFile();
            return true;
        } catch (IOException e){
            return false;
        }
    }

    public void fetchBooking(){
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId){
//        todo
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the ticket id to cancel");
        ticketId = s.next();

        if (ticketId == null || ticketId.isEmpty()) {
            System.out.println("Ticket ID cannot be null or empty.");
            return Boolean.FALSE;
        }

        String finalTicketId1 = ticketId;  //Because strings are immutable
        boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));

        String finalTicketId = ticketId;
        user.getTicketsBooked().removeIf(Ticket -> Ticket.getTicketId().equals(finalTicketId));
        if (removed) {
            System.out.println("Ticket with ID " + ticketId + " has been canceled.");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket found with ID " + ticketId);
            return Boolean.FALSE;
        }
    }

    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);

        } catch (IOException e){
            return new ArrayList<>();

            }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }



}
