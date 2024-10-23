package com.playground.common.utils;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.AntPathMatcher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppRoutes {

  private static final AntPathMatcher pathMatcher = new AntPathMatcher();

  public static final String API_V1_PREFIX = "/api/v1";

  // General routes (won't be used in controllers)
  public static final String FAVICON = "/favicon.ico";
  public static final String SWAGGER_UI = "/swagger-ui/**";
  public static final String API_DOCS = "/api-docs";

  public static final String SWAGGER_JSON = "/docs/**";
  public static final String LOGIN = "/login";
  public static final String REGISTER = "/register";
  public static final String LOGOUT = "/logout";
  public static final String PROFILE = "/profile";

  public static final String SWAGGER_JSON_WITH_PREFIX = API_V1_PREFIX + SWAGGER_JSON;
  public static final String LOGIN_WITH_PREFIX = API_V1_PREFIX + LOGIN;
  public static final String REGISTER_WITH_PREFIX = API_V1_PREFIX + REGISTER;
  public static final String LOGOUT_WITH_PREFIX = API_V1_PREFIX + LOGOUT;
  public static final String PROFILE_WITH_PREFIX = API_V1_PREFIX + PROFILE;

  public static final List<String> publicRoutes = List.of(
    LOGIN_WITH_PREFIX,
    REGISTER_WITH_PREFIX,
    FAVICON,
    SWAGGER_JSON_WITH_PREFIX,
    SWAGGER_UI,
    API_DOCS
  );

  public static final List<String> privateRoutes = List.of(PROFILE_WITH_PREFIX, LOGOUT_WITH_PREFIX);

  public static boolean isPublicRoute(String path) {
    return publicRoutes.stream().anyMatch(route -> pathMatcher.match(route, path));
  }

  public static boolean isPrivateRoute(String path) {
    return privateRoutes.stream().anyMatch(route -> pathMatcher.match(route, path));
  }
}
