// ---------------- Product Category Filter ----------------
function initProductFilters() {
    $('.category-btn').click(function() {
        // Remove active class from all buttons
        $('.category-btn').removeClass('active');
        // Add active class to clicked button
        $(this).addClass('active');

        // Get selected category
        var category = $(this).data('category');

        // Show/hide products based on category
        $('#productContainer .col-md-4').each(function() {
            var prodCategory = $(this).data('category');
            if (category === 'All' || prodCategory === category) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });
}

// ---------------- Load Products via AJAX (Admin) ----------------
function loadProducts() {
    $("#main-content").load("/productsList", function() {
        // Initialize the filter buttons after content loads
        initProductFilters();
    });
}

// ---------------- Update Product Modal Handler (Admin) ----------------
$(document).on("click", ".updateProductBtn", function() {
    let productId = $(this).data("id");
    console.log("Clicked product:", productId);

    // Load fragment via AJAX
    $("#updateProductContent").load("/editProduct/" + productId, function(response, status, xhr) {
        if (status === "success") {
            console.log("Fragment loaded successfully!");
            $("#updateProductModal").modal("show");
        } else {
            console.error("Error loading fragment: " + xhr.status + " " + xhr.statusText);
        }
    });
});

// ---------------- Submit Update Form (Admin) ----------------
$(document).on("submit", ".updateProductForm", function(e) {
    e.preventDefault();

    let formData = new FormData(this);

    $.ajax({
        url: $(this).attr('action'),
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        success: function(response) {
            $("#updateProductModal").modal("hide");
            loadProducts(); // reload products list
        },
        error: function(xhr, status, error) {
            alert("Update failed: " + error);
        }
    });
});

// ---------------- Document Ready ----------------
$(document).ready(function() {
    console.log("Dashboard JS loaded");

    // Initialize filters (works for customer and admin)
    initProductFilters();

    // Load products on admin page
    if ($("#main-content").length) {
        loadProducts();
    }

    // Admin left-side menu navigation
    $("#Products").click(function(e) {
        e.preventDefault();
        loadProducts();
    });

    $("#Orders").click(function(e) {
        e.preventDefault();
        $("#main-content").load("/orders");
    });

    $("#Charts").click(function(e) {
        e.preventDefault();
        $("#main-content").load("/charts");
    });

    $("#ManageUsers").click(function(e) {
        e.preventDefault();
        $("#main-content").load("/manageUsers");
    });
});
