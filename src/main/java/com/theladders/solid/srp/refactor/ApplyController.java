package com.theladders.solid.srp.refactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
import com.theladders.solid.srp.http.HttpRequest;
import com.theladders.solid.srp.http.HttpResponse;
import com.theladders.solid.srp.job.Job;
import com.theladders.solid.srp.jobseeker.JobseekerProfile;
import com.theladders.solid.srp.jobseeker.JobseekerProfileManager;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.resume.Resume;

public class ApplyController
{
  private final JobseekerProfileManager jobseekerProfileManager;
  private final ResumeManager           resumeManager;
  private final ApplyManager            applyManager;


  public ApplyController(JobseekerProfileManager jobseekerProfileManager,
                         ResumeManager resumeManager, ApplyManager applyManager)
  {
    this.jobseekerProfileManager = jobseekerProfileManager;
    this.resumeManager = resumeManager;
    this.applyManager = applyManager;

  }


  public HttpResponse handle(HttpRequest request,
                             HttpResponse response,
                             String origFileName){
      
        Jobseeker jobseeker = request.getSession().getJobseeker();



        Job job = applyManager.getJob(request.getParameter("jobId"));

        if (job == null) {

            ApplyView.provideInvalidJobView(response, 0);
            return response;
        }

        Map<String, Object> model = new HashMap();//
        List<String> errList = new ArrayList();//



        try//
        {
          Resume resume = resumeManager.getResume(origFileName,jobseeker, request);
          applyManager.apply(jobseeker, job, resume);
        }
        catch (Exception e)
        {
          errList.add("We could not process your application.");
          ApplyView.provideErrorView(response, errList, model);
          return response;
        }//

        model.put("jobId", job.getJobId());//
        model.put("jobTitle", job.getTitle());//

        JobseekerProfile profile = jobseekerProfileManager.getJobSeekerProfile(jobseeker);//

        if (!profile.hasPermissions(jobseeker))//
        
        {
          ApplyView.provideResumeCompletionView(response, model);
          return response;
        }

        ApplyView.provideApplySuccessView(response, model);

        return response;
  }

    public HttpResponse newHandle(HttpRequest request,
                               HttpResponse response,
                               String origFileName){


        applyHelper(request, origFileName);
        Jobseeker jobseeker = request.getSession().getJobseeker();

        Job job = applyManager.getJob(request.getParameter("jobId"));

        if (job == null) {

            ApplyView.provideInvalidJobView(response, 0);
            return response;
        }

        Map<String, Object> model = new HashMap();//
        List<String> errList = new ArrayList();//



        try//
        {
            Resume resume = resumeManager.getResume(origFileName,jobseeker, request);
            applyManager.apply(jobseeker, job, resume);
        }
        catch (Exception e)
        {
            errList.add("We could not process your application.");
            ApplyView.provideErrorView(response, errList, model);
            return response;
        }//

        model.put("jobId", job.getJobId());//
        model.put("jobTitle", job.getTitle());//

        JobseekerProfile profile = jobseekerProfileManager.getJobSeekerProfile(jobseeker);//


        if (!profile.hasPermissions(jobseeker))//

        {
            ApplyView.provideResumeCompletionView(response, model);
            return response;
        }

        ApplyView.provideApplySuccessView(response, model);

        return response;
    }


    }




