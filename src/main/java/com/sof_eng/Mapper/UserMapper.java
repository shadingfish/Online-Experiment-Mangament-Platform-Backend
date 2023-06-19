package com.sof_eng.Mapper;

import com.sof_eng.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {
    void addUser(User user);
    void deleteUserById(Long id);
    void updateUser(User user);
    User getUserById(Long id);
    User getUserByName(String username);
    List<User> getAllUser(String username);
}