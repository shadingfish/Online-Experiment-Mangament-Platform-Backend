package com.sof_eng.model.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class Experiment {
    private Long id;
    private String title;
    private String url;
    private String founder;
    private Long founderId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Timestamp create_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Timestamp active_time;
    private String directory;
    private String description;
    // Getter and Setter methods
    // ...

/*    public Experiment(Long id, String title, String url, String founder, Long founderId, Timestamp createTime, Integer volume, Timestamp activeTime, String dictionary) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.founder = founder;
        this.founderId = founderId;
        this.create_Time = createTime;
        this.active_Time = activeTime;
        this.directory = dictionary;
    }

    public Experiment(String title, Timestamp createTime, Integer volume, Timestamp activeTime, String dictionary) {
        this.id = 0L;
        this.title = title;
        this.url = "none";
        this.founder = "founder";
        this.founderId = 0L;
        this.create_Time = createTime;
        this.active_Time = activeTime;
        this.directory = dictionary;
    }*/

    public Experiment() {
    }
}
