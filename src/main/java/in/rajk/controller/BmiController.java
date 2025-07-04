package in.rajk.controller;

import in.rajk.model.BmiRecord;
import in.rajk.model.BmiRequest;
import in.rajk.model.Member;
import in.rajk.repository.BmiRecordRepository;
import in.rajk.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class BmiController {

    @Autowired
    private BmiRecordRepository bmiRecordRepository;

    @Autowired
    private MemberRepository memberRepository;


    @GetMapping("/bmi")
    public String showBmiForm(Model model, Principal principal) {
        Optional<Member> optionalMember = memberRepository.findByEmail(principal.getName());

        model.addAttribute("bmiRequest", new BmiRequest());

        // Always allow access
        return "bmi";
    }

    @PostMapping("/bmi")
    public String calculateBmi(@ModelAttribute BmiRequest bmiRequest, Model model, Principal principal) {
        Optional<Member> optionalMember = memberRepository.findByEmail(principal.getName());

        double heightM = bmiRequest.getHeight() / 100.0;
        double bmi = bmiRequest.getWeight() / (heightM * heightM);
        bmi = Math.round(bmi * 10.0) / 10.0;

        String category = getCategory(bmi);
        double bmiPrime = bmi / 25.0;
        double ponderalIndex = bmiRequest.getWeight() / Math.pow(heightM, 3);
        double minWeight = 18.5 * heightM * heightM;
        double maxWeight = 25.0 * heightM * heightM;

        // Save BMI only if user is a registered Member (not admin)
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            BmiRecord record = new BmiRecord();
            record.setAge(bmiRequest.getAge());
            record.setGender(bmiRequest.getGender());
            record.setHeight(bmiRequest.getHeight());
            record.setWeight(bmiRequest.getWeight());
            record.setBmi(bmi);
            record.setCategory(category);
            record.setDate(LocalDate.now());
            record.setMember(member);
            bmiRecordRepository.save(record);
        }

        model.addAttribute("bmi", bmi);
        model.addAttribute("category", category);
        model.addAttribute("bmiPrime", Math.round(bmiPrime * 100.0) / 100.0);
        model.addAttribute("ponderalIndex", Math.round(ponderalIndex * 10.0) / 10.0);
        model.addAttribute("healthyRange", String.format("%.1f kg - %.1f kg", minWeight, maxWeight));
        model.addAttribute("statusMessage", getMessage(category));
        model.addAttribute("bmiRequest", bmiRequest);

        return "bmi";
    }

    //==============================================

    @GetMapping("/bmi/history")
    public String showBmiHistory(Model model, Principal principal) {
        Optional<Member> optionalMember = memberRepository.findByEmail(principal.getName());

        if (optionalMember.isEmpty()) {
            model.addAttribute("error", "BMI history is only available to gym members.");
            return "403"; // Reuse the same 403.html page
        }

        Member member = optionalMember.get();
        List<BmiRecord> history = bmiRecordRepository.findByMemberIdOrderByDateDesc(member.getId());
        model.addAttribute("bmiHistory", history);
        return "bmi_history";
    }

    private String getCategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        else if (bmi < 25) return "Normal";
        else if (bmi < 30) return "Overweight";
        else return "Obese";
    }

    private String getMessage(String category) {
        return switch (category) {
            case "Underweight" -> "You may need to gain some weight.";
            case "Normal" -> "You are in a healthy range!";
            case "Overweight" -> "Consider exercising more regularly.";
            case "Obese" -> "You may want to consult a health professional.";
            default -> "";
        };
    }
}
