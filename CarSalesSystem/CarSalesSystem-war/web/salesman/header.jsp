<%-- 
    Document   : header.jsp
    Created on : Apr 3, 2025, 1:02:33 AM
    Author     : Chew Jin Ni
--%>

<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="../css/navbar.css"/>
</head>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <!-- Logo -->
        <a class="navbar-brand" href="home.jsp">
            <img src="../image/logo.png" alt="Company Logo">
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                
                <!-- Sales -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="accountsDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Sales
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="accountsDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoadTickets">Sales Tickets</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoadBookedSales">Booked Sales</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoadPaidSales">Paid Sales</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoadInstalmentTracking">Instalment Payment Review</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoadCancelledSales">Cancelled Sales</a></li>
                    </ul>
                </li> 
                
                <!-- Blacklist Customer -->
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/FetchBlacklistCustomers">Blacklist Customer</a></li>
                
                <!-- Profile -->
                <li class="nav-item"><a class="nav-link" href="profile.jsp">Profile</a></li>

                <!-- Logout -->
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>