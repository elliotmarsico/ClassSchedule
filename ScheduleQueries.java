
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ellio
 */
public class ScheduleQueries {
    private static PreparedStatement getCourseList;
    private static PreparedStatement addSchedule;
    private static PreparedStatement dropSchedules;
    private static PreparedStatement getScheduleList;
    private static PreparedStatement getScheduleListByCode;
    private static Connection connection;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?,?,?,?,?)");
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(2, entry.getCourseCode());
            addSchedule.setString(3, entry.getStudentID());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setTimestamp(5, entry.getTimestamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleByStudent = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleList = connection.prepareStatement("select semester, courseCode, studentID, status, timestamp from app.schedule order by semester");
            resultSet = getScheduleList.executeQuery();

            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry();
                entry.setSemester(resultSet.getString(1));
                entry.setCourseCode(resultSet.getString(2));
                entry.setStudentID(resultSet.getString(3));
                entry.setStatus(resultSet.getString(4));
                entry.setTimestamp(resultSet.getTimestamp(5));
                
                if(entry.getSemester().equals(semester) && entry.getStudentID().equals(studentID)){
                    scheduleByStudent.add(entry);
                }
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleByStudent;
    }
    
    public int getScheduledStudentCount(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            getCourseList = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule order by semester");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                CourseEntry entry = new CourseEntry();
                entry.setSemester(resultSet.getString(1));
                entry.setCourseCode(resultSet.getString(2));
                entry.setSeats(resultSet.getInt(4));
                if(entry.getSemester().equals(semester) && entry.getCourseCode().equals(courseCode)){
                    return entry.getSeats();
                }
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return 0;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleByCode = new ArrayList<>();
        try
        {
            getScheduleListByCode = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule order by semester");
            resultSet = getScheduleListByCode.executeQuery();
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry();
                entry.setSemester(resultSet.getString(1));
                entry.setCourseCode(resultSet.getString(2));
                entry.setStudentID(resultSet.getString(3));
                entry.setStatus(resultSet.getString(4));
                entry.setTimestamp(resultSet.getTimestamp(5));
                if(entry.getSemester().equals(semester) && entry.getCourseCode().equals(courseCode) && entry.getStatus().equals("S")){
                    scheduleByCode.add(entry);
                }
            }
        }catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduleByCode;
    } 
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode){
        ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<ScheduleEntry>();
        connection = DBConnection.getConnection();
        try
        {
            getScheduleListByCode = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule order by semester");
            resultSet = getScheduleListByCode.executeQuery();
            while(resultSet.next())
            {
                ScheduleEntry entry = new ScheduleEntry();
                entry.setSemester(resultSet.getString(1));
                entry.setCourseCode(resultSet.getString(2));
                entry.setStudentID(resultSet.getString(3));
                entry.setStatus(resultSet.getString(4));
                entry.setTimestamp(resultSet.getTimestamp(5));
                if(entry.getSemester().equals(semester) && entry.getCourseCode().equals(courseCode) && entry.getStatus().equals("W")){
                    waitlistedStudents.add(entry);
                }
            }
        }catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return waitlistedStudents;
    }
    
    public static ArrayList<ScheduleEntry> dropSchedulesByStudent(String studentID){//admin dropping student
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> droppedSchedules = new ArrayList<>();
        try{    
                dropSchedules = connection.prepareStatement("select semester, coursecode from app.schedule where STUDENTID=?");
                dropSchedules.setString(1, studentID);
                resultSet = dropSchedules.executeQuery();
                while(resultSet.next()){
                    ScheduleEntry s = new ScheduleEntry();
                    s.setSemester(resultSet.getString(1));
                    s.setCourseCode(resultSet.getString(2));
                    droppedSchedules.add(s);
                }
                
                dropSchedules = connection.prepareStatement("delete from app.schedule where STUDENTID=?");
                dropSchedules.setString(1, studentID);
                dropSchedules.executeUpdate();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        return droppedSchedules;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){//student dropping course
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> droppedSchedule = new ArrayList<>();
        try{                   
                dropSchedules = connection.prepareStatement("delete from app.schedule where COURSECODE=? and STUDENTID=? and SEMESTER=?");
                dropSchedules.setString(1, courseCode);
                dropSchedules.setString(2, studentID);
                dropSchedules.setString(3, semester);
                dropSchedules.executeUpdate();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
    }
    
    public static ArrayList<ScheduleEntry> dropScheduleByCourse(String semester, String courseCode){//admin dropping course
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> droppedSchedules = new ArrayList<>();
        try{    
                dropSchedules = connection.prepareStatement("select semester, studentID from app.schedule where COURSECODE=? and SEMESTER=?");
                dropSchedules.setString(1, courseCode);
                dropSchedules.setString(2, semester);
                resultSet = dropSchedules.executeQuery();
                while(resultSet.next()){
                    ScheduleEntry s = new ScheduleEntry();
                    s.setSemester(resultSet.getString(1));
                    s.setStudentID(resultSet.getString(2));
                    droppedSchedules.add(s);
                }
                
                dropSchedules = connection.prepareStatement("delete from app.schedule where COURSECODE=?");
                dropSchedules.setString(1, courseCode);
                dropSchedules.executeUpdate();
            }
            catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        return droppedSchedules;
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry){//waitlisted -> scheduled
       connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("UPDATE app.schedule set STATUS=? where SEMESTER=? and COURSECODE=? and STUDENTID=? order by TIMESTAMP");
            addSchedule.setString(1, "S");
            addSchedule.setString(2, entry.getSemester());
            addSchedule.setString(3, entry.getCourseCode());
            addSchedule.setString(4, entry.getStudentID());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        } 
    }
    
    public static ScheduleEntry fillOpenSeat(String semester, String courseCode){
        ScheduleEntry s = new ScheduleEntry();
        s.setTimestamp(new Timestamp(System.currentTimeMillis()));
        s.setStudentID("none");
        s.setStatus("S");
        connection = DBConnection.getConnection();
        try
        {
            dropSchedules = connection.prepareStatement("select SEMESTER, COURSECODE, STUDENTID, STATUS, TIMESTAMP from app.schedule where SEMESTER=? and COURSECODE=? and STATUS='W'");
            dropSchedules.setString(1, semester);
            dropSchedules.setString(2, courseCode);
            resultSet = dropSchedules.executeQuery();
            while(resultSet.next()){
                if(resultSet.getTimestamp(5).before(s.getTimestamp())){
                    s = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                }
            }
            
            addSchedule = connection.prepareStatement("UPDATE app.schedule set STATUS=? where SEMESTER=? and COURSECODE=? and STUDENTID=?");
            addSchedule.setString(1, "S");
            addSchedule.setString(2, semester);
            addSchedule.setString(3, courseCode);
            addSchedule.setString(4, s.getStudentID());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        } 
        return s;
    }
}
