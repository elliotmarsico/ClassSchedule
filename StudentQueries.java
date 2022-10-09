/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ellio
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    private static PreparedStatement addStudent;
    private static PreparedStatement dropStudent;
    private static PreparedStatement getStudentList;
    private static Connection connection;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student){
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> allStudents = new ArrayList<StudentEntry>();
        try
        {
            getStudentList = connection.prepareStatement("select studentid, firstname, lastname from app.student order by studentid");
            resultSet = getStudentList.executeQuery();

            while(resultSet.next())
            {
                StudentEntry entry = new StudentEntry();
                entry.setStudentID(resultSet.getString(1));
                entry.setFirstName(resultSet.getString(2));
                entry.setLastName(resultSet.getString(3));
                allStudents.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allStudents;
    }
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        try
        {
            getStudentList = connection.prepareStatement("select studentid, firstname, lastname from app.student order by studentid");
            resultSet = getStudentList.executeQuery();

            while(resultSet.next())
            {
                StudentEntry entry = new StudentEntry();
                entry.setStudentID(resultSet.getString(1));
                entry.setFirstName(resultSet.getString(2));
                entry.setLastName(resultSet.getString(3));
                if(entry.getStudentID().equals(studentID)){
                    return entry;
                }
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        throw new Error("No student found with designated ID.");
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        StudentEntry studentDropped = new StudentEntry();
        try
        {
            getStudentList = connection.prepareStatement("select studentid, firstname, lastname from app.student order by studentid");
            resultSet = getStudentList.executeQuery();

            while(resultSet.next())
            {
                StudentEntry entry = new StudentEntry();
                entry.setStudentID(resultSet.getString(1));
                entry.setFirstName(resultSet.getString(2));
                entry.setLastName(resultSet.getString(3));
                if(entry.getStudentID().equals(studentID)){
                    studentDropped = entry;
                }
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        if(!studentDropped.isEmpty()){
            try{
                dropStudent = connection.prepareStatement("delete from app.student where STUDENTID=?");
                StudentEntry student = getStudent(studentID);
                dropStudent.setString(1, student.getStudentID());
                dropStudent.executeUpdate();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
    }
}
