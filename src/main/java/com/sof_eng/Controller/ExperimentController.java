package com.sof_eng.Controller;


import com.sof_eng.Service.ExperimentService;
import com.sof_eng.Service.UserService;
import com.sof_eng.model.CommonResult;
import com.sof_eng.model.DTO.Experiment;
import com.sof_eng.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/experiment/")
public class ExperimentController {


    @Autowired
    ExperimentService experimentService;
    UserService userService;


    /**
     * experimentVO {
     *
     *     private String title;
     *     private String url;
     *     private Date createTime;
     *     private Integer volume;
     *     private Date activeTime;
     *     private String zipName;
     *     private String dictionary
     *
     *     }
     * 插入新的实验
     * @param token
     * @param experimentVO
     * @return
     */
    @CrossOrigin
    @PostMapping("/upload")
    public CommonResult<?> uploadExp(@RequestHeader("Authorization") String token, @RequestBody Experiment experimentVO){
        try{
            //JWT反解析
            String username = "";

            User founder = userService.getUserByName(username);
            //Experiment experiment = new Experiment(0L, experimentVO.getTitle(), experimentVO.getUrl(), username, founder.getId(), experimentVO.getCreateTime(), experimentVO.getVolume(), experimentVO.getActiveTime(), experimentVO.getZipName(), experimentVO.getDictionary());
            experimentService.addExperiment(experimentVO);

            return CommonResult.success(experimentVO);
        }catch(Exception e) {
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }

    /**
     * 查询所有实验
     * @param token
     * @return
     */
    @CrossOrigin
    @GetMapping("/getlist")
    public CommonResult<?> getExpList(@RequestHeader("Authorization") String token){

        try{
            String username = "";
            List<Experiment> expList = experimentService.getListByUsername(username);
            System.out.print("成功查询到实验列表：");
            System.out.println(expList);

            return CommonResult.success(expList);

        }catch (Exception e){
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }

    /**
     * 删除实验
     * @param id
     * @return
     */
    @CrossOrigin
    @PostMapping("/delete")
    public CommonResult<?> deleteExp(@RequestBody long id){
        try{
            Experiment experiment = experimentService.getExperimentById(id);
            experimentService.deleteExperimentById(id);
            return CommonResult.success(experiment.getTitle());
        }catch (Exception e){
            log.info(e.getMessage());
            return CommonResult.error(411, "出错：" + e.getMessage());
        }
    }
}
