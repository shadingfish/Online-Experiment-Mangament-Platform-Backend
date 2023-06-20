package com.sof_eng.Mapper;


import com.sof_eng.model.DTO.Distribution;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DistributionMapper {
    List<Distribution> getListByUserId(Long userId);
    void addDistribution(Distribution distribution);
    void deleteDistributionById(Long id);
    void updateDistribution(Distribution distribution);
    Distribution getDistributionById(Long id);
    List<String> getUsernameByExpId(Long expId);
    Distribution getDistributionByParExp(Long expId, String participant);
    List<Distribution> getListByExpId(Long expId);
}
