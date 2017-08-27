package com.agustosoftware.storechecker.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StoreList {

    @JsonProperty("stores")
    List<Store> stores;

    public List<Store> getStores() {
        if (stores == null) {
            stores = new ArrayList<Store>();
        }
        return stores;
    }
}
