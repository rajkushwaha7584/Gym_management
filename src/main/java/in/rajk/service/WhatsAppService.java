//package in.rajk.service;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class WhatsAppService {
//
//    @Value("${whatsapp.token}")
//    private String accessToken;
//
//    @Value("${whatsapp.phone.number.id}")
//    private String phoneNumberId;
//
//    private String whatsappApiUrl;
//
//    @PostConstruct
//    public void init() {
//        this.whatsappApiUrl = "https://graph.facebook.com/v19.0/" + phoneNumberId + "/messages";
//    }
//
//    public void sendMessage(String toPhoneNumber, String messageText) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(accessToken);
//
//        Map<String, Object> payload = new HashMap<>();
//        payload.put("messaging_product", "whatsapp");
//        payload.put("to", toPhoneNumber);
//        payload.put("type", "text");
//
//        Map<String, String> text = new HashMap<>();
//        text.put("body", messageText);
//        payload.put("text", text);
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.postForEntity(whatsappApiUrl, request, String.class);
//            if (response.getStatusCode().is2xxSuccessful()) {
//                System.out.println("✅ WhatsApp sent to " + toPhoneNumber);
//            } else {
//                System.err.println("❌ WhatsApp API error (" + response.getStatusCode() + "): " + response.getBody());
//            }
//        } catch (Exception ex) {
//            System.err.println("❗ Exception while sending WhatsApp to " + toPhoneNumber + ": " + ex.getMessage());
//        }
//    }
//}
package in.rajk.service;

import in.rajk.model.Member;
import in.rajk.model.MessageLog;
import in.rajk.repository.MemberRepository;
import in.rajk.repository.MessageLogRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsAppService {

    @Value("${whatsapp.token}")
    private String accessToken;

    @Value("${whatsapp.phone.number.id}")
    private String phoneNumberId;

    private String whatsappApiUrl;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MessageLogRepository messageLogRepository;

    @PostConstruct
    public void init() {
        this.whatsappApiUrl = "https://graph.facebook.com/v19.0/" + phoneNumberId + "/messages";
    }

    public void sendMessage(String toPhoneNumber, String messageText) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", toPhoneNumber);
        payload.put("type", "text");

        Map<String, String> text = new HashMap<>();
        text.put("body", messageText);
        payload.put("text", text);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        // 🧠 Try to fetch Member by phone (remove +91 if needed)
        String rawPhone = toPhoneNumber.replace("+91", "").trim();
        Member member = memberRepository.findByPhone(rawPhone);

        // 📝 Prepare log
        MessageLog log = new MessageLog();
        log.setPhone(toPhoneNumber);
        log.setMemberName(member != null ? member.getFullName() : "Unknown");
        log.setEmail(member != null ? member.getEmail() : "Unknown");
        log.setMessageContent(messageText);
        log.setTimestamp(LocalDateTime.now());

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(whatsappApiUrl, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.setSuccess(true);
                System.out.println("✅ WhatsApp message sent to " + toPhoneNumber);
            } else {
                log.setSuccess(false);
                log.setErrorMessage("API Response: " + response.getStatusCode() + " - " + response.getBody());
                System.err.println("❌ API error sending to " + toPhoneNumber + ": " + response.getBody());
            }
        } catch (Exception ex) {
            log.setSuccess(false);
            log.setErrorMessage("Exception: " + ex.getMessage());
            System.err.println("❗ Exception sending WhatsApp to " + toPhoneNumber + ": " + ex.getMessage());
        }

        messageLogRepository.save(log);
    }
}
