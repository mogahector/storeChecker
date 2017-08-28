package com.agustosoftware.storechecker.service.Impl;

import com.agustosoftware.storechecker.domain.entity.Checklist;
import com.agustosoftware.storechecker.domain.entity.Store;
import com.agustosoftware.storechecker.repository.ChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ChecklistServiceImpl {

    @Autowired
    private ChecklistRepository checklistRepository;

    public Checklist createChecklist(Store store) throws Exception {
        Checklist checklist = new Checklist();
        checklist.setDateTime( LocalDateTime.now() );
        checklistRepository.save(checklist);
        return checklist;
    }
}
