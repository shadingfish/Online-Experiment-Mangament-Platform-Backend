package com.sof_eng.Service;


import com.sof_eng.Mapper.UserMapper;
import com.sof_eng.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void addUser(User user) {
        userMapper.addUser(user);
    }

    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    public User getUserByName(String username)  {
        return userMapper.getUserByName(username);
    }
}