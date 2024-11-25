package com.TripLikeMovie.backend.domain.member.service.email;


import com.TripLikeMovie.backend.domain.member.domain.Member;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.SendVerificationRequest;
import com.TripLikeMovie.backend.domain.member.presentation.dto.request.VerifyEmailCodeRequest;
import com.TripLikeMovie.backend.domain.post.domain.Post;
import com.TripLikeMovie.backend.global.error.exception.email.EmailNotVerifiedException;
import com.TripLikeMovie.backend.global.error.exception.email.InvalidVerificationCodeException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private static final int CODE_LENGTH = 6;
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final Map<String, String> verificationCods = new ConcurrentHashMap<>();
    private final Set<String> verifiedEmails = ConcurrentHashMap.newKeySet();

    @Override
    public void sendSignUpVerificationCode(SendVerificationRequest sendVerificationRequest) {
        String code = generateVerificationCode();
        verificationCods.put(sendVerificationRequest.getEmail(), code);

        String subject = "회원가입 인증 코드";
        String content = buildHtmlContent(code);

        sendHtmlEmail(sendVerificationRequest.getEmail(), subject, content);
    }

    private String buildHtmlContent(String code) {
        return String.format("""
             <html>
                 <body style="font-family: Arial, sans-serif; line-height: 1.6; margin: 0; padding: 0; background-color: #0169ff;">
                     <div style="max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); overflow: hidden;">
                         <div style="padding: 20px; background: #0169ff; color: white; text-align: center;">
                             <h1 style="margin: 0; font-size: 24px;">TripLikeMovie</h1>
                             <p style="margin: 5px 0; font-size: 16px;">영화같은 여행을 떠나보세요</p>
                         </div>
                         <div style="padding: 30px;">
                             <h2 style="color: #333; font-size: 20px; margin-bottom: 20px;">이메일 인증 안내</h2>
                             <p style="font-size: 16px; color: #555; margin-bottom: 15px;">
                                 아래 인증 코드를 입력해주세요 인증 코드는 10분간 유효합니다.
                             </p>
                             <div style="text-align: center; margin: 20px 0;">
                                 <span style="display: inline-block; font-size: 24px; font-weight: bold; color: #0169ff; padding: 10px 20px; background: #f1f1f1; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);">
                                     %s
                                 </span>
                             </div>
                             <p style="font-size: 14px; color: #888; text-align: center; margin-top: 20px;">
                                 본 이메일은 자동 발신되었으며, 회신하지 마세요. 인증 코드는 타인과 공유하지 마십시오.
                             </p>
                         </div>
                     </div>
                     <footer style="text-align: center; font-size: 12px; color: #aaa; margin-top: 10px;">
                         &copy; 2024 TripLikeMovie. All Rights Reserved.
                     </footer>
                 </body>
             </html>
            """, code);
    }


    private void sendHtmlEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true로 설정해야 HTML로 전송됨

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송에 실패했습니다.", e);
        }
    }

    @Override
    public void verifyEmailCode(VerifyEmailCodeRequest verifyEmailCodeRequest) {
        String storedCode = verificationCods.get(verifyEmailCodeRequest.getEmail());
        boolean isVerified =
            storedCode != null && storedCode.equals(verifyEmailCodeRequest.getCode());

        if (!isVerified) {
            throw InvalidVerificationCodeException.EXCEPTION;
        }
        verificationCods.remove(verifyEmailCodeRequest.getEmail());
        verifiedEmails.add(verifyEmailCodeRequest.getEmail());
    }

    @Override
    public void isEmailVerified(String email) {
        if (!verifiedEmails.contains(email)) {
            throw EmailNotVerifiedException.EXCEPTION;
        }
    }

    @Override
    public void deleteEmailVerificationCode(String email) {
        verifiedEmails.remove(email);
    }

    @Override
    public void sendChangePasswordVerificationCode(String email) {
        String code = generateVerificationCode();
        verificationCods.put(email, code);

        String subject = "비밀번호 찾기 인증 코드";
        String content = buildHtmlContent(code);

        sendHtmlEmail(email, subject, content);
    }

    @Override
    public void reportPost(Member member, Post post, String reason) {
        sendReportNotificationToAdmin(member, post, reason);
    }

    private void sendReportNotificationToAdmin(Member member, Post post, String reason) {
        String subject = "새로운 게시물 신고 알림";
        String content = buildReportEmailContentForAdmin(member, post, reason);
        sendHtmlEmail("triplikemovie@gmail.com", subject, content);
    }

    private String buildReportEmailContentForAdmin(Member member, Post post, String reason) {
        String reportUrl = "http://localhost:8080/api/v1/admin/post/" + post.getId();

        StringBuilder emailContent = new StringBuilder();
        emailContent.append("<html>")
            .append("<body style=\"font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333;\">")
            .append("<div style=\"max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); overflow: hidden;\">")
            .append("<div style=\"padding: 20px; background: #FF5722; color: white; text-align: center;\">")
            .append("<h1 style=\"margin: 0; font-size: 24px;\">TripLikeMovie 신고 알림</h1>")
            .append("<p style=\"margin: 5px 0; font-size: 16px;\">새로운 게시물 신고가 접수되었습니다.</p>")
            .append("</div>")
            .append("<div style=\"padding: 30px;\">")
            .append("<h2 style=\"color: #333; font-size: 20px; margin-bottom: 20px;\">신고 세부 사항</h2>")
            .append("<p style=\"font-size: 16px; color: #555; margin-bottom: 10px;\">")
            .append("<strong>신고자:</strong> ").append(member.getNickname()).append("<br>")
            .append("<strong>신고된 게시물 링크:</strong> <a href=\"").append(reportUrl).append("\" style=\"color: #0169ff; text-decoration: none;\">").append(reportUrl).append("</a><br>")
            .append("<strong>신고 사유:</strong> ").append(reason).append("<br>")
            .append("<strong>신고 시각:</strong> ").append(LocalDateTime.now()).append("<br>")
            .append("</p>")
            .append("<p style=\"font-size: 14px; color: #888; text-align: center; margin-top: 20px;\">")
            .append("신고된 게시물에 대해 신속하게 조치를 취해주세요.")
            .append("</p>")
            .append("</div>")
            .append("</div>")
            .append("<footer style=\"text-align: center; font-size: 12px; color: #aaa; margin-top: 10px;\">")
            .append("&copy; 2024 TripLikeMovie. All Rights Reserved.")
            .append("</footer>")
            .append("</body>")
            .append("</html>");

        return emailContent.toString();
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
