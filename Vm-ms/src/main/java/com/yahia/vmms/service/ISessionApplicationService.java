package com.yahia.vmms.service;

import com.yahia.vmms.dto.CondidateDto;
import com.yahia.vmms.dto.SessionApplicationDto;
import com.yahia.vmms.entity.SessionApplication;

public interface ISessionApplicationService {

    /**
     * This method will create an application to a voting session
     *
     * @param sessionApplicationDto - SessionApplicationDto object
     */
    void createCondidate(SessionApplicationDto sessionApplicationDto);
}
