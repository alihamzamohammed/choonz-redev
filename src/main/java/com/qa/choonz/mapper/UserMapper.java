package com.qa.choonz.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.rest.dto.UserDTO;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO mapToDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }

    public User mapToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }

    public List<UserDTO> listMapToDTO(List<User> users) {
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<User> listMapToUser(List<UserDTO> userDTOs) {
        return userDTOs.stream().map(this::mapToUser).collect(Collectors.toList());
    }

}
