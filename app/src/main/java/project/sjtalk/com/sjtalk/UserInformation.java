package project.sjtalk.com.sjtalk;

/**
 * Created by Himanshu on 15-03-2017.
 */
public class UserInformation {
    public String name;
    public String mobile;
    public String batch;
    public String branch;

    public UserInformation(){

    }

    public UserInformation(String name, String mobile, String batch, String branch) {
        this.name = name;
        this.mobile = mobile;
        this.batch=batch;
        this.branch=branch;
    }
}
