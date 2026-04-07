package com.dalgo.SpringBoot.Controller;

import com.dalgo.SpringBoot.Entity.Users;
import com.dalgo.SpringBoot.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserEntryController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Users> createUsers(@RequestBody Users user){
        try{
            userService.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<?> getusers(){
        List<Users> userList = userService.getAll();
        if(!userList.isEmpty()) return new ResponseEntity<>(userList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("id/{userid}")
    public ResponseEntity<?> getUserByID(@PathVariable ObjectId userid){
        Optional<Users> user = userService.getbyid(userid);
        if(user.isPresent()) return new ResponseEntity<>(user, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{userid}")
    public ResponseEntity<?> updateUser(@PathVariable ObjectId userid,@RequestBody Users user){
        if(userService.updatebyid(userid, user)) return new ResponseEntity<>(user, HttpStatus.RESET_CONTENT);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @DeleteMapping("id/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId userid){
        if(userService.deletebyid(userid)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
