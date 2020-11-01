

 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<nav class="navbar navbar-expand-md navbar-dark"
		style="background-color: brown">




<c:if test="${LOGIN_ID == null}" var="flg" />

<c:if test="${!flg}" >
		
		<div>
			<a href="<%= request.getContextPath() %>" class="navbar-brand"> Todo App</a>
			
			
		</div>
		
		<ul class="navbar-nav">
				
			</ul>

			<ul class="navbar-nav navbar-collapse justify-content-end">
				<li><a href="<%=request.getContextPath()%>/mytodo"
					class="nav-link"><%= request.getSession().getAttribute("LOGIN_ID") %>'s todos</a></li>
				<li><a href="<%=request.getContextPath()%>/logout"
					class="nav-link">Logout</a></li>
			</ul>
		
</c:if>

<c:if test="${flg}" >

			<div>
				<a href="https://www.javaguides.net" class="navbar-brand"> Todo
					App</a>
			</div>

			<ul class="navbar-nav navbar-collapse justify-content-end">
			<li><a href="<%= request.getContextPath() %>/login" class="nav-link">Login</a></li>
			<li><a href="<%= request.getContextPath() %>/register" class="nav-link">Signup</a></li>
		</ul>
			
</c:if>
		
	</nav>
</header>
