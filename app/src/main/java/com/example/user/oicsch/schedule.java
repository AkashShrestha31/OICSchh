package com.example.user.oicsch;

/**
 * Created by Aakash on 7/30/2016.
 */
public class schedule {
    private String ITime;
    private String FTime;
    private String subject;
    public schedule(String ITime, String FTime, String subject)
    {
     this.ITime=ITime;
        this.FTime=FTime;
        this.subject=subject;
    }
    public String getITime()
    {
        return ITime;
    }
    public String getFTime()
    {
        return FTime;
    }
    public String getsubject()
    {
        return subject;
    }
    public void setITime(String ITime)
    {
        this.ITime=ITime;
    }
    public void setFTime(String FTime)
    {
this.FTime=FTime;
            }
    public void setsubject(String subject)
    {
this.subject=subject;
    }
}
