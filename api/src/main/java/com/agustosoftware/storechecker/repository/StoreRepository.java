package com.agustosoftware.storechecker.repository;

import com.agustosoftware.storechecker.domain.entity.Store;
import com.agustosoftware.storechecker.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface StoreRepository extends CrudRepository<Store, Long> {
}
