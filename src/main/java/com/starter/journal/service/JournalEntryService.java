package com.starter.journal.service;

import com.starter.journal.entity.JournalEntry;
import com.starter.journal.entity.User;
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

    @Autowired
    private UserService userService;

    public JournalEntry saveEntry(JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getUserEntries().add(saved);
        userService.saveUser(user);
        return saved;
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(String userName,ObjectId id){
        User user = userService.findByUserName(userName);
        journalEntryRepository.deleteById(id);
        user.getUserEntries().removeIf(x->x.getId().equals(id));
        userService.saveUser(user);
    }

    public JournalEntry updateById(String userName,ObjectId id,JournalEntry newEntry){
        JournalEntry current = journalEntryRepository.findById(id).orElse(null);
        if(current!=null){
            current.setDate(LocalDateTime.now());
            current.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent():current.getContent());
            current.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("")? newEntry.getTitle() : current.getTitle());
        }return journalEntryRepository.save(current);

    }
}
