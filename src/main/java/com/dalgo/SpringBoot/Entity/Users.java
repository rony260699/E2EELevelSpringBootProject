package com.dalgo.SpringBoot.Entity;


import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
public class Users {

    @Id
    private ObjectId id;

    private LocalDateTime date;

    @Indexed(unique = true) // But eta automatic indexing korbe na.
                            // Application properties a giye " spring.data.mongodb.auto.index-creation=true " eta dite hobe
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @DBRef // Ei annotation users collection er sathe journal_entries collection er link kore dilo.
           //userJournalEntries like a foreign key. though this is not sql.
    private List<JournalEntry> usersJournalEntries = new ArrayList<>();
}
