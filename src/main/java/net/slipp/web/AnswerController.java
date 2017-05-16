package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.Answer;
import net.slipp.domain.AnswerRepository;
import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository repository;

	@PostMapping("")
	public String create(@PathVariable Long questionId, String contents, HttpSession httpSession) {

		if (!HttpSessionUtils.isLogin(httpSession)) {
			return "redirect:/users/loginForm";
		}

		User loginUser = HttpSessionUtils.getUserFromSession(httpSession);
		Question question = questionRepository.findOne(questionId);
		Answer answer = new Answer(loginUser, question, contents);
		repository.save(answer);
		return String.format("redirect:/questions/%d", questionId);
	}

}
