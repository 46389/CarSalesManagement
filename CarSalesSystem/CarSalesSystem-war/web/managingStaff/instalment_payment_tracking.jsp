<%-- 
    Document   : instalment_payment_tracking
    Created on : Apr 10, 2025, 2:27:26 PM
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
                            <th scope="col">Salesman ID</th>
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
                                <td>${payment.instalment_id.sales_id.salesman.user_id}</td>
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
                                    <c:choose>
                                        <c:when test="${payment.status eq 'Pending_Approval'}">
                                            <span class="badge bg-warning">${payment.status}</span>
                                        </c:when>
                                        <c:when test="${payment.status eq 'Approved'}">
                                            <span class="badge bg-success">${payment.status}</span>
                                        </c:when>
                                        <c:when test="${payment.status eq 'Rejected'}">
                                            <span class="badge bg-danger">${payment.status}</span>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>${payment.remark}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
