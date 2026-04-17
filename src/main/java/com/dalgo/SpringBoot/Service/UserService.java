package com.dalgo.SpringBoot.Service;

import com.dalgo.SpringBoot.Entity.Users;
import com.dalgo.SpringBoot.Repository.UserServiceRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService implements UserDetailsService {

    @Autowired
    private UserServiceRepository userServiceRepo;

    //Create any user
    public void addUser(Users user){

        user.setDate(LocalDateTime.now());
        userServiceRepo.save(user);

    }


    // Get All users
    public List<Users> getAll(){
        return userServiceRepo.findAll();
    }

    // get user by id
    public Optional<Users> getbyid(ObjectId userid){
        return userServiceRepo.findById(userid);
    }

    // get user by name
    public Users getbyusername(String username){
        return userServiceRepo.findByUserName(username);
    }

    // update user by id
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


    // delete user by id
    public boolean deletebyid(ObjectId userid){
        Optional<Users> user = userServiceRepo.findById(userid);
        if(user.isPresent()) {
            userServiceRepo.deleteById(userid);
            return true;
        }
        return false;
    }



    // Auth providation
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = getbyusername(username);
        return new User(user.getUserName(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
