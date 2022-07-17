package com.dojo.projects.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dojo.projects.models.Job;
import com.dojo.projects.models.User;

@Repository
public interface JobRepository extends CrudRepository<Job, Long> {
    List<Job> findAll();
    
    List<Job> findByWorkerIsNull();
    
    List<Job> findByWorkerNotContaining(User worker);

}