<%-- 
    Document   : payment_feedback_analysis
    Created on : Apr 16, 2025, 8:54:49 PM
    Author     : Chew Jin Ni
--%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Feedback Analysis</title>
        <link rel="stylesheet" type="text/css" href="../css/sales_table.css">
        <link rel="stylesheet" type="text/css" href="../css/payment_feedback.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    </head>
    <body>
        <%@ include file="header.jsp" %>
        <h2 class="h2-class">Payment & Feedback Analysis</h2>
        <div class="dashboard-container">
            <div class="chart-card">
              <h2>Sales Status</h2>
              <canvas id="salesStatusChart"></canvas>
            </div>

            <div class="chart-card">
              <h2>Rating Distribution</h2>
              <canvas id="ratingChart"></canvas>
            </div>

            <div class="chart-card">
              <h2>Instalment Plan Status</h2>
              <canvas id="instalmentChart"></canvas>
            </div>
        </div>
        
        <div class="table-container" style="margin-bottom: 400px;">
            <h2 class="text-center my-4">Feedback List</h2>
            <!-- Responsive Table -->
            <div class="table-responsive">
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th scope="col">Salesman ID</th>
                            <th scope="col">Customer ID</th>
                            <th scope="col">Sales ID</th>
                            <th scope="col">Rating</th>
                            <th scope="col">Comment</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="feedback" items="${sessionScope.feedback_list}">
                            <tr>
                                <td>${feedback.salesman_id}</td>
                                <td>${feedback.customer_id}</td>
                                <td>${feedback.sales_id}</td>
                                <td>${feedback.rating}</td>
                                <td>${feedback.comment}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>        
        
        <%
            Map<String, Integer> statusCount = (Map<String, Integer>) session.getAttribute("sales_status");
            if (statusCount == null) {
                statusCount = new HashMap<>(); // Initialize an empty map if null
            }
        %>

        <script>
            const ctx = document.getElementById('salesStatusChart').getContext('2d');
            const salesStatusChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Cancelled', 'Pending Payment', 'Payment Approval', 'Manufacturing', 'Delivering', 'Delivered', 'Repossessed'],
                    datasets: [{
                        label: 'Sales Count',
                        data: [
                            <%= statusCount.getOrDefault("Cancelled", 0) %>,
                            <%= statusCount.getOrDefault("Pending_Payment", 0) %>,
                            <%= statusCount.getOrDefault("Pending_Payment_Approval", 0) %>,
                            <%= statusCount.getOrDefault("Manufacturing", 0) %>,
                            <%= statusCount.getOrDefault("Delivering", 0) %>,
                            <%= statusCount.getOrDefault("Delivered", 0) %>,
                            <%= statusCount.getOrDefault("Repossessed", 0) %>
                        ],
                        backgroundColor: [
                            '#FF6384', '#FFCE56', '#36A2EB', '#4BC0C0', '#9966FF', '#FF9F40', '#C45850'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    indexAxis: 'y',
                    responsive: true,
                    plugins: {
                        legend: { display: false },
                        title: {
                            display: true,
                            text: 'Total Sales Status Count'
                        }
                    },
                    scales: {
                        x: {
                            beginAtZero: true
                        }
                    }
                }
            });
        </script>
        
        <%
            List<Object[]> stars = (List<Object[]>) session.getAttribute("stars");
            int[] ratingCounts = new int[5];
            if (stars != null && !stars.isEmpty()) {
                for (Object[] row : stars) {
                    for (int i = 0; i < 5; i++) {
                        if (row[i] != null) {
                            ratingCounts[i] += ((Number) row[i]).intValue();
                        }
                    }
                }
            }
        %>

        <script>
            const ratingLabels = ["1 Star", "2 Stars", "3 Stars", "4 Stars", "5 Stars"];
            const ratingData = [<%
                for (int i = 0; i < ratingCounts.length; i++) {
                    out.print(ratingCounts[i]);
                    if (i < ratingCounts.length - 1) out.print(", ");
                }
            %>];

            new Chart(document.getElementById("ratingChart"), {
                type: 'doughnut',
                data: {
                    labels: ratingLabels,
                    datasets: [{
                        label: 'Ratings',
                        data: ratingData,
                        backgroundColor: ["#ff6384", "#ff9f40", "#ffcd56", "#4bc0c0", "#36a2eb"]
                    }]
                },
                options: {
                    responsive: true
                }
            });
        </script>
        
        <%
            List<Object[]> instalment_status = (List<Object[]>) session.getAttribute("instalment_status");
            int completed = 0, ongoing = 0;
            if (instalment_status != null && !instalment_status.isEmpty()) {
                for (Object[] row : instalment_status) {
                    if (row[0] != null) {
                        completed += ((Number) row[0]).intValue();
                    }
                    if (row[1] != null) {
                        ongoing += ((Number) row[1]).intValue();
                    }
                }
            }
        %>  
        <script>
            const instalmentLabels = ["Completed", "Ongoing"];
            const instalmentData = [<%= completed %>, <%= ongoing %>];

            new Chart(document.getElementById("instalmentChart"), {
                type: 'pie',
                data: {
                    labels: instalmentLabels,
                    datasets: [{
                        label: 'Instalment Plans',
                        data: instalmentData,
                        backgroundColor: ["#2ecc71", "#e74c3c"]
                    }]
                },
                options: {
                    responsive: true
                }
            });
        </script>
    </body>
</html>
