package com.sof_eng.Controller.FileHandler;

import com.sof_eng.Mapper.FileMapper;
import com.sof_eng.Util.JwtTokenUtil;
import com.sof_eng.model.CommonResult;
import com.sof_eng.model.DTO.otreeFile;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@Api("python文件接口")
@RequestMapping("/api")
public class FileUpdateController {
    @Autowired
    FileMapper fileMapper;
    @ResponseBody
    @PostMapping("/updateFileRec")
    @CrossOrigin(methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
    public CommonResult<?> updateFileRec(@RequestBody otreeFile otreeFile){
        //System.out.println(otreeFile);
        fileMapper.updateFileRec(otreeFile.getId());
        return CommonResult.success(otreeFile);
    }
}
