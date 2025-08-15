package com.starter.journal.controller;

import com.starter.journal.entity.JournalEntry;
import com.starter.journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("entries")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllEntries(){
        List<JournalEntry> entries = journalEntryService.getAll();
        if(entries!=null && !entries.isEmpty()){
            return new ResponseEntity<>(entries,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{requestedId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId requestedId){
        JournalEntry current = journalEntryService.getById(requestedId).orElse(null);
        if(current!=null){
            return new ResponseEntity<>(current, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry newEntry){
        try {
            journalEntryService.saveEntry(newEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("delete/{requestedId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId requestedId){
        try {
            journalEntryService.deleteById(requestedId);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update/{requestedId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId requestedId,@RequestBody JournalEntry requestedEntry){
        journalEntryService.updateById(requestedId,requestedEntry);
        if(requestedEntry!=null){
        return new ResponseEntity<>(requestedEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    }

