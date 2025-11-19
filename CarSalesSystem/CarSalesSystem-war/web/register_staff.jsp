<%-- 
    Document   : registerStaff
    Created on : Mar 16, 2025, 7:26:20 PM
    Author     : Chew Jin Ni
--%>

<%@page import="model.Users.Role"%>
<%@page import="model.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Registration Page</title>
    <link rel="stylesheet" type="text/css" href="css/registration.css">
    <link rel="stylesheet" type="text/css" href="css/notification.css">
    <script>
        window.onload = function () {
            showStep(1);
            showNotification();
        };

        function showStep(step) {
            let steps = document.getElementsByClassName("step");
            for (let i = 0; i < steps.length; i++) {
                steps[i].style.display = "none";
            }
            document.getElementById("step" + step).style.display = "block";
        }

        function nextStep(current, next) {
            showStep(next);
        }

        function showNotification() {
            let errorNotification = document.getElementById("errorNotification");

            if (errorNotification && errorNotification.textContent.trim() !== "") {
                errorNotification.style.display = "block";
                setTimeout(() => {
                    errorNotification.style.display = "none";
                }, 5000);
            }
        }
    </script>
</head>
<body>
    <c:if test="${not empty sessionScope.user and sessionScope.user.role eq 'Managing_Staff'}">
        <%@include file="managingStaff/header.jsp" %>
    </c:if>
    <div id="errorNotification" class="error_notification">
        <%= request.getAttribute("errorMsg") != null ? request.getAttribute("errorMsg") : "" %>
    </div>
    <div class="register-container">
        <!-- Left Section: Image -->
        <div class="image-container">
            <img src="image/vector.jpg" alt="Logo" class="logo">
        </div>
        <!-- Right Section: Login Form -->
        <div class="form-container">
            <h2>Staff Registration</h2>
            <form action="RegisterStaff" method="POST">
                <!-- Step 1: Personal Info -->
                <div id="step1" class="step">
                    <div class="form-group">
                        <label>Full Name</label>
                        <input type="text" name="name" id=""name value="${not empty param.name ? param.name : ''}">
                    </div>
                    <div class="form-group">
                        <label>IC</label>
                        <input type="text" name="ic" id="ic" placeholder="991004081245" value="${not empty param.ic ? param.ic : ''}">
                    </div>
                    <div class="form-group">
                        <label>Phone</label>
                        <input type="text" name="phone" id="phone" placeholder="012-1231475" value="${not empty param.phone ? param.phone : ''}">
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" id="email" placeholder="abc@gmail.com" value="${not empty param.email ? param.email : ''}">
                    </div>
                    <div class="form-group">
                        <label>Role</label>
                        <select name="role" id="role" required value="${not empty param.role ? param.role : ''}">
                            <option value="Salesman">Salesman</option>
                            <option value="Managing_Staff">Managing Staff</option>
                        </select>
                    </div>
                    <button type="button" onclick="nextStep(1, 2)">Next</button>
                </div>

                <div id="step2" class="step">
                    <div class="form-group">
                        <label>Username</label>
                        <input type="text" name="username" id="username" value="${not empty param.username ? param.username : ''}">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password" id="password" placeholder="Password length more than 8 with uppercase, lowercase, digits and special characters">
                    </div>
                    <div class="form-group">
                        <label>Confirm Password</label>
                        <input type="password" name="confirm_password" id="confirm_password">
                    </div>
                    <div class="button-container">
                        <button type="button" onclick="nextStep(2, 1)">Back</button>
                        <button type="submit" value="RegisterStaff">Register</button>
                    </div> 
                </div>
            </form>
        </div>
    </div>
</body>
</html>


