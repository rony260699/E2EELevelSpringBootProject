package com.dalgo.SpringBoot.Entity;

import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

// Its a pojo.Plain Old Java Object
@Document(collection = "journal_entries")  // this class info will be the row/documents of a collection or table. this is ORM
public class JournalEntry {

    /*
    age private String id; chilo. tokhon kichu entries store kora hoisilo.
    But id er datatype objectid deyar por ager documents gulo r get kora jacchilo na. error dicchilo.
    to. ei problem theke bachte worst upay holo old doc delete kora. r best practice holo
    mongoshell a ei script ta chalano:
    db.journal_entries.find({ _id: { $type: "string" } }).forEach(function(doc) {
    var newId = ObjectId();

    var oldId = doc._id;
    doc._id = newId;
    doc.old_id = oldId;

    db.journal_entries.insertOne(doc);
    db.journal_entries.deleteOne({ _id: oldId });
});

     */
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

     // primary or unique key
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
