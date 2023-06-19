package com.sof_eng.Controller;


import com.sof_eng.Mapper.UserMapper;
import com.sof_eng.Util.JwtTokenUtil;
import com.sof_eng.model.CommonResult;
import com.sof_eng.model.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "用户信息接口")
@RequestMapping("/admin-api/user/")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @GetMapping("/getall")
    public CommonResult<?> getAllUser(@RequestHeader("Authorization") String authHeader) {
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User foundUser = userMapper.getUserByName(username);
        if(foundUser.getChara().equals("普通用户"))
            return CommonResult.error(114514,"没有权限进行该操作");
        List<User> users=userMapper.getAllUser(username);
        return CommonResult.success(users);
    }
    @GetMapping("/profile/get")
    public CommonResult<?> getUserProfile(@RequestHeader("Authorization") String authHeader) {
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        String token = authHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User foundUser = userMapper.getUserByName(username);
        CommonResult<User> result = CommonResult.success(foundUser);
        return result;
    }
    @PostMapping("/profile/change")
    public CommonResult<?> changeUserProfile(@RequestBody User user, @RequestHeader("Authorization") String token) {
        // 解析Authorization请求头中的JWT令牌 Bearer access_token
        token=token.substring(7);
        if(!jwtTokenUtil.validateToken(token))
            return CommonResult.error(500,"token validation failed");
        try {
            // 将用户信息保存到数据库
            User temp=userMapper.getUserByName(jwtTokenUtil.getUsernameFromToken(token));
            user.setId(temp.getId());
            user.setUsername(temp.getUsername());
            user.setPassword(temp.getPassword());
            System.out.println(user);
            userMapper.updateUser(user);
        } catch (Exception e) {
            // 处理插入失败的情况
            System.out.println(e.toString());
            return CommonResult.error(50003,"User information changed failed");
        }
        return CommonResult.success("User information changed successfully");
    }
}
