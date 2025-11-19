<%-- 
    Document   : blackisted_customer
    Created on : Mar 28, 2025, 9:07:43 AM
    Author     : Chew Jin Ni
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blacklisted Customer Page</title>
        <link rel="stylesheet" type="text/css" href="../css/sales_table.css">
        <link rel="stylesheet" type="text/css" href="../css/notification.css">
    </head>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            window.confirmRequestChange = function (blacklist_id, request_status) {
                const isConfirmed = confirm("Confirm to change status of this blacklist request?");
                if (isConfirmed) {            
                    document.getElementById('request_blacklist_id').value = blacklist_id;
                    document.getElementById('request_status').value = request_status;
                    document.getElementById('requestStatusForm').submit();
                }
            };
            
            window.deleteRequest = function (blacklist_id) {
                const isConfirmed = confirm("Confirm to delete this request?");
                if (isConfirmed) {            
                    document.getElementById('blacklist_id').value = blacklist_id;
                    document.getElementById('deleteRequestForm').submit();
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
    <body>
        <div id="notification" class="error_notification">
            <c:if test="${not empty sessionScope.manager_errorMsg}">
                ${sessionScope.manager_errorMsg}
                <% session.removeAttribute("manager_errorMsg");%>
            </c:if>  
        </div>
        <%@ include file="header.jsp" %>
        <!-- Tickets Table -->
        <div class="table-container">
            <h2 class="text-center my-4">Blacklist Requests Review</h2>

            <!-- Responsive Table -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Customer ID</th>
                            <th scope="col">Customer Name</th>
                            <th scope="col">Customer IC</th>
                            <th scope="col">Blacklist Reason</th>
                            <th scope="col">Status</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="customer" items="${sessionScope.blacklist_customers}">
                            <tr>
                                <td>${customer.getUser().getUser_id()}</td>
                                <td>${customer.getUser().getName()}</td>
                                <td>${customer.getUser().getIc()}</td>
                                <td>${customer.getBlacklisted_reason()}</td>
                                <td>
                                    <form id="requestStatusForm" action="${pageContext.request.contextPath}/UpdateBlacklistStatus" method="POST" style="display: none;">
                                        <input type="hidden" name="blacklist_id" id="request_blacklist_id">
                                        <input type="hidden" name="request_status" id="request_status">
                                    </form>
                                        
                                    <select id="requestStatus" onchange="confirmRequestChange('${customer.getBlacklist_id()}', this.value)" 
                                    style="${customer.getStatus() eq 'Pending' ? 'background-color: #ffc107;' : 
                                            customer.getStatus() eq 'Approved' ? 'background-color: #78ff9c;' : 
                                            customer.getStatus() eq 'Rejected' ? 'background-color: #ff6363;' : ''}">
                                
                                        <option style="background-color: white" value="Pending"
                                            <c:if test="${customer.getStatus() eq 'Pending'}">selected</c:if>
                                        >Pending</option>

                                        <option style="background-color: white" value="Approved"
                                            <c:if test="${customer.getStatus() eq 'Approved'}">selected</c:if>
                                        >Approved</option>

                                        <option style="background-color: white" value="Rejected"
                                            <c:if test="${customer.getStatus() eq 'Rejected'}">selected</c:if>
                                        >Rejected</option>
                                    </select>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/DeleteBlacklistRequest" id="deleteRequestForm" method="POST">
                                        <input type="hidden" name="blacklist_id" id="blacklist_id">
                                    </form>
                                    <button class="btn btn-sm btn-danger" onclick="deleteRequest('${customer.getBlacklist_id()}')">Delete</button>
                                </td>
                            </tr> 
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
