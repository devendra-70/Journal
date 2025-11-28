package com.starter.journal.service;

import com.starter.journal.entity.JournalEntry;
import com.starter.journal.entity.User;
import com.starter.journal.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getUserEntries().add(saved);
            userService.saveUser(user);
            return saved;
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("Error occured saving the entry",e);
        }
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> getById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(String userName,ObjectId id){
        boolean remove=false;
        try {
            User user = userService.findByUserName(userName);
            journalEntryRepository.deleteById(id);
            remove=user.getUserEntries().removeIf(x -> x.getId().equals(id));
            if(remove){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch (Exception e){
            throw new RuntimeException("Error occured during delete",e);
        }
        return remove;
    }

    @Transactional
    public JournalEntry updateEntry(String userName, ObjectId id, JournalEntry newEntry) {
        User user = userService.findByUserName(userName);
        boolean exists = user.getUserEntries().stream()
                .anyMatch(entry -> entry.getId().equals(id));
        if (exists) {
            JournalEntry oldEntry = journalEntryRepository.findById(id).orElse(null);
            if (oldEntry != null) {
                String newTitle = newEntry.getTitle();
                String newContent = newEntry.getContent();
                if (newTitle != null && !newTitle.isEmpty()) {
                    oldEntry.setTitle(newTitle);
                }
                if (newContent != null && !newContent.isEmpty()) {
                    oldEntry.setContent(newContent);
                }
                journalEntryRepository.save(oldEntry);
                return oldEntry;
            }
        }
        return null;
    }

}
