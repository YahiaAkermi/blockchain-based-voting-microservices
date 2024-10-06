package com.yahia.vmms.service.impl;

import com.yahia.vmms.constants.VotingSessionConstants;
import com.yahia.vmms.dto.CondidateDto;
import com.yahia.vmms.entity.Condidate;
import com.yahia.vmms.exception.BelowAgeOfConscentException;
import com.yahia.vmms.exception.ResourceAlreadyExists;
import com.yahia.vmms.mapper.CondidateMapper;
import com.yahia.vmms.repository.CondidateRepository;
import com.yahia.vmms.repository.VotingSessionsRepository;
import com.yahia.vmms.service.ICondidatService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class CondidateServideImpl implements ICondidatService {



    public CondidateServideImpl(CondidateRepository condidateRepository, VotingSessionsRepository votingSessionsRepository){
        this.condidateRepository=condidateRepository;
        this.votingSessionsRepository=votingSessionsRepository;
    }

    private final CondidateRepository condidateRepository;
    private final VotingSessionsRepository votingSessionsRepository;


    /**
     * this method will create a condidate
     * @param condidateDto -  CondidateDto  object
     */
    @Override
    public void createCondidate(CondidateDto condidateDto) {
        //check if the condidate already exists
        boolean condidateExists=condidateRepository.findByEmail(condidateDto.getEmail()).isPresent();

        if (condidateExists){
            throw new ResourceAlreadyExists("Condidate", condidateDto.getEmail());
        }

        //checking if he is in leagal age to be a condidate
        if (Period.between(condidateDto.getDateOfBirth(), LocalDate.now()).getYears() < VotingSessionConstants.AGE_OF_CONSCENT){
            throw new BelowAgeOfConscentException();
        }

        //mapping to candidate so we can store it in the db
        Condidate condidate= CondidateMapper.mapToCondidate(condidateDto,new Condidate());

        condidateRepository.save(condidate);

    }
}
