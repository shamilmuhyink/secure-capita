package com.shamilmuhyin.securecapita.service;

import com.shamilmuhyin.securecapita.domain.User;
import com.shamilmuhyin.securecapita.dto.UserDTO;

public interface UserService {
    UserDTO createUser(User user);
}
