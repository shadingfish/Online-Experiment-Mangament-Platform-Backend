package com.sof_eng.Mapper;

import com.sof_eng.model.DTO.otreeFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {
    public void insertFileRec(otreeFile otreeFile);
    public List<otreeFile> getFileRec(String username, String endWith);
    public void updateFileRec (Long id);
    public otreeFile getFileRecById(Long id);
    public void removeFileRecById(Long id);
}
