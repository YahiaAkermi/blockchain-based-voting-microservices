package com.yahia.votingms.service.client.vsDetailsCache;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VsDetailsService {

    private VsDetailsRepository vsDetailsRepository;


    @Cacheable(value = "VsDetails", key = "#vsId")
    public Optional<VsDetails> getVsDetails(Long vsId) {
        return vsDetailsRepository.findById(vsId);
    }

    @CachePut(value = "VsDetails", key = "#vsDetails.votingSessionId")
    public VsDetails saveUser(VsDetails vsDetails) {
        return vsDetailsRepository.save(vsDetails);
    }

    @CacheEvict(value = "VsDetails", key = "#vsId")
    public void deleteUser(Long vsId) {
        vsDetailsRepository.deleteById(vsId);
    }
}
