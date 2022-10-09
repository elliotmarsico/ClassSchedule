/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ellio
 */
public class StudentEntry {
    private String studentID;
    private String firstName;
    private String lastName;
    
    
    public StudentEntry(){
        this.studentID = "none";
    }
    
    public StudentEntry(String studentID, String firstName, String lastName){
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    @Override
    public String toString(){
        return this.lastName + ", " + this.firstName;
    }
    
    public String getStudentID(){
        return this.studentID;
    }
    
    public void setStudentID(String newStudentID){
        this.studentID = newStudentID;
    }
    
    public String getFirstName(){
        return this.firstName;
    }
    
    public void setFirstName(String newFirstName){
        this.firstName = newFirstName;
    }
    
    public String getLastName(){
        return this.lastName;
    }
    
    public void setLastName(String newLastName){
        this.lastName = newLastName;
    }
    
    public boolean isEmpty(){
        return(this.studentID.equals("none"));
    }   
}
