package com.example.bookstore_user.controller;

import com.example.bookstore_user.dto.LoginDto;
import com.example.bookstore_user.dto.ResponseDto;
import com.example.bookstore_user.dto.UserDto;
import com.example.bookstore_user.model.User;
import com.example.bookstore_user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


    @RestController
    @RequestMapping("/User")
    public class UserController {
        @Autowired
        IUserService userService;

        // Add new user data
        @PostMapping("/insert")
        public ResponseEntity<String> AddAddressDetails(@Valid @RequestBody UserDto userDto) {
            String token = userService.addRecord(userDto);
            ResponseDto respDTO = new ResponseDto("Data Added Successfully", token);
            return new ResponseEntity(respDTO, HttpStatus.CREATED);
        }
        // Get all user data
        @GetMapping("/findAll")
        public ResponseEntity<ResponseDto> findAllDetail() {
            List<User> userList = userService.findAll();
            ResponseDto responseDTO = new ResponseDto("** All User List ** ", userList);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
//        gGet user by id
        @GetMapping("/get/{Id}")
        public ResponseEntity<ResponseDto> FindById(@PathVariable Long Id) {
            User response = userService.FindById(Id);
            ResponseDto responseDto = new ResponseDto("***All Details user list using Id***", response);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        //        gGet user by id
        @GetMapping("/Get/{Id}")
        public User FindByUserId(@PathVariable Long Id) {
            User response = userService.FindByUserId(Id);
           return response;
        }


        //        data by using email
        @GetMapping("/email/{email}")
        public ResponseEntity<ResponseDto> getDataByemail(@PathVariable String email) {
            User personDetailsList = userService.getUserByemail(email);
            ResponseDto respDTO = new ResponseDto("*** Data by using email ***", personDetailsList);
            return new ResponseEntity<>(respDTO, HttpStatus.OK);
        }
        // updated person details using email
        @PutMapping("/edit/{email_address}")
        public ResponseEntity<ResponseDto> updateById(@PathVariable String email_address, @Valid @RequestBody UserDto userDTO) {
            User Details = userService.editByEmail(userDTO, email_address);
            ResponseDto respDTO = new ResponseDto(" **** Person details is updated *****", Details);
            return new ResponseEntity<>(respDTO, HttpStatus.OK);
        }
        // get data by using token
        @GetMapping("/retrieve/{token}")
        public ResponseEntity<ResponseDto> getUserDetails(@Valid @PathVariable String token) {
            User response = userService.getDataByToken(token);
            ResponseDto respDTO = new ResponseDto("Data retrieved successfully", response);
            return new ResponseEntity(respDTO, HttpStatus.CREATED);
        }
        //User login
        @PostMapping("/login")
        public ResponseEntity<ResponseDto> loginUser(@RequestBody LoginDto loginDTO) {
            User response = userService.loginUser(loginDTO);
            ResponseDto responseDTO = new ResponseDto("Login Successful!", response);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        // forgot password
        @PostMapping("/forgotPassword")
        public ResponseEntity<ResponseDto> forgotPassword(@RequestBody LoginDto loginDTO) {
            String response=userService.forgotPassword(loginDTO);
            ResponseDto responseDTO = new ResponseDto("Checking password for given email id", response);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        // reset password
        @PostMapping("/resetPassword")
        public ResponseEntity<ResponseDto> changePassword(@RequestBody LoginDto loginDTO) {
            String response = userService.resetPassword(loginDTO);
            ResponseDto responseDTO = new ResponseDto("Password Status:", response);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        // delete data
        @DeleteMapping("/delete/{id}/{token}")
        public ResponseEntity<ResponseDto> retriveData(@PathVariable Long id ,@PathVariable String token ){
            String user =userService.deleteByid(id,token);
            ResponseDto response =new ResponseDto("Delete data by token",user);
            return new  ResponseEntity(response,HttpStatus.ACCEPTED);
        }
        // verify user by token
        @GetMapping("/verify/{token}")
        public ResponseEntity<ResponseDto> verifyUser(@PathVariable String token) {
            User user =userService.verifyUser(token);
            ResponseDto responseDTO = new ResponseDto("User verified successfully", user);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
    }


