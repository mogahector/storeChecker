package com.agustosoftware.storechecker.repository;

import com.agustosoftware.storechecker.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
