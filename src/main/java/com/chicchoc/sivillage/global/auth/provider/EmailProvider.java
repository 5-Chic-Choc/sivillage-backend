package com.chicchoc.sivillage.global.auth.provider;

import com.chicchoc.sivillage.global.common.entity.BaseResponseStatus;
import com.chicchoc.sivillage.global.error.exception.BaseException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmailProvider {

    private final JavaMailSender javaMailSender;
    private static final String SUBJECT = "[SIVillage] 회원가입 인증 메일입니다.";

    public boolean sendVerificationEmail(String email, String verificationCode) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            String htmlContent = getVerificationMessage(verificationCode);

            messageHelper.setTo(email);
            messageHelper.setSubject(SUBJECT);
            messageHelper.setText(htmlContent, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_SEND_EMAIL);
        }

        return true;
    }

    private String getVerificationMessage(String verificationCode) {
        String verificationMessage = """
                <center>
                    <table cellspacing='0' cellpadding='0' border='0' width='750' 
                        style='border-collapse:collapse;border-spacing:0;margin:0 auto;max-width:750px'>
                        <tbody>
                            <tr>
                                <td align='center'>
                                    <div style='border-bottom:4px solid #131922'>
                                        <h1 style='margin:0'>
                                            <a href='#' style='display:block;text-decoration:none'>
                                                <img src='https://lh5.googleusercontent.com/proxy/ZfSLRg-MtiTUByZnWBAZysv5QepE8dqM2RbKyL2vJSZ5pHurcA5Wm2D2Zjd5r3ynVcM_91Prw5sKH57Q1db_FGiI8p5spwCSO7PX5Ov_Y8R5Ce6fL6WVJrxRvyEKplm1kCbJY-M-roNyzQ6m5ggREzPoZx0CFYhm8CIzOjPggAI_HqwGyQN94y6ja-9MUY57hJY6woZdc2ouberpHWuopSRRNv7iGH4n-pOJwWII-yYpdH7K9IRyDp1hUNQoRpzoEWjCiQx0aBloA8tvIiA_09DyZsy2DX41BAyTJh5WBXrbTz_1bVdDcu5AXtxA7MqYTpgSN2mC9cuMWd2urcZIMuAu1_EO9ifi8Hw5BiW2YROZn6jbApHdZBk-Worz5Mf55ECbeGIj0oEEb4w2picwUZ39HTGRW46GKPLXpKuhpETNRFaHdX4O86fdPMLrAIihEfU0lZah7g'
                                                     alt='S.I.VILLAGE THE REAL LUXURY' style='border:0;vertical-align:top'/>
                                            </a>
                                        </h1>
                                    </div>
                                    <table cellspacing='0' cellpadding='0' border='0' width='100%' 
                                        style='border-bottom:16px solid #f0f0f0;border-collapse:collapse;border-spacing:0'>
                                        <tbody>
                                            <tr><td height='108'></td></tr>
                                            <tr>
                                                <td style='padding:0 48px'>
                                                    <p style='text-align: center; color:#131922;font-family:"Apple SD Gothic Neo","Malgun Gothic",sans-serif;font-size:56px;letter-spacing:-0.1px;line-height:68px;margin:0'>
                                                        고객님, <strong style='color:#d99c63;font-weight:normal'>인증번호가</strong><br/> 발급되었습니다.
                                                    </p>
                                                    <table cellspacing='0' cellpadding='0' border='0' width='100%' 
                                                        style='border-collapse:collapse;border-spacing:0'>
                                                        <tbody>
                                                            <tr><td height='34'></td></tr>
                                                            <tr>
                                                                <td style='text-align: center; color:#000; font-family:"Apple SD Gothic Neo","Malgun Gothic",sans-serif;font-size:28px;letter-spacing:-0.1px;line-height:44px'>
                                                                    안녕하세요! 아래 인증번호를 입력해 주세요.<br/><br/>
                                                                    <div style='color:#000;font-family:"Apple SD Gothic Neo","Malgun Gothic",sans-serif;font-size:28px;font-weight:normal;letter-spacing:-0.1px;line-height:40px;padding:16px 8px;padding-bottom:70px;padding-left:8px;padding-right:20px;padding-top:70px;'>
                                                                        인증번호: """ + verificationCode + """
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr><td height='108'></td></tr>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </center>
                """;

        return verificationMessage;
    }
}
