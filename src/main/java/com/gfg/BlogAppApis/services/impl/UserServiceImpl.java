package com.gfg.BlogAppApis.services.impl;

import com.gfg.BlogAppApis.config.AppConstants;
import com.gfg.BlogAppApis.entities.Role;
import com.gfg.BlogAppApis.entities.User;
import com.gfg.BlogAppApis.exceptions.ResourceNotFoundException;
import com.gfg.BlogAppApis.payloads.UserDto;
import com.gfg.BlogAppApis.repositories.RoleRepo;
import com.gfg.BlogAppApis.repositories.UserRepo;
import com.gfg.BlogAppApis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user=modelMapper.map(userDto,User.class);
        //encoded password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //roles
        Role role =roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        User newUser=userRepo.save(user);
        return modelMapper.map(newUser,UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        User savedUser=userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," id ",userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser=userRepo.save(user);
        UserDto userDto1=this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," Id ",userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=userRepo.findAll();
        List<UserDto> userDtos=users.stream().map(user->userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," Id ",userId));
        userRepo.delete(user);
    }
    //Method to convert userdto to user
    private User dtoToUser(UserDto userDto){
        User user=modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto=modelMapper.map(user,UserDto.class);

//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());

        return userDto;
    }
}
