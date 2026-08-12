package com.example.journalapp.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "journal_entries")
@Data
public class JournalEntry {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;

    // public LocalDateTime getDate(){
    //     return this.date;
    // }
    // public void setDate(LocalDateTime date){
    //     this.date = date;
    // }
    // public ObjectId getId(){
    //     return this.id;
    // }
    // public void setId(ObjectId id){
    //     this.id = id;
    // }
    // public String getTitle(){
    //     return this.title;
    // }
    // public void setTitle(String title){
    //     this.title = title;
    // }
    // public String getContent(){
    //     return this.content;
    // }
    // public void setContent(String content){
    //     this.content = content;
    // }
}
