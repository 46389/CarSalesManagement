<%-- 
    Document   : header
    Created on : Mar 30, 2025, 1:45:08 PM
    Author     : Chew Jin Ni
--%>

<head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="../css/navbar.css"/>
</head>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container">
        <!-- Logo -->
        <a class="navbar-brand" href="customer_home.jsp">
            <img src="../image/logo.png" alt="Company Logo">
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navigation Links -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/CustomerHome">Car Catalog</a></li>

                <!-- Dropdown Menu for Purchase History -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Purchase History
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoadInterestedCars">Interested</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoadBookedCars">Booked</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/LoadOwnedCars">Owned</a></li>
                    </ul>
                </li>
                
                <li class="nav-item"><a class="nav-link" href="profile.jsp">Customer Profile</a></li>
                
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/Logout">Logout</a></li>
            </ul>
        </div>
    </div>
</nav>

