package com.example.bookstore_user.service;
import com.example.bookstore_user.dto.LoginDto;
import com.example.bookstore_user.dto.UserDto;
import com.example.bookstore_user.exception.UserException;
import com.example.bookstore_user.model.User;
import com.example.bookstore_user.repo.UserRepo;
import com.example.bookstore_user.utility.EmailSenderService;
import com.example.bookstore_user.utility.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepo repository;
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    EmailSenderService emailSenderService;
     @Autowired
     RestTemplate restTemplate;
    @Override
    public String addRecord(UserDto addressDto) throws UserException {
        User book = new User(addressDto);
        repository.save(book);
        String token = tokenUtil.createToken(book.getUserid());
        String userData = "Your Details: \n" + book.getFirstName() + "\n" + book.getAddress() + "\n"
                + book.getLastName() + "\n" + book.getDOB() + "\n" + book.getUserid() + "\n" +
                book.getPassword();
        emailSenderService.sendEmail(book.getEmail_address(), "Added Your Details", userData);
        return token;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User FindById(Long Id) {
        Optional<User> user = repository.findById(Id);
        if(user.isPresent())
            return user.get();
        else

        {
            throw new UserException("invalid id");
        }
    }
    @Override
    public User FindByUserId(Long id) {
        Optional<User> user = repository.findById(id);
        if(user.isPresent())
            return user.get();
        else {
            return null;
        }
    }

    @Override
    public User getUserByemail(String email) {
        User user = repository.findByEmail(email);
        return user;
    }

    @Override
    public User editByEmail(UserDto userDTO, String email) {
        User editdata = repository.findByEmail(email);
        if (editdata != null) {
            editdata.setFirstName(userDTO.getFirstName());
            editdata.setLastName(userDTO.getLastName());
            editdata.setEmail_address(userDTO.getEmail_address());
            editdata.setAddress(userDTO.getAddress());
            editdata.setDOB(userDTO.getDOB());
            editdata.setPassword(userDTO.getPassword());
            User user = repository.save(editdata);
            String token = tokenUtil.createToken(editdata.getUserid());
            emailSenderService.sendEmail(editdata.getEmail_address(), "Added Your Details/n", "http://localhost:8080/user/retrieve/" + token);
            return user;
        } else {
            throw new UserException("email:" + email + " is not present ");
        }

    }

    @Override
    public User getDataByToken(String token) {
        Long Userid = tokenUtil.decodeToken(token);
        Optional<User> existingData = repository.findById(Userid);
        if (existingData.isPresent()) {
            return existingData.get();
        } else
            throw new UserException("Invalid Token");
    }

    @Override
    public User loginUser(LoginDto loginDTO) {
        Optional<User> userDetails = Optional.ofNullable(repository.findByEmail(loginDTO.getEmail_address()));
        if (userDetails.isPresent()) {
            if (userDetails.get().getPassword().equals(loginDTO.getPassword())) {
                emailSenderService.sendEmail(userDetails.get().getEmail_address(), "Login", "Login Successful!");
                return userDetails.get();
            } else
                emailSenderService.sendEmail(userDetails.get().getEmail_address(), "Login", "You have entered Invalid password!");
            throw new UserException("Wrong Password!!!");
        } else
            throw new UserException("Login Failed, Entered wrong email or password!!!");
    }

    @Override
    public String forgotPassword(LoginDto loginDTO) {
        User user = repository.findByEmail(loginDTO.getEmail_address());
        if (user != null) {
            emailSenderService.sendEmail(user.getEmail_address(), "Login", "http://localhost:8081/User/resetPassword");

            return " email link send";
        } else {
            return "Please Enter Valid Email Id And Try Again!";
        }
    }

    @Override
    public String resetPassword(LoginDto loginDTO) {
        Optional<User> userDetails = Optional.ofNullable(repository.findByEmail(loginDTO.getEmail_address()));
        String password = loginDTO.getPassword();
        if (userDetails.isPresent()) {
            userDetails.get().setPassword(password);
            repository.save(userDetails.get());
            return "Password Changed";
        } else
            return "Password is not valid";
    }

    @Override
    public String deleteByid(Long Id, String token) {
        Optional<User> user = repository.findById(Id);
        Long userid = tokenUtil.decodeToken(token);
        Optional<User> userToken = repository.findById(userid);
        if (user.get().getFirstName().equals(userToken.get().getFirstName())) {
            repository.deleteById(Id);
            return user.get() + "User is deleted for this ID";
        } else
            throw new UserException("Data is not match");

    }

    @Override
    public User verifyUser(String token) {
        Long userid = tokenUtil.decodeToken(token);
        Optional<User> user = repository.findById(userid);
        if (user.isPresent()) {
            return user.get();

        } else
            throw new UserException("token is not valid");

    }


}














