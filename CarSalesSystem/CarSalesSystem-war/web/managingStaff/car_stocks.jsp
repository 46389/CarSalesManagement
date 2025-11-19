<%-- 
    Document   : car_orders.jsp
    Created on : Mar 28, 2025, 9:06:10 AM
    Author     : Chew Jin Ni
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Car Information</title>
    <link rel="stylesheet" type="text/css" href="../css/table.css">
    <link rel="stylesheet" type="text/css" href="../css/notification.css">
    <style>
        /* Default background color */
        .table > tbody > tr {
            --bs-table-bg: white; /* White background */
        }

        /* Light red background for low stock */
        .low-stock {
            --bs-table-bg: #ffcccc !important; /* Light red */
        }      
    </style>
    
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            window.toggleEditMode = function (button) {
                const row = button.closest('tr');
                const modelNameCell = row.querySelector('.modelName-cell');
                const brandCell = row.querySelector('.brand-cell');
                const fuelTypeCell = row.querySelector('.fuelType-cell');
                const horsepowerCell = row.querySelector('.horsepower-cell');
                const seatsCell = row.querySelector('.seats-cell');
                const transmissionCell = row.querySelector('.transmission-cell');
                const colourCell = row.querySelector('.colour-cell');
                const yearCell = row.querySelector('.year-cell');
                const priceCell = row.querySelector('.price-cell');

                // Toggle view mode and editable mode for fields
                modelNameCell.querySelector('.view-mode').style.display = 'none';
                modelNameCell.querySelector('.editable-field').style.display = 'inline-block';

                brandCell.querySelector('.view-mode').style.display = 'none';
                brandCell.querySelector('.editable-field').style.display = 'inline-block';

                fuelTypeCell.querySelector('.view-mode').style.display = 'none';
                fuelTypeCell.querySelector('.editable-field').style.display = 'inline-block';

                horsepowerCell.querySelector('.view-mode').style.display = 'none';
                horsepowerCell.querySelector('.editable-field').style.display = 'inline-block';

                seatsCell.querySelector('.view-mode').style.display = 'none';
                seatsCell.querySelector('.editable-field').style.display = 'inline-block';

                transmissionCell.querySelector('.view-mode').style.display = 'none';
                transmissionCell.querySelector('.editable-field').style.display = 'inline-block';

                colourCell.querySelector('.view-mode').style.display = 'none';
                colourCell.querySelector('.editable-field').style.display = 'inline-block';

                yearCell.querySelector('.view-mode').style.display = 'none';
                yearCell.querySelector('.editable-field').style.display = 'inline-block';

                priceCell.querySelector('.view-mode').style.display = 'none';
                priceCell.querySelector('.editable-field').style.display = 'inline-block';

                // Show Save and Cancel buttons, hide Edit button
                button.style.display = 'none';
                row.querySelector('.save-button').style.display = 'inline-block';
                row.querySelector('.cancel-button').style.display = 'inline-block';
            };

            // Function to save changes and revert to view mode
            window.saveChanges = function (car_id, button) {
                const row = button.closest('tr');
                const modelNameCell = row.querySelector('.modelName-cell');
                const brandCell = row.querySelector('.brand-cell');
                const fuelTypeCell = row.querySelector('.fuelType-cell');
                const horsepowerCell = row.querySelector('.horsepower-cell');
                const seatsCell = row.querySelector('.seats-cell');
                const transmissionCell = row.querySelector('.transmission-cell');
                const colourCell = row.querySelector('.colour-cell');
                const yearCell = row.querySelector('.year-cell');
                const priceCell = row.querySelector('.price-cell');

                modelNameCell.querySelector('.view-mode').style.display = 'inline-block';
                modelNameCell.querySelector('.editable-field').style.display = 'none';

                brandCell.querySelector('.view-mode').style.display = 'inline-block';
                brandCell.querySelector('.editable-field').style.display = 'none';

                fuelTypeCell.querySelector('.view-mode').style.display = 'inline-block';
                fuelTypeCell.querySelector('.editable-field').style.display = 'none';

                horsepowerCell.querySelector('.view-mode').style.display = 'inline-block';
                horsepowerCell.querySelector('.editable-field').style.display = 'none';

                seatsCell.querySelector('.view-mode').style.display = 'inline-block';
                seatsCell.querySelector('.editable-field').style.display = 'none';

                transmissionCell.querySelector('.view-mode').style.display = 'inline-block';
                transmissionCell.querySelector('.editable-field').style.display = 'none';

                colourCell.querySelector('.view-mode').style.display = 'inline-block';
                colourCell.querySelector('.editable-field').style.display = 'none';

                yearCell.querySelector('.view-mode').style.display = 'inline-block';
                yearCell.querySelector('.editable-field').style.display = 'none';

                priceCell.querySelector('.view-mode').style.display = 'inline-block';
                priceCell.querySelector('.editable-field').style.display = 'none';

                // Show Edit button, hide Save and Cancel buttons
                row.querySelector('.edit-button').style.display = 'inline-block';
                button.style.display = 'none';
                row.querySelector('.cancel-button').style.display = 'none';

                document.getElementById('edit_car_id').value = car_id;
                document.getElementById('edit_model_name').value = modelNameCell.querySelector('.editable-field').value;
                document.getElementById('edit_brand').value = brandCell.querySelector('.editable-field').value;
                document.getElementById('edit_fuel_type').value = fuelTypeCell.querySelector('.editable-field').value;
                document.getElementById('edit_horsepower').value = horsepowerCell.querySelector('.editable-field').value;
                document.getElementById('edit_seats').value = seatsCell.querySelector('.editable-field').value;
                document.getElementById('edit_transmission').value = transmissionCell.querySelector('.editable-field').value;
                document.getElementById('edit_colour').value = colourCell.querySelector('.editable-field').value;
                document.getElementById('edit_year').value = yearCell.querySelector('.editable-field').value;
                document.getElementById('edit_price').value = priceCell.querySelector('.editable-field').value;

                document.getElementById('updateCarForm').submit();
            };

            // Function to cancel edits and revert to view mode
            window.cancelEditMode = function (button) {
                const row = button.closest('tr');
                const modelNameCell = row.querySelector('.modelName-cell');
                const brandCell = row.querySelector('.brand-cell');
                const fuelTypeCell = row.querySelector('.fuelType-cell');
                const horsepowerCell = row.querySelector('.horsepower-cell');
                const seatsCell = row.querySelector('.seats-cell');
                const transmissionCell = row.querySelector('.transmission-cell');
                const colourCell = row.querySelector('.colour-cell');
                const yearCell = row.querySelector('.year-cell');
                const priceCell = row.querySelector('.price-cell');

                modelNameCell.querySelector('.view-mode').style.display = 'inline-block';
                modelNameCell.querySelector('.editable-field').style.display = 'none';

                brandCell.querySelector('.view-mode').style.display = 'inline-block';
                brandCell.querySelector('.editable-field').style.display = 'none';

                fuelTypeCell.querySelector('.view-mode').style.display = 'inline-block';
                fuelTypeCell.querySelector('.editable-field').style.display = 'none';

                horsepowerCell.querySelector('.view-mode').style.display = 'inline-block';
                horsepowerCell.querySelector('.editable-field').style.display = 'none';

                seatsCell.querySelector('.view-mode').style.display = 'inline-block';
                seatsCell.querySelector('.editable-field').style.display = 'none';

                transmissionCell.querySelector('.view-mode').style.display = 'inline-block';
                transmissionCell.querySelector('.editable-field').style.display = 'none';

                colourCell.querySelector('.view-mode').style.display = 'inline-block';
                colourCell.querySelector('.editable-field').style.display = 'none';

                yearCell.querySelector('.view-mode').style.display = 'inline-block';
                yearCell.querySelector('.editable-field').style.display = 'none';

                priceCell.querySelector('.view-mode').style.display = '';
                priceCell.querySelector('.editable-field').style.display = 'none';

                // Show Edit button, hide Save and Cancel buttons
                row.querySelector('.edit-button').style.display = 'inline-block';
                button.style.display = 'none';
                row.querySelector('.save-button').style.display = 'none';
            };

            window.toggleRestockMode = function (button) {
                const row = button.closest('tr');
                row.querySelector(".stock-field").style.display = 'inline-block';

                // Show Edit button, hide Save and Cancel buttons
                row.querySelector('.save-restock-button').style.display = 'inline-block';
                button.style.display = 'none';
                row.querySelector('.cancel-restock-button').style.display = 'inline-block';
            };

            window.restockCar = function (car_id, button) {
                const row = button.closest('tr');
                row.querySelector(".stock-field").style.display = 'none';

                row.querySelector('.restock-button').style.display = 'inline-block';
                button.style.display = 'none';
                row.querySelector('.cancel-restock-button').style.display = 'none';

                document.getElementById('restock_car_id').value = car_id;
                document.getElementById('restock_number').value = row.querySelector(".stock-field").value;
                document.getElementById('restockCarForm').submit();
            };

            window.cancelRestockMode = function (button) {
                const row = button.closest('tr');
                row.querySelector(".stock-field").style.display = 'none';
                // Show Edit button, hide Save and Cancel buttons
                row.querySelector('.restock-button').style.display = 'inline-block';
                button.style.display = 'none';
                row.querySelector('.save-restock-button').style.display = 'none';
            };
            
            window.deleteCar = function (car_id) {
                const isConfirmed = confirm("Confirm to delete this car?");
                if (isConfirmed) {            
                    document.getElementById('delete_car_id').value = car_id;
                    document.getElementById('deleteCarForm').submit();
                }
            };
            
            document.getElementById('searchCar').addEventListener('keydown', function (event) {
                // Check if the pressed key is "Enter" (key code 13)
                if (event.key === 'Enter') {
                    let searchTerm = this.value.trim(); // Get the search term and trim whitespace
                    searchTerm = searchTerm.toLowerCase();
                    document.getElementById('search_input').value = searchTerm;
                    document.getElementById('filterCar').submit();
                }
            });

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
        <c:if test="${not empty sessionScope.manager_errorMsg}">
            ${sessionScope.manager_errorMsg}
            <% session.removeAttribute("manager_errorMsg");%>
        </c:if>  
    </div>
    <%@ include file="header.jsp" %>
    <div class="table-container">
        <h2 class="text-center my-4">Car Information</h2>
        
        <!-- Search Bar -->
        <form id="filterCar" style="display: none" action="${pageContext.request.contextPath}/FilterCarTable" method="POST">
            <input type="hidden" id="search_input" name="search_input">  
        </form>
        <div class="function-bar">
            <a href="add_new_car.jsp"><button class="btn btn-primary add-button">Add New Car</button></a>
            <input type="text" id="searchCar" placeholder="Search by Model Name/Brand/Colour/FuelType...">
        </div>
        
        <!-- Responsive Table -->
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                    <tr>
                        <th scope="col">Model Name</th>
                        <th scope="col">Brand</th>
                        <th scope="col">Fuel Type</th>
                        <th scope="col">HorsePower</th>
                        <th scope="col">Seats</th>
                        <th scope="col">Transmission Type</th>
                        <th scope="col">Colour</th>
                        <th scope="col">Year of Manufacture</th>
                        <th scope="col">Price</th>
                        <th scope="col">Available/Interested</th>
                        <th scope="col">Booked</th>
                        <th scope="col">Paid</th>
                        <th scope="col">Add Stock</th>
                        <th scope="col">Edit Car</th>
                        <th scope="col">Delete Car</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="car" items="${sessionScope.all_cars}">
                    <tr class="${car.availableCount < 10 ? 'low-stock' : ''}">
                        <!-- Model Name -->
                        <td class="modelName-cell">
                            <span class="view-mode">${car.model_name}</span>
                            <input type="text" class="editable-field" value="${car.model_name}">
                        </td>

                        <!-- Brand -->
                        <td class="brand-cell">
                            <span class="view-mode">${car.brand}</span>
                            <input type="text" class="editable-field" value="${car.brand}">
                        </td>

                        <!-- Fuel Type -->
                        <td class="fuelType-cell">
                            <span class="view-mode">${car.fuel_type}</span>
                            <input type="text" class="editable-field" value="${car.fuel_type}">
                        </td>

                        <!-- HorsePower -->
                        <td class="horsepower-cell">
                            <span class="view-mode">${car.horsepower}</span>
                            <input type="number" class="editable-field" min="1" value="${car.horsepower}">
                        </td>

                        <!-- Seats -->
                        <td class="seats-cell">
                            <span class="view-mode">${car.seats}</span>
                            <input type="number" class="editable-field" min="1" value="${car.seats}">
                        </td>

                        <!-- Transmission Type -->
                        <td class="transmission-cell">
                            <span class="view-mode">${car.transmission_type}</span>
                            <input type="text" class="editable-field" value="${car.transmission_type}">
                        </td>

                        <!-- Colour -->
                        <td class="colour-cell">
                            <span class="view-mode">${car.colour}</span>
                            <input type="text" class="editable-field" value="${car.colour}">
                        </td>
                        
                        <!-- Year of Manufacture -->
                        <td class="year-cell">
                            <span class="view-mode">${car.year_of_manufacture}</span>
                            <input type="number" class="editable-field" min="1" value="${car.year_of_manufacture}">
                        </td>
                        
                        <!-- Price -->
                        <td class="price-cell">
                            <span class="view-mode">${car.price}</span>
                            <input type="number" class="editable-field" min="1" step="0.01" value="${car.price}">
                        </td>
                        
                        <!-- Available/Interested Stock Count -->
                        <td class="available-cell">
                            <span class="view-mode">${car.availableCount}</span>
                        </td>
                        
                        <!-- Booked Stock Count -->
                        <td class="booked-cell">
                            <span class="view-mode">${car.bookedCount}</span>
                        </td>
                        
                        <!-- Paid Stock Count -->
                        <td class="paid-cell">
                            <span class="view-mode">${car.paidCount}</span>
                        </td>
                        
                        <!-- Restock Button -->
                        <td>
                            <form id="restockCarForm" action="${pageContext.request.contextPath}/RestockCar" method="POST" style="display: none;">
                                <input type="hidden" name="car_id" id="restock_car_id">
                                <input type="hidden" name="restock_number" id="restock_number">
                            </form>
                            
                            <input type="number" class="stock-field" id="add_stock_number_${car.car_id}" style="display:none" min="1" step="1">
                           
                            <button class="btn btn-primary restock-button" onclick="toggleRestockMode(this)">Restock</button>
                            <button class="btn btn-success save-restock-button" onclick="restockCar('${car.car_id}', this)" style="display: none;">Confirm</button>
                            <button class="btn btn-secondary cancel-restock-button" onclick="cancelRestockMode(this)" style="display: none;">Cancel</button>
                        </td>

                        <!-- Edit Mode Button -->
                        <td class="updateCarField">
                            <form id="updateCarForm" action="${pageContext.request.contextPath}/UpdateCarInfo" method="POST" style="display: none;">
                                <input type="hidden" name="car_id" id="edit_car_id">
                                <input type="hidden" name="model_name" id="edit_model_name">
                                <input type="hidden" name="brand" id="edit_brand">
                                <input type="hidden" name="fuel_type" id="edit_fuel_type">
                                <input type="hidden" name="horsepower" id="edit_horsepower">
                                <input type="hidden" name="seats" id="edit_seats">
                                <input type="hidden" name="transmission" id="edit_transmission">
                                <input type="hidden" name="colour" id="edit_colour">
                                <input type="hidden" name="year" id="edit_year">
                                <input type="hidden" name="price" id="edit_price">
                            </form>
                            <button class="btn btn-primary edit-button" onclick="toggleEditMode(this)">Edit</button>
                            <button class="btn btn-success save-button" onclick="saveChanges('${car.car_id}', this)" style="display: none;">Save</button>
                            <button class="btn btn-secondary cancel-button" onclick="cancelEditMode(this)" style="display: none;">Cancel</button>  
                        </td>
                        
                        <!-- Delete Car Button -->
                        <td>
                            <form action="${pageContext.request.contextPath}/DeleteCar" id="deleteCarForm" method="POST">
                                <input type="hidden" name="car_id" id="delete_car_id">
                            </form>
                            <button class="btn btn-sm btn-danger" onclick="deleteCar('${car.car_id}')">Delete</button>
                        </td>
                    </tr>  
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
