package com.theladders.solid.srp.refactor;

import com.theladders.solid.srp.http.HttpRequest;
import com.theladders.solid.srp.jobseeker.Jobseeker;
import com.theladders.solid.srp.resume.MyResumeManager;
import com.theladders.solid.srp.resume.Resume;
import com.theladders.solid.srp.resume.ResumeRepository;

public class ResumeManager
{
  private final ResumeRepository resumeRepository;
  private final MyResumeManager myResumeManager;

  public ResumeManager(ResumeRepository resumeRepository, MyResumeManager myResumeManager)
  {
    this.resumeRepository = resumeRepository;
      this.myResumeManager = myResumeManager;
  }

  public Resume saveResume(Jobseeker jobseeker,
                           String fileName)
  {

    Resume resume = new Resume(fileName);
    resumeRepository.saveResume(jobseeker.getId(), resume);

    return resume;
  }



    public Resume getResume(String newResumeFileName,
                             Jobseeker jobseeker,
                             HttpRequest request){

        Resume resume;

        if (!"existing".equals(request.getParameter("whichResume")))
        {
            resume = saveResume(jobseeker, newResumeFileName);

            if (resume != null && "yes".equals(request.getParameter("makeResumeActive")))
            {
                myResumeManager.saveAsActive(jobseeker, resume);
            }
        }
        else
        {
            resume = myResumeManager.getActiveResume(jobseeker.getId());
        }

        return resume;
    }

}
