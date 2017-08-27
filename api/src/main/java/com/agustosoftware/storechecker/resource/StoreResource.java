package com.agustosoftware.storechecker.resource;

import com.agustosoftware.storechecker.domain.entity.Store;
import com.agustosoftware.storechecker.domain.entity.StoreList;
import com.agustosoftware.storechecker.exception.BadRequestException;
import com.agustosoftware.storechecker.exception.NotFoundException;
import com.agustosoftware.storechecker.repository.StoreRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/stores")
public class StoreResource {

    @Autowired
    private StoreRepository storeRepository;

    @PostMapping()
    public @ResponseBody
    ResponseEntity createStore (@RequestBody Store store) {
        Store createStore = storeRepository.save(store);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createStore.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{storeId}")
    public @ResponseBody
    ResponseEntity getStore(@PathVariable String storeId) throws Exception {
        Long searchId = checkAndGetStoreId(storeId);

        Store store = storeRepository.findOne(searchId);
        if (store == null) {
            String errMsg = String.format("Store with ID: %s was not found.", storeId);
            throw new NotFoundException(errMsg);
        }

        return ResponseEntity.ok(store);
    }

    @PutMapping(path = "/{storeId}")
    public @ResponseBody
    ResponseEntity updateStore(@PathVariable String storeId, @RequestBody Store store) throws Exception {
        Long searchId = checkAndGetStoreId(storeId);

        Store existingStore = storeRepository.findOne(searchId);
        if (existingStore == null) {
            String errMsg = String.format("Store with ID: %s was not found.", storeId);
            throw new NotFoundException(errMsg);
        }

        store.setId(existingStore.getId());
        storeRepository.save(store);

        return ResponseEntity.ok(store);
    }

    @DeleteMapping(path = "/{storeId}")
    public @ResponseBody
    ResponseEntity deleteStore(@PathVariable String storeId) throws Exception {
        Long searchId = checkAndGetStoreId(storeId);

        storeRepository.delete(searchId);

        return ResponseEntity.noContent().build();
    }   

    @GetMapping()
    public @ResponseBody ResponseEntity getStores() {
        Iterable<Store> stores = storeRepository.findAll();
        StoreList storeList = new StoreList();
        storeList.getStores().addAll(Lists.newArrayList(stores));
        return ResponseEntity.ok(storeList);
    }

    private Long checkAndGetStoreId(@PathVariable String storeId) throws BadRequestException {
        Long searchId;
        try {
            searchId = Long.parseLong(storeId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("Store id must be an integer value.");
        }
        return searchId;
    }

}
