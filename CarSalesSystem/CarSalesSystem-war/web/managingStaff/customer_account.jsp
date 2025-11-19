<%-- 
    Document   : customer_account
    Created on : Apr 2, 2025, 10:21:10 AM
    Author     : Chew Jin Ni
--%>

<%@page import="model.Customer"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Account Information</title>
    <link rel="stylesheet" type="text/css" href="../css/table.css">
    <link rel="stylesheet" type="text/css" href="../css/notification.css">
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            window.toggleEditMode = function (button) {
                const row = button.closest('tr');
                const nameCell = row.querySelector('.name-cell');
                const genderCell = row.querySelector('.gender-cell');
                const ageCell = row.querySelector('.age-cell');
                const icCell = row.querySelector('.ic-cell');
                const emailCell = row.querySelector('.email-cell');
                const phoneCell = row.querySelector('.phone-cell');
                const addressCell = row.querySelector('.address-cell');
                const occupationCell = row.querySelector('.occupation-cell');
                const employmentCell = row.querySelector('.employment-cell');
                const salaryCell = row.querySelector('.salary-cell');
                const licenseCell = row.querySelector('.license-cell');

                // Toggle view mode and editable mode for fields
                nameCell.querySelector('.view-mode').style.display = 'none';
                nameCell.querySelector('.editable-field').style.display = 'inline-block';
                
                genderCell.querySelector('.view-mode').style.display = 'none';
                genderCell.querySelector('.editable-field').style.display = 'inline-block';
                
                ageCell.querySelector('.view-mode').style.display = 'none';
                ageCell.querySelector('.editable-field').style.display = 'inline-block';
                
                icCell.querySelector('.view-mode').style.display = 'none';
                icCell.querySelector('.editable-field').style.display = 'inline-block';

                emailCell.querySelector('.view-mode').style.display = 'none';
                emailCell.querySelector('.editable-field').style.display = 'inline-block';

                phoneCell.querySelector('.view-mode').style.display = 'none';
                phoneCell.querySelector('.editable-field').style.display = 'inline-block';
                
                addressCell.querySelector('.view-mode').style.display = 'none';
                addressCell.querySelector('.editable-field').style.display = 'inline-block';
                
                occupationCell.querySelector('.view-mode').style.display = 'none';
                occupationCell.querySelector('.editable-field').style.display = 'inline-block';
                
                employmentCell.querySelector('.view-mode').style.display = 'none';
                employmentCell.querySelector('.editable-field').style.display = 'inline-block';
                
                salaryCell.querySelector('.editable-field').style.display = 'inline-block';
                
                licenseCell.querySelector('.editable-field').style.display = 'inline-block';

                // Show Save and Cancel buttons, hide Edit button
                button.style.display = 'none';
                row.querySelector('.save-button').style.display = 'inline-block';
                row.querySelector('.cancel-button').style.display = 'inline-block';
            };
            
            // Function to save changes and revert to view mode
            window.saveChanges = function (user_id, button) {
                const row = button.closest('tr');
                const nameCell = row.querySelector('.name-cell');
                const genderCell = row.querySelector('.gender-cell');
                const ageCell = row.querySelector('.age-cell');
                const icCell = row.querySelector('.ic-cell');
                const emailCell = row.querySelector('.email-cell');
                const phoneCell = row.querySelector('.phone-cell');
                const addressCell = row.querySelector('.address-cell');
                const occupationCell = row.querySelector('.occupation-cell');
                const employmentCell = row.querySelector('.employment-cell');
                const salaryCell = row.querySelector('.salary-cell');
                const licenseCell = row.querySelector('.license-cell');

                // Hide editable fields and show view mode
                nameCell.querySelector('.view-mode').style.display = 'inline-block';
                nameCell.querySelector('.editable-field').style.display = 'none';
                
                genderCell.querySelector('.view-mode').style.display = 'inline-block';
                genderCell.querySelector('.editable-field').style.display = 'none';
                
                ageCell.querySelector('.view-mode').style.display = 'inline-block';
                ageCell.querySelector('.editable-field').style.display = 'none';
                
                icCell.querySelector('.view-mode').style.display = 'inline-block';
                icCell.querySelector('.editable-field').style.display = 'none';

                emailCell.querySelector('.view-mode').style.display = 'inline-block';
                emailCell.querySelector('.editable-field').style.display = 'none';

                phoneCell.querySelector('.view-mode').style.display = 'inline-block';
                phoneCell.querySelector('.editable-field').style.display = 'none';
                
                addressCell.querySelector('.view-mode').style.display = 'inline-block';
                addressCell.querySelector('.editable-field').style.display = 'none';
                
                occupationCell.querySelector('.view-mode').style.display = 'inline-block';
                occupationCell.querySelector('.editable-field').style.display = 'none';
                
                employmentCell.querySelector('.view-mode').style.display = 'inline-block';
                employmentCell.querySelector('.editable-field').style.display = 'none';
                
                salaryCell.querySelector('.editable-field').style.display = 'none';
                
                licenseCell.querySelector('.editable-field').style.display = 'none';
                
                // Show Edit button, hide Save and Cancel buttons
                row.querySelector('.edit-button').style.display = 'inline-block';
                button.style.display = 'none';
                row.querySelector('.cancel-button').style.display = 'none';
                
                // Submit form to save changes (optional)
                document.getElementById('updated_name_'+user_id).value = nameCell.querySelector('.editable-field').value;
                console.log(nameCell.querySelector('.editable-field').value);
                document.getElementById('updated_age_'+user_id).value = ageCell.querySelector('.editable-field').value;
                console.log(ageCell.querySelector('.editable-field').value);
                document.getElementById('updated_gender_'+user_id).value = genderCell.querySelector('.editable-field').value;
                document.getElementById('updated_ic_'+user_id).value = icCell.querySelector('.editable-field').value;
                document.getElementById('updated_email_'+user_id).value = emailCell.querySelector('.editable-field').value;
                document.getElementById('updated_phone_'+user_id).value = phoneCell.querySelector('.editable-field').value;
                document.getElementById('updated_address_'+user_id).value = addressCell.querySelector('.editable-field').value;
                document.getElementById('updated_occupation_'+user_id).value = occupationCell.querySelector('.editable-field').value;  
                document.getElementById('updated_employment_'+user_id).value = employmentCell.querySelector('.editable-field').value;
                document.getElementById('updateForm_'+user_id).submit();             
            };

            // Function to cancel edits and revert to view mode
            window.cancelEditMode = function (button) {
                const row = button.closest('tr');
                const nameCell = row.querySelector('.name-cell');
                const genderCell = row.querySelector('.gender-cell');
                const ageCell = row.querySelector('.age-cell');
                const icCell = row.querySelector('.ic-cell');
                const emailCell = row.querySelector('.email-cell');
                const phoneCell = row.querySelector('.phone-cell');
                const addressCell = row.querySelector('.address-cell');
                const occupationCell = row.querySelector('.occupation-cell');
                const employmentCell = row.querySelector('.employment-cell');
                const salaryCell = row.querySelector('.salary-cell');
                const licenseCell = row.querySelector('.license-cell');
                
                nameCell.querySelector('.editable-field').value = nameCell.querySelector('.view-mode').textContent;
                genderCell.querySelector('.editable-field').value = genderCell.querySelector('.view-mode').textContent;
                ageCell.querySelector('.editable-field').value = ageCell.querySelector('.view-mode').textContent;
                icCell.querySelector('.editable-field').value = icCell.querySelector('.view-mode').textContent;
                emailCell.querySelector('.editable-field').value = emailCell.querySelector('.view-mode').textContent;
                phoneCell.querySelector('.editable-field').value = phoneCell.querySelector('.view-mode').textContent;
                addressCell.querySelector('.editable-field').value = addressCell.querySelector('.view-mode').textContent;
                occupationCell.querySelector('.editable-field').value = occupationCell.querySelector('.view-mode').textContent;
                employmentCell.querySelector('.editable-field').value = employmentCell.querySelector('.view-mode').textContent;

                // Hide editable fields and show view mode
                nameCell.querySelector('.view-mode').style.display = 'inline-block';
                nameCell.querySelector('.editable-field').style.display = 'none';
                
                genderCell.querySelector('.view-mode').style.display = 'inline-block';
                genderCell.querySelector('.editable-field').style.display = 'none';
                
                ageCell.querySelector('.view-mode').style.display = 'inline-block';
                ageCell.querySelector('.editable-field').style.display = 'none';
                
                icCell.querySelector('.view-mode').style.display = 'inline-block';
                icCell.querySelector('.editable-field').style.display = 'none';

                emailCell.querySelector('.view-mode').style.display = 'inline-block';
                emailCell.querySelector('.editable-field').style.display = 'none';

                phoneCell.querySelector('.view-mode').style.display = 'inline-block';
                phoneCell.querySelector('.editable-field').style.display = 'none';
                
                addressCell.querySelector('.view-mode').style.display = 'inline-block';
                addressCell.querySelector('.editable-field').style.display = 'none';
                
                occupationCell.querySelector('.view-mode').style.display = 'inline-block';
                occupationCell.querySelector('.editable-field').style.display = 'none';
                
                employmentCell.querySelector('.view-mode').style.display = 'inline-block';
                employmentCell.querySelector('.editable-field').style.display = 'none';
                
                salaryCell.querySelector('.editable-field').style.display = 'none';
                
                licenseCell.querySelector('.editable-field').style.display = 'none';

                // Show Edit button, hide Save and Cancel buttons
                row.querySelector('.edit-button').style.display = 'inline-block';
                button.style.display = 'none';
                row.querySelector('.save-button').style.display = 'none';
            };
            
            document.getElementById('searchUsername').addEventListener('keydown', function (event) {
                // Check if the pressed key is "Enter" (key code 13)
                if (event.key === 'Enter') {
                    let searchTerm = this.value.trim(); // Get the search term and trim whitespace
                    searchTerm = searchTerm.toLowerCase();
                    document.getElementById('search_input').value = searchTerm;
                    document.getElementById('account_type').value = "customer";
                    document.getElementById('filterTable').submit();
                }
            });
            
            
            window.changeStateForm = function (user_id) {
                const isConfirmed = confirm("Confirm to deactivate this account?");
                if (isConfirmed) {            
                    document.getElementById('user_id').value = user_id;
                    document.getElementById('deactivateForm').submit();
                }
            };
                    
            window.confirmApprovalChange = function (user_id, status) {
                const isConfirmed = confirm("Confirm to change approval status?");
                if(isConfirmed) {
                    document.getElementById('status_user_id').value = user_id;
                    document.getElementById('status').value = status;
                    document.getElementById(`changeApprovalForm`).submit();
                }
            };
            
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
</head>
<body>
    <div id="notification" class="error_notification">
        <c:if test="${not empty sessionScope.manager_errorMsg}">
            ${sessionScope.manager_errorMsg}
            <% session.removeAttribute("manager_errorMsg");%>
        </c:if>  
    </div>
    <%@ include file="header.jsp" %>
    <div class="table-container">
        <h2 class="text-center my-4">Customer Account Information</h2>

        <!-- Search Bar -->
        <form id="filterTable" style="display: none" action="${pageContext.request.contextPath}/FilterTable" method="POST">
            <input type="hidden" id="search_input" name="search_input">  
            <input type="hidden" id="account_type" name="account_type">  
        </form>
        <div class="function-bar">
            <!-- Search Bar -->
            <input type="text" id="searchUsername" placeholder="Search by Username/Name/IC...">
        </div>
            
        <!-- Responsive Table -->
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">Username</th>
                        <th scope="col">Name</th>
                        <th scope="col">Gender</th>
                        <th scope="col">Age</th>
                        <th scope="col">IC</th>
                        <th scope="col">Email</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Address</th>
                        <th scope="col">Occupation</th>
                        <th scope="col">Employment</th>
                        <th scope="col">Salary Slip</th>
                        <th scope="col">Driving License</th>
                        <th scope="col">Status</th>
                        <th scope="col">Approval</th>
                        <th scope="col">Changes</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="customer" items="${sessionScope.customers_list}">
                    <tr>
                        <!-- Username -->
                            <td class="username-cell">
                                <span class="view-mode">${customer.getUser_id()}</span>
                            </td>

                            <!-- Name -->
                            <td class="name-cell">
                                <span class="view-mode">${customer.getName()}</span>
                                <input type="text" class="editable-field" value="${customer.getName()}">
                            </td>
                            
                            <!-- Gender -->
                            <td class="gender-cell">
                                <span class="view-mode">${customer.getGender()}</span>
                                <select class="editable-field">
                                    <option value="Male" ${customer.getGender() eq 'Male' ? 'selected' : ''}>Male</option>
                                    <option value="Female" ${customer.getGender() eq 'Female' ? 'selected' : ''}>Female</option>
                                </select>
                            </td>
                            
                             <!-- Age -->
                            <td class="age-cell">
                                <span class="view-mode">${customer.getAge()}</span>
                                <input type="number" class="editable-field" value="${customer.getAge()}">
                            </td>

                            <!-- IC -->
                            <td class="ic-cell">
                                <span class="view-mode">${customer.getIc()}</span>
                                <input type="text" class="editable-field" value="${customer.getIc()}">
                            </td>

                            <!-- Email -->
                            <td class="email-cell">
                                <span class="view-mode">${customer.getEmail()}</span>
                                <input type="email" class="editable-field" value="${customer.getEmail()}">
                            </td>

                            <!-- Phone -->
                            <td class="phone-cell">
                                <span class="view-mode">${customer.getPhone()}</span>
                                <input type="text" class="editable-field" value="${customer.getPhone()}">
                            </td>
                            
                            <!-- Address -->
                            <td class="address-cell">
                                <span class="view-mode">${customer.getAddress()}</span>
                                <textarea class="editable-field">${customer.getAddress()}</textarea>
                            </td>
                            
                            <!-- Occupation -->
                            <td class="occupation-cell">
                                <span class="view-mode">${customer.getOccupation()}</span>
                                <input type="text" class="editable-field" value="${customer.getOccupation()}">
                            </td>
                            
                            <!-- Employment Status -->
                            <td class="employment-cell">
                                <span class="view-mode">${customer.getEmployment_status()}</span>
                                <select class="editable-field">
                                    <option value="Employed" ${customer.getEmployment_status() eq 'Employed' ? 'selected' : ''}>Employed</option>
                                    <option value="Unemployed" ${customer.getEmployment_status() eq 'Unemployed' ? 'selected' : ''}>Unemployed</option>
                                    <option value="Freelancer" ${customer.getEmployment_status() eq 'Freelancer' ? 'selected' : ''}>Freelancer</option>
                                    <option value="Self_Employed" ${customer.getEmployment_status() eq 'Self_Employed' ? 'selected' : ''}>Self Employed</option>
                                    <option value="Contract" ${customer.getEmployment_status() eq 'Contract' ? 'selected' : ''}>Contract</option>
                                </select>
                            </td>
                            
                        <%
                            String basePath = "D:\\NetbeanProject\\CarSalesSystem\\CarSalesSystem-war\\web\\";
                            String salary_relativePath = ""; 
                            String license_relativePath = ""; 
                            String salary_absolutePath = ((Customer) pageContext.getAttribute("customer")).getSalary_slip();

                            if (salary_absolutePath != null && !salary_absolutePath.isEmpty()) {
                                if (salary_absolutePath.startsWith(basePath)) {
                                    salary_relativePath = "../" + salary_absolutePath.substring(basePath.length()).replace("\\", "/");
                                }
                            }
                            
                            String license_absolutePath = ((Customer) pageContext.getAttribute("customer")).getDriving_license();

                            if (license_absolutePath != null && !license_absolutePath.isEmpty()) {
                                if (license_absolutePath.startsWith(basePath)) {
                                    license_relativePath = "../" + license_absolutePath.substring(basePath.length()).replace("\\", "/");
                                }
                            }

                            // Store the processed relativePath in the request scope
                            pageContext.setAttribute("salary_relativePath", salary_relativePath);
                            pageContext.setAttribute("license_relativePath", license_relativePath);
                        %>
                        
                        <!-- Salary Slip -->
                        <td class="salary-cell">
                            <c:if test="${not empty salary_relativePath}">
                                <a href="${salary_relativePath}" target="_blank">View Salary</a>
                                <input type="file" class="editable-field" name="salary_slip" accept="image/*" form="updateForm_${customer.getUser_id()}">
                            </c:if>  
                        </td>
                        
                        <!-- Driving License -->
                        <td class="license-cell">
                            <c:if test="${not empty license_relativePath}">
                                <a href="${license_relativePath}" target="_blank">View License</a>
                                <input type="file" class="editable-field" name="driving_license" accept="image/*" form="updateForm_${customer.getUser_id()}">
                            </c:if>
                        </td>
                        
                        <!-- Account State -->
                        <td>
                            <c:choose>
                                <c:when test="${customer.state eq 'Active'}">
                                    <span class="badge bg-success">${customer.getState()}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-danger">${customer.getState()}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        
                        <!-- Account Approval -->
                        <form id="changeApprovalForm" action="${pageContext.request.contextPath}/UpdateStatus" method="POST" style="display: none;">
                            <input type="hidden" name="status_user_id" id="status_user_id">
                            <input type="hidden" name="status" id="status">
                        </form>
                        <td>
                            <select id="approvalStatus" onchange="confirmApprovalChange('${customer.getUser_id()}', this.value)" 
                                    style="${customer.getApproval() eq 'Pending' ? 'background-color: #ffc107;' : 
                                            customer.getApproval() eq 'Approved' ? 'background-color: #78ff9c;' : 
                                            customer.getApproval() eq 'Rejected' ? 'background-color: #ff6363;' : ''}">
                                
                                <option style="background-color: white" value="Pending"
                                    <c:if test="${customer.getApproval() eq 'Pending'}">selected</c:if>
                                >Pending</option>

                                <option style="background-color: white" value="Approved"
                                    <c:if test="${customer.getApproval() eq 'Approved'}">selected</c:if>
                                >Approved</option>

                                <option style="background-color: white" value="Rejected"
                                    <c:if test="${customer.getApproval() eq 'Rejected'}">selected</c:if>
                                >Rejected</option>
                            </select>                        
                        </td>
                        
                        <!-- Edit Mode Button -->
                        <td>
                            <button class="btn btn-primary edit-button" onclick="toggleEditMode(this)">Edit</button>
                            <button class="btn btn-success save-button" onclick="saveChanges('${customer.getUser_id()}', this)" style="display: none;">Save</button>
                            <button class="btn btn-secondary cancel-button" onclick="cancelEditMode(this)" style="display: none;">Cancel</button>
                            
                            <!-- Form to submit updates -->
                            <form id="updateForm_${customer.getUser_id()}" action="${pageContext.request.contextPath}/EditCustomerAccount" method="POST" style="display: none;" enctype="multipart/form-data">
                                <input type="hidden" name="user_id" value="${customer.getUser_id()}">
                                <input type="hidden" name="name" id="updated_name_${customer.getUser_id()}">
                                <input type="hidden" name="gender" id="updated_gender_${customer.getUser_id()}">
                                <input type="hidden" name="age" id="updated_age_${customer.getUser_id()}">
                                <input type="hidden" name="ic" id="updated_ic_${customer.getUser_id()}">
                                <input type="hidden" name="email" id="updated_email_${customer.getUser_id()}">
                                <input type="hidden" name="phone" id="updated_phone_${customer.getUser_id()}">
                                <input type="hidden" name="address" id="updated_address_${customer.getUser_id()}">
                                <input type="hidden" name="occupation" id="updated_occupation_${customer.getUser_id()}">
                                <input type="hidden" name="employment" id="updated_employment_${customer.getUser_id()}">
                            </form>
                        </td>
                        
                        <!-- Activate/Deactivate Action -->
                        <td>
                            <form id="deactivateForm" action="${pageContext.request.contextPath}/DeactivateAccount" method="POST" style="display: none;">
                                <input type="hidden" name="user_id" id="user_id">
                            </form>
                            <c:choose>
                                <c:when test="${customer.state eq 'Active'}">
                                    <button class="btn btn-sm btn-danger" onclick="changeStateForm('${customer.getUser_id()}')">Deactivate</button>
                                </c:when>
                                <c:otherwise>
                                    <button class="btn btn-sm btn-success" onclick="changeStateForm('${customer.getUser_id()}')">Activate</button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>  
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>