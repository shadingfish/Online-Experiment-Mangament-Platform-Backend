package com.sof_eng.Service;

import com.sof_eng.Mapper.ExperimentDatasetMapper;
import com.sof_eng.model.DTO.ExperimentDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperimentDatasetService {
    @Autowired
    private ExperimentDatasetMapper experimentDatasetMapper;

    public void addExperimentDataset(ExperimentDataset experimentDataset) {
        experimentDatasetMapper.addExperimentDataset(experimentDataset);
    }

    public void deleteExperimentDatasetById(Long id) {
        experimentDatasetMapper.deleteExperimentDatasetById(id);
    }

    public void updateExperimentDataset(ExperimentDataset experimentDataset) {
        experimentDatasetMapper.updateExperimentDataset(experimentDataset);
    }

    public ExperimentDataset getExperimentDatasetById(Long id) {
        return experimentDatasetMapper.getExperimentDatasetById(id);
    }
}
