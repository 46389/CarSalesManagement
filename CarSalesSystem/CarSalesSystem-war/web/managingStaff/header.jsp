<%-- 
    Document   : managing_staff_header
    Created on : Mar 28, 2025, 8:47:41 AM
    Author     : Chew Jin Ni
--%>

<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/navbar.css"/>
</head>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <!-- Logo -->
        <a class="navbar-brand" href="home.jsp">
            <img src="${pageContext.request.contextPath}/image/logo.png" alt="Company Logo">
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <!-- Accounts Dropdown -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="accountsDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Accounts
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="accountsDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FetchCustomerAccount">Customer</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FetchSalesmanAccount">Salesman</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FetchManagingStaffAccount">Managing Staff</a></li>
                    </ul>
                </li>
                
                <!-- Review Dropdown -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="reviewDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Payment & Feedback Review
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="reviewDropdown">
                        <!-- Deposit & Instalment Plan -->
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FetchDepositInstalment">Deposit & Instalment Plan</a></li>
                        <!-- Instalment Payment -->
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FetchPaymentInformation">Instalment Payment</a></li>
                        <!-- Blacklisted Customer -->
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FetchBlacklistCustomers">Blacklisted Customers</a></li>
                    </ul>
                </li>
                
                <!-- Dashboard Dropdown -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="dashboardDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Dashboard
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="dashboardDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FetchSalesReport">Sales Performance Report</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/managingStaff/car_trends.xhtml">Car Trends Report</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/FetchSalesmanPerformance">Salesman Performance Report</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/PaymentFeedbackAnalysis">Payment & Feedback Analysis</a></li>                    </ul>
                </li>
                
                <!-- Car Stock -->
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/FetchCarInformation">Car Stocks</a></li>
                
                <!-- Change Password -->
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/managingStaff/change_password.jsp">Change Password</a></li>
                
                <!-- Logout -->
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

