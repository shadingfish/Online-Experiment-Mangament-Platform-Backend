package com.sof_eng.model.DTO;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class ExperimentDataset {
    private Long id;
    private String dataset;
    private String expName;
    private Long expId;
    private Timestamp activeTime;

    // Getter and Setter methods
    // ...
}

