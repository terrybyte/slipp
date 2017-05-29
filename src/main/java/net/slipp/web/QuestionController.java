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
import net.slipp.domain.Result;
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
		
		Question question = repository.findOne(id);
		Result result = valid(httpSession, question);
		
		if (!result.isValid()) {
			redirectAttributes.addFlashAttribute("errorMessage", result.getErrorMessage());
			return "redirect:/users/loginForm";
		}
		
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}
	
//	private void hasPermission(HttpSession httpSession, Question question) {
//		if (!HttpSessionUtils.isLogin(httpSession)) {
//			throw new IllegalStateException("로그인이 필요합니다.");
//		}
//		
//		User loginUser = HttpSessionUtils.getUserFromSession(httpSession);
//		
//		if (!question.isSameWriter(loginUser)) {
//			throw new IllegalStateException("자신의 글만 수정, 삭제할 수 있습니다.");
//		}
//	}
	
	private Result valid(HttpSession httpSession, Question question) {
		if (!HttpSessionUtils.isLogin(httpSession)) {
			return Result.fail("로그인이 필요합니다.");
//			throw new IllegalStateException("로그인이 필요합니다.");
		}
		
		User loginUser = HttpSessionUtils.getUserFromSession(httpSession);
		
		if (!question.isSameWriter(loginUser)) {
			return Result.fail("자신의 글만 수정, 삭제할 수 있습니다.");
//			throw new IllegalStateException("자신의 글만 수정, 삭제할 수 있습니다.");
		}
		
		return Result.ok();
	}
	
	//질문 업데이트
	@PutMapping("/{id}")
	public String updateForm(@PathVariable Long id, String title, String contents
			, Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {
		
		Question question = repository.findOne(id);
		Result result = valid(httpSession, question);
		
		if (!result.isValid()) {
			redirectAttributes.addFlashAttribute("errorMessage", result.getErrorMessage());
			return "redirect:/users/loginForm";
		}
		
		question.update(title, contents);
		repository.save(question);
		return String.format("redirect:/questions/%d", id);
	}
	
	//질문 삭제
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, HttpSession httpSession, RedirectAttributes redirectAttributes) {
		Question question = repository.findOne(id);
		Result result = valid(httpSession, question);
		
		if (!result.isValid()) {
			redirectAttributes.addFlashAttribute("errorMessage", result.getErrorMessage());
			return "redirect:/users/loginForm";
		}
		
		repository.delete(id);
		return "redirect:/";
	}
	
}
