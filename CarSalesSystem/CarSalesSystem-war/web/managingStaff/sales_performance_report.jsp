<%-- 
    Document   : sales_performance_report
    Created on : Apr 15, 2025, 9:45:41 PM
    Author     : Chew Jin Ni
--%>

<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Daily Sales Chart</title>
    <!-- Include Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        canvas {
            max-width: 800px;
            margin: 0 auto;
            display: block;
        }
        .h2-class{
            margin-top: 120px;
            margin-bottom: 60px;
            text-align: center;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>
    <h2 class="h2-class">Daily Sales Data Per Month</h2>

    <!-- Canvas for Chart.js -->
    <canvas id="salesChart" width="1000" height="600"></canvas>

    <%
        // Retrieve monthly sales data from the request attribute
        Map<Integer, List<Map<String, Object>>> monthlySalesData =
            (Map<Integer, List<Map<String, Object>>>) request.getAttribute("monthlySalesData");
    %>

    <script>
        // Prepare data for Chart.js
        const monthlySalesData = {};
        <%
            if (monthlySalesData != null) {
                for (Map.Entry<Integer, List<Map<String, Object>>> entry : monthlySalesData.entrySet()) {
                    int monthNumber = entry.getKey();
                    String monthName = util.Validation.getMonthName(monthNumber); // Servlet method call
                    out.print("monthlySalesData['" + monthName + "'] = [");

                    for (Map<String, Object> dailyData : entry.getValue()) {
                        int day = (int) dailyData.get("day");
                        double totalSales = (double) dailyData.get("totalSales");
                        out.print("{ day: " + day + ", totalSales: " + totalSales + " },");
                    }

                    out.print("];");
                }
            }
        %>

        // Debugging: Log the monthlySalesData object
        console.log("Monthly Sales Data:", monthlySalesData);

        // Prepare labels (days 1 to 31)
        const days = Array.from({ length: 31 }, (_, i) => i + 1);

        // Extract datasets
        const datasets = [];
        for (const [month, data] of Object.entries(monthlySalesData)) {
            const salesByDay = Array(31).fill(0); // Initialize array for 31 days with default value 0
            data.forEach(dailyData => {
                if (dailyData.day >= 1 && dailyData.day <= 31) { // Ensure day is within valid range
                    salesByDay[dailyData.day - 1] = dailyData.totalSales; // Fill sales for specific days
                }
            });

            datasets.push({
                label: month,
                data: salesByDay,
                borderColor: getRandomColor(),
                borderWidth: 2,
                fill: false
            });
        }

        // Debugging: Log the final datasets object
        console.log("Datasets:", datasets);

        // Create the chart
        const ctx = document.getElementById('salesChart').getContext('2d');
        const salesChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: days, // Days 1 to 31 on the x-axis
                datasets: datasets
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top'
                    }
                },
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Day of the Month'
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'Total Sales'
                        }
                    }
                }
            }
        });

        // Helper function to generate random colors for datasets
        function getRandomColor() {
            const r = Math.floor(Math.random() * 256);
            const g = Math.floor(Math.random() * 256);
            const b = Math.floor(Math.random() * 256);
            return `rgba(${r}, ${g}, ${b}, 1)`;
        }
    </script>
</body>
</html>