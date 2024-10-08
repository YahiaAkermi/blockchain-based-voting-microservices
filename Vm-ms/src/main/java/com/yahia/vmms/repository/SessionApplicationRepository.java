package com.yahia.vmms.repository;

import com.yahia.vmms.entity.Condidate;
import com.yahia.vmms.entity.SessionApplication;
import com.yahia.vmms.entity.compositeIDs.ApplicationID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface SessionApplicationRepository extends JpaRepository<SessionApplication, ApplicationID> {


    boolean existsByApplicationID(ApplicationID applicationID);

    @Override
    Optional<SessionApplication> findById(ApplicationID applicationID);

    Collection<SessionApplication> findByApplicationID_CondidateId(Long condidateId);

    Collection<SessionApplication>  findByApplicationID_CondidateIdAndPartyContainingIgnoreCase(Long condidateId,String searchTerm);
}
