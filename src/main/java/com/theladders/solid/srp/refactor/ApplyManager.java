package com.theladders.solid.srp.refactor;

import com.theladders.solid.srp.job.Job;
import com.theladders.solid.srp.job.JobSearchService;
import com.theladders.solid.srp.job.application.ApplicationFailureException;
import com.theladders.solid.srp.job.application.JobApplicationResult;
import com.theladders.solid.srp.job.application.JobApplicationSystem;
import com.theladders.solid.srp.job.application.UnprocessedApplication;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.resume.Resume;

/**
 * Created by atzubeli on 5/23/14.
 */
public class ApplyManager {

    private final JobSearchService jobSearchService;
    private final JobApplicationSystem jobApplicationSystem;

    public ApplyManager(JobSearchService jobSearchService,
                        JobApplicationSystem jobApplicationSystem) {

        this.jobSearchService = jobSearchService;
        this.jobApplicationSystem = jobApplicationSystem;
    }


    public Job getJob(String jobIdString) {

        int jobId = Integer.parseInt(jobIdString);

        Job job = jobSearchService.getJob(jobId);
        return job;
    }


    public void apply(Jobseeker jobseeker,
                      Job job,
                      Resume resume) {


        UnprocessedApplication application = new UnprocessedApplication(jobseeker, job, resume);

        JobApplicationResult applicationResult = jobApplicationSystem.apply(application);

        if (applicationResult.failure()) {
            throw new ApplicationFailureException(applicationResult.toString());
        }
    }
}