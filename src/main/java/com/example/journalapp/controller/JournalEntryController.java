package com.example.journalapp.controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalapp.entity.JournalEntry;
import com.example.journalapp.entity.User;
import com.example.journalapp.service.JournalEntryService;
import com.example.journalapp.service.UserService;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry, @PathVariable String username){
        try {
            entry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(entry, username);
            return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
        User user =userService.findByUsername(username);
        List<JournalEntry> entries = user.getJournalEntries();
        if(entries.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }


    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{username}/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId id,@PathVariable String username){
        journalEntryService.deleteById(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{username}/{id}")
    public ResponseEntity<JournalEntry> updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry,@PathVariable String username){
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle() != null && newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK); 
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

