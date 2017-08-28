package com.agustosoftware.storechecker.repository;

import com.agustosoftware.storechecker.domain.entity.Checklist;
import org.springframework.data.repository.CrudRepository;

public interface ChecklistRepository extends CrudRepository<Checklist, Long> {
}
