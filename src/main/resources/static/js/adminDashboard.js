// ---------------- Document Ready ----------------
$(document).ready(function() {
    console.log("Dashboard JS loaded");

    function showFloatingButton(visible) {
        if(visible) {
            $(".floating-btn").fadeIn();
        } else {
            $(".floating-btn").fadeOut();
        }
    }

    // ---------------- Product Filters ----------------
    function initProductFilters() {
        $('.category-btn').click(function() {
            $('.category-btn').removeClass('active');
            $(this).addClass('active');

            var category = $(this).data('category');
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

    // ---------------- Load Products ----------------
    function loadProducts() {
        $("#main-content").load("/productsList", function() {
            initProductFilters();
            showFloatingButton(true);
        });
    }

    loadProducts();

    // ---------------- Navigation Clicks ----------------
    $("#Products").click(function(e) {
        e.preventDefault();
        loadProducts();
    });

    $("#Orders").click(function(e) {
        e.preventDefault();
        $("#main-content").load("/orders", function() {
            showFloatingButton(false);
        });
    });

    $("#Charts").click(function(e) {
        e.preventDefault();
        $("#main-content").load("/charts", function() {
            showFloatingButton(false);
        });
    });

    $("#ManageUsers").click(function(e) {
        e.preventDefault();
        $("#main-content").load("/manageUsers", function() {
            showFloatingButton(false);
            initUserToggleButtons(); // Initialize toggle buttons after content loads
        });
    });

    // ---------------- Toggle User Active/Inactive ----------------
    function initUserToggleButtons() {
        $(".btn-sm").off("click").on("click", function() {
            var userId = $(this).data("id");
            var button = $(this);

            $.ajax({
                url: "/admin/toggleUserActive/" + userId,
                type: "POST",
                success: function(response) {
                    // Replace the user table fragment with updated data
                    $("#main-content").html(response);
                    initUserToggleButtons(); // Re-initialize buttons after reload
                },
                error: function(xhr, status, error) {
                    alert("Failed to update user status: " + error);
                }
            });
        });
    }

    // ---------------- Update Product Modal ----------------
    $(document).on("click", ".updateProductBtn", function() {
        let productId = $(this).data("id");
        console.log("Clicked product:", productId);

        $("#updateProductContent").load("/editProduct/" + productId, function(response, status, xhr) {
            if (status === "success") {
                $("#updateProductModal").modal("show");
            } else {
                console.error("Error loading fragment: " + xhr.status + " " + xhr.statusText);
            }
        });
    });

    $(document).on("submit", ".updateProductForm", function(e) {
        e.preventDefault();
        
         if (!validateUpdateProductForm()) {
        return; // Stop submission if validation fails
    }
        let formData = new FormData(this);

        $.ajax({
            url: $(this).attr('action'),
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                $("#updateProductModal").modal("hide");
                loadProducts();
            },
            error: function(xhr, status, error) {
                alert("Update failed: " + error);
            }
        });
    });

});
// ---------------- Validate Update Product Form ----------------
function validateUpdateProductForm() {
    let isValid = true;
    let name = $("input[name='name']").val().trim();
    let description = $("textarea[name='description']").val().trim();
    let price = parseFloat($("input[name='price']").val().trim());
    let category = $("select[name='categoryId']").val();
    let stock = $("select[name='stockId']").val();
    let image = $("input[name='productImage']").val();

    // Product Name (>= 5 chars)
    if (name.length < 5) {
        Swal.fire({
            icon: "warning",
            title: "Invalid Name",
            text: "Product name must be at least 5 characters long."
        });
        isValid = false;
    }

    // Description (>= 10 chars)
    else if (description.length < 10) {
        Swal.fire({
            icon: "warning",
            title: "Invalid Description",
            text: "Description must be at least 10 characters long."
        });
        isValid = false;
    }

    // Price (Qty > 10)
    else if (isNaN(price) || price <= 10) {
        Swal.fire({
            icon: "warning",
            title: "Invalid Price",
            text: "Price must be greater than 10."
        });
        isValid = false;
    }

    // Category must be selected
    else if (!category) {
        Swal.fire({
            icon: "warning",
            title: "Category Required",
            text: "Please select a category."
        });
        isValid = false;
    }

    // Stock must be selected
    else if (!stock) {
        Swal.fire({
            icon: "warning",
            title: "Stock Required",
            text: "Please select stock status."
        });
        isValid = false;
    }

    return isValid;
}
