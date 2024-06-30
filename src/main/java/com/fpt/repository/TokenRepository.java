package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpt.entity.Token;
import com.fpt.entity.User;
import com.fpt.enums.TokenType;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	boolean existsByToken(String token);

	Token findByTokenAndTokenType(String token, TokenType tokenType);

	Token findByToken(String token);

	void deleteByUserAndTokenType(User user, TokenType tokenType);

	Token findByUserId(Long id);
}
