package com.qa.choonz.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import com.qa.choonz.mapper.UserMapper;
import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.persistence.repository.UserRepository;
import com.qa.choonz.rest.dto.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class UserServiceUnitTest {

    @MockBean
    private UserRepository repo;

    @MockBean
    private UserMapper mapper;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserService service;

    UserService mockedService;

    User user;
    UserDTO userDTO;

    @BeforeEach
    void setup() {
        mockedService = mock(UserService.class);
        user = new User();
        user.setId(1);
        user.setUsername("test");
        user.setPassword("test");
        user.setPasswordConfirm("test");
        user.setRoles("USER");

        userDTO = new UserDTO();
        userDTO.setUsername("test");
        userDTO.setPassword("encoded_password");
    }

    @Test
    void saveTest() {
        when(encoder.encode(Mockito.anyString())).thenReturn("encoded_password");
        when(repo.save(Mockito.any(User.class))).thenReturn(user);
        when(mapper.mapToDTO(Mockito.any(User.class))).thenReturn(userDTO);
        assertThat(service.save(user)).isEqualTo(userDTO);
        verify(encoder, times(1)).encode(Mockito.anyString());
        verify(repo, times(1)).save(Mockito.any(User.class));
        verify(mapper, times(1)).mapToDTO(Mockito.any(User.class));
    }

    @Test
    void findByUsernameTest() {
        when(repo.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        assertThat(service.findByUsername("user")).isEqualTo(user);
        verify(repo, times(1)).findByUsername(Mockito.anyString());
    }

    @Test
    void updateTest() {
        when(encoder.encode(Mockito.anyString())).thenReturn("encoded_password");
        when(repo.save(Mockito.any(User.class))).thenReturn(user);
        when(mapper.mapToDTO(Mockito.any(User.class))).thenReturn(userDTO);
        when(mockedService.findByUsername(Mockito.anyString())).thenReturn(user);
        when(repo.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        assertThat(service.update(user)).isEqualTo(userDTO);
        verify(encoder, times(1)).encode(Mockito.anyString());
        verify(repo, times(1)).save(Mockito.any(User.class));
        verify(mapper, times(1)).mapToDTO(Mockito.any(User.class));
        verify(repo, times(1)).findByUsername(Mockito.anyString());
    }

    @Test
    void deleteTrueTest() {
        when(mockedService.findByUsername(Mockito.anyString())).thenReturn(user);
        when(repo.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        when(repo.existsById(Mockito.anyInt())).thenReturn(true);
        assertThat(service.delete(user)).isFalse();
        verify(repo, times(1)).existsById(Mockito.anyInt());
        verify(repo, times(1)).deleteById(Mockito.anyInt());
        verify(repo, times(1)).findByUsername(Mockito.anyString());
    }

    @Test
    void deleteFalseTest() {
        when(mockedService.findByUsername(Mockito.anyString())).thenReturn(user);
        when(repo.existsById(Mockito.anyInt())).thenReturn(false);
        when(repo.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        assertThat(service.delete(user)).isTrue();
        verify(repo, times(1)).existsById(Mockito.anyInt());
        verify(repo, times(1)).deleteById(Mockito.anyInt());
        verify(repo, times(1)).findByUsername(Mockito.anyString());
    }

}
