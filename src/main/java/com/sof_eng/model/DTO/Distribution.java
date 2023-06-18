package com.sof_eng.model.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Distribution {
    private Long id;
    private Long expId;
    private String expName;
    private String url;
    private String participant;
    private Long participantId;
    private LocalDateTime createTime;
    private LocalDateTime activeTime;

    // Getter and Setter methods
    // ...
}
