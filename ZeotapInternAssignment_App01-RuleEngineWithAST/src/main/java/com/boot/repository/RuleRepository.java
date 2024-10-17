package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.model.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}
