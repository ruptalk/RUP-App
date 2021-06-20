package org.techtown.reducetheuseofplastic;

public class UserIdPoint {
    private String UserId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getUserPoint() {
        return UserPoint;
    }

    public void setUserPoint(int userPoint) {
        UserPoint = userPoint;
    }

    private int UserPoint;
    public UserIdPoint(int picture,String UserId,int UserPoint){
        this.UserId=UserId;
        this.UserPoint=UserPoint;
    }
    public UserIdPoint(){

    }

}
