package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.slipp.domain.Question;
import net.slipp.domain.QuestionRepository;
import net.slipp.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository repository;
	
	//등록 폼
	@GetMapping("/form")
	public String form(HttpSession session) {
		if (!HttpSessionUtils.isLogin(session)) {
			return "redirect:/users/loginForm";
		}
		return "/qna/form";
	}
	
	//qna 등록
	@PostMapping("")
	public String regist(String title, String contents, HttpSession httpSession){
		if (!HttpSessionUtils.isLogin(httpSession)) {
			return "redirect:/users/loginForm";
		}
		
		User user = HttpSessionUtils.getUserFromSession(httpSession);
		Question question = new Question(user, title, contents);
		repository.save(question);
		
		return "redirect:/";
	}
	
	//qna 상세
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", repository.findOne(id));
		return "/qna/show";
	}
	
	//질문 업데이트 폼
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession httpSession
			, RedirectAttributes redirectAttributes) {
		try 
		{
			Question question = repository.findOne(id);
			hasPermission(httpSession, question);
			model.addAttribute("question", question);
			return "/qna/updateForm";
		} catch (IllegalStateException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getLocalizedMessage());
			return "redirect:/users/loginForm";
		}
	}
	
	private void hasPermission(HttpSession httpSession, Question question) {
		if (!HttpSessionUtils.isLogin(httpSession)) {
			throw new IllegalStateException("로그인이 필요합니다.");
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(httpSession);
		
		if (!question.isSameWriter(loginUser)) {
			throw new IllegalStateException("자신의 글만 수정, 삭제할 수 있습니다.");
		}
	}
	
	//질문 업데이트
	@PutMapping("/{id}")
	public String updateForm(@PathVariable Long id, String title, String contents
			, Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
		try 
		{
			Question question = repository.findOne(id);
			hasPermission(httpSession, question);
			question.update(title, contents);
			repository.save(question);
			return String.format("redirect:/questions/%d", id);
		} catch (IllegalStateException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getLocalizedMessage());
			return "redirect:/users/loginForm";
		}
	}
	
	//질문 삭제
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession httpSession, RedirectAttributes redirectAttributes) {
		try 
		{
			Question question = repository.findOne(id);
			hasPermission(httpSession, question);
			repository.delete(id);
			return "redirect:/";
		} catch (IllegalStateException e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getLocalizedMessage());
			return "redirect:/users/loginForm";
		}
	}
	
}
