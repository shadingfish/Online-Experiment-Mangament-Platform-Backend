package com.sof_eng.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Distribution {
    private Long id;
    private Long expId;
    private String expName;
    private String url;
    private String participant;
    private Long participantId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Timestamp createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Timestamp activeTime;

    // Getter and Setter methods
    // ...

    public Distribution() {
    }

    public Distribution(Long id, Long expId, String expName, String url, String participant, Long participantId, Timestamp createTime, Timestamp activeTime) {
        this.id = id;
        this.expId = expId;
        this.expName = expName;
        this.url = url;
        this.participant = participant;
        this.participantId = participantId;
        this.createTime = createTime;
        this.activeTime = activeTime;
    }
}
