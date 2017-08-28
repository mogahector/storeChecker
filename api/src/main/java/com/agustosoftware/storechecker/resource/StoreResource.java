package com.agustosoftware.storechecker.resource;

import com.agustosoftware.storechecker.domain.entity.Store;
import com.agustosoftware.storechecker.domain.entity.StoreList;
import com.agustosoftware.storechecker.exception.BadRequestException;
import com.agustosoftware.storechecker.exception.NotFoundException;
import com.agustosoftware.storechecker.repository.StoreRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@RequestMapping("/stores")
@ExposesResourceFor(Store.class)
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
        store.add(linkTo(methodOn(StoreResource.class).getStore(storeId)).withSelfRel());
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

        store.setStoreId(existingStore.getStoreId());
        storeRepository.save(store);

        return ResponseEntity.ok(store);
    }

    @DeleteMapping(path = "/{storeId}")
    public @ResponseBody
    ResponseEntity deleteStore(@PathVariable String storeId) throws Exception {
        Long searchId = checkAndGetStoreId(storeId);

        if(storeRepository.exists(searchId)) {
            storeRepository.delete(searchId);
        }else{
            throw new NotFoundException("Store with id:" + searchId + " not found");
        }

        return ResponseEntity.noContent().build();
    }   

    @GetMapping()
    public @ResponseBody ResponseEntity getStores() throws Exception {
        Iterable<Store> stores = storeRepository.findAll();
        for (Store store :
                stores) {
            store.add(linkTo(methodOn(StoreResource.class).getStore(store.getStoreId().toString())).withSelfRel());
        }
        return ResponseEntity.ok(stores);
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
