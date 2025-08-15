package com.starter.journal.controller;

import com.starter.journal.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("entries")
public class JournalEntryController {

    private Map<Long,JournalEntry> journalEntries=new HashMap<>();

    @GetMapping
    public Map<Long,JournalEntry> getAllEntries(){
        return journalEntries;
    }

    @GetMapping("id/{requestedId}")
    public JournalEntry getEntryById(@PathVariable Long requestedId){
        return journalEntries.get(requestedId);
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry newEntry){
        journalEntries.put(newEntry.getId(),newEntry);
            return journalEntries.get(newEntry.getId());
    }

    @DeleteMapping("delete/{requestedId}")
    public boolean deleteEntry(@PathVariable Long requestedId){
        journalEntries.remove(requestedId);
        return journalEntries.get(requestedId) == null;
    }

    @PutMapping("update/{requestedId}")
    public JournalEntry updateEntry(@PathVariable Long requestedId,@RequestBody JournalEntry requestedEntry){
        return journalEntries.put(requestedId,requestedEntry);
    }


    }

