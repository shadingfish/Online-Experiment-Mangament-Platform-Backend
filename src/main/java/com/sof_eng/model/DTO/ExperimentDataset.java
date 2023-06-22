package com.sof_eng.model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class ExperimentDataset {
    private Long id;
    private String dataset;
    private String expName;
    private Long expId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Timestamp activeTime;

    // Getter and Setter methods
    // ...
}

