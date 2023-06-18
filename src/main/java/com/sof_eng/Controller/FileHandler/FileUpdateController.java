package com.sof_eng.Controller.FileHandler;

import com.sof_eng.Mapper.FileMapper;
import com.sof_eng.Util.JwtTokenUtil;
import com.sof_eng.model.CommonResult;
import com.sof_eng.model.DTO.otreeFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class FileUpdateController {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    FileMapper fileMapper;
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

    @ResponseBody
    @PostMapping("/update")
    public CommonResult<?> updateFileRec(@RequestBody otreeFile id){
        System.out.println("touched update");
        fileMapper.updateFileRec(id.getId());
        otreeFile otreeFile=fileMapper.getFileRecById(id.getId());
        return CommonResult.success(otreeFile);
    }

    @ResponseBody
    @PostMapping("/remove")
    public CommonResult<?> removeFileRec(@RequestBody otreeFile id){
        System.out.println("touched remove");
        fileMapper.updateFileRec(id.getId());
        fileMapper.removeFileRecById(id.getId());
        return CommonResult.success("成功移除："+fileMapper.getFileRecById(id.getId()));

    }
}
