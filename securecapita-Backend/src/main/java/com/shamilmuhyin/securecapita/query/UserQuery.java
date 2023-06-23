package com.shamilmuhyin.securecapita.query;

public class UserQuery {

    public static final String INSERT_USER_QUERY = "INSERT INTO Users(first_name, last_name, email, password) Values(:firstName, :lastName, :email, :password)";
    public static final String COUNT_USER_EMAIL_QUERY = "SELECT COUNT(*) FROM Users WHERE email = :email";
    public static final String INSERT_ACCOUNT_VERIFICATION_URL_QUERY = "INSERT INTO AccountVerification(user_id, url) Values(:userId, :url)";
    public static final String SELECT_USER_BY_EMAIL = "SELECT id FROM Users WHERE email = :email";
}
