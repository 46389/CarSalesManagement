<%-- 
    Document   : profile
    Created on : Mar 30, 2025, 3:10:58 PM
    Author     : Chew Jin Ni
--%>

<%@page import="model.Users"%>
<%@page import="model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../css/notification.css">
    <link rel="stylesheet" type="text/css" href="../css/customer_profile.css">
    <title>Edit Profile</title>
</head>
    <script>
        
        function openPasswordModal() {
            document.getElementById('passwordModal').style.display = 'block';
        }

        function closePasswordModal() {
            document.getElementById('passwordModal').style.display = 'none';
        }
       
        document.addEventListener("DOMContentLoaded", function () {
            window.onload = function () {
                let notification = document.getElementById("notification");
                if (notification.textContent.trim() !== "") {
                    notification.style.display = "block";
                    setTimeout(() => {
                        notification.style.display = "none";
                    }, 5000);
                }
            };
        });
    </script>    
<body>
    <%
        if(session.getAttribute("user") != null){
            Users user = (Users) session.getAttribute("user");
            if(user == null || user.getRole() == null || user.getRole() != Users.Role.Customer){
                response.sendRedirect("../login.jsp");
                return;
            }
        }else{
            response.sendRedirect("../login.jsp");
            return;
        }
    %>
    <div id="notification" class="error_notification">
        <c:if test="${not empty sessionScope.customer_errorMsg}">
            ${sessionScope.customer_errorMsg}
            <% session.removeAttribute("customer_errorMsg");%>
        </c:if>  
    </div>
    <%@ include file="header.jsp" %>
    <div class="profile-container">
        <h2>Edit Profile</h2>
        <form method="POST" action="${pageContext.request.contextPath}/UpdateCustomerProfile" enctype="multipart/form-data">
            <input type="hidden" name="type" id="type" value="edit_profile" >
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${not empty sessionScope.user ? sessionScope.user.user_id: ''}" readonly>
            </div>      
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${not empty sessionScope.user ? sessionScope.user.name: ''}">
            </div>
            <div class="form-group">
                <label for="age">Age: </label>
                <input type="number" id="age" name="age" min="1" value="${not empty sessionScope.user ? sessionScope.user.age: ''}">
            </div> 
            <div class="form-group">
                <label for="gender">Gender:</label>
                <select id="gender" name="gender" class="form-control">
                    <option value="Male" ${sessionScope.user.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${sessionScope.user.gender == 'Female' ? 'selected' : ''}>Female</option>
                </select>
            </div> 
            <div class="form-group">
                <label for="ic">IC:</label>
                <input type="text" id="ic" name="ic" value="${not empty sessionScope.user ? sessionScope.user.ic: ''}">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${not empty sessionScope.user ? sessionScope.user.email: ''}">
            </div>
            <div class="form-group">
                <label for="phone">Phone:</label>
                <input type="tel" id="phone" name="phone" value="${not empty sessionScope.user ? sessionScope.user.phone: ''}">
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="${not empty sessionScope.user ? sessionScope.user.address: ''}">
            </div>  
            <div class="form-group">
                <label for="employment">Employment Status:</label>
                <select id="employment" name="employment" class="form-control">
                    <option value="Employed" ${sessionScope.user.employment_status == 'Employed' ? 'selected' : ''}>Employed</option>
                    <option value="Unemployed" ${sessionScope.user.employment_status == 'Unemployed' ? 'selected' : ''}>Unemployed</option>
                    <option value="Self_Employed" ${sessionScope.user.employment_status == 'Self_Employed' ? 'selected' : ''}>Self Employed</option>
                    <option value="Freelancer" ${sessionScope.user.employment_status == 'Freelancer' ? 'selected' : ''}>Freelancer</option>
                    <option value="Contract" ${sessionScope.user.employment_status == 'Contract' ? 'selected' : ''}>Contract</option>
                </select>            
            </div>  
            <div class="form-group">
                <label for="occupation">Occupation:</label>
                <input type="text" id="occupation" name="occupation" value="${not empty sessionScope.user ? sessionScope.user.occupation: ''}">
            </div>  
            <%
                
                String basePath = "D:\\NetbeanProject\\CarSalesSystem\\CarSalesSystem-war\\web\\";
                String salary_relativePath = ""; 
                String license_relativePath = ""; 
                Customer customer = (Customer) session.getAttribute("user");
                String salary_absolutePath = customer.getSalary_slip();

                if (salary_absolutePath != null && !salary_absolutePath.isEmpty()) {
                    if (salary_absolutePath.startsWith(basePath)) {
                        salary_relativePath = "../" + salary_absolutePath.substring(basePath.length()).replace("\\", "/");
                    }
                }

                String license_absolutePath = customer.getDriving_license();

                if (license_absolutePath != null && !license_absolutePath.isEmpty()) {
                    if (license_absolutePath.startsWith(basePath)) {
                        license_relativePath = "../" + license_absolutePath.substring(basePath.length()).replace("\\", "/");
                    }
                }

                // Store the processed relativePath in the request scope
                pageContext.setAttribute("salary_relativePath", salary_relativePath);
                pageContext.setAttribute("license_relativePath", license_relativePath);
            %>
            
            <td>
                <div class="form-group">
                    <label>Salary Slip:</label>
                    <c:if test="${not empty salary_relativePath}">
                        <div class="flex-container">
                            <button class="view-button" type="button" onclick="window.open('${salary_relativePath}', '_blank')">View Salary</button>
                            <div class="file-upload-container">
                                <label class="file-upload-btn" for="salary_slip">Choose File</label>
                                <input type="file" id="salary_slip" name="salary_slip" accept="image/*" class="file-upload-input">
                                <span class="file-upload-label">No file chosen</span>
                            </div>
                        </div>
                    </c:if>
                </div>
            </td>
                
            <td>
                <div class="form-group">
                    <label>Driving License:</label>
                    <c:if test="${not empty license_relativePath}">
                        <div class="flex-container">
                            <button class="view-button" type="button" onclick="window.open('${license_relativePath}', '_blank')">View License</button>
                            <div class="file-upload-container">
                                <label class="file-upload-btn" for="driving_license">Choose File</label>
                                <input type="file" id="driving_license" name="driving_license" accept="image/*" class="file-upload-input">
                                <span class="file-upload-label">No file chosen</span>
                            </div>
                        </div>
                    </c:if>
                </div>
            </td>

            <button type="submit" class="btn-save">Save Changes</button>
            <button type="button" onclick="openPasswordModal()" class="btn-cancel">Change Password</button>
        </form>
    </div>

   <!-- Password Change Modal -->
    <div class="modal" id="passwordModal">
        <div class="modal-content">
            <h3>Change Password</h3>
            <br>
            <form method="POST" action="${pageContext.request.contextPath}/UpdateCustomerProfile">
                <input type="hidden" name="type" id="type" value="edit_password">
                <div class="form-group">
                    <label for="oldPassword">Old Password:</label>
                    <input type="password" id="oldPassword" name="oldPassword" required>
                </div>
                <div class="form-group">
                    <label for="newPassword">New Password:</label>
                    <input type="password" id="newPassword" name="newPassword" placeholder="Password length more than 8 with uppercase, lowercase, digits and special characters" required>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">Confirm New Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
                <button type="submit" class="btn-save">Save Changes</button>
                <button type="button" onclick="closePasswordModal()" class="btn-cancel">Cancel</button>
            </form>
        </div>
    </div>
</body>
</html>