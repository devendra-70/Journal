package com.starter.journal.controller;

import com.starter.journal.entity.JournalEntry;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JournalEntryController {

    private Map<Long,JournalEntry> journalEntries=new HashMap<>();


    public Map<Long,JournalEntry> getAll(){
        return journalEntries;
    }
}
