package com.sof_eng.Service;


import com.sof_eng.Mapper.DistributionMapper;
import com.sof_eng.model.DTO.Distribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributionService {
    @Autowired
    private DistributionMapper distributionMapper;

    public void addDistribution(Distribution distribution) {
        distributionMapper.addDistribution(distribution);
    }

    public void deleteDistributionById(Long id) {
        distributionMapper.deleteDistributionById(id);
    }

    public void updateDistribution(Distribution distribution) {
        distributionMapper.updateDistribution(distribution);
    }

    public Distribution getDistributionById(Long id) {
        return distributionMapper.getDistributionById(id);
    }

    public List<String> getUsernameByExpId(Long expId) {
        return distributionMapper.getUsernameByExpId(expId);
    }

    public Distribution getDistributionByParExp(Long expId, String participant){
        return distributionMapper.getDistributionByParExp(expId, participant);
    }

    public List<Distribution> getListByExpId(Long expId) {
        return distributionMapper.getListByExpId(expId);
    }

    public List<Distribution> getListByUserId(Long id) {
        return distributionMapper.getListByUserId(id);
    }
}

