package com.padhi.web;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.padhi.bean.User;
import com.padhi.dao.UserDao;
//git test

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	UserDao userDao;
	
	public void init() throws ServletException{
	 userDao=new UserDao();
	}
   
   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action=request.getServletPath();
		switch(action) {
		case "/new":
			showNewForm(request,response);
			break;
		
		case "/insert":
			insertUser(request, response);
			break;
		
		case "/delete":
			deleteUser(request, response);
			break;
		
		case "/edit":
			try {
				showEditForm(request, response);
				} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
			break;
		
		case "/update":
			try {
				updateUser(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		
		default:
			try {
				listUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
		private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			RequestDispatcher dispatcher=request.getRequestDispatcher("user-form.jsp");
			dispatcher.forward(request, response);
		}
		
		private void insertUser(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			String country=request.getParameter("country");
			
			User newUser=new User(name,email,country);
			  
			try {
				userDao.insertUser(newUser);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			response.sendRedirect("list");
		}
		
		private void deleteUser(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
			int id=Integer.parseInt(request.getParameter("id"));
			try {
				userDao.deleteUser(id);
				
			}
			catch(Exception e ) {
				e.printStackTrace();
			}
			response.sendRedirect("list");
		}
		
		private void showEditForm(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException,SQLException{
			int id=Integer.parseInt(request.getParameter("id"));
			User existingUser;
			
			try {
				existingUser=userDao.selectUser(id);
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("user-form.jsp");
				request.setAttribute("user", existingUser);
				requestDispatcher.forward(request, response);
			}
			catch(Exception e) {
				e.printStackTrace();
				
			}
		}
		
		private void updateUser(HttpServletRequest request,HttpServletResponse response) throws SQLException,IOException{
			int id=Integer.parseInt(request.getParameter("id"));
			String name=request.getParameter("name");
			String email=request.getParameter("email");
			String country=request.getParameter("country");
			User user=new User(id,name,email,country);
			userDao.updateUser(user);
			response.sendRedirect("List");
		}
		
		//default
		
		private void listUser(HttpServletRequest request,HttpServletResponse response) throws SQLException,IOException{
			try {
				List<User> listUser=userDao.selectAllUser();
				request.setAttribute("listUser", listUser);
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("user-list.jsp");
				requestDispatcher.forward(request, response);
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		 
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
