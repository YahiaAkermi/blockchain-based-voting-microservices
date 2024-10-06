package com.yahia.vmms.service;

import com.yahia.vmms.dto.CondidateDto;
import com.yahia.vmms.dto.VotingSessionDto;

public interface ICondidatService {

    /**
     *this method will create a condidate
     *@param condidateDto -  CondidateDto  object
     */
    void createCondidate(CondidateDto condidateDto);

}
