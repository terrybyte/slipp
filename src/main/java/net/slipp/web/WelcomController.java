package net.slipp.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.slipp.domain.User;

@Controller
public class WelcomController {
	
	@GetMapping("/helloworld")
	public String welcome(User user, Model model) {
		System.out.println("user = " + user.toString());
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("email", user.getEmail());
		return "welcome";
	}
	
}
