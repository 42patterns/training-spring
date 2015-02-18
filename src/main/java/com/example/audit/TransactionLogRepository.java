package com.example.audit;

import org.springframework.data.repository.CrudRepository;

public interface TransactionLogRepository extends CrudRepository<TranslationLog, Long> {

}
