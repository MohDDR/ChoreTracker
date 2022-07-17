package com.dojo.projects.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dojo.projects.models.Job;
import com.dojo.projects.models.LoginUser;
import com.dojo.projects.models.User;
import com.dojo.projects.services.JobService;
import com.dojo.projects.services.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userServ;
    
    @Autowired
    private JobService jobServ;
    
    @GetMapping("/")
    public String index(Model model, HttpSession session) {
    	if (session.getAttribute("user_id")==null) {
    		System.out.println("login and registration page");
    		model.addAttribute("newUser", new User());
    		model.addAttribute("newLogin", new LoginUser());
    		return "index.jsp";
    	} else {
    		return "redirect:/dashboard";
    	}
    }
    
	@GetMapping("/dashboard")
    public String home(Model model, HttpSession session) {
    	
		if (session.getAttribute("user_id")==null) {
			System.out.println("not logged in");
    		return "redirect:/logout";
    	} else {
    		System.out.println("logged in");
	    	long id = (long) session.getAttribute("user_id");
	    	
	    	User user = userServ.findUser(id);
	    	model.addAttribute("user", user);
	        model.addAttribute("jobs", jobServ.allJobsUserIsNotPending(user));
	        return "all_jobs.jsp";
    	}
    }
	
	@RequestMapping("/dashboard/{id}")
	public String acceptJob(@PathVariable("id") Long id, 
			HttpSession session, Model model) {
		if (session.getAttribute("user_id")==null) {
			System.out.println("not logged in");
    		return "redirect:/logout";
    	} else {
    		if(userServ.isSaved(session, id)) {
    			userServ.createPendingJob(session, jobServ.findJob(id));
    		}
    		return "redirect:/dashboard";
    	}
	}
    
    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.setAttribute("user_id", null);
        return "redirect:/";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, 
            BindingResult result, Model model, HttpSession session) {
    	System.out.println("user object is going to register service");
    	User user = userServ.register(newUser, result);
    	System.out.println("user object has returned from register service");
    	if(result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }
        
        long id = user.getId();
        System.out.println("user object id is in session");
        session.setAttribute("user_id", id);
        return "redirect:/dashboard";
    }
    
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
            BindingResult result, Model model, HttpSession session) {

    	User user = userServ.login(newLogin, result);
        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }
    
        long id = user.getId();
        session.setAttribute("user_id", id);
        return "redirect:/dashboard";
    }
	
    @GetMapping("/addJob")
    public String newJob(Model model, @ModelAttribute("job") Job job, 
    		HttpSession session) {
    	if (session.getAttribute("user_id")==null) {
    		System.out.println("not logged in");
    		return "redirect:/logout";
    	} else {
    		return "new.jsp";
    	}
    }
    
    @PostMapping("/addJob")
    public String createJob(@Valid @ModelAttribute("job") Job job, 
    		BindingResult result, HttpSession session) {
    	if (result.hasErrors()) {
            return "new.jsp";
        } else {
        	System.out.println("assoiciating logged in user with new show");
        	long id = (long) session.getAttribute("user_id");
        	job.setPostedBy(userServ.findUser(id));
            jobServ.createJob(job);
            return "redirect:/dashboard";
    	}
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model, 
    		@ModelAttribute("job") Job job, HttpSession session) {
    	if (session.getAttribute("user_id")==null) {
    		System.out.println("please login again");
    		return "redirect:/logout";
    	} else {
    		if(jobServ.cancelAuthorization((Long) session.getAttribute("user_id"), id)) {
    			System.out.println("authorized");
		        Job displayJob = jobServ.findJob(id);
		        model.addAttribute("job", displayJob);
		        return "edit.jsp";
    		}else {
    			System.out.println("who are you?");
    			return "redirect:/dashboard";
    		}
    	}
    }
    
    @PutMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("job") Job job, 
    		BindingResult result, HttpSession session) {
    	if (result.hasErrors()) {
            return "edit.jsp";
        } else {
        	System.out.println("date check no error");
        	long id = (long) session.getAttribute("user_id");
        	job.setPostedBy(userServ.findUser(id));
        	jobServ.updateJob(job);
            return "redirect:/dashboard";
        }
    }
    
    @GetMapping("/view/{id}")
	public String view(Model model, @PathVariable("id") Long jobId, 
			HttpSession session) {
    	System.out.println("show job page");
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/logout";
    	} else {
    		long user_id = (long) session.getAttribute("user_id");
	    	model.addAttribute("user", userServ.findUser(user_id));
	    	
	    	Job job = jobServ.findJob(jobId);
			model.addAttribute(job);
			return "view.jsp";
    	}
	}
    
    @DeleteMapping("/cancel/{id}")
    public String cancel(@PathVariable("id") Long id, HttpSession session) {
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/logout";
    	} else {
    		if(jobServ.cancelAuthorization((Long) session.getAttribute("user_id"), id)) {
	    		jobServ.deleteJob(id);
	    		return "redirect:/dashboard";
    		}else {
    			return "redirect:/dashboard";
    		}
    	}
    }
    
    @DeleteMapping("/done/{id}")
    public String done(@PathVariable("id") Long id, HttpSession session) {
    	if (session.getAttribute("user_id")==null) {
    		return "redirect:/logout";
    	} else {
    		if(jobServ.doneAuthorization((Long) session.getAttribute("user_id"), id)) {
	    		jobServ.deleteJob(id);
	    		System.out.println("completed job");
	    		return "redirect:/dashboard";
    		}else {
    			System.out.println("failed to complete");
    			return "redirect:/dashboard";
    		}
    	}
    }
}
