package ch.qoa.an.perfapp;

public class SportItem {

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer rep;
    private Integer time;

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

    Integer getRep(){
        return rep;
    }
    void setRep(Integer repetition){
        this.rep=repetition;
    }

    Integer getTime(){
        return time;
    }
    void setTime(Integer time){
        this.time=time;
    }


}
