package com.chicchoc.sivillage.global.auth.application;

import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.global.auth.dto.SignUpRequestDto;
import com.chicchoc.sivillage.global.auth.infrastructure.AuthRepository;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final AuthRepository authRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void signUp(SignUpRequestDto signUpRequestDto) {


    String uuid = UUID.randomUUID().toString();
    String password = passwordEncoder.encode(signUpRequestDto.getPassword());
    Member member = signUpRequestDto.toEntity(uuid, password);

    // 중복검사
    if(authRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
      throw new IllegalArgumentException("이미 가입된 회원입니다.");
    }
    try {
      authRepository.save(member);
    } catch(Exception e) {
      log.error("Exception: {}", e);
      throw new IllegalArgumentException("회원가입에 실패하였습니다.");
    }
  }

}
