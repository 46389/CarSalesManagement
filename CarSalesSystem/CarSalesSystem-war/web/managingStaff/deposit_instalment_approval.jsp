<%-- 
    Document   : deposit_approval
    Created on : Mar 28, 2025, 9:07:15 AM
    Author     : Chew Jin Ni
--%>

<%@page import="util.DepositInstalment"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="util.Validation"%>
<%@page import="model.Deposit"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Deposit Approval Page</title>
    <link rel="stylesheet" type="text/css" href="../css/sales_table.css">
    <style>
        .table td:nth-child(19){
            min-width: 240px;
            white-space: nowrap;
        }
    </style>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            window.depositStatusChange = function (deposit_id, status) {
                const isConfirmed = confirm("Confirm to change this deposit status?");
                if (isConfirmed) {            
                    document.getElementById('status_deposit_id').value = deposit_id;
                    document.getElementById('deposit_status_value').value = status;
                    document.getElementById('depositStatusForm').submit();
                }
            };
            
            window.instalmentPlanStatusChange = function (instalment_id, status) {
                const isConfirmed = confirm("Confirm to change this instalment plan status?");
                if (isConfirmed) {            
                    document.getElementById('instalment_plan_id').value = instalment_id;
                    document.getElementById('instalment_status_value').value = status;
                    document.getElementById('installmentStatusForm').submit();
                }
            };
            
            // Attach event listener to the textarea
            document.querySelectorAll('textarea[id="plan_remark"]').forEach(textarea => {
                textarea.addEventListener("keydown", handlePlanRemark);
            });
            
            document.querySelectorAll('textarea[id="deposit_remark"]').forEach(textarea => {
                textarea.addEventListener("keydown", handleDepositRemark);
            });
        });

        // Function to handle form submission on Enter key press
        function handlePlanRemark(event) {
            if (event.key === "Enter" && !event.shiftKey) { // Prevent Shift+Enter from triggering submission
                event.preventDefault();
                
                const isConfirmed = confirm("Confirm to change this instalment plan remark?");
                if(isConfirmed){
                    const instalmentId = event.target.dataset.instalmentId; // Retrieve instalment ID from the data attribute
                    const form = document.getElementById("planRemarkForm");

                    // Set the hidden input values
                    form.querySelector("#plan_id").value = instalmentId; // Set instalment ID
                    form.querySelector("#instalment_plan_remark").value = event.target.value; // Set remark value

                    // Submit the form
                    form.submit();
                }
            }
        };
        
        function handleDepositRemark(event) {
            if (event.key === "Enter" && !event.shiftKey) { // Prevent Shift+Enter from triggering submission
                event.preventDefault();
                
                const isConfirmed = confirm("Confirm to change this deposit remark?");
                if(isConfirmed){
                    const depositId = event.target.dataset.depositId; // Retrieve deposit ID from the data attribute
                    const form = document.getElementById("depositRemarkForm");

                    // Set the hidden input values
                    form.querySelector("#deposit_id").value = depositId; // Set deposit ID
                    form.querySelector("#deposit_plan_remark").value = event.target.value; // Set remark value

                    // Submit the form
                    form.submit();
                }
            }
        };
    </script>
</head>
<body>
    <%@ include file="header.jsp" %>

    <!-- Tickets Table -->
    <div class="table-container">
        <h2 class="text-center my-4">Deposit and Instalment Plan Review</h2>

        <!-- Responsive Table -->
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">Sales ID</th>
                        <th scope="col">Salesman ID</th>
                        <th scope="col">Customer ID</th>
                        <th scope="col">Car ID</th>
                        <th scope="col">Model Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Created Date</th>
                        <th scope="col">Deposit Amount</th>
                        <th scope="col">Deposit Document</th>
                        <th scope="col">Deposit Status</th>
                        <th scope="col">Deposit Remark</th>
                        <th scope="col">Instalment Plan</th>
                        <th scope="col">Instalment Status</th>
                        <th scope="col">Instalment Remark</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="depIns" items="${sessionScope.deposit_instalment_list}">
                        <tr>
                            <td>${depIns.sales_id}</td>
                            <td>${depIns.salesman_id}</td>
                            <td>${depIns.customer_id}</td>
                            <td>${depIns.car_id}</td>
                            <td>${depIns.model_name}</td>
                            <td>${depIns.car_price}</td>
                                             
                            <!-- Created at --> 
                            <td>
                                <%
                                    LocalDateTime isoTimestamp = ((DepositInstalment) pageContext.getAttribute("depIns")).getCreatedDate();

                                    String formattedDate = Validation.formatLocalDateTime(isoTimestamp, "dd/MM/yyyy HH:mm:ss");
                                    pageContext.setAttribute("datetime", formattedDate);
                                %>
                                ${datetime}
                            </td>
                            
                            <!-- Deposit Amount --> 
                            <td>${depIns.deposit_amount}</td>
                            
                            <!-- Deposit Status Dropdown --> 
                            <td>
                                <form id="depositStatusForm" action="${pageContext.request.contextPath}/EditDepositInstalmentStatus" method="POST" style="display: none;">
                                    <input type="hidden" name="id" id="status_deposit_id">
                                    <input type="hidden" name="status_type" id="status_type" value="deposit">
=                                   <input type="hidden" name="status" id="deposit_status_value">
                                </form>
                                
                                <select id="depositStatus" onchange="depositStatusChange('${depIns.deposit_id}', this.value)" 
                                    style="${depIns.deposit_status eq 'Pending_Approval' ? 'background-color: #ffc107;' : 
                                            depIns.deposit_status eq 'Approved' ? 'background-color: #78ff9c;' : 
                                            depIns.deposit_status eq 'Rejected' ? 'background-color: #ff6363;' : ''}">
                                
                                    <option style="background-color: white" value="Pending_Approval"
                                        <c:if test="${depIns.deposit_status eq 'Pending_Approval'}">selected</c:if>
                                    >Pending</option>

                                    <option style="background-color: white" value="Approved"
                                        <c:if test="${depIns.deposit_status eq 'Approved'}">selected</c:if>
                                    >Approved</option>

                                    <option style="background-color: white" value="Rejected"
                                        <c:if test="${depIns.deposit_status eq 'Rejected'}">selected</c:if>
                                    >Rejected</option>
                                </select>  
                            </td>
                            
                            <!-- Deposit Document Display -->
                            <td>
                                <%
                                    String basePath = "D:\\NetbeanProject\\CarSalesSystem\\CarSalesSystem-war\\web\\";
                                    String deposit_relativePath = "../image/logo.png"; // Default image
                                    String deposit_absolutePath = ((DepositInstalment) pageContext.getAttribute("depIns")).getDeposit_doc();

                                    if (deposit_absolutePath != null) {
                                        if (deposit_absolutePath.startsWith(basePath)) {
                                            deposit_relativePath = "../" + deposit_absolutePath.substring(basePath.length()).replace("\\", "/");
                                        }
                                    }

                                    pageContext.setAttribute("deposit_relativePath", deposit_relativePath);
                                %>
                                <a href="${deposit_relativePath}" target="_blank">View Document</a>
                            </td>
                            
                            <!-- Deposit Status Remark -->
                            <td>
                                <!-- Hidden Form for Submitting Deposit Remark -->
                                <form id="depositRemarkForm" action="${pageContext.request.contextPath}/EditRemark" method="POST" style="display: none;">
                                    <input type="hidden" name="remark_id" id="deposit_id">
                                    <input type="hidden" name="type" id="type" value="deposit_remark">
                                    <input type="hidden" name="remark" id="deposit_plan_remark">
                                </form>

                                <!-- Textarea for User Input -->
                                <textarea id="deposit_remark" rows="8" cols="16" data-deposit-id="${depIns.deposit_id}">${depIns.deposit_remark != null ? depIns.deposit_remark : ''}</textarea>
                            </td>
                            
                            <!-- Instalment Plan -->
                            <td>
                                <p>Total Amount to Pay: ${depIns.total_amount}</p>
                                <p>Interest Rate: ${depIns.interest_rate}</p>
                                <p>Loan Amount: ${depIns.loan_amount}</p>
                                <p>Tenure(months): ${depIns.tenure_months}</p>
                                <p>Monthly Payment: ${depIns.monthly_payment}</p>
                            </td>
                            
                            <!-- Instalment Plan Approval -->
                            <td>
                                <form id="installmentStatusForm" action="${pageContext.request.contextPath}/EditDepositInstalmentStatus" method="POST" style="display: none;">
                                    <input type="hidden" name="id" id="instalment_plan_id">
                                    <input type="hidden" name="status_type" id="status_type" value="instalment">
=                                   <input type="hidden" name="status" id="instalment_status_value">
                                </form>
                                
                                <select id="instalmentPlanStatusForm" onchange="instalmentPlanStatusChange('${depIns.instalment_id}', this.value)" 
                                    style="${depIns.plan_status eq 'Pending_Approval' ? 'background-color: #ffc107;' : 
                                            depIns.plan_status eq 'Approved' ? 'background-color: #78ff9c;' : 
                                            depIns.plan_status eq 'Rejected' ? 'background-color: #ff6363;' : ''}">
                                
                                    <option style="background-color: white" value="Pending_Approval"
                                        <c:if test="${depIns.plan_status eq 'Pending_Approval'}">selected</c:if>
                                    >Pending</option>

                                    <option style="background-color: white" value="Approved"
                                        <c:if test="${depIns.plan_status eq 'Approved'}">selected</c:if>
                                    >Approved</option>

                                    <option style="background-color: white" value="Rejected"
                                        <c:if test="${depIns.plan_status eq 'Rejected'}">selected</c:if>
                                    >Rejected</option>
                                </select>  
                            </td>
                            
                            <!-- Instalment Plan Status Remark -->
                            <td>
                                <form id="planRemarkForm" action="${pageContext.request.contextPath}/EditRemark" method="POST" style="display: none;">
                                    <input type="hidden" name="remark_id" id="plan_id">
                                    <input type="hidden" name="type" value="plan_remark">
                                    <input type="hidden" name="remark" id="instalment_plan_remark">
                                </form>
                                <textarea id="plan_remark" rows="8" cols="16" data-instalment-id="${depIns.instalment_id}">${depIns.plan_remark != null ? depIns.plan_remark : ''}</textarea>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
