package com.dalgo.SpringBoot.Service;

import com.dalgo.SpringBoot.Entity.JournalEntry;
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

    // Save 1 entry on the db
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
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
    public boolean deletebyid(ObjectId jid){
        if(journalEntryRepo.existsById(jid)){
            journalEntryRepo.deleteById(jid);
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
