<%-- 
    Document   : managing_staff_account
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
    <title>Managing Staff Account Information</title>
    <link rel="stylesheet" type="text/css" href="../css/table.css">
    <link rel="stylesheet" type="text/css" href="../css/notification.css">
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            // Function to toggle edit mode for a specific row
            window.toggleEditMode = function (button) {
                const row = button.closest('tr');
                const nameCell = row.querySelector('.name-cell');
                const icCell = row.querySelector('.ic-cell');
                const emailCell = row.querySelector('.email-cell');
                const phoneCell = row.querySelector('.phone-cell');

                // Toggle view mode and editable mode for fields
                nameCell.querySelector('.view-mode').style.display = 'none';
                nameCell.querySelector('.editable-field').style.display = 'inline-block';

                icCell.querySelector('.view-mode').style.display = 'none';
                icCell.querySelector('.editable-field').style.display = 'inline-block';

                emailCell.querySelector('.view-mode').style.display = 'none';
                emailCell.querySelector('.editable-field').style.display = 'inline-block';

                phoneCell.querySelector('.view-mode').style.display = 'none';
                phoneCell.querySelector('.editable-field').style.display = 'inline-block';

                // Show Save and Cancel buttons, hide Edit button
                button.style.display = 'none';
                row.querySelector('.save-button').style.display = 'inline-block';
                row.querySelector('.cancel-button').style.display = 'inline-block';
            };

            // Function to save changes and revert to view mode
            window.saveChanges = function (user_id, button) {
                const row = button.closest('tr');
                const nameCell = row.querySelector('.name-cell');
                const icCell = row.querySelector('.ic-cell');
                const emailCell = row.querySelector('.email-cell');
                const phoneCell = row.querySelector('.phone-cell');

                // Hide editable fields and show view mode

                nameCell.querySelector('.view-mode').style.display = 'inline-block';
                nameCell.querySelector('.editable-field').style.display = 'none';

                icCell.querySelector('.view-mode').style.display = 'inline-block';
                icCell.querySelector('.editable-field').style.display = 'none';

                emailCell.querySelector('.view-mode').style.display = 'inline-block';
                emailCell.querySelector('.editable-field').style.display = 'none';

                phoneCell.querySelector('.view-mode').style.display = 'inline-block';
                phoneCell.querySelector('.editable-field').style.display = 'none';

                // Show Edit button, hide Save and Cancel buttons
                row.querySelector('.edit-button').style.display = 'inline-block';
                button.style.display = 'none';
                row.querySelector('.cancel-button').style.display = 'none';
                
                // Submit form to save changes (optional)
                document.getElementById('updated_name_'+user_id).value = nameCell.querySelector('.editable-field').value;
                document.getElementById('updated_ic_'+user_id).value = icCell.querySelector('.editable-field').value;
                document.getElementById('updated_email_'+user_id).value = emailCell.querySelector('.editable-field').value;
                document.getElementById('updated_phone_'+user_id).value = phoneCell.querySelector('.editable-field').value;
                document.getElementById('updateForm_'+user_id).submit();
            };

            // Function to cancel edits and revert to view mode
            window.cancelEditMode = function (button) {
                const row = button.closest('tr');
                const nameCell = row.querySelector('.name-cell');
                const icCell = row.querySelector('.ic-cell');
                const emailCell = row.querySelector('.email-cell');
                const phoneCell = row.querySelector('.phone-cell');
                
                nameCell.querySelector('.editable-field').value = nameCell.querySelector('.view-mode').textContent;
                icCell.querySelector('.editable-field').value = icCell.querySelector('.view-mode').textContent;
                emailCell.querySelector('.editable-field').value = emailCell.querySelector('.view-mode').textContent;
                phoneCell.querySelector('.editable-field').value = phoneCell.querySelector('.view-mode').textContent;
                
                // Hide editable fields and show view mode
                nameCell.querySelector('.view-mode').style.display = 'inline-block';
                nameCell.querySelector('.editable-field').style.display = 'none';

                icCell.querySelector('.view-mode').style.display = 'inline-block';
                icCell.querySelector('.editable-field').style.display = 'none';

                emailCell.querySelector('.view-mode').style.display = 'inline-block';
                emailCell.querySelector('.editable-field').style.display = 'none';

                phoneCell.querySelector('.view-mode').style.display = 'inline-block';
                phoneCell.querySelector('.editable-field').style.display = 'none';

                // Show Edit button, hide Save and Cancel buttons
                row.querySelector('.edit-button').style.display = 'inline-block';
                button.style.display = 'none';
                row.querySelector('.save-button').style.display = 'none';
            };

            // Existing functions for deactivation and approval status change
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
                    document.getElementById('changeApprovalForm').submit();
                }
            };
            
            window.addNewStaff = function () {
                const isConfirmed = confirm("Confirm to add new staff?");
                if(isConfirmed) {
                    window.location.href = "../register_staff.jsp";
                }
            };
            
            document.getElementById('searchUsername').addEventListener('keydown', function (event) {
                // Check if the pressed key is "Enter" (key code 13)
                if (event.key === 'Enter') {
                    let searchTerm = this.value.trim(); // Get the search term and trim whitespace
                    searchTerm = searchTerm.toLowerCase();
                    document.getElementById('search_input').value = searchTerm;
                    document.getElementById('account_type').value = "managing_staff";
                    document.getElementById('filterTable').submit();
                }
            });
            
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
        <h2 class="text-center my-4">Managing Staff Account Information</h2>
        
        <form id="filterTable" style="display: none" action="${pageContext.request.contextPath}/FilterTable" method="POST">
            <input type="hidden" id="search_input" name="search_input">  
            <input type="hidden" id="account_type" name="account_type">  
        </form>
        <div class="function-bar">
            <button class="btn btn-primary edit-button" onclick="addNewStaff()">Add New Staff</button>
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
                        <th scope="col">IC</th>
                        <th scope="col">Email</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Account Status</th>
                        <th scope="col">Approval</th>
                        <th scope="col">Modification</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="managing_staff" items="${sessionScope.managing_staff_list}">
                        <tr id="${managing_staff.getUser_id()}">
                            <!-- Username -->
                            <td class="username-cell">
                                <span class="view-mode">${managing_staff.getUser_id()}</span>
                            </td>

                            <!-- Name -->
                            <td class="name-cell">
                                <span class="view-mode">${managing_staff.getName()}</span>
                                <input type="text" class="editable-field" value="${managing_staff.getName()}">
                            </td>

                            <!-- IC -->
                            <td class="ic-cell">
                                <span class="view-mode">${managing_staff.getIc()}</span>
                                <input type="text" class="editable-field" value="${managing_staff.getIc()}">
                            </td>

                            <!-- Email -->
                            <td class="email-cell">
                                <span class="view-mode">${managing_staff.getEmail()}</span>
                                <input type="email" class="editable-field" value="${managing_staff.getEmail()}">
                            </td>

                            <!-- Phone -->
                            <td class="phone-cell">
                                <span class="view-mode">${managing_staff.getPhone()}</span>
                                <input type="text" class="editable-field" value="${managing_staff.getPhone()}">
                            </td>

                            <!-- Account Status -->
                            <td>
                                <c:choose>
                                    <c:when test="${managing_staff.state eq 'Active'}">
                                        <span class="badge bg-success">${managing_staff.getState()}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger">${managing_staff.getState()}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <!-- Approval -->
                            <form id="changeApprovalForm" action="${pageContext.request.contextPath}/UpdateStatus" method="POST" style="display: none;">
                                <input type="hidden" name="status_user_id" id="status_user_id">
                                <input type="hidden" name="status" id="status">
                            </form>
                            <td>
                                <select id="approvalStatus" onchange="confirmApprovalChange('${managing_staff.getUser_id()}', this.value)" 
                                        style="${managing_staff.getApproval() eq 'Pending' ? 'background-color: #ffc107;' : 
                                                managing_staff.getApproval() eq 'Approved' ? 'background-color: #78ff9c;' : 
                                                managing_staff.getApproval() eq 'Rejected' ? 'background-color: #ff6363;' : ''}">
                                    <option style="background-color: white" value="Pending"
                                        <c:if test="${managing_staff.getApproval() eq 'Pending'}">selected</c:if>
                                    >Pending</option>
                                    <option style="background-color: white" value="Approved"
                                        <c:if test="${managing_staff.getApproval() eq 'Approved'}">selected</c:if>
                                    >Approved</option>
                                    <option style="background-color: white" value="Rejected"
                                        <c:if test="${managing_staff.getApproval() eq 'Rejected'}">selected</c:if>
                                    >Rejected</option>
                                </select>                        
                            </td>

                            <!-- Edit Mode Buttons -->
                            <td>
                                <button class="btn btn-primary edit-button" onclick="toggleEditMode(this)">Edit</button>
                                <button class="btn btn-success save-button" onclick="saveChanges('${managing_staff.getUser_id()}', this)" style="display: none;">Save</button>
                                <button class="btn btn-secondary cancel-button" onclick="cancelEditMode(this)" style="display: none;">Cancel</button>

                                <!-- Form to submit updates -->
                                <form id="updateForm_${managing_staff.getUser_id()}" action="${pageContext.request.contextPath}/EditStaffAccount" method="POST" style="display: none;">
                                    <input type="hidden" name="user_id" value="${managing_staff.getUser_id()}">
                                    <input type="hidden" name="name" id="updated_name_${managing_staff.getUser_id()}">
                                    <input type="hidden" name="ic" id="updated_ic_${managing_staff.getUser_id()}">
                                    <input type="hidden" name="email" id="updated_email_${managing_staff.getUser_id()}">
                                    <input type="hidden" name="phone" id="updated_phone_${managing_staff.getUser_id()}">
                                </form>
                            </td>

                            <!-- Activate/Deactivate Action -->
                            <td>
                                <form id="deactivateForm" action="${pageContext.request.contextPath}/DeactivateAccount" method="POST" style="display: none;">
                                    <input type="hidden" name="user_id" id="user_id">
                                </form>
                                <c:choose>
                                    <c:when test="${managing_staff.state eq 'Active'}">
                                        <button class="btn btn-sm btn-danger" onclick="changeStateForm('${managing_staff.getUser_id()}')">Deactivate</button>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-sm btn-success" onclick="changeStateForm('${managing_staff.getUser_id()}')">Activate</button>
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
