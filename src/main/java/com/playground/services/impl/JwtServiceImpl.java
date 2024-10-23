package com.playground.services.impl;

import com.playground.entities.User;
import com.playground.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class JwtServiceImpl implements JwtService {

  @Value("${jwt.access.secret}")
  private String ACCESS_SECRET;

  @Value("${jwt.access.expiration}")
  private String REFRESH_SECRET;

  @Value("${jwt.refresh.secret}")
  private Long ACCESS_EXPIRATION;

  @Value("${jwt.refresh.expiration}")
  private Long REFRESH_EXPIRATION;

  @Override
  public String generateAccessToken(User user) {
    Date expiration = Date.from(Instant.now().plusSeconds(ACCESS_EXPIRATION));

    return Jwts.builder()
      .subject(user.getId())
      .claim("role", user.getRole())
      .expiration(expiration)
      .signWith(getSecretKeyFromString(ACCESS_SECRET))
      .compact();
  }

  @Override
  public String generateRefreshToken(User user) {
    Date expiration = Date.from(Instant.now().plusSeconds(REFRESH_EXPIRATION));

    return Jwts.builder()
      .subject(user.getId())
      .expiration(expiration)
      .signWith(getSecretKeyFromString(REFRESH_SECRET))
      .compact();
  }

  @Override
  public boolean validateAccessToken(String token) {
    try {
      Jwts.parser()
        .verifyWith(getSecretKeyFromString(ACCESS_SECRET))
        .build()
        .parseSignedClaims(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty");
    }
    return false;
  }

  @Override
  public boolean validateRefreshToken(String token) {
    try {
      Jwts.parser()
        .verifyWith(getSecretKeyFromString(REFRESH_SECRET))
        .build()
        .parseSignedClaims(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty");
    }
    return false;
  }

  private SecretKey getSecretKeyFromString(String secret) {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
