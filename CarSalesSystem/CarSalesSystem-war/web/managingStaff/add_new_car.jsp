<%-- 
    Document   : add_new_car
    Created on : Apr 9, 2025, 5:16:26 PM
    Author     : Chew Jin Ni
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Car</title>
    <link rel="stylesheet" type="text/css" href="../css/add_car.css">
    <link rel="stylesheet" type="text/css" href="../css/notification.css">
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
</head>
<body>
    <div id="notification" class="error_notification">
        <c:if test="${not empty sessionScope.manager_errorMsg}">
            ${sessionScope.manager_errorMsg}
            <% session.removeAttribute("manager_errorMsg");%>
        </c:if>  
    </div>
    
    <%@ include file="header.jsp" %>
    
    <div class="form-container"> 
        <h2>Add New Car</h2>
        <form id="addCarForm" action="${pageContext.request.contextPath}/AddNewCar" method="POST" enctype="multipart/form-data">
            <div class="form-group">
                <label for="model_name">Model Name:</label>
                <input type="text" id="model_name" name="model_name">
            </div>
            <div class="form-group">
                <label for="brand">Brand:</label>
                <input type="text" id="brand" name="brand">
            </div>
            <div class="form-group">
                <label for="fuel_type">Fuel Type:</label>
                <input type="text" id="fuel_type" name="fuel_type">
            </div>
            <div class="form-group">
                <label for="horsepower">Horsepower:</label>
                <input type="number" min="1" value="1" id="horsepower" name="horsepower">
            </div>
            <div class="form-group">
                <label for="seats">Seats:</label>
                <input type="number" min="1" value="1" id="seats" name="seats">
            </div>
            <div class="form-group">
                <label for="transmission_type">Transmission Type:</label>
                <input type="text" id="transmission_type" name="transmission">
            </div>
            <div class="form-group">
                <label for="colour">Colour:</label>
                <input type="text" id="colour" name="colour" required>
            </div>
            <div class="form-group">
                <label for="price">Price:</label>
                <input type="number" min="1" step="0.01" value="1" id="price" name="price">
            </div>
            <div class="form-group">
                <label for="year_of_manufacture">Year of Manufacture:</label>
                <input type="number" id="year_of_manufacture" name="year">
            </div>
            <div class="form-group">
                <label for="car_image">Upload Car Image:</label>
                <input type="file" id="car_image" name="car_image" accept="image/*">
            </div>
            <button type="submit" class="btn btn-primary">Add Car</button>
        </form>
    </div>
</body>
</html>
