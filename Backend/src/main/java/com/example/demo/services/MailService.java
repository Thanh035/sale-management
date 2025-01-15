package com.example.demo.services;

import com.example.demo.domain.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;

    private final HttpServletRequest request;

    @Async
    public void sendEmail(String to, String subject, String content) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}", to, subject,
                content);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            /*
             * FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
             * helper.addAttachment("Invoice", file);
             */

            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        String resetLink = "http://localhost:4200/login/forgot_password/finish?key=" + user.getResetKey();
        sendEmail(user.getEmail(),
                "Lời mời tham gia vào Teyandee shop",
                "Bạn nhận được lời mời tham gia vào Teyande shop" +
                        "\n" + user.getFullname() + " mời bạn trở thành thành viên của Teyande shop. Nếu không muốn làm thành viên của Teyande shop bạn có thể bỏ qua email này." +
                        "\nChấp nhận lời mời: " + resetLink + "" +
                        "\nEmail này sẽ hết hiệu lực sau 24 giờ.");
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        String content = "Activation key: " + user.getActivationKey();
        String subject = "Automated Message: Account Activation Confirmation / Tin nhắn tự động: Xác nhận kích hoạt Tài khoản";
        sendEmail(user.getEmail(), subject, content);
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        String resetLink = "http://localhost:4200/login/forgot_password/finish?key=" + user.getResetKey();
        sendEmail(user.getEmail(),
                "Khôi phục mật khẩu",
                "Bạn vừa yêu cầu khôi phục mật khẩu tài khoản Teyandee Shop?" +
                        "\nHãy thiết lập mật khẩu mới cho tài khoản của bạn. Nếu bạn không yêu cầu điều này, hãy xoá email này để bảo vệ tài khoản của bạn." +
                        "\nKhôi phục mật khẩu: " + resetLink +
                        "\nEmail này sẽ hết hiệu lực sau 24 giờ.");
    }

}
