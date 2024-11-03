package com.yahia.votingms.service.client.vsDetailsCache;

import com.yahia.votingms.dto.clientDtos.VotingSessionDto;
import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithId;
import com.yahia.votingms.dto.clientDtos.VotingSessionDtoWithIdAndCondidates;

import java.util.stream.Collectors;

public  class CacheMapper {

    public static VsDetails mapToVsDetails( VotingSessionDtoWithIdAndCondidates votingSessionDtoWithIdAndCondidates,VsDetails vsDetails){

        vsDetails.setVotingSessionId(votingSessionDtoWithIdAndCondidates.getVotingSessionId());
        vsDetails.setStartDate(votingSessionDtoWithIdAndCondidates.getStartDate());
        vsDetails.setEndDate(votingSessionDtoWithIdAndCondidates.getEndDate());
        vsDetails.setVotingStatus(votingSessionDtoWithIdAndCondidates.getVotingStatus());

        //transfrom from ArrayList<Long> to String
        String transformedCandidatesList = votingSessionDtoWithIdAndCondidates.getListCondidates()
                .stream()
                .map(Object::toString) // Convert each Long to String
                .collect(Collectors.joining(",")); // Join with commas

        //now we set the condidates
        vsDetails.setListCondidates(transformedCandidatesList);


        return vsDetails;
    }


    public static VotingSessionDtoWithIdAndCondidates mapToVotingSessionDtoWithIdAndCondidates(VsDetails vsDetails, VotingSessionDtoWithIdAndCondidates votingSessionDtoWithIdAndCondidates){

        votingSessionDtoWithIdAndCondidates.setVotingSessionId(vsDetails.getVotingSessionId());
        votingSessionDtoWithIdAndCondidates.setStartDate(vsDetails.getStartDate());
        votingSessionDtoWithIdAndCondidates.setEndDate(vsDetails.getEndDate());
        votingSessionDtoWithIdAndCondidates.setVotingStatus(vsDetails.getVotingStatus());

        //list of candidates I am going to set where I use this method



        return votingSessionDtoWithIdAndCondidates;
    }

}
