package com.shamilmuhyin.securecapita.service.implementation;

import com.shamilmuhyin.securecapita.domain.User;
import com.shamilmuhyin.securecapita.dto.UserDTO;
import com.shamilmuhyin.securecapita.dtomapper.UserDTOMapper;
import com.shamilmuhyin.securecapita.repository.UserRepository;
import com.shamilmuhyin.securecapita.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository<User> userRepository;
    @Override
    public UserDTO createUser(User user) {
        return  UserDTOMapper.fromUser(userRepository.create(user));
    }
}
