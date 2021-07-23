package org.techtown.reducetheuseofplastic;

public class RankItem {
    private int id;
    private String user_id;
    private int user_point;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public RankItem(){

    }
    public RankItem( int id, String user_id, int user_point){
        this.id=id;
        this.user_id=user_id;
        this.user_point=user_point;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getUser_point() {
        return user_point;
    }

    public void setUser_point(int user_point) {
        this.user_point = user_point;
    }



}
