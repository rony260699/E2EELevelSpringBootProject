package com.dalgo.SpringBoot.Service;

import com.dalgo.SpringBoot.Entity.Users;
import com.dalgo.SpringBoot.Repository.UserServiceRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserServiceRepository userServiceRepo;

    public void addUser(Users user){

        user.setDate(LocalDateTime.now());
        userServiceRepo.save(user);

    }


    public List<Users> getAll(){
        return userServiceRepo.findAll();
    }

    public Optional<Users> getbyid(ObjectId userid){
        return userServiceRepo.findById(userid);
    }

    public boolean updatebyid(ObjectId userid, Users newUser){
        Optional<Users> oldUser = userServiceRepo.findById(userid);

        if(oldUser.isPresent()){

         Users user = oldUser.get();
         if(!newUser.getUserName().isBlank() && !newUser.getPassword().isBlank()){
             user.setUserName(newUser.getUserName());
             user.setPassword(newUser.getPassword());
             userServiceRepo.save(user);
             return true;
         }
        }
        return false;
    }

    public boolean deletebyid(ObjectId userid){
        Optional<Users> user = userServiceRepo.findById(userid);
        if(user.isPresent()) {
            userServiceRepo.deleteById(userid);
            return true;
        }
        return false;
    }

}
