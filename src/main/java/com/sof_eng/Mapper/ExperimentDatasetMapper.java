package com.sof_eng.Mapper;


import com.sof_eng.model.DTO.ExperimentDataset;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ExperimentDatasetMapper {
    void addExperimentDataset(ExperimentDataset experimentDataset);
    void deleteExperimentDatasetById(Long id);
    void updateExperimentDataset(ExperimentDataset experimentDataset);
    ExperimentDataset getExperimentDatasetById(Long id);
}
