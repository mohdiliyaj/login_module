package in.ashokit.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import in.ashokit.binding.LoginResponse;
import in.ashokit.entity.Student;
import in.ashokit.entity.User;
import in.ashokit.repo.StudentRepo;
import in.ashokit.repo.UserRepo;
import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService{
	
	private UserRepo userRepo;
	private StudentRepo studentRepo;
	
	public UserService(UserRepo userRepo, StudentRepo studentRepo) {
		this.userRepo = userRepo;
		this.studentRepo = studentRepo;
	}
	
	@Override
	public LoginResponse loginCheck(User user) {
		User byEmailAndPassword = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if(byEmailAndPassword != null) {
			LoginResponse login = new LoginResponse();
			login.setUserId(byEmailAndPassword.getUserId());
			login.setUserRole(byEmailAndPassword.getUserRole());
			return login;
		}
		return null;
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Student saveUser(Student student) {
		ModelMapper mapper = new ModelMapper();		
		User user = mapper.map(student, User.class);
		user.setUserRole("student");
		User savedUser = userRepo.save(user);
		student.setUser(savedUser);
		return studentRepo.save(student);
	}
	
	@Override
	public User findUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
}
