package com.sof_eng.Mapper;

import com.sof_eng.model.DTO.otreeFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    public void insertFileRec(otreeFile otreeFile);
    public List<otreeFile> getFileRec(String username, String endWith);
    public void updateFileRec (Long id);
    public otreeFile getFileRecById(Long id);
}
