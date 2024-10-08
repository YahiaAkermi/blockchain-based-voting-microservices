package com.yahia.vmms.service.impl;

import com.yahia.vmms.constants.VotingSessionConstants;
import com.yahia.vmms.dto.CondidateDto;
import com.yahia.vmms.dto.CondidateDtoWithId;
import com.yahia.vmms.entity.Condidate;
import com.yahia.vmms.exception.BelowAgeOfConscentException;
import com.yahia.vmms.exception.ResourceAlreadyExists;
import com.yahia.vmms.exception.RessourceNotFoundException;
import com.yahia.vmms.mapper.CondidateMapper;
import com.yahia.vmms.repository.CondidateRepository;
import com.yahia.vmms.repository.VotingSessionsRepository;
import com.yahia.vmms.service.ICondidatService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CondidateServiceImpl implements ICondidatService {

    private final CondidateRepository condidateRepository;
    private final VotingSessionsRepository votingSessionsRepository;

    public CondidateServiceImpl(CondidateRepository condidateRepository, VotingSessionsRepository votingSessionsRepository) {
        this.condidateRepository = condidateRepository;
        this.votingSessionsRepository = votingSessionsRepository;
    }

    // CREATE
    @Override
    public void createCondidate(CondidateDto condidateDto) {

        boolean condidateExists = condidateRepository.findByEmail(condidateDto.getEmail()).isPresent();

        if (condidateExists) {
            throw new ResourceAlreadyExists("Condidate", condidateDto.getEmail());
        }

        if (Period.between(condidateDto.getDateOfBirth(), LocalDate.now()).getYears() < VotingSessionConstants.AGE_OF_CONSCENT) {
            throw new BelowAgeOfConscentException();
        }

        Condidate condidate = CondidateMapper.mapToCondidate(condidateDto, new Condidate());
        condidateRepository.save(condidate);
    }

    /**
     * This method will fetch all candidates
     *
     * @return List of CondidateDto objects
     */
    public List<CondidateDtoWithId> fetchAllCondidates() {

        List<Condidate> condidates = condidateRepository.findAll();

        List<CondidateDtoWithId> condidateDtos = condidates.stream().map(
                candidate-> {

                    CondidateDtoWithId condidateDtoWithId=CondidateMapper.mapFromCondidateToDtoWithId(candidate,new CondidateDtoWithId());
                    condidateDtoWithId.setCondidateDto(CondidateMapper.mapToCondidateDto(candidate,new CondidateDto()));

                    return condidateDtoWithId;
                }
        ).collect(Collectors.toList());



        return condidateDtos;
    }

    /**
     * This method will fetch a candidate by ID
     *
     * @param condidateId - Long ID of the candidate
     * @return CondidateDto object
     */
    public CondidateDtoWithId fetchCondidateById(Long condidateId) {

        Condidate condidate = condidateRepository.findById(condidateId)
                .orElseThrow(() -> new RessourceNotFoundException("Condidate", "ID", condidateId.toString()));

        CondidateDtoWithId condidateDtoWithId=CondidateMapper.mapFromCondidateToDtoWithId(condidate,new CondidateDtoWithId());
        condidateDtoWithId.setCondidateDto(CondidateMapper.mapToCondidateDto(condidate,new CondidateDto()));

        return condidateDtoWithId;
    }

    /**
     * This method will update a candidate by ID
     *
     * @param condidateId       - Long ID of the candidate
     * @param updatedCondidateDto - CondidateDto object containing updated details
     * @return true if the candidate is successfully updated
     */
    public boolean updateCondidate(Long condidateId, CondidateDto updatedCondidateDto) {

        Condidate condidate = condidateRepository.findById(condidateId)
                .orElseThrow(() -> new RessourceNotFoundException("Condidate", "ID", condidateId.toString()));

        // Check if email is updated and already exists for another candidate
        if (!condidate.getEmail().equals(updatedCondidateDto.getEmail())) {
            boolean emailExists = condidateRepository.findByEmail(updatedCondidateDto.getEmail()).isPresent();
            if (emailExists) {
                throw new ResourceAlreadyExists("Condidate", updatedCondidateDto.getEmail());
            }
        }

        // Age validation
        if (Period.between(updatedCondidateDto.getDateOfBirth(), LocalDate.now()).getYears() < VotingSessionConstants.AGE_OF_CONSCENT) {
            throw new BelowAgeOfConscentException();
        }

        // Updating condidate fields
        condidate.setName(updatedCondidateDto.getName());
        condidate.setEmail(updatedCondidateDto.getEmail());
        condidate.setDateOfBirth(updatedCondidateDto.getDateOfBirth());

        condidateRepository.save(condidate);
        return true;
    }


    /**
     * This method will delete a candidate by ID
     *
     * @param condidateId - Long ID of the candidate
     * @return true if the candidate is successfully deleted
     */
    public boolean deleteCondidate(Long condidateId) {
        Condidate condidate = condidateRepository.findById(condidateId)
                .orElseThrow(() -> new RessourceNotFoundException("Condidate", "ID", condidateId.toString()));

        condidateRepository.deleteById(condidateId);
        return true;
    }
}
