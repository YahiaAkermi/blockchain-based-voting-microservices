package com.yahia.vmms.repository;

import com.yahia.vmms.entity.Condidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CondidateRepository extends JpaRepository<Condidate,Long> {

    Optional<Condidate> findByEmail(String condidateEmail);


}
