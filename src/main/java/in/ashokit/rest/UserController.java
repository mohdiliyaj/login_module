package in.ashokit.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.binding.LoginResponse;
import in.ashokit.entity.Student;
import in.ashokit.entity.User;
import in.ashokit.service.IUserService;

@RestController
public class UserController {
	
	private IUserService userService;
	
	public UserController(IUserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/")
	public ResponseEntity<LoginResponse> loginCheck(@RequestBody User user){
		
		LoginResponse loginCheck = userService.loginCheck(user);
		if(loginCheck != null) {
			return new ResponseEntity<>(loginCheck, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> saveStudent(@RequestBody Student student){
		User userByEmail = userService.findUserByEmail(student.getEmail());
		if(userByEmail == null) {
			Student saveUser = userService.saveUser(student);
			if(saveUser != null) {
				return new ResponseEntity<String>("Registration successfull", HttpStatus.CREATED);
			}
			return new ResponseEntity<String>("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("user already exists", HttpStatus.ALREADY_REPORTED);
	}
}
