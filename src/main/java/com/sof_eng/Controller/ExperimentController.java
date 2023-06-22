package com.sof_eng.Controller;


import com.sof_eng.Service.DistributionService;
import com.sof_eng.Service.ExperimentService;
import com.sof_eng.Service.UserService;
import com.sof_eng.Util.JwtTokenUtil;
import com.sof_eng.model.CommonResult;
import com.sof_eng.model.DTO.Distribution;
import com.sof_eng.model.DTO.Experiment;
import com.sof_eng.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.apache.poi.ss.usermodel.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Slf4j
@RestController
@RequestMapping("/experiment")
public class ExperimentController {


    @Autowired
    ExperimentService experimentService;
    @Autowired
    UserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    DistributionService distributionService;

/*    *//**
     * experimentVO {
     *
     *     private String title;
     *     private Date createTime;
     *     private Integer volume;
     *     private Date activeTime;
     *     private String zipName;
     *     private String directory
     *
     *     }
     * 插入新的实验
     * @param authHeader 检验头
     * @param experimentVO 前端传过来的数据体
     * @return 一个Experiment类，即创建成功的数据库记录 CommonResult.success(experiment)
     *//*
    @CrossOrigin
    @PostMapping("/upload")
    public CommonResult<?> uploadExp(@RequestHeader("Authorization") String authHeader, @RequestBody Experiment experimentVO){
        System.out.println("获得前端新增请求：" + experimentVO);
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(400, "invalid token");

        //JWT反解析
        String username= jwtTokenUtil.getUsernameFromToken(token);
        System.out.println(username);

        try{
            User founder = userService.getUserByName(username);
            Experiment experiment = new Experiment(0L, experimentVO.getTitle(), "none", username, founder.getId(), experimentVO.getCreateTime(), experimentVO.getVolume(), experimentVO.getActiveTime(), experimentVO.getZipName(), experimentVO.getDirectory());
            experimentService.addExperiment(experiment);

            return CommonResult.success(experiment);
        }catch(Exception e) {
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }*/

    /**
     * 查询所有实验
     * @param authHeader 检验头
     * @return List<Experiment>所有实验的列表 CommonResult.success(expList)
     */
    @CrossOrigin
    @GetMapping("/getlist")
    public CommonResult<?> getExpList(@RequestHeader("Authorization") String authHeader){
        //System.out.println("authHeader: " + authHeader);
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        //System.out.println("token: " + token);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(400, "invalid token");
        //JWT反解析
        String username= jwtTokenUtil.getUsernameFromToken(token);
        //System.out.println(username);

        try{
            List<Experiment> expList = experimentService.getListByUsername(username);
            //System.out.print("成功查询到实验列表：");
            //System.out.println(expList);

            return CommonResult.success(expList);

        }catch (Exception e){
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }
    /**
     *
     * @param experiment 实验
     * @param authHeader 检验头
     * @return 所修改的实验 CommonResult.success(experiment);
     */
    @CrossOrigin
    @PostMapping("/update")
    public CommonResult<?> updateExp(@RequestHeader("Authorization") String authHeader, @RequestBody Experiment experiment){
        String token = authHeader.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(400, "invalid token");
        experimentService.updateExperiment(experiment);
        return CommonResult.success(experiment);
    }


    /**
     *
     * @param authHeader 检验头
     * @return 所删除的实验 CommonResult.success(experiment);
     */
    @CrossOrigin
    @DeleteMapping("/delete")
    public CommonResult<?> deleteExp(
            @RequestBody Experiment experiment, @RequestHeader("Authorization") String authHeader){
        Long id=experiment.getId();
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(400, "invalid token");
        //JWT反解析
        String username= jwtTokenUtil.getUsernameFromToken(token);
        //System.out.println("登录人:" + username);

        try{
            experimentService.deleteExperimentById(id);
            return CommonResult.success(experiment);
        }catch (Exception e){
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }

    /**
     * @param expId 实验id
     * @param userId 要增加的被试者的id（前端填写）
     * @param authHeader 检验头
     * @return 返回插入的distribution
     */
    @CrossOrigin
    @PostMapping("/addParticipant")
    public CommonResult<?> addParticipant(@RequestParam("expId") Long expId, @RequestParam("userId") Long userId,@RequestHeader("Authorization") String authHeader){
        //验证expId
        Experiment experiment = experimentService.getExperimentById(expId);
        if(experiment == null){
            return CommonResult.error(400, "invalid expId");
        }
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(400, "invalid token");
        //JWT反解析
        String username= jwtTokenUtil.getUsernameFromToken(token);
        //System.out.println("登录人:" + username);

        try{
            User user = userService.getUserById(userId);
            if(user == null){
                return CommonResult.error(400, "invalid userId");
            }

            //检查是否已经插入
            Distribution distribution = distributionService.getDistributionByParExp(expId, user.getUsername());
            if(distribution != null) {
                return CommonResult.error(300, "已经导入该参与者。");
            }

            distribution = new Distribution();
            // 构造 Distribution 对象并插入到数据库
            distribution.setExpId(expId);
            distribution.setExpName(experiment.getTitle());
            distribution.setUrl("none");
            distribution.setParticipant(user.getUsername());
            distribution.setParticipantId(user.getId());
            distribution.setCreateTime(experiment.getCreate_time());
            distribution.setActiveTime(experiment.getActive_time());

            distributionService.addDistribution(distribution);

            return CommonResult.success(distribution);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }

    }

    /**
     *
     * @param expId 实验的id
     * @param file 参与者名单列表，里面只有一列数据
     * @param authHeader 检验头
     * @return distributionList
     */
    @CrossOrigin
    @PostMapping("/uploadParticipant")
    public CommonResult<?> uploadParticipant(@RequestParam("expId") Long expId, @RequestPart("file") MultipartFile file,@RequestHeader("Authorization") String authHeader){
        //验证expId
        Experiment experiment = experimentService.getExperimentById(expId);
        if(experiment == null){
            return CommonResult.error(400, "invalid expId");
        }
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(400, "invalid token");
        //JWT反解析
        String username= jwtTokenUtil.getUsernameFromToken(token);
        //System.out.println("登录人:" + username);

        List<Distribution> distributionList = new ArrayList<>();

        try{
            // 创建工作簿对象
            Workbook workbook = WorkbookFactory.create(file.getInputStream());

            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);

            // 遍历行
            for (Row row : sheet) {
                // 获取参与者信息，假设参与者信息在第一列（索引为0）
                Cell participantCell = row.getCell(0);

                String participant;
                try {
                    participant = participantCell.getStringCellValue();
                }catch(Exception e) {
                    participant = String.valueOf(participantCell.getNumericCellValue());
                }

                User user = userService.getUserByName(participant);
                if(user == null){
                    continue;
                }
                Distribution isJoined = distributionService.getDistributionByParExp(expId, user.getUsername());
                if(isJoined != null) {
                    continue;
                }

                // 构造 Distribution 对象并插入到数据库
                Distribution distribution = new Distribution();
                distribution.setExpId(expId);
                distribution.setExpName(experiment.getTitle());
                distribution.setUrl("none");
                distribution.setParticipant(participant);
                distribution.setParticipantId(user.getId());
                distribution.setCreateTime(experiment.getCreate_time());
                distribution.setActiveTime(experiment.getActive_time());
                distributionService.addDistribution(distribution);
                distributionList.add(distribution);
            }

            //System.out.println("所有新增的参与者:" + distributionList);
            workbook.close();
            return CommonResult.success(distributionList);
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }

    /**
     *
     * @param expId 实验id
     * @param authHeader 检验头
     * @return 返回了一个List<String>，这是所有该实验参与者username组成的列表
     */
    @CrossOrigin
    @GetMapping("/getParticipant")
    public CommonResult<?> getParticipant(@RequestParam("expId") Long expId, @RequestHeader("Authorization") String authHeader){
        //验证expId
        Experiment experiment = experimentService.getExperimentById(expId);
        if(experiment == null){
            return CommonResult.error(400, "invalid expId");
        }
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(400, "invalid token");
        //JWT反解析
        String username= jwtTokenUtil.getUsernameFromToken(token);
        //System.out.println("登录人:" + username);

        try{
            List<String> participants = distributionService.getUsernameByExpId(expId);

            return CommonResult.success(participants);
        }catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }

    /**
     *
     * @param expId 实验id
     * @param participant 参与者的username
     * @param authHeader 检验头
     * @return 删除了的Distribution CommonResult.success(distribution);
     */
    @CrossOrigin
    @PostMapping("/deleteParticipant")
    public CommonResult<?> deleteParticipant(@RequestParam("expId") Long expId, @RequestParam("username") String participant, @RequestHeader("Authorization") String authHeader){
        //验证expId
        Experiment experiment = experimentService.getExperimentById(expId);
        if(experiment == null){
            return CommonResult.error(400, "invalid expId");
        }
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(400, "invalid token");
        //JWT反解析
        String username= jwtTokenUtil.getUsernameFromToken(token);
        //System.out.println("登录人:" + username);
        Distribution distribution = new Distribution();

        try{
            distribution = distributionService.getDistributionByParExp(expId, participant);
            distributionService.deleteDistributionById(distribution.getId());
            return CommonResult.success(distribution);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }
}
