package com.yahia.vmms.repository;

import com.yahia.vmms.entity.VotingSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


@Repository
public interface VotingSessionsRepository extends JpaRepository<VotingSessions,Long> {


    boolean existsByTitleIgnoreCase(String title);

    Collection<VotingSessions> findAllBySessionAdminIdAndTitleContainingIgnoreCase(Long sessionAdminId,String searchTerm);
 }
