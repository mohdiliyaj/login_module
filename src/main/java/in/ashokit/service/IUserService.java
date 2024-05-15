package in.ashokit.service;

import in.ashokit.binding.LoginResponse;
import in.ashokit.entity.Student;
import in.ashokit.entity.User;

public interface IUserService {
	
	public LoginResponse loginCheck(User user);
	
	public Student saveUser(Student student);
	
	public User findUserByEmail(String email);
	
}
