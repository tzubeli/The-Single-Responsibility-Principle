package com.theladders.solid.srp.jobseeker;

import com.theladders.solid.srp.refactor.ProfileStatus;

public class JobseekerProfile
{
  private final int id;
  private final ProfileStatus status;

  public JobseekerProfile(int id, ProfileStatus status)
  {
    this.id = id;
    this.status = status;
  }

  public ProfileStatus getStatus()
  {
    return status;
  }

  public int getId()
  {
    return id;
  }

  public boolean hasPermissions(Jobseeker jobseeker) {

      if (!jobseeker.isPremium() &&
             (this.getStatus().equals(ProfileStatus.INCOMPLETE) ||
              this.getStatus().equals(ProfileStatus.NO_PROFILE) ||
              this.getStatus().equals(ProfileStatus.REMOVED)))
          return false;
      return true;
  }
}
