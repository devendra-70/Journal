package com.starter.journal.controller;

import com.starter.journal.entity.JournalEntry;
import com.starter.journal.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<JournalEntry> getAllEntries(){
        return journalEntryService.getAll();
    }

    @GetMapping("id/{requestedId}")
    public JournalEntry getEntryById(@PathVariable ObjectId requestedId){
        return journalEntryService.getById(requestedId).orElse(null);
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry newEntry){
        return journalEntryService.saveEntry(newEntry);

    }

    @DeleteMapping("delete/{requestedId}")
    public void deleteEntry(@PathVariable ObjectId requestedId){
        journalEntryService.deleteById(requestedId);
    }

    @PutMapping("update/{requestedId}")
    public JournalEntry updateEntry(@PathVariable ObjectId requestedId,@RequestBody JournalEntry requestedEntry){
        return journalEntryService.updateById(requestedId,requestedEntry);
    }


    }

