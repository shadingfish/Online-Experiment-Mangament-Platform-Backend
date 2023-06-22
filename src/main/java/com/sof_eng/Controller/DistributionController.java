package com.sof_eng.Controller;


import com.sof_eng.Service.DistributionService;
import com.sof_eng.Service.ExperimentService;
import com.sof_eng.Service.UserService;
import com.sof_eng.Util.JwtTokenUtil;
import com.sof_eng.model.CommonResult;
import com.sof_eng.model.DTO.Distribution;
import com.sof_eng.model.DTO.Experiment;
import com.sof_eng.model.DistributionRequest;
import com.sof_eng.model.DistributionVo;
import com.sof_eng.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/distribution/")
public class DistributionController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    DistributionService distributionService;

    @Autowired
    ExperimentService experimentService;

    @Autowired
    UserService userService;

    @CrossOrigin
    @PostMapping("/experimentByUrl")
    public CommonResult<?> expByUrl(@RequestHeader("Authorization") String authHeader, @RequestBody DistributionRequest distributionUrlVO) {
        try{
            /*
            // 解析Authorization请求头中的JWT令牌 Bearer access_token
            String token = authHeader.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User founduser=userService.getUserByName(username);
            if(founduser.getChara()!="管理员"){//校验该用户是否为管理员即是否具有一键分发的权限
                return CommonResult.error(403,"用户没有实行该功能的权限");
            }
             */
            Experiment experiment =experimentService.getExperimentById(distributionUrlVO.getExpId());
            if(!experiment.getUrl().equals("none") ){//确认实验是否开始
                List<Distribution> distributions=distributionService.getListByExpId(distributionUrlVO.getExpId());
                //System.out.println(distributions);
                for (Distribution distribution : distributions) {
                    distribution.setUrl(distributionUrlVO.getUrl());
                    distributionService.updateDistribution(distribution);
                }
                return CommonResult.success("一键分发实验成功");
            }else{
                return CommonResult.error(404,"实验未开始，请开始后重试");
            }
        }catch(Exception e) {
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/joinedByList")
    public CommonResult<?> getJoinedExpList(@RequestHeader("Authorization") String authHeader) {
        try{
            // 解析Authorization请求头中的JWT令牌 Bearer access_token
            String token = authHeader.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            User founduser=userService.getUserByName(username);
            List<Distribution> distributions=distributionService.getListByUserId(founduser.getId());
//            List<DistributionVo> distributionVoList = new ArrayList<>();
//            for(Distribution distribution:distributions){
//                DistributionVo distributionVo=new DistributionVo();
//                distributionVo.setExpId(distribution.getExpId());
//                distributionVo.setExpName(distribution.getExpName());
//                distributionVo.setUrl(distribution.getUrl());
//                distributionVoList.add(distributionVo);
//            }
            return CommonResult.success(distributions);
        }catch(Exception e) {
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }
}
