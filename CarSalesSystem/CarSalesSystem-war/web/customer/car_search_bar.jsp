<%-- 
    Document   : car_search_bar
    Created on : Apr 2, 2025, 9:59:41 AM
    Author     : Chew Jin Ni
--%>
<body>
    <div class="container mt-5">
        <!-- Search Form -->
        <form id="searchForm" action="${pageContext.request.contextPath}/CustomerHome" method="GET" class="row g-3 align-items-end">
            <!-- Brand Dropdown -->
            <div class="col-md-2 col-sm-6">
                <label for="brand" class="form-label">Brand</label>
                <select id="brand" name="brand" class="form-select">
                    <option value="">-- Select Brand --</option>
                    <c:forEach var="brand" items="${sessionScope.existing_brands}">
                        <option value="${brand}">${brand}</option>
                    </c:forEach>                  
                </select>
            </div>

            <!-- Model Name Input -->
            <div class="col-md-2 col-sm-6">
                <label for="modelName" class="form-label">Model Name</label>
                <input type="text" id="modelName" name="modelName" class="form-control" placeholder="Enter model name">
            </div>

            <!-- Fuel Type Dropdown -->
            <div class="col-md-2 col-sm-6">
                <label for="fuelType" class="form-label">Fuel Type</label>
                <select id="fuelType" name="fuelType" class="form-select">
                    <option value="">-- Select Fuel Type --</option>
                    <c:forEach var="fuel_type" items="${sessionScope.existing_fuelTypes}">
                        <option value="${fuel_type}">${fuel_type}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Seats Dropdown -->
            <div class="col-md-2 col-sm-6">
                <label for="seats" class="form-label">Seats</label>
                <select id="seats" name="seats" class="form-select">
                    <option value="">-- Select Seats --</option>
                    <c:forEach var="seats" items="${sessionScope.existing_seats}">
                        <option value="${seats}">${seats}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Price Range + Search Button -->
            <div class="col-12 col-md-4 d-flex flex-column flex-md-row gap-3 align-items-end">
                <!-- Price Range -->
                <div class="flex-grow-1">
                    <label for="priceRange" class="form-label mb-0">Price Range</label>
                    <div class="input-group">
                        <input type="number" id="minPrice" name="minPrice" class="form-control" placeholder="Min Price">
                        <span class="input-group-text">-</span>
                        <input type="number" id="maxPrice" name="maxPrice" class="form-control" placeholder="Max Price">
                    </div>
                </div>
                <!-- Search Button -->
                <button type="submit" name="action" value="search" class="btn btn-primary flex-shrink-0">Search</button>
                <button type="reset" name="action" value="reset" class="btn btn-primary flex-shrink-0">Reset</button>
            </div>
        </form>
    </div>
</body>
