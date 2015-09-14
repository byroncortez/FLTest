package test.freelancer.com.fltest.volley;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import test.freelancer.com.fltest.dao.Program;

public class ResponsePrograms {

    @SerializedName("results")
    List<Program> programs;
    int count;

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}
