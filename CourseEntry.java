/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ellio
 */
public class CourseEntry {
    private String semester;
    private String courseCode;
    private String description;
    private int seats;
    
    public CourseEntry(){}
    
    public CourseEntry(String semester, String courseCode, String description, int seats){
        this.semester = semester;
        this.courseCode = courseCode;
        this.description = description;
        this.seats = seats;
    }
    
    @Override
    public String toString() {
        return this.courseCode;
    }
    
    public String getStatus(){
        if(seats <= 0){
            return "W";
        }else{
            return "S";
        }
    }
    
    public String getSemester(){
        return this.semester;
    }
     public void setSemester(String newSemester){
         this.semester = newSemester;
     }
     
     public void newSeatTaken(){
         this.seats -= 1;
     }
     
     public String getCourseCode(){
         return this.courseCode;
     }
     
     public void setCourseCode(String newCourseCode){
         this.courseCode = newCourseCode;
     }
     
     public String getDescription(){
         return this.description;
     }
     
     public void setDescription(String newDescription){
         this.description = newDescription;
     }
     
     public int getSeats(){
         return this.seats;
     }
     
     public void setSeats(int newSeats){
         this.seats = newSeats;
     }
}
