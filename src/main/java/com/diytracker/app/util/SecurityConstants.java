package com.diytracker.app.util;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/signup";
    public static final String SIGN_IN_URL = "/users/signin";
    public static final String ACTIVATION_URL = "/users/activate";
    public static final String PWD_RESET_URL = "/users/resetPassword";
    public static final String PWD_RECOVER_URL = "/users/recoverPassword";
}
