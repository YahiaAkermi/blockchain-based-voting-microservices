package com.yahia.vmms.repository;

import com.yahia.vmms.entity.SessionApplication;
import com.yahia.vmms.entity.compositeIDs.ApplicationID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionApplicationRepository extends JpaRepository<SessionApplication, ApplicationID> {

    @Override
    Optional<SessionApplication> findById(ApplicationID applicationID);
}
