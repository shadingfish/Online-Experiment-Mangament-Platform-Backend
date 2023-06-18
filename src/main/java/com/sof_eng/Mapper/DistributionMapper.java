package com.sof_eng.Mapper;


import com.sof_eng.model.DTO.Distribution;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DistributionMapper {
    void addDistribution(Distribution distribution);
    void deleteDistributionById(Long id);
    void updateDistribution(Distribution distribution);
    Distribution getDistributionById(Long id);
}
