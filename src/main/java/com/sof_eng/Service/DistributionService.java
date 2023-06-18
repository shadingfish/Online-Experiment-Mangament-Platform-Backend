package com.sof_eng.Service;


import com.sof_eng.Mapper.DistributionMapper;
import com.sof_eng.model.DTO.Distribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

