package com.sof_eng.model.DTO;


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
    private Timestamp createTime;
    private Timestamp activeTime;
    private String directory;
    // Getter and Setter methods
    // ...

/*    public Experiment(Long id, String title, String url, String founder, Long founderId, Date createTime, Integer volume, Date activeTime, String zipName, String dictionary) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.founder = founder;
        this.founderId = founderId;
        this.createTime = createTime;
        this.volume = volume;
        this.activeTime = activeTime;
        this.zipName = zipName;
        this.dictionary = dictionary;
    }

    public Experiment(String title, String url, Date createTime, Integer volume, Date activeTime, String zipName, String dictionary) {
        this.id = 0L;
        this.title = title;
        this.url = url;
        this.founder = "founder";
        this.founderId = 0L;
        this.createTime = createTime;
        this.volume = volume;
        this.activeTime = activeTime;
        this.zipName = zipName;
        this.dictionary = dictionary;
    }*/

    public Experiment() {
    }
}
