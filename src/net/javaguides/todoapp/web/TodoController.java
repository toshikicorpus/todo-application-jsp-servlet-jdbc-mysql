package net.javaguides.todoapp.web;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.javaguides.todoapp.dao.TodoDao;
import net.javaguides.todoapp.dao.TodoDaoImpl;
import net.javaguides.todoapp.model.Todo;

/**
 * ControllerServlet.java This servlet acts as a page controller for the
 * application, handling all requests from the todo.
 * 
 * @email Ramesh Fadatare
 */

@WebServlet("/")
public class TodoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TodoDao todoDAO;

	public void init() {
		todoDAO = new TodoDaoImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println("gopost/");
		System.out.println(action);
		
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println("doget/");
		System.out.println(action);
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertTodo(request, response);
				break;
			case "/delete":
				deleteTodo(request, response);
				break;
			case "/mytodo":
				userTodo(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateTodo(request, response);
				break;
			case "/":
				listTodo(request, response);
				break;
			case "/logout":
				userLogout(request, response);
				break;
			default:
				HttpSession session = request.getSession();
				String login_id = (String)session.getAttribute("LOGIN_ID");
				System.out.println(login_id);
				RequestDispatcher dispatcher;
				if(login_id==null) {
					dispatcher = request.getRequestDispatcher("login/login.jsp");	
					dispatcher.forward(request, response);
				}else {
					listTodo(request, response);	
				}
				
				
				
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void listTodo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		
		HttpSession session = request.getSession();
		String login_id = (String)session.getAttribute("LOGIN_ID");
		
		System.out.println(login_id);
		if(login_id!=null) {
			
			List<Todo> listTodo = todoDAO.selectAllTodos();
			request.setCharacterEncoding("UTF-8");
			request.setAttribute("listTodo", listTodo);
			//RequestDispatcher dispatcher = request.getRequestDispatcher("/");
			RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-list.jsp");
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect("login/login.jsp");	
		}
		
		
	}
	
	private void userTodo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		
		HttpSession session = request.getSession();
		String login_id = (String)session.getAttribute("LOGIN_ID");
		
		System.out.println(login_id);
		if(login_id!=null) {
			
			List<Todo> listTodo = todoDAO.selectUserTodos(login_id);
			request.setCharacterEncoding("UTF-8");
			request.setAttribute("listTodo", listTodo);
			//RequestDispatcher dispatcher = request.getRequestDispatcher("/");
			RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-list.jsp");
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect("login/login.jsp");	
		}
		
		
	}
	
	private void userLogout(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException,ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.setAttribute("LOGIN_ID",null);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("login/login.jsp");
		dispatcher.forward(request, response);

	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-form.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		Todo existingTodo = todoDAO.selectTodo(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-form.jsp");
		request.setAttribute("todo", existingTodo);
		dispatcher.forward(request, response);

	}

	private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		String title = request.getParameter("title");
		HttpSession session = request.getSession();
		String username= (String)session.getAttribute("LOGIN_ID");
		//String username = request.getParameter("username");
		String description = request.getParameter("description");
		
		/*DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"),df);*/
		System.out.println(title);
		System.out.println(description);
		
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		Todo newTodo = new Todo(title, username, description, LocalDate.now(), isDone);
		todoDAO.insertTodo(newTodo);
		response.sendRedirect("list");
	}

	private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		
		String title = request.getParameter("title");
		HttpSession session = request.getSession();
		String username= (String)session.getAttribute("LOGIN_ID");
		//String username = request.getParameter("username");
		String description = request.getParameter("description");
		//DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));
		
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		Todo updateTodo = new Todo(id, title, username, description, targetDate, isDone);
		
		todoDAO.updateTodo(updateTodo);
		
		response.sendRedirect("list");
	}

	private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		todoDAO.deleteTodo(id);
		response.sendRedirect("list");
	}
}
