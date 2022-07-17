package com.dojo.projects.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dojo.projects.models.Job;
import com.dojo.projects.models.User;
import com.dojo.projects.repositories.JobRepository;

@Service
public class JobService {

	@Autowired
	JobRepository jobRepo;
	
	public List<Job> allJobs(){
		return jobRepo.findAll();
	}
	
	public List<Job> allJobsUsesAreNotPending(){
		return jobRepo.findByWorkerIsNull();
	}
	
	public List<Job> allJobsUserIsNotPending(User worker){
		return jobRepo.findByWorkerNotContaining(worker);
	}
	
	public Job createJob(Job job) {
		jobRepo.save(job);
		return job;
	}
	
	public Job findJob(Long id) {
        Optional<Job> optionalJob = jobRepo.findById(id);
        if(optionalJob.isPresent()) {
            return optionalJob.get();
        } else {
            return null;
        }
    }
	
	public Job updateJob(Job job) {
		return jobRepo.save(job);
	}
	
	public void deleteJob(Long id) {
		Optional<Job> optionalJob = jobRepo.findById(id);
		if(optionalJob.isPresent()) {
			jobRepo.deleteById(id);
		}
	}
	
	public boolean cancelAuthorization(Long user_id, Long job_id) {
    	Job job = findJob(job_id);
    	System.out.println("user" + user_id + "job" + job.getPostedBy().getId());
    	if (user_id.equals(job.getPostedBy().getId())) {
    		System.out.println(user_id + job.getPostedBy().getId());
    		return true;
    	} else {
    		return false;
    	}
    }
	
	public boolean doneAuthorization(Long user_id, Long job_id) {
		Job job = findJob(job_id);
		System.out.println("user" + user_id + "job" + job.getWorker().get(0).getId());

    	for(User worker: job.getWorker()) {
    		System.out.println("user" + user_id + "job" + worker.getId());
	    	if (user_id.equals(worker.getId())) {
	    		System.out.println("user" + user_id + "job" + worker.getId());
	    		return true;
	    	}
	    	System.out.println("user" + user_id + "job" + worker.getId());
		}
    	System.out.println("failed to complete");
    	return false;
	}
}
