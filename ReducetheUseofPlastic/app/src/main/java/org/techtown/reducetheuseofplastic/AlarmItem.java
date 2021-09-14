package org.techtown.reducetheuseofplastic;

public class AlarmItem {
    private String content;
    private String date;

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content=content;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String month, String day){
        this.date=""+month+"월 "+day+"일";
    }
}
