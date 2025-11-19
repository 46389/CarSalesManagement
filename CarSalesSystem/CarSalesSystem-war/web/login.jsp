<%-- 
    Document   : login
    Created on : Mar 15, 2025, 9:50:00 PM
    Author     : Chew Jin Ni
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <link rel="stylesheet" type="text/css" href="css/notification.css">
</head>
<body>
    <div id="notification" class="error_notification">
        <%= request.getAttribute("errorMsg") != null ? request.getAttribute("errorMsg") : "" %>
    </div>
    <div class="login-container">
        <!-- Left Section: Image -->
        <div class="image-container">
            <img src="image/logo.png" alt="Logo" class="logo">
        </div>

        <!-- Right Section: Login Form -->
        <div class="form-container">
            <h2>Login</h2>
            <form action="Login" method="POST">
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="username" id="username" >
                </div>

                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" id="password" >
                </div>
                <button type="submit" value="Login">Login</button>

                <p>Don't have an account? <a href="register_customer.jsp">Register here</a></p>
            </form>
        </div>
    </div>
    
    <script>
        window.onload = function () {
            let notification = document.getElementById("notification");
            if (notification.textContent.trim() !== "") {
                notification.style.display = "block";
                setTimeout(() => {
                    notification.style.display = "none";
                }, 5000);
            }
        };
    </script>
</body>
</html>
