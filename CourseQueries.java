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

public class CourseQueries {
    private static PreparedStatement addCourse;
    private static PreparedStatement dropCourse;
    private static PreparedStatement getCourseList;
    private static PreparedStatement modifyCourse;
    private static Connection connection;
    private static ResultSet resultSet;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
        try
        {
            getCourseList = connection.prepareStatement("select semester, courseCode, description, seats from app.course order by semester, coursecode");
            resultSet = getCourseList.executeQuery();

            while(resultSet.next())
            {
                CourseEntry entry = new CourseEntry();
                entry.setSemester(resultSet.getString(1));
                entry.setCourseCode(resultSet.getString(2));
                entry.setDescription(resultSet.getString(3));
                entry.setSeats(resultSet.getInt(4));
                allCourses.add(entry);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allCourses;
    }
    
    public static ArrayList<CourseEntry> getCoursesBySemester(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> coursesBySemester = new ArrayList<CourseEntry>();
        try
        {
            getCourseList = connection.prepareStatement("select semester, courseCode, description, seats from app.course order by semester, coursecode");
            resultSet = getCourseList.executeQuery();

            while(resultSet.next())
            {
                CourseEntry entry = new CourseEntry();
                entry.setSemester(resultSet.getString(1));
                entry.setCourseCode(resultSet.getString(2));
                entry.setDescription(resultSet.getString(3));
                entry.setSeats(resultSet.getInt(4));
                if(entry.getSemester().equals(semester)){
                    coursesBySemester.add(entry);
                }
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return coursesBySemester;        
    }
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (semester, courseCode, description, seats) values (?,?,?,?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> allCourseCodes = new ArrayList<String>();
        try
        {
            getCourseList = connection.prepareStatement("select semester, coursecode, description, seats from app.course order by semester");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                allCourseCodes.add(resultSet.getString(2));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allCourseCodes;
    }
    
    public static int getCourseSeats(String semester, String courseCode){
        ArrayList<CourseEntry> allCourses = getAllCourses(semester);
        for(int i = 0; i <= allCourses.size(); i++){
            if(allCourses.get(i).getSemester().equals(semester) && allCourses.get(i).getCourseCode().equals(courseCode)){
                return allCourses.get(i).getSeats();
            }
        }
        return 0;
    }
    
    public static void updateCourseSeats(String semester, String courseCode, int seats){
        ArrayList<CourseEntry> allCourses = getAllCourses(semester);
        for(int i = 0; i < allCourses.size(); i++){
            if(allCourses.get(i).getSemester().equals(semester) && allCourses.get(i).getCourseCode().equals(courseCode)){
                try
                {
                    modifyCourse = connection.prepareStatement("update app.course set seats = ?");
                    modifyCourse.setInt(1, seats);
                    modifyCourse.executeUpdate();
                }
                catch(SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }
            }
        }
    }
    
    //part 2
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropCourse = connection.prepareStatement("delete from app.course where COURSECODE=? and SEMESTER=?");
            dropCourse.setString(1, courseCode);
            dropCourse.setString(2, semester);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    
    
    
}
