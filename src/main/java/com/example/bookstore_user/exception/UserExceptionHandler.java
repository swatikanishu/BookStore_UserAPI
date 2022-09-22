package com.example.bookstore_user.exception;

import com.example.bookstore_user.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errmsg = errorList.stream()
                .map(objErr -> objErr.getDefaultMessage())
                .collect(Collectors.toList());
        ResponseDto respDTO = new ResponseDto("Exception while processing rest request", errmsg.toString());
        return new ResponseEntity(respDTO, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseDto> handleAddressBookException(UserException exception){
        ResponseDto resDTO = new ResponseDto("Exception while processing User REST request", exception.getMessage());
        return new ResponseEntity(resDTO, HttpStatus.BAD_REQUEST);
    }

}

