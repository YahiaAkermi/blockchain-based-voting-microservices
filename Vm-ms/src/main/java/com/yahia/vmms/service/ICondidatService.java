package com.yahia.vmms.service;

import com.yahia.vmms.dto.CondidateDto;
import com.yahia.vmms.dto.CondidateDtoWithId;

import java.util.List;

public interface ICondidatService {

    /**
     * This method will create a candidate
     *
     * @param condidateDto - CondidateDto object
     */
    void createCondidate(CondidateDto condidateDto);

    /**
     * This method will fetch all candidates
     *
     * @return List of CondidateDto objects
     */
    List<CondidateDtoWithId> fetchAllCondidates();

    /**
     * This method will fetch a candidate by ID
     *
     * @param condidateId - Long ID of the candidate
     * @return CondidateDto object
     */
    CondidateDtoWithId fetchCondidateById(Long condidateId);

    /**
     * This method will update a candidate by ID
     *
     * @param condidateId       - Long ID of the candidate
     * @param updatedCondidateDto - CondidateDto object containing updated details
     * @return true if the candidate is successfully updated
     */
    boolean updateCondidate(Long condidateId, CondidateDto updatedCondidateDto);

    /**
     * This method will delete a candidate by ID
     *
     * @param condidateId - Long ID of the candidate
     * @return true if the candidate is successfully deleted
     */
    boolean deleteCondidate(Long condidateId);
}
