package com.theladders.solid.srp.refactor;

import com.theladders.solid.srp.Result;
import com.theladders.solid.srp.http.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by atzubeli on 5/22/14.
 */
public class ApplyView {



    public static void provideApplySuccessView(HttpResponse response, Map<String, Object> model){

        Result result = new Result("success", model);
        response.setResult(result);
    }

    public static void provideResumeCompletionView(HttpResponse response, Map<String, Object> model){

        Result result = new Result("completeResumePlease", model);
        response.setResult(result);
    }

    public static void provideErrorView(HttpResponse response, List<String> errList, Map<String, Object> model){

        Result result = new Result("error", model, errList);
        response.setResult(result);
    }

    public static void provideInvalidJobView(HttpResponse response, int jobId){

        Map<String, Object> model = new HashMap();
        model.put("jobId", jobId);

        Result result = new Result("invalidJob", model);
        response.setResult(result);
    }
}
