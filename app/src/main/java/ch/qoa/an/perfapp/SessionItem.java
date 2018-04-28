package ch.qoa.an.perfapp;

public class SessionItem {

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer abdos;
    private Integer dorsaux;
    private Integer corde;
    private Integer squats;
    private Integer abdosTime;
    private Integer dorsauxTime;
    private Integer cordeTime;
    private Integer squatsTime;

    Integer getYear(){
        return year;
    }
    void setYear(Integer SessionYear){
        this.year=SessionYear;
    }

    Integer getMonth(){
        return month;
    }
    void setMonth(Integer SessionMonth){
        this.month=SessionMonth;
    }

    Integer getDay(){
        return day;
    }
    void setDay(Integer SessionDay){
        this.day=SessionDay;
    }

    Integer getRepCorde(){
        return corde;
    }
    void setRepCorde(Integer RepCorde){
        this.corde=RepCorde;
    }

    Integer getRepSquats(){
        return squats;
    }
    void setRepSquats(Integer RepSquats){
        this.squats=RepSquats;
    }

    Integer getRepAbdos(){
        return abdos;
    }
    void setRepAbdos(Integer RepAbdo){
        this.abdos=RepAbdo;
    }

    Integer getRepDorsaux(){
        return dorsaux;
    }
    void setRepDorsaux(Integer RepDorsaux){
        this.dorsaux=RepDorsaux;
    }

    Integer getTimeCorde(){
        return cordeTime;
    }
    void setTimeCorde(Integer TimeCorde){
        this.cordeTime=TimeCorde;
    }

    Integer getTimeSquats(){
        return squatsTime;
    }
    void setTimeSquats(Integer TimeSquats){
        this.squatsTime=TimeSquats;
    }

    Integer getTimeAbdos(){
        return abdosTime;
    }
    void setTimeAbdos(Integer TimeAbdo){
        this.abdosTime=TimeAbdo;
    }

    Integer getTimeDorsaux(){
        return dorsauxTime;
    }
    void setTimeDorsaux(Integer TimeDorsaux){
        this.dorsauxTime=TimeDorsaux;
    }


}
