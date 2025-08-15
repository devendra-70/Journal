package com.starter.journal.service;

import com.starter.journal.entity.JournalEntry;
import com.starter.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        return journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateById(ObjectId id,JournalEntry newEntry){
        JournalEntry current = journalEntryRepository.findById(id).orElse(null);
        if(current!=null){
            current.setDate(LocalDateTime.now());
            current.setContent(newEntry.getContent()!=null && newEntry.getContent().equals("") ? newEntry.getContent():current.getContent());
            current.setTitle(newEntry.getTitle() != null && newEntry.getTitle().equals("")? newEntry.getTitle() : current.getTitle());
        }

        return journalEntryRepository.save(current);

    }
}
