package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository repository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLogin(session)) {
			return "redirect:/users/loginForm";
		}
		return "/qna/form";
	}
	
	@PostMapping("")
	public String regist(String title, String contents, HttpSession httpSession){
		if (!HttpSessionUtils.isLogin(httpSession)) {
			return "redirect:/users/loginForm";
		}
		
		User user = HttpSessionUtils.getUserFromSession(httpSession);
		
//		Question question = new Question(user.getUserID(), title, contents);
		Question question = new Question(user, title, contents);
		repository.save(question);
		
		return "redirect:/";
	}
	
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", repository.findOne(id));
		return "/qna/show";
	}
}
