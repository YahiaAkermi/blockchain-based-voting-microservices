package com.yahia.vmms.repository;

import com.yahia.vmms.entity.Condidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondidateRepository extends JpaRepository<Condidate,Long> {
}
