package net.slipp.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.slipp.domain.User;
import net.slipp.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("session");
		return "redirect:/";
	}

	@GetMapping("/loginForm")
	public String loginForm(){
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userID, String password, HttpSession session) {
		User user = userRepository.findByUserID(userID);
		
		if (user == null || !password.equals(user.getPassword())) {
			return "redirect:/users/loginForm";
		}
		
		//session
		session.setAttribute("session", user);
		
		return "redirect:/";
	}
	
	
	
	//회원가입 등록
	@PostMapping("")
	public String create(User user, Model model) {
		System.out.println(user.toString());
		userRepository.save(user);
		return "redirect:/users";
	}
	
	//회원목록
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
	
	//회원가입 페이지
	@GetMapping("/form")
	public String form(Model model) {
		return "/user/form";
	}
	
	//회원수정 페이지
	@GetMapping("/{id}/form")
	public String updateForm1(@PathVariable Long id, Model model, HttpSession session) {
		User sessionUser = (User) session.getAttribute("session");
		
		if (sessionUser == null) {
			return "redirect:/users/loginForm";
		}
		
		if (!id.equals(sessionUser.getId())) {
			throw new IllegalStateException("invalid session!");
		}
		
		User user = userRepository.findOne(sessionUser.getId());
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	@GetMapping("/updateForm")
	public String updateForm(Model model, HttpSession session) {
		User sessionUser = (User) session.getAttribute("session");
		
		if (sessionUser == null) {
			return "redirect:/users/loginForm";
		}
		
		User user = userRepository.findOne(sessionUser.getId());
		model.addAttribute("user", user);
		return "/user/updateForm";
	}
	
	//회원수정
	@PutMapping("/{id}")
	public String update1(@PathVariable Long id, User updateUser, Model model) {
		User user = userRepository.findOne(id);
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
	//회원수정
	@PutMapping("/udpate")
	public String update(User updatedUser, Model model, HttpSession session) {
		User userSession = (User) session.getAttribute("session");
		
		if (userSession == null) {
			return "redirect:/users/loginForm";
		}
		
		User user = userRepository.findOne(userSession.getId());
		user.update(updatedUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
}