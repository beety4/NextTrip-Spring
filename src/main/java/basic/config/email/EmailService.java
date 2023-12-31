package basic.config.email;

import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {
	private final JavaMailSender javaMailSender;
	private static final String SENDER = "202244016@itc.ac.kr";
	private String authKey;
	
	private MimeMessage writeMSG(String sendTo) {
		authKey = newKey();
		
		MimeMessage msg = javaMailSender.createMimeMessage();
		
		try {
			msg.setFrom(SENDER);
			msg.setRecipients(MimeMessage.RecipientType.TO, sendTo);
			msg.setSubject("[NextTrip] 이메일 인증을 완료해주세요!");
			String mailText = "";
			mailText+= "<div style='margin:20px;'>";
		    mailText+= "<h1> ShopIT 이메일 인증</h1>";
		    mailText+= "<br>";
		    mailText+= "<p>아래 코드를 복사해 입력해주세요<p>";
		    mailText+= "<br>";
		    mailText+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
		    mailText+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
		    mailText+= "<div style='font-size:130%'>";
		    mailText+= "CODE : <strong>";
		    mailText+= authKey +"</strong><div><br/> ";
		    mailText+= "</div>";
			msg.setText(mailText, "UTF-8", "html");
		} catch ( MessagingException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	public String sendMail(String sendTo) {
		MimeMessage msg = writeMSG(sendTo);
		javaMailSender.send(msg);
		return authKey;
	}
	
	
	private static String newKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();
 
        for (int i = 0; i < 5; i++) { // 인증코드 5자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤
 
            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }
}
