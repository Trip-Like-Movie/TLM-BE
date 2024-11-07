package com.TripLikeMovie.backend.domain.member.service.email;


import com.TripLikeMovie.backend.domain.member.presentation.dto.request.MemberSignUpRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyEmailCodeRequest;
import com.TripLikeMovie.backend.global.error.exception.email.EmailNotVerifiedException;
import com.TripLikeMovie.backend.global.error.exception.email.InvalidVerificationCodeException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String formEmail;

    private static final int CODE_LENGTH = 6;
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final Map<String, String> verificationCods = new ConcurrentHashMap<>();
    private final Set<String> verifiedEmails = ConcurrentHashMap.newKeySet();

    @Override
    public void sendVerificationCode(SendVerificationRequest sendVerificationRequest) {

        String code = generateVerificationCode();
        verificationCods.put(sendVerificationRequest.getEmail(), code);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(sendVerificationRequest.getEmail());
        message.setFrom(formEmail);
        message.setSubject("회원가입 인증 코드");
        message.setText("인증 코드는 " + code + "입니다.");

        mailSender.send(message);
    }

    @Override
    public void verifyEmailCode(VerifyEmailCodeRequest verifyEmailCodeRequest) {
        String storedCode = verificationCods.get(verifyEmailCodeRequest.getEmail());
        boolean isVerified = storedCode != null && storedCode.equals(verifyEmailCodeRequest.getCode());

        if (!isVerified) {
            throw InvalidVerificationCodeException.EXCEPTION;
        }
        verificationCods.remove(verifyEmailCodeRequest.getEmail());
        verifiedEmails.add(verifyEmailCodeRequest.getEmail());
    }

    @Override
    public void isEmailVerified(MemberSignUpRequest signUpRequest) {
        if (!verifiedEmails.contains(signUpRequest.getEmail())) {
            throw EmailNotVerifiedException.EXCEPTION;
        }
    }

    @Override
    public void deleteEmailVerificationCode(String email) {
        verifiedEmails.remove(email);
    }

    private String generateVerificationCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = SECURE_RANDOM.nextInt(NUMERIC_CHARACTERS.length());
            code.append(NUMERIC_CHARACTERS.charAt(randomIndex));
        }
        return code.toString();
    }

}
