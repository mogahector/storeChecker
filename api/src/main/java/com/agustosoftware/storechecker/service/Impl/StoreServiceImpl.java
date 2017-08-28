package com.agustosoftware.storechecker.service.Impl;

import com.agustosoftware.storechecker.domain.entity.Checklist;
import com.agustosoftware.storechecker.domain.entity.Store;
import com.agustosoftware.storechecker.exception.BadRequestException;
import com.agustosoftware.storechecker.repository.ChecklistRepository;
import com.agustosoftware.storechecker.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class StoreServiceImpl {

    @Autowired
    private StoreRepository storeRepository;

    public Store findStoreById(String id) throws Exception {
        return storeRepository.findOne(checkAndGetStoreId(id));
    }

    public void saveStore(Store store){
        storeRepository.save(store);
    }

    private long checkAndGetStoreId(String userId) throws BadRequestException {
        long searchId;
        try {
            searchId = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("User id must be an integer value.");
        }
        return searchId;
    }
}
