package com.sof_eng.model.DTO;


import lombok.Data;

import java.sql.Date;
@Data
public class otreeFile {
    private Long id;
    private String title;
    private String founder;
    private Long founder_id;
    private Date create_time;
    private Date active_time;
    private String directory;
    private String filetype;
}
