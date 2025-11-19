<%-- 
    Document   : register
    Created on : Mar 15, 2025, 9:08:01 PM
    Author     : Chew Jin Ni
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Registration Page</title>
    <link rel="stylesheet" type="text/css" href="css/registration.css">
    <link rel="stylesheet" type="text/css" href="css/notification.css">
    <script>
        window.onload = ("DOMContentLoaded", function () {
            showStep(1);
            showNotification();
        });

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
                console.log("shown1");

                errorNotification.style.display = "block";
                
                setTimeout(() => {
                    errorNotification.style.display = "none";
                    console.log("shown2");
                }, 5000);
            }
        }
    </script>
</head>
<body>
    <div id="errorNotification" class="error_notification">
        <%= request.getAttribute("errorMsg") != null ? request.getAttribute("errorMsg") : "" %>
    </div>
    <div class="register-container">
        <div class="image-container">
            <img src="image/vector.jpg" alt="Registration">
        </div>
        <div class="form-container">
            <h2>Customer Registration</h2>
            <form action="RegisterCustomer" method="POST" enctype="multipart/form-data">
                <!-- Step 1: Personal Info -->
                <div id="step1" class="step">
                    <div class="form-group">
                        <label>Full Name</label>
                        <input type="text" name="name" value="${not empty param.name ? param.name : ''}">
                    </div>
                    <div class="form-group">
                        <label>Age</label>
                        <input type="number" name="age" value="${not empty param.age ? param.age : ''}">
                    </div>
                    <div class="form-group">
                        <label>Gender</label>
                        <select name="gender">
                            <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                            <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" name="email" value="${not empty param.email ? param.email : ''}">
                    </div>
                    <div class="form-group">
                        <label>Phone</label>
                        <input type="text" name="phone" value="${not empty param.phone ? param.phone : ''}">
                    </div>
                    <button type="button" onclick="nextStep(1, 2)">Next</button>
                    <p>Already have an account? <a href="login.jsp">Login here</a></p>
                </div>

                <!-- Step 2: Personal Info -->
                <div id="step2" class="step">
                    <div class="form-group">
                        <label>IC Number</label>
                        <input type="text" name="ic" value="${not empty param.ic ? param.ic : ''}">
                    </div>
                    <div class="form-group">
                        <label>Address</label>
                        <textarea name="address">${not empty param.address ? param.address : ''}</textarea>
                    </div>
                    <div class="form-group">
                        <label>Employment Status</label>
                        <select name="employment_status">
                            <option value="Employed" ${param.employment_status == 'Employed' ? 'selected' : ''}>Employed</option>
                            <option value="Unemployed" ${param.employment_status == 'Unemployed' ? 'selected' : ''}>Unemployed</option>
                            <option value="Self_Employed" ${param.employment_status == 'Self_Employed' ? 'selected' : ''}>Self-Employed</option>
                            <option value="Freelancer" ${param.employment_status == 'Freelancer' ? 'selected' : ''}>Freelancer</option>
                            <option value="Contract" ${param.employment_status == 'Contract' ? 'selected' : ''}>Contract</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Occupation</label>
                        <input type="text" name="occupation" value="${not empty param.occupation ? param.occupation : ''}">
                    </div>
                    <button type="button" onclick="nextStep(2, 1)">Back</button>
                    <button type="button" onclick="nextStep(2, 3)">Next</button>
                </div>
                
                <!-- Step 3: Upload Documents -->
                <div id="step3" class="step">
                    <div class="form-group">
                        <label>Upload Salary Slip</label>
                        <input type="file" name="salary_slip" accept=".jpg,.png,.pdf">
                    </div>
                    <div class="form-group">
                        <label>Upload Driving License</label>
                        <input type="file" name="driving_license" accept=".jpg,.png,.pdf">
                    </div>
                    <button type="button" onclick="nextStep(3, 2)">Back</button>
                    <button type="button" onclick="nextStep(3, 4)">Next</button>
                </div>
                    
                <!-- Step 4: Create Account -->
                <div id="step4" class="step">
                    <div class="form-group">
                        <label>Username</label>
                        <input type="text" name="username" value="${not empty param.username ? param.username : ''}">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" name="password">
                    </div>
                    <div class="form-group">
                        <label>Confirm Password</label>
                        <input type="password" name="confirm_password">
                    </div>
                    <button type="button" onclick="nextStep(4, 3)">Back</button>
                    <button type="submit" value="RegisterCustomer">Register</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>