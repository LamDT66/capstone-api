package com.fpt.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.fpt.dto.TokenDTO;
import com.fpt.entity.Token;
import com.fpt.entity.User;
import com.fpt.enums.TokenType;
import com.fpt.repository.TokenRepository;
import com.fpt.service.TokenService;
import com.fpt.service.UserService;
import com.fpt.utils.Constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

	@Value("${token.registration.time.expiration}")
    private long REGISTRATION_EXPIRATION_TIME;
	
	@Value("${token.reset-password.time.expiration}")
    private long RESET_PASSWORD_EXPIRATION_TIME;
	
	@Value("${token.jwt.time.expiration}")
    private long JWT_EXPIRATION_TIME;
	
	@Value("${token.jwt.secret}")
	private String JWT_SECRET;
	
	@Value("${token.jwt.header.authorization}")
	private String JWT_TOKEN_AUTHORIZATION;
	
	@Value("${token.jwt.prefix}")
	private String JWT_TOKEN_PREFIX;
	
	@Value("${refreshtoken.jwt.time.expiration}")
	private long JWT_REFRESH_EXPIRATION_TIME;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenRepository repository;

	@Override
	public boolean isTokenExists(String token) {
		return repository.existsByToken(token);
	}
	
	@Override
	public boolean isRegistrationTokenValid(String token) {
		Token tokenEntity = repository.findByTokenAndTokenType(token, TokenType.REGISTRATION);

		if (tokenEntity == null) return false;

		if (tokenEntity.getExpiredDate().isBefore(LocalDateTime.now())) {
			repository.delete(tokenEntity);
			return false;
		}

		return true;
	}

	@Override
	public Token createRegistrationToken(User user) {
		User currentUser = userService.getCurrentLoginUser();
		User creator = currentUser != null ? currentUser : user;
		
		// delete old token
		repository.deleteByUserAndTokenType(user, TokenType.REGISTRATION);
		
		// create new token
		Token newToken = Token.builder()
				.token(UUID.randomUUID().toString())
				.tokenType(TokenType.REGISTRATION)
				.user(user)
				.expiredDate(LocalDateTime.now().plus(REGISTRATION_EXPIRATION_TIME, ChronoUnit.MILLIS))
				.createdAt(LocalDateTime.now())
				.creator(creator)
				.modifier(creator)
				.modifiedAt(LocalDateTime.now())
				.build();
		return repository.save(newToken);
	}
	
	@Override
	public Token createResetPasswordOTP(User user) {
		User currentUser = userService.getCurrentLoginUser();
		User creator = currentUser != null ? currentUser : user;
		
		// delete old token
		repository.deleteByUserAndTokenType(user, TokenType.RESET_PASSWORD);
		
		// create new token
		Token newToken = Token.builder()
				.token(generateOTP())
				.tokenType(TokenType.RESET_PASSWORD)
				.user(user)
				.expiredDate(LocalDateTime.now().plus(RESET_PASSWORD_EXPIRATION_TIME, ChronoUnit.MILLIS))
				.createdAt(LocalDateTime.now())
				.creator(creator)
				.modifier(creator)
				.modifiedAt(LocalDateTime.now())
				.build();
		return repository.save(newToken);
	}
	
	private String generateOTP() {
		Random random = new Random();
		StringBuilder otp = new StringBuilder();
		for (int i = 0; i < Constants.OTP_LENGTH; i++) {
			otp.append(random.nextInt(10));
		}
		return otp.toString();
	}

	@Override
	public boolean isResetPasswordOtpValid(String otp) {
		Token tokenEntity = repository.findByTokenAndTokenType(otp, TokenType.RESET_PASSWORD);

		if (tokenEntity == null) return false;

		if (tokenEntity.getExpiredDate().isBefore(LocalDateTime.now())) {
			repository.delete(tokenEntity);
			return false;
		}

		return true;
	}
	
    @Override
    public String createJWTToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    
    public Authentication parseTokenToUserInformation(HttpServletRequest request) {
        String token = request.getHeader(JWT_TOKEN_AUTHORIZATION);
        
        if (token == null) {
        	return null;
        }
        
        // parse the token
        try {
        String email = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token.replace(JWT_TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        
        User user = userService.getUserByEmail(email);
        
        // return authentication
        return email != null ?
                new UsernamePasswordAuthenticationToken(
                		user.getEmail(), 
                		null, 
                		AuthorityUtils.createAuthorityList(user.getRole().getSettingName())) :
                null;
        } catch (Exception e) {
			return null;
		}
    }

	@Override
	public Token createJWTRefreshToken(User user) {
		User currentUser = userService.getCurrentLoginUser();
		User creator = currentUser != null ? currentUser : user;
		
		// delete old token
		repository.deleteByUserAndTokenType(user, TokenType.REFRESH);
		
		// create new token
		Token refreshToken = Token.builder()
				.token(UUID.randomUUID().toString())
				.tokenType(TokenType.REFRESH)
				.user(user)
				.expiredDate(LocalDateTime.now().plus(JWT_REFRESH_EXPIRATION_TIME, ChronoUnit.MILLIS))
				.creator(creator)
				.createdAt(LocalDateTime.now())
				.modifier(creator)
				.modifiedAt(LocalDateTime.now())
				.build();
		return repository.save(refreshToken);
	}
	
	@Override
	public boolean isJWTRefreshTokenValid(String refreshToken) {
		Token tokenEntity = repository.findByTokenAndTokenType(refreshToken, TokenType.REFRESH);

		if (tokenEntity == null) return false;

		if (tokenEntity.getExpiredDate().isBefore(LocalDateTime.now())) {
			repository.delete(tokenEntity);
			return false;
		}

		return true;
	}

	@Override
	@Transactional
	public TokenDTO exchangeNewToken(String refreshToken) {
		// find old refresh token
		Token oldRefreshToken = repository.findByTokenAndTokenType(refreshToken, TokenType.REFRESH);

		// delete old refresh token
		repository.deleteByUserAndTokenType(oldRefreshToken.getUser(), TokenType.REFRESH);

		// create new refresh token
		Token newRefreshToken = createJWTRefreshToken(oldRefreshToken.getUser());

		// create new jwt token
		String newToken = createJWTToken(oldRefreshToken.getUser().getEmail());

		return new TokenDTO(newToken, newRefreshToken.getToken());
	}
}
