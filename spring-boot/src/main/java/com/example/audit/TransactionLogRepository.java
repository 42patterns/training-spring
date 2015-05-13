package com.example.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "logs", path = "logs")
public interface TransactionLogRepository extends JpaRepository<TranslationLog, Long> {



}
