package com.example.bookstore_user.service;
import com.example.bookstore_user.dto.LoginDto;
import com.example.bookstore_user.dto.UserDto;
import com.example.bookstore_user.model.User;

import java.util.List;

public interface IUserService {
    String addRecord(UserDto addressDto);
    List<User> findAll();
    User FindById(Long id);
    User getUserByemail(String email);
    User editByEmail(UserDto userDTO, String email_address);
    User getDataByToken(String token);
    User loginUser(LoginDto loginDTO);
    String forgotPassword(LoginDto loginDTO);
    String resetPassword(LoginDto loginDTO);

    String deleteByid(Long id, String token);

    User verifyUser(String token);

    User FindByUserId(Long id);
}

