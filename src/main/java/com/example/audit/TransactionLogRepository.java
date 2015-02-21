package com.example.audit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLogRepository extends JpaRepository<TranslationLog, Long> {

}
