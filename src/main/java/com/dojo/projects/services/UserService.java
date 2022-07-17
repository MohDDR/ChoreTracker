package com.dojo.projects.services;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.dojo.projects.models.Job;
import com.dojo.projects.models.LoginUser;
import com.dojo.projects.models.User;
import com.dojo.projects.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private JobService jobServ;
    
    public User register(User newUser, BindingResult result) {
    		System.out.println("user object email is being checked");
    	Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
    	if(potentialUser.isPresent()) {
    		System.out.println("user object email is already in use");
    		result.rejectValue("email", "Matches", "This email address is already associated with an account!");
    	}
    	
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    		System.out.println("user object password is false");
    	    result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
    	}

    	if(result.hasErrors()) {
    		System.out.println("user object has errors");
    	    return null;
    	}

    	else {
    			System.out.println("user object has no errors");
    		String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    			System.out.println("user object is hashed and being saved");
    		newUser.setPassword(hashed);
    		return userRepo.save(newUser);
    	}  	
    }

    public User login(LoginUser newLogin, BindingResult result) {

    		System.out.println("login object email is being checked");
    	Optional<User> potentialUser = userRepo.findByEmail(newLogin.getEmail());

    	if(!potentialUser.isPresent()) {
    		System.out.println("login object email is not registered");
    		result.rejectValue("email", "Matches", "This email address is not registered!");
    	}

    	else if(!BCrypt.checkpw(newLogin.getPassword(), potentialUser.orElse(null).getPassword())) {
    		System.out.println("login object password is false");
    		result.rejectValue("password", "Matches", "Invalid Password!");
    	}

    	if(result.hasErrors()) {
    		System.out.println("login object has errors");
    	    return null;
    	}

    	else {
    		System.out.println("login object has no errors");
    		return potentialUser.orElse(null);
    	}
    }

	public User findUser(long id) {
		Optional<User> optUser = userRepo.findById(id);
		if(optUser.isPresent()) {
			return optUser.get();
		}
		else {
			return null;
		}
	}
	
	public User createPendingJob(HttpSession session, Job pendingJob){
		User worker = findUser((long) session.getAttribute("user_id"));
		
		worker.getPendingJobs().add(pendingJob);
		userRepo.save(worker);
		
		return worker;
	}
	public boolean isSaved(HttpSession session, Long job_id) {
		Job job = jobServ.findJob(job_id);
		long user_id = (long) session.getAttribute("user_id");
		
    	for(User worker: job.getWorker()) {
	    	if (user_id == worker.getId()) {
	    		System.out.println("user" + user_id + "job" + worker.getId());
	    		return false;
	    	}
		}
    	System.out.println("complete");
    	return true;
	}

}