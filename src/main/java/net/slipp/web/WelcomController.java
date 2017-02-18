package net.slipp.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.slipp.domain.Repo;
import net.slipp.domain.User;

@Controller
public class WelcomController {
	
	@GetMapping("/welcome")
	public String welcome(User user, Model model) {
		System.out.println("user = " + user.toString());
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("email", user.getEmail());
		return "welcome";
	}
	
	@GetMapping("/helloworld")
	public String helloworld(Model model) {
//		List<Repo> repos = new ArrayList<Repo>();
		List<Repo> repos = Arrays.asList(
				new Repo("resque"),
				new Repo("hub"),
				new Repo("ribs")
				);
//		{
//			  "name": "Willy",
//			  "wrapped": function() {
//			    return function(text, render) {
//			      return "<b>" + render(text) + "</b>"
//			    }
//			  }
//			}
		model.addAttribute("name", "Willy");
		model.addAttribute("wrapped", "function() {"+
			    "return function(text, render) {" +
			      "return \"<b>\" + render(text) + \"</b>\""+
			    "}"+
			  "}"
			    );
		
//		model.addAttribute("repos", repos);
		return "helloworld";
	}
	
}
