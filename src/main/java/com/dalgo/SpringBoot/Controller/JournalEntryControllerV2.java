package com.dalgo.SpringBoot.Controller;

import com.dalgo.SpringBoot.Entity.JournalEntry;
import com.dalgo.SpringBoot.Entity.Users;
import com.dalgo.SpringBoot.Service.JournalEntryService;
import com.dalgo.SpringBoot.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journals")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
//    // get all document
//    @GetMapping
//    public List<JournalEntry> getAll(){
//        return journalEntryService.getAll();
//    }

    /// get all document
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesByUser(@PathVariable String userName){
        Users user = userService.getbyusername(userName);
        List<JournalEntry> allJournalsOfUser =  user.getUsersJournalEntries();
        if(allJournalsOfUser != null && !allJournalsOfUser.isEmpty()){
            return new ResponseEntity<>(allJournalsOfUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

// ///////////////////////////
    /// Save or Create a documents
//    @PostMapping
//    public boolean addEntries(@RequestBody JournalEntry je){
//        je.setDate(LocalDateTime.now());
//        journalEntryService.saveEntry(je);
//        return true;
//    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> addEntries(@RequestBody JournalEntry je, @PathVariable String userName){
        Users user = userService.getbyusername(userName);
        try{
            je.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(je, user);
            return new ResponseEntity<>(je, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
// /////////////////////////

//    // get 1 document by its id
      // Without HTTP Status code
//    @GetMapping("id/{myid}")
//    public JournalEntry getbyid(@PathVariable ObjectId myid){
//        return journalEntryService.findentrybyid(myid).orElse(null);
//    }

    // Return With Http Status Code
    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getbyid(@PathVariable ObjectId myid){
        Optional<JournalEntry> je = journalEntryService.findentrybyid(myid);
        if(je.isPresent()){
            return new ResponseEntity<>(je.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
// ///////////////////////

//    // Detele Document
//    @DeleteMapping("id/{myid}")
//    public boolean dletejournal(@PathVariable ObjectId myid){
//        return journalEntryService.deletebyid(myid);
//    }

    @DeleteMapping("id/{userName}/{myid}")
    public ResponseEntity<?> dletejournal(@PathVariable String userName, @PathVariable ObjectId myid){
        journalEntryService.deletebyid(userName, myid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
// ////////////////////////

    // Update Document
    // video 16 er updating part ta kora hoyni
    @PutMapping("id/{myid}")
    public ResponseEntity<?> updatejournal(@PathVariable ObjectId myid, @RequestBody JournalEntry je){
        JournalEntry newje = journalEntryService.updatebyid(myid, je.getContent(), je.getTitle());
        if(newje != null){
            return new ResponseEntity<>(newje, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
