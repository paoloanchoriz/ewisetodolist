<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/jquery-ui.min.css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap-3.3.6-dist/css/bootstrap.min.css"></link>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/styles.css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Home page</title>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="${pageContext.request.contextPath}">Ewise TODO List</a>
			</div>
		</div>
	</nav>
	
	<div id="homePage" class="container">
		<h1>Home page</h1>
		<div class="links">
			<span><a href="#" id="add-link" title="Add New">Add New List</a></span>
		</div>
		<div id="result">
			<table id="resultTable" class="table">
				<thead>
					<tr>
						<th>Name</th>
						<th>Description</th>
						<th></th>
						<th></th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr class="emptyMessage"><td colspan="6">There are no items to display.</td></tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<div id="todoList" class="container">
		<h1 id="todoListName">${todoList.name}</h1>
		<h4><em id="todoListDescription">${todoList.description}</em></h4>
		<div class="links">
			<span><a href="#" id="add-link" title="Add New">Add New Item</a></span><br/>
			<span><a href="#" id="export-link" title="Export">Export as Text File</a></span><br/>
			<span><a href="#" id="back-link" title="Back">Back</a></span><br/>
		</div>
		<div id="result">
			<table id="resultTable" class="table">
				<thead>
					<tr>
						<th></th>
						<th>Name*</th>
						<th>Description</th>
						<th></th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<tr class="emptyMessage"><td colspan="7">There are no items to display.</td></tr>
				</tbody>
			</table>
		</div>
	</div>
	
	<div id="dialog-form" title="">
		<form id="add">
			<label for="name">Name*</label>
			<input type="text" maxlength="50" name="name" id="name" value="" class="text ui-widget-content ui-corner-all"/>
			<br/>
			<label for="description">Description</label>
			<textarea rows="5" maxlength="200" name="description" id="description" class="text ui-widget-content ui-corner-all"></textarea>
		</form>
		<br/>
		<p id="errorContainer">Sample Error Container</p>
	</div>
	
	<input type="hidden" value="${pageContext.request.contextPath}" id="pageContext" />
	<script src="${pageContext.request.contextPath}/resources/jquery-1.12.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jquery-ui.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/ajax-loading.js"></script>
	<script src="${pageContext.request.contextPath}/resources/bootstrap-3.3.6-dist/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/AjaxControl.js"></script>
	<script src="${pageContext.request.contextPath}/resources/home.js"></script>
</body>
</html>