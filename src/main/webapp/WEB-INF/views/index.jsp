<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>File Uploader</title>
</head>
<body>

  <h1>File Uploader</h1>
  <hr />

  <h3>Single File Upload</h3>
  <form action="/upload" method="post" accept-charset="utf-8" enctype="multipart/form-data">
    <table>
      <tr>
        <td>Select File</td>
        <td><input type="text" name ="fileName"></td>
        <td><input type="file" name="file"></td>
        <td><input type="submit" value="Upload"></td>
      </tr>
    </table>
  </form>
  <br>
  <hr />
  <span style="color: red; font-size: 14px;">${displayFileName}</span>
  <br>
  <span style="color: green; font-size: 14px;">${multipartFile}</span>
</body>
</html>