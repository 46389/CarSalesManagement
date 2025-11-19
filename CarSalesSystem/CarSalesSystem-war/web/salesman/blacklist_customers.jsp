<%-- 
    Document   : blacklist_customer
    Created on : Apr 8, 2025, 1:43:01 AM
    Author     : Chew Jin Ni
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BlackList Customer</title>
        <link rel="stylesheet" type="text/css" href="../css/sales_table.css">
        <link rel="stylesheet" type="text/css" href="../css/popup_container.css">
        <script>
            document.addEventListener("DOMContentLoaded", function () {

                window.blacklistCustomerRequest = function () {
                    // Show the pop-up
                    document.getElementById('popupOverlay').style.display = 'block';
                    document.getElementById('popupContainer').style.display = 'block';
                };
                
                window.closeBlacklistForm = function () {
                    // Show the pop-up
                    document.getElementById('popupOverlay').style.display = 'none';
                    document.getElementById('popupContainer').style.display = 'none';
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
    </head>
    <body>
        <div id="notification" class="error_notification">
            <c:if test="${not empty sessionScope.salesman_errorMsg}">
                ${sessionScope.salesman_errorMsg}
                <% session.removeAttribute("salesman_errorMsg");%>
            </c:if>  
        </div>
        <%@ include file="header.jsp" %>
        <!-- Tickets Table -->
        <div class="table-container">
            <h2 class="text-center my-4">Blacklist Customer</h2>
            
            <div class="function-bar">
                <button class="btn btn-primary edit-button" onclick="blacklistCustomerRequest()">Blacklist Customer Request</button>
            </div>

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
                                    <c:choose>
                                        <c:when test="${customer.getStatus() eq 'Pending'}">
                                            <span class="badge bg-warning">${customer.getStatus()}</span>
                                        </c:when>
                                        <c:when test="${customer.getStatus() eq 'Approved'}">
                                            <span class="badge bg-success">${customer.getStatus()}</span>
                                        </c:when>
                                        <c:when test="${customer.getStatus() eq 'Rejected'}">
                                            <span class="badge bg-danger">${customer.getStatus()}</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${customer.getStatus() eq 'Pending'}">
                                        <form action="${pageContext.request.contextPath}/DeleteBlacklistRequest" id="deleteRequestForm" method="POST">
                                            <input type="hidden" name="blacklist_id" id="blacklist_id">
                                        </form>
                                        <button class="btn btn-sm btn-danger" onclick="deleteRequest('${customer.getBlacklist_id()}')">Delete</button>
                                    </c:if>
                                </td>
                            </tr> 
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            
            <!-- Pop-up Overlay -->
            <div class="popup-overlay" id="popupOverlay" onclick="closeBlacklistForm()"></div>

            <!-- Pop-up Container -->
            <div class="popup-container" id="popupContainer">
                <h2>Request Blacklist</h2>
                <br>
                <form action="${pageContext.request.contextPath}/RequestBlacklistCustomer" method="POST" id="blacklistForm">
                    <label for="cus_id">Customer ID:</label>
                    <select id="customer_id" name="customer_id">
                        <c:forEach var="cus" items="${sessionScope.customers}">
                            <option value="${cus.getUser_id()}">${cus.getUser_id()}</option>
                        </c:forEach>
                    </select>
                    <br><br>
                    <label for="blacklist_reason">Blacklist Reason:</label>
                    <textarea id="blacklist_reason" name="blacklist_reason" rows="4" cols="50" required></textarea>
                    
                    <div>
                        <button type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
