package com.playground.services;

import com.playground.entities.User;

public interface JwtService {
  String generateAccessToken(User user);

  String generateRefreshToken(User user);

  boolean validateAccessToken(String token);

  boolean validateRefreshToken(String token);
}
