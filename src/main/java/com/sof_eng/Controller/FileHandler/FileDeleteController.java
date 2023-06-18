package com.sof_eng.Controller.FileHandler;

import com.sof_eng.Mapper.FileMapper;
import com.sof_eng.model.CommonResult;
import com.sof_eng.model.DTO.otreeFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api")
public class FileDeleteController {
    @Autowired
    FileMapper fileMapper;
    @ResponseBody
    @DeleteMapping("/removeFileRec")
    public CommonResult<?> removeFileRec(@RequestBody otreeFile otreeFile){
        //System.out.println(otreeFile);
        fileMapper.removeFileRecById(otreeFile.getId());
        return CommonResult.success(otreeFile);
    }
}
