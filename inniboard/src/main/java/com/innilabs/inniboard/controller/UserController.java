package com.innilabs.inniboard.controller;

import java.util.Map;

import com.innilabs.inniboard.dto.User;
import com.innilabs.inniboard.repository.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Api(description = "회원 자원을 이용한 api") //Controller 단위로 api 메타데이터 명시
@RequestMapping("/api/")
public class UserController {
    @Autowired
    UserDao userDao;

    @ApiOperation(value = "Get All Users", notes = "모든 회원 조회")
    @GetMapping(value = "/users")
    public ResponseEntity<Map<String, User>> getAllUsers(){
        return new ResponseEntity<Map<String, User>>(userDao.getAllUsers(), HttpStatus.OK);
    }
    
    @ApiOperation(value = "Get User By Id", notes = "아이디로 회원 조회")
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        return new ResponseEntity<User>(userDao.getUserById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Create User", notes = "회원 생성")
    @PostMapping(value="/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping(value="users/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable String id, @RequestBody User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "Token", value = "헤더 테스트", dataType = "string", paramType = "header", required = true, defaultValue = "token")
    })
    @DeleteMapping(value = "users/delete")
    public ResponseEntity<User> deleteUserById(@RequestParam String id){
        return new ResponseEntity<>(HttpStatus.OK);
    }    
}