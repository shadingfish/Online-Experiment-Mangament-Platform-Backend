package com.sof_eng.Mapper;


import com.sof_eng.model.DTO.Experiment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ExperimentMapper {
    void addExperiment(Experiment experiment);

    void deleteExperimentById(Long id);

    void updateExperiment(Experiment experiment);

    Experiment getExperimentById(Long id);

    List<Experiment> getListByUsername(String username);

    int startExp(Experiment experiment);
}