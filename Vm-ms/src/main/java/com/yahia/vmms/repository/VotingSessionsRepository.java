package com.yahia.vmms.repository;

import com.yahia.vmms.entity.VotingSessions;
import com.yahia.vmms.entity.enums.Visibility;
import com.yahia.vmms.entity.enums.VotingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


@Repository
public interface VotingSessionsRepository extends JpaRepository<VotingSessions,Long> {


    @Override
    Optional<VotingSessions> findById(Long votingSessionId);

    boolean existsByTitleIgnoreCase(String title);

    Collection<VotingSessions> findAllBySessionAdminIdAndTitleContainingIgnoreCase(Long sessionAdminId,String searchTerm);

    Collection<VotingSessions> findAllByAllowedRegionsContainingOrVisibilityEquals(String clientCountryCode, Visibility visibility );

    Collection<VotingSessions> findAllByVotingStatusEqualsAndAllowedRegionsContainingOrVisibilityEquals( VotingStatus votingStatus ,String clientCountryCode, Visibility visibility);

    void deleteById(Long votingSessionId);

}
