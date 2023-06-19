package com.sof_eng.Service;


import com.sof_eng.Mapper.ExperimentMapper;
import com.sof_eng.model.DTO.Experiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperimentService {
    @Autowired
    private ExperimentMapper experimentMapper;

    public void addExperiment(Experiment experiment) {
        experimentMapper.addExperiment(experiment);
    }

    public void deleteExperimentById(Long id) {
        experimentMapper.deleteExperimentById(id);
    }

    public void updateExperiment(Experiment experiment) {
        experimentMapper.updateExperiment(experiment);
    }

    public Experiment getExperimentById(Long id) {
        return experimentMapper.getExperimentById(id);
    }

    public List<Experiment> getListByUsername(String username){
        return experimentMapper.getListByUsername(username);
    }

    public int startExp(Long expId, String url){
        Experiment experiment = experimentMapper.getExperimentById(expId);
        experiment.setUrl(url);
        return experimentMapper.startExp(experiment);
    }
}
