package com.sof_eng.Controller.FileHandler;

import com.sof_eng.Mapper.ExperimentMapper;
import com.sof_eng.Mapper.FileMapper;
import com.sof_eng.Util.JwtTokenUtil;
import com.sof_eng.model.CommonResult;
import com.sof_eng.model.DTO.Experiment;
import com.sof_eng.model.DTO.otreeFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@Controller
@RestController
@RequestMapping("/api")
public class FileGetController {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    FileMapper fileMapper;
    @Autowired
    ExperimentMapper experimentMapper;
    @PostMapping("/getFileRec")
    @ResponseBody
    public CommonResult<?> getFileRec(@RequestBody String endWith, @RequestHeader("Authorization") String token ){
        int l=endWith.length();
        endWith=endWith.substring(0,l-1);
        token=token.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(50003,"invalid token");
        String username=jwtTokenUtil.getUsernameFromToken(token);
        List<otreeFile> otreeFiles=fileMapper.getFileRec(username,endWith);
        return CommonResult.success(otreeFiles);
    }
    @PostMapping("/getExpRec")
    @ResponseBody
    public CommonResult<?> getExpRec(@RequestHeader("Authorization") String token ){
        token=token.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(50003,"invalid token");
        String username=jwtTokenUtil.getUsernameFromToken(token);
        List<Experiment> experiments=experimentMapper.getListByUsername(username);
        return CommonResult.success(experiments);
    }
}
