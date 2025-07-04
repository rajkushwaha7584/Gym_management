package in.rajk.controller;

import in.rajk.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members/search")
    public String searchMembers(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("members", memberRepository.searchByNameOrPhone(keyword));
        model.addAttribute("keyword", keyword);
        return "members";
    }
}
