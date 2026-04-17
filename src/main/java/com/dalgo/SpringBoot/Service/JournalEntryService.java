package com.dalgo.SpringBoot.Service;

import com.dalgo.SpringBoot.Entity.JournalEntry;
import com.dalgo.SpringBoot.Entity.Users;
import com.dalgo.SpringBoot.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepo;

    @Autowired
    private UserService userService;

    // Save 1 entry on the db
    public void saveEntry(JournalEntry journalEntry, Users user){

        JournalEntry je = journalEntryRepo.save(journalEntry);
        user.getUsersJournalEntries().add(je);
        userService.addUser(user);

    }

    // get all documents from db
    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }



    //find 1 document by their id
    public Optional<JournalEntry> findentrybyid(ObjectId jid){
        return journalEntryRepo.findById(jid);
    }

    //delete document by id
    public boolean deletebyid(String userName, ObjectId jid){
        Users user = userService.getbyusername(userName);
        if(journalEntryRepo.existsById(jid)){
            user.getUsersJournalEntries().removeIf(x -> x.getId().equals(jid));
            journalEntryRepo.deleteById(jid);
            userService.addUser(user);

            return true;
        }
        return false;
    }


    // Update documents
    public JournalEntry updatebyid(ObjectId jid, String cont, String title){

        JournalEntry temp = journalEntryRepo.findById(jid).orElse(null);
        if(temp == null){
            return null;
        }else{
            temp.setContent(cont);
            temp.setTitle(title);
            journalEntryRepo.save(temp);
        }
        return temp;
    }


}
