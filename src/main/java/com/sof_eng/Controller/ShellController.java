package com.sof_eng.Controller;

import com.sof_eng.Service.ShellService;
import com.sof_eng.model.command;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("实验运行模块")
@Component
public class ShellController {
    @Autowired
    ShellService shellService;

    @PostMapping({"/executeShell"})
    public String execshell(@RequestBody command cmdline, @RequestHeader("Authorization") String token) {
        return this.shellService.exec(cmdline,token);
    }
}