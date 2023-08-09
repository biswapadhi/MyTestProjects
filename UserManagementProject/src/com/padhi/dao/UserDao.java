package com.padhi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.padhi.bean.User;
//test for git
//test 2
//test 3
//test 4
public class UserDao {
	private String jdbcURL="jdbc:mysql://localhost:3306/usermanagement";
	private String jdbcUserName="root";
	private String jdbcPassword="root";
	private String jdbcDriver="com.mysql.cj.jdbc.Driver";
	
	private static final String INSERT_USER_SQL="INSERT INTO users"+"(name,email,country) values"
													+"(?,?,?);";
	private static final String SELECT_USER_BY_ID="select id,name,email,country from users where id=?;";
	
	private static final String SELECT_ALL_USER="select * from users";
	
	private static final String DELETE_USER="delete from users where id=?;";
	
	private static final String UPDATE_USER="update users set name=?,email=?,country=? where id=?;";
	
	public UserDao() {
		 
	}
	
	protected Connection getConnection() {
		Connection connection=null;
		try {
			Class.forName(jdbcDriver);
			connection=DriverManager.getConnection(jdbcURL,jdbcUserName,jdbcPassword);
		}
		
		catch(SQLException e){
			e.printStackTrace();
			
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		 return connection;
	}
	
	//Insert user
	public void insertUser(User user) throws SQLException{
		try {
			Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(INSERT_USER_SQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
	}
	 
	
	//Select user by id
	
	public User selectUser(int id) {
		User user=null;
		try {
			Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(SELECT_USER_BY_ID);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				String name=resultSet.getString("name");
				String email=resultSet.getString("email");
				String country=resultSet.getString("country");
				
				user=new User(id,name,email,country);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return user;
		
	}
	
	//select all user
	public List<User> selectAllUser() {
		List<User> users=new ArrayList<User>();
		try {
			Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_USER);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				String email=resultSet.getString("email");
				String country=resultSet.getString("country");
				
				users.add(new User(id,name,email,country));
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return users; 
	}
	
	//update user
	 public boolean updateUser(User user) throws SQLException{
		 boolean rowUpdated=false;
		 try {
			 Connection connection=getConnection();
			 PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_USER);
			 preparedStatement.setString(1, user.getName());
			 preparedStatement.setString(2, user.getEmail());
			 preparedStatement.setString(3, user.getCountry());
			 preparedStatement.setInt(4, user.getId());
			 rowUpdated=preparedStatement.executeUpdate() > 0;// returns true or false
		 }
		 catch(SQLException e) {
			 e.printStackTrace();
		 }
		 return rowUpdated;
	 }
	
	//delete User
	 public boolean deleteUser(int id) throws SQLException{
		boolean rowDeleted=false  ;
		try {
			Connection connection=getConnection();
			PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USER);
			preparedStatement.setInt(1, id);
			rowDeleted=preparedStatement.executeUpdate() > 0;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return rowDeleted;
	 }

}
