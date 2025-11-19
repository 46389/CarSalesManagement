<%-- 
    Document   : installment_payment_approval
    Created on : Apr 9, 2025, 11:14:44 PM
    Author     : Chew Jin Ni
--%>

<%@page import="util.Validation"%>
<%@page import="model.InstalmentPayment"%>
<%@page import="java.time.LocalDateTime"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instalment Payment Tracking</title>
        <link rel="stylesheet" type="text/css" href="../css/sales_table.css">
    </head>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            window.paymentStatusChange = function (payment_id, status) {
                const isConfirmed = confirm("Confirm to change this payment status?");
                if (isConfirmed) {            
                    document.getElementById('payment_id').value = payment_id;
                    document.getElementById('payment_status').value = status;
                    document.getElementById('paymentStatusForm').submit();
                }
            };
            
            // Attach event listener to the textarea
            document.querySelectorAll('textarea[id="payment_remark"]').forEach(textarea => {
                textarea.addEventListener("keydown", handlePaymentRemark);
            });
            
        });
        
        function handlePaymentRemark(event) {
            if (event.key === "Enter" && !event.shiftKey) { // Prevent Shift+Enter from triggering submission
                event.preventDefault();
                
                const isConfirmed = confirm("Confirm to change this instalment payment remark?");
                if(isConfirmed){
                    const paymentId = event.target.dataset.paymentId; 
                    const form = document.getElementById("paymentRemarkForm");

                    // Set the hidden input values
                    form.querySelector("#payment-id").value = paymentId; // Set instalment ID
                    form.querySelector("#payment_remark_field").value = event.target.value; // Set remark value

                    // Submit the form
                    form.submit();
                }
            }
        };
    </script>
    <body>
        <%@ include file="header.jsp" %>
        <!-- Tickets Table -->
        <div class="table-container">
            <h2 class="text-center my-4">Instalment Payment Tracking</h2>

            <!-- Responsive Table -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Sales ID</th>
                            <th scope="col">Customer ID</th>
                            <th scope="col">Payment ID</th>
                            <th scope="col">Payment Date</th>
                            <th scope="col">Amount</th>
                            <th scope="col">Payment Document</th>
                            <th scope="col">Payment Status</th>
                            <th scope="col">Payment Remark</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="payment" items="${sessionScope.payment_list}">
                            <tr>
                                <td>${payment.instalment_id.sales_id.sales_id}</td>
                                <td>${payment.instalment_id.sales_id.customer.user_id}</td>
                                <td>${payment.payment_id}</td>
                                <td>
                                    <%
                                        LocalDateTime isoTimestamp = ((InstalmentPayment) pageContext.getAttribute("payment")).getCreated_at();

                                        String formattedDate = Validation.formatLocalDateTime(isoTimestamp, "dd/MM/yyyy HH:mm:ss");
                                        pageContext.setAttribute("datetime", formattedDate);
                                    %>
                                    ${datetime}
                                </td>
                                <td>${payment.amount_paid}</td>
                                <td>
                                    <%
                                        String basePath = "D:\\NetbeanProject\\CarSalesSystem\\CarSalesSystem-war\\web\\";
                                        String receipt_absolutePath = ((InstalmentPayment) pageContext.getAttribute("payment")).getReceipt();
                                        String receipt_relativePath = "";
                                        if (receipt_absolutePath != null) {
                                            if (receipt_absolutePath.startsWith(basePath)) {
                                                receipt_relativePath = "../" + receipt_absolutePath.substring(basePath.length()).replace("\\", "/");
                                            }
                                        }

                                        pageContext.setAttribute("receipt_relativePath", receipt_relativePath);
                                    %>
                                    <a href="${receipt_relativePath}" target="_blank">View Document</a>
                                </td>
                                <td>
                                    <form id="paymentStatusForm" action="${pageContext.request.contextPath}/UpdatePaymentStatus" method="POST" style="display: none;">
                                        <input type="hidden" name="payment_id" id="payment_id">
    =                                   <input type="hidden" name="status" id="payment_status">
                                    </form>

                                    <select id="paymentStatus" onchange="paymentStatusChange('${payment.payment_id}', this.value)" 
                                        style="${payment.status eq 'Pending_Approval' ? 'background-color: #ffc107;' : 
                                                payment.status eq 'Approved' ? 'background-color: #78ff9c;' : 
                                                payment.status eq 'Rejected' ? 'background-color: #ff6363;' : ''}">

                                        <option style="background-color: white" value="Pending_Approval"
                                            <c:if test="${payment.status eq 'Pending_Approval'}">selected</c:if>
                                        >Pending</option>

                                        <option style="background-color: white" value="Approved"
                                            <c:if test="${payment.status eq 'Approved'}">selected</c:if>
                                        >Approved</option>

                                        <option style="background-color: white" value="Rejected"
                                            <c:if test="${payment.status eq 'Rejected'}">selected</c:if>
                                        >Rejected</option>
                                    </select> 
                                </td>
                                <td>
                                    <!-- Hidden Form for Submitting Deposit Remark -->
                                    <form id="paymentRemarkForm" action="${pageContext.request.contextPath}/UpdatePaymentRemark" method="POST" style="display: none;">
                                        <input type="hidden" name="payment_id" id="payment-id">
                                        <input type="hidden" name="remark" id="payment_remark_field">
                                    </form>

                                    <!-- Textarea for User Input -->
                                    <textarea id="payment_remark" rows="8" cols="16" data-payment-id="${payment.payment_id}">${payment.remark != null ? payment.remark : ''}</textarea>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
