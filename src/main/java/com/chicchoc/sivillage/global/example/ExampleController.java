package com.chicchoc.sivillage.global.example;

import com.chicchoc.sivillage.domain.member.application.MemberService;
import com.chicchoc.sivillage.domain.member.domain.Member;
import com.chicchoc.sivillage.global.common.entity.BaseResponse;
import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/example/example/exam")
public class ExampleController {

    private final MemberService memberService;


    // 성공 & Return 객체가 필요한 경우
    @PostMapping("/email")
    public BaseResponse<Member> getUser(@Valid @RequestBody Dto dto) {

        try {
            Member user = memberService.findMemberByEmail(dto.getEmail());
            // 만약 해당 사용자가 없다면 예외 발생
            if (user == null) {
                throw new BaseException(BaseResponseStatus.NO_EXIST_USER); // Case 2 : 실패 시 반환
            }
            return new BaseResponse<>(user);  // Case 1 : 성공 시 반환
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());  // 예외 처리
        }
    }

    //기존의 BaseResponseEntity를 사용하는 방식
    @GetMapping("/{email2}")
    public ResponseEntity<BaseResponse<Member>> getUser2(@PathVariable String email) {
        try {
            Member user = memberService.findMemberByEmail(email);
            if (user == null) {
                throw new BaseException(BaseResponseStatus.NO_EXIST_USER);
            }
            return new ResponseEntity<>(new BaseResponse<>(user), HttpStatus.OK);
        } catch (BaseException e) {
            return new ResponseEntity<>(new BaseResponse<>(e.getStatus()), e.getStatus().getHttpStatusCode());
        }
    }

    // 성공 & return 객체가 필요 없는 경우 (단순 테스트용)
    @GetMapping("/test")
    public BaseResponse<String> test() {
        return new BaseResponse<>();  // 성공 시 메시지 및 상태 반환
    }
}
