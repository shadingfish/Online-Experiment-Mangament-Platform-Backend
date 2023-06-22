package com.sof_eng.Controller;



import com.sof_eng.Mapper.UserMapper;
import com.sof_eng.Util.JwtTokenUtil;
import com.sof_eng.model.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@Api(tags = "用户登录接口")
@RequestMapping("/admin-api/auth/")
public class AuthController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    public CommonResult<?> login(@RequestBody LoginRequest loginUser) {

        System.out.println("检测到登录用户: " + loginUser);

        User user = userMapper.getUserByName(loginUser.getUsername());
        if (user == null) {
            return CommonResult.error(50007,"没有找到用户");
        }

        if (!loginUser.getPassword().equals(user.getPassword())) {
            return CommonResult.error(50007,"登录失败，账号密码不正确");
        }
        String username = loginUser.getUsername();
        // 生成访问令牌和刷新令牌
        String accessToken = jwtTokenUtil.generateAccessToken(username);
        String refreshToken = jwtTokenUtil.generateRefreshToken(username);
        TokenResponse token_resp = new TokenResponse(accessToken,refreshToken);

        CommonResult<TokenResponse> result = CommonResult.success(token_resp);

        return result;
    }
    @PostMapping("/register")
    public CommonResult<?> register(@RequestBody User reg_user) {

        User foundUser = userMapper.getUserByName(reg_user.getUsername());

        if (foundUser != null) {
            return CommonResult.error(50003,"用户已存在");
        }

        try {
            // 将用户信息保存到数据库
            userMapper.addUser(reg_user);
            File userdirpy=new File(reg_user.getUsername()+File.separator+"PyPath");
            userdirpy.mkdirs();
            File userdirexp=new File(reg_user.getUsername()+File.separator+"ExPath");
            userdirexp.mkdirs();
        } catch (Exception e) {
            // 处理插入失败的情况
            System.out.println(e.toString());
            return CommonResult.error(50003,"User registration failed");
        }
        return CommonResult.success("User registered successfully");
    }
    @PostMapping("/ChangePassword")
    public CommonResult<?> ChangePassword(@RequestBody PasswordChangeRequest pcr, @RequestHeader("Authorization") String token) {
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(114514,"token已过期，请重新登录");

        User foundUser = userMapper.getUserByName(pcr.getUsername());

        if (foundUser == null) {
            return CommonResult.error(50004,"用户不存在");
        }
        try {
            foundUser.setPassword(pcr.getPassword());
            // 将用户信息保存到数据库
            userMapper.updateUser(foundUser);
        } catch (Exception e) {
            // 处理插入失败的情况
            System.out.println(e.toString());
            return CommonResult.error(50003,"User password changed failed");
        }
        return CommonResult.success("User password changed successfully");
    }
}
