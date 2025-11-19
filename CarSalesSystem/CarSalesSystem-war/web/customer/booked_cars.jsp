<%-- 
    Document   : booked_cars
    Created on : Apr 2, 2025, 9:59:48 AM
    Author     : Chew Jin Ni
--%>

<%@page import="model.Deposit"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.CarType"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Booked Cars</title>
        <link rel="stylesheet" type="text/css" href="../css/interested_cars.css">
        <link rel="stylesheet" type="text/css" href="../css/popup_container.css">
        <link rel="stylesheet" type="text/css" href="../css/rating.css">
    </head>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            window.openRating = function (sales_id) {
                // Show the pop-up
                document.getElementById('popupOverlay').style.display = 'block';
                document.getElementById('popupContainer').style.display = 'block';
            };

            // Function to close the pop-up
            window.closeRating = function () {
                document.getElementById('popupOverlay').style.display = 'none';
                document.getElementById('popupContainer').style.display = 'none';
                resetRatingModal();
            };
            
            function resetRatingModal() {
                const stars = document.querySelectorAll('.star');
                stars.forEach((star) => {
                    star.classList.remove('selected');
                    star.style.color = 'gray';
                });
                document.getElementById('rating-value').textContent = '0';
                document.getElementById('rating-text').value = '';
            }
            
            window.submitRating = function () {
                const isConfirmed = confirm("Confirm to give rating for this purchase?");
                if (isConfirmed) {            
                    const ratingValue = document.getElementById('rating-value').textContent;
                    const feedbackText = document.getElementById('rating-text').value;

                    if (ratingValue === '0') {
                        alert('Please select a rating before submitting.');
                        return;
                    }

                    // Populate the hidden form fields
                    document.getElementById('rating_value_field').value = ratingValue;
                    document.getElementById('feedback_text_field').value = feedbackText;

                    // Submit the form
                    document.getElementById('ratingForm').submit();
                }
            };
            
            // Handle stars and rating values
            const stars = document.querySelectorAll('.star');
            const selectedRating = document.getElementById('rating-value');

            // Add event listeners for hover and click
            stars.forEach((star) => {
                // Hover Effect
                star.addEventListener('mouseover', () => {
                    const value = star.getAttribute('data-value');
                    stars.forEach((s, index) => {
                        if (index < value) {
                            s.style.color = 'gold'; // Highlight stars up to the hovered star
                        } else {
                            s.style.color = 'gray'; // Unhighlight remaining stars
                        }
                    });
                });

                // Remove Hover Effect on Mouse Out
                star.addEventListener('mouseout', () => {
                    const currentRating = parseInt(selectedRating.textContent);
                    stars.forEach((s, index) => {
                        if (index < currentRating) {
                            s.style.color = 'gold'; // Keep selected stars highlighted
                        } else {
                            s.style.color = 'gray'; // Unhighlight unselected stars
                        }
                    });
                });

                // Click Event to Select a Rating
                star.addEventListener('click', () => {
                    const value = star.getAttribute('data-value');
                    selectedRating.textContent = value; // Update the selected rating

                    // Highlight selected stars
                    stars.forEach((s, index) => {
                        if (index < value) {
                            s.classList.add('selected');
                            s.style.color = 'gold';
                        } else {
                            s.classList.remove('selected');
                            s.style.color = 'gray';
                        }
                    });
                });
            });
            
            window.submitPayment = function(sales_id){
                document.getElementById('sales-id').value = sales_id;
                document.getElementById('updateDepositForm').submit();
            };
       
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
    <body>
        <div id="notification" class="error_notification">
            <c:if test="${not empty sessionScope.customer_errorMsg}">
                ${sessionScope.customer_errorMsg}
                <% session.removeAttribute("customer_errorMsg");%>
            </c:if>  
        </div>
        <%@ include file="header.jsp" %>
        <h2 class="page-title">Booked Cars</h2>
        <c:if test="${not empty booked_cars}">
            <div class="car-container">
                <!-- Iterate over the 'cars' list -->
                <c:forEach var="sales" items="${sessionScope.booked_cars}">
                    <c:set var="type" value="${sales.car_id.car_type}" />
                    <c:if test="${not empty type}">
                        <c:set var="car" value="${type.car}" />
                        <c:if test="${not empty car}">
                            <%
                                // Retrieve the absolutePath from the type object
                                String absolutePath = ((CarType) pageContext.getAttribute("type")).getImage();
                                String basePath = "D:\\NetbeanProject\\CarSalesSystem\\CarSalesSystem-war\\web\\";
                                String relativePath = "../image/logo.png"; // Default image

                                if (absolutePath != null && !absolutePath.isEmpty()) {
                                    if (absolutePath.startsWith(basePath)) {
                                        relativePath = "../" + absolutePath.substring(basePath.length()).replace("\\", "/");
                                    }
                                }

                                // Store the processed relativePath in the request scope
                                pageContext.setAttribute("relativePath", relativePath);
                            %>
                            <div class="car-stock-card">
                                <!-- Display the CarType's image -->
                                <img src="${relativePath}" alt="${type.colour}" class="car-image">

                                <!-- Card Details -->
                                <div class="card-details">
                                    <div class="card-header">
                                        <h3>${car.model_name}</h3>
                                    </div>

                                    <div class="card-body">
                                        <p><strong>Brand:</strong> ${car.brand}</p>
                                        <p><strong>Fuel Type:</strong> ${car.fuel_type}</p>
                                        <p><strong>Horsepower:</strong> ${car.horsepower}</p>
                                        <p><strong>Seats:</strong> ${car.seats}</p>
                                        <p><strong>Transmission Type:</strong> ${car.transmission_type}</p>
                                        <p><strong>Colour:</strong> ${type.colour}</p>
                                        <p><strong>Year of Manufacture:</strong> ${type.year_of_manufacture}</p>
                                        <h4 class="price">RM <strong>${type.price}</strong></h4>
                                    </div>
                                    <hr>
                                    <!-- Footer: Price and Buttons -->
                                   <div class="card-footer">
                                        <strong>Assigned Salesman:</strong>
                                        <span class="badge bg-success">${sales.salesman.user_id} | ${sales.salesman.name}</span>
                                    </div>
                                    <hr>
                                    <div class="card-footer">
                                        <strong>Sales Status:</strong>
                                        <span class="badge bg-warning">${sales.getSalesStatus()}</span>
                                    </div>
                                    <hr>
                                    <!-- Deposit Document Section -->
                                    <div class="deposit-section">
                                        <strong>Upload Deposit Document:</strong>
                                        <c:forEach var="dp" items="${sessionScope.deposit_list}">
                                            <c:if test="${dp.sales_id.sales_id == sales.sales_id}">
                                                <c:if test="${not empty dp.deposit_doc}">
                                                    <%
                                                        String base = "D:\\NetbeanProject\\CarSalesSystem\\CarSalesSystem-war\\web\\";
                                                            String deposit_absolutePath = ((Deposit) pageContext.getAttribute("dp")).getDeposit_doc();
                                                            String deposit_relativePath = "";
                                                            if (deposit_absolutePath != null) {
                                                                if (deposit_absolutePath.startsWith(base)) {
                                                                    deposit_relativePath = "../" + deposit_absolutePath.substring(base.length()).replace("\\", "/");
                                                                }
                                                            }

                                                            pageContext.setAttribute("deposit_relativePath", deposit_relativePath);
                                                    %>
                                                    <c:if test="${not empty deposit_relativePath}">
                                                        <a href="${deposit_relativePath}" target="_blank">View Document</a>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                        <form id="updateDepositForm" action="${pageContext.request.contextPath}/CusUpdatePaymentDoc" method="POST" enctype="multipart/form-data">
                                            <input type="hidden" id="sales-id" name="sales_id" value="${sales.sales_id}">
                                            <input type="hidden" id="type" name="type" value="deposit_doc">
                                            <input type="file" id="deposit_file" name="doc" accept="image/*" onchange="submitPayment('${sales.sales_id}')">
                                        </form>
                                    </div>

                                    <!-- Rating Section -->
                                    <c:if test="${not empty sales.rating.rating_id}">
                                        <hr>
                                        <strong>Rating:     </strong>${sales.rating.rating}
                                        <br>
                                        <strong>Comment:    </strong>${sales.rating.comment}
                                    </c:if>

                                    <!-- Rate Us Button -->
                                    <div class="rate-us-button">
                                        <button onclick="openRating('${sales.sales_id}')" class="btn btn-primary">Rate Us</button>
                                    </div>
                                    <form id="ratingForm" action="${pageContext.request.contextPath}/SubmitRating" method="POST" style="display: none;">
                                        <input type="hidden" name="sales_id" id="rating_sales_id" value="${sales.sales_id}">
                                        <input type="hidden" name="rating_value" id="rating_value_field">
                                        <input type="hidden" name="rating_feedback" id="feedback_text_field">
                                    </form>
                                </div>
                            </div>
                        </c:if>
                    </c:if>
                </c:forEach>
            </div>
        </c:if>
         <!-- Pop-up Overlay -->
        <div class="popup-overlay" id="popupOverlay" onclick="closeRating()"></div>

        <!-- Pop-up Container -->
        <div class="popup-container" id="popupContainer">
            <div class="modal-content">
                <h2>Rate Your Experience</h2>
                <!-- Star Rating -->
                <div class="rating-stars">
                    <span class="star" data-value="1">&#9734;</span>
                    <span class="star" data-value="2">&#9734;</span>
                    <span class="star" data-value="3">&#9734;</span>
                    <span class="star" data-value="4">&#9734;</span>
                    <span class="star" data-value="5">&#9734;</span>
                </div>
                <!-- Selected Rating -->
                <p id="selected-rating">Your Rating: <span id="rating-value">0</span> Stars</p>
                <!-- Feedback Text Area -->
                <label for="rating-text">Additional Feedback:</label>
                <textarea id="rating-text" placeholder="Enter your feedback here..."></textarea>
                <!-- Submit Button -->
                <button id="submit-rating" onclick="submitRating()" class="btn btn-success">Submit Rating</button>
            </div>
        </div>   
    </body>
</html>
