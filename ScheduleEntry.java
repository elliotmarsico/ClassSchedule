/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ellio
 */
import java.sql.Timestamp;

public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timestamp;
    
    public ScheduleEntry(){
        
    }
    
    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp timestamp){
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timestamp = timestamp;
    }
    
    public String getSemester(){
        return this.semester;
    }
    
    public void setSemester(String newSemester){
        this.semester = newSemester;
    }
    
    public String getCourseCode(){
        return this.courseCode;
    }
    
    public void setCourseCode(String newCourseCode){
        this.courseCode = newCourseCode;
    }
    
    public String getStudentID(){
        return this.studentID;
    }
    
    public void setStudentID(String newStudentID){
        this.studentID = newStudentID;
    }
    
    public String getStatus(){
        return this.status;
    }
    
    public void setStatus(String newStatus){
        this.status = newStatus;
    }
    
    public Timestamp getTimestamp(){
        return this.timestamp;
    }
    
    public void setTimestamp(Timestamp newTimestamp){
        this.timestamp = newTimestamp;
    }
}
