package com.sof_eng.model.DTO;


import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class otreeFile {
    private Long id;
    private String title;
    private String founder;
    private Long founder_id;
    private String create_time;
    private String active_time;
    private String directory;
    private String filetype;
}
