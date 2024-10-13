package com.yahia.votingms.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "votes")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
public class Vote extends BaseEntity {

    @Id
    private String id;

    private Long voterId;

    private Long condidateId;

    private Long votingSessionId;


}
