package com.ll.blog.domain.Email.service;

import com.ll.blog.domain.global.redis.service.RedisService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.sender-email}")
    private String senderEmail; //보내는사람 이메일

    //인증번호 6자리 생성
    public int randomValueGeneration() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder randomValue = new StringBuilder();
        for (int i = 1; i <= 6; i++) {
            randomValue.append(secureRandom.nextInt(9) + 1) ; // 0 ~ 9 까지의 임의의 숫자를 한개씩 6번 누적
        }
        return Integer.parseInt(randomValue.toString());
    }

    //mail을 어디서 보내는지, 어디로 보내는지 , 인증 번호를 html 형식으로 어떻게 보내는지 작성합니다.
    public String joinEmail(String email) {
        int randomValue = randomValueGeneration();
        String title = "회원 가입 인증 이메일 입니다."; // 이메일 제목
        String content = htmlContent(randomValue);
        mailSend(senderEmail, email, title, content); //메일을 전송 (보내는사람 , 받는사람 , 제목 , 내용)
        redisService.setDataExpire(String.valueOf(randomValue), email , 60 * 5L); //인증번호 (key) , 이메일(value) 5분동안 redis 에 저장
        return Integer.toString(randomValue); //인증번호 6자리
    }

    //이메일을 전송합니다.
    public void mailSend(String setFrom, String toMail, String title, String content) {
        MimeMessage message = javaMailSender.createMimeMessage(); //JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8"); //이메일 메시지와 관련된 설정을 수행합니다.
            // true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom); //이메일의 발신자 주소 설정
            helper.setTo(toMail); //이메일의 수신자 주소 설정
            helper.setSubject(title); //이메일의 제목을 설정
            helper.setText(content,true); //이메일의 내용 설정 두 번째 매개 변수에 true를 설정하여 html 설정으로한다.
            javaMailSender.send(message);
        } catch (MessagingException e) { //이메일 서버에 연결할 수 없거나, 잘못된 이메일 주소를 사용하거나, 인증 오류가 발생하는 등 오류
            // 이러한 경우 MessagingException이 발생
            e.printStackTrace(); //e.printStackTrace()는 예외를 기본 오류 스트림에 출력하는 메서드
        }
    }

    public String htmlContent (int randomValue) {
        Context context = new Context();
        context.setVariable("authCode" , randomValue);
        return templateEngine.process("domain/member/mail" , context);
    }

    //인증번호 일치하는지 여부 , 본인이메일 일치하는지 여부
    public Boolean verificationCodeCheck(String email, String verificationCode) {
        String value = redisService.getData(verificationCode); //key에 해당하는 value값을 반환.
        if (value == null) {
            return false;
        }
       return value.equals(email); //value 값 일치여부
    }
}
