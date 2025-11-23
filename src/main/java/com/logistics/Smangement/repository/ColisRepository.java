package com.logistics.Smangement.repository;

import com.logistics.Smangement.entity.Colis;
import com.logistics.Smangement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ColisRepository extends MongoRepository<Colis, String>, QueryByExampleExecutor<Colis> {
    Page<Colis> findAllByTransporteur(User transporteur, Pageable pageable);
}
