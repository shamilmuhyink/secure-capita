package com.shamilmuhyin.securecapita.repository;

import com.shamilmuhyin.securecapita.domain.Role;
import com.shamilmuhyin.securecapita.domain.User;

import java.util.Collection;

public interface RoleRepository<T extends Role>{
    // Basic CRUD Operations
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);

    // Complex operations
    void addRoleToUser(Long userId, String roleName);
    Role getRoleByUserId(Long userId);
    Role getRoleByUserEmail(String email);
    void updateRoleName(Long userId, String roleName);
}
