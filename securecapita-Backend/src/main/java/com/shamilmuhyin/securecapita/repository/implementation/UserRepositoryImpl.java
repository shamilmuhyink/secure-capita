package com.shamilmuhyin.securecapita.repository.implementation;

import com.shamilmuhyin.securecapita.domain.Role;
import com.shamilmuhyin.securecapita.domain.User;
import com.shamilmuhyin.securecapita.exception.APIException;
import com.shamilmuhyin.securecapita.repository.RoleRepository;
import com.shamilmuhyin.securecapita.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static com.shamilmuhyin.securecapita.enumeration.RoleType.ROLE_USER;
import static com.shamilmuhyin.securecapita.enumeration.VerificationType.ACCOUNT;
import static com.shamilmuhyin.securecapita.query.UserQuery.*;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;
    @Override
    public User create(User user) {
        // check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0)
            throw new APIException("Email already in use, Please enter different email");
        // save new user
        try{
            KeyHolder holder  = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            user.setId(requireNonNull(holder.getKey().longValue()));
//            user.setId(jdbc.queryForObject(SELECT_USER_BY_EMAIL, Map.of("email",user.getEmail()), Long.class));
            // add role to the user
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            //send verification URL
            String verificationURL = getVerificationURL(UUID.randomUUID().toString(), ACCOUNT.getType());
            //save URL in verification table
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, Map.of("userId", user.getId(), "url", verificationURL));
            //send email with verification URL
//            emailService.sendVerificationURL(user.getFirstName(), user.getEmail(), verificationURL, ACCOUNT);
            //return newly created user
            user.setEnabled(false);
            user.setIsNotLocked(true);
            return user;
            //if any error throw exception with proper message
        }
        catch(Exception exception){
            throw new APIException("An error occurred, Please try again.");
        }
    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email", email), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationURL(String key, String type) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/"+ type +"/"+ key).toUriString();
    }

}
