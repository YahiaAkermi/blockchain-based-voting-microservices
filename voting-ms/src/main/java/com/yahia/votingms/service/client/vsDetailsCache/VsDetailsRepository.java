package com.yahia.votingms.service.client.vsDetailsCache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VsDetailsRepository extends CrudRepository<VsDetails,Long> {

    boolean existsByVotingSessionId(Long vsId);

}
