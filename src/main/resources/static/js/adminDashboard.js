function initProductFilters() {
    $('.category-btn').click(function() {
        $('.category-btn').removeClass('active');
        $(this).addClass('active');

        var category = $(this).data('category');

        $('#productContainer .col-md-4').each(function() {
            var prodCategory = $(this).data('category');
            if(category === 'All' || prodCategory === category) {
                $(this).show();
            } else {
                $(this).hide();
            }
        });
    });
}

// ---------------- Load Products & Bind Events ----------------
function loadProducts() {
    $("#main-content").load("/productsList", function() {
        // Initialize the filter buttons after content loads
        initProductFilters();

        // Bind update modal button click after products load
        $('.updateProductBtn').click(function() {
            let id = $(this).data('id');

            // Fetch product by ID using AJAX
            $.get('/getProductById/' + id, function(product) {
                let modal = $('#updateProductModal');

                // Pre-fill modal fields
                modal.find('input[name="id"]').val(product.id);
                modal.find('input[name="name"]').val(product.name);
                modal.find('textarea[name="description"]').val(product.description);
                modal.find('input[name="price"]').val(product.price);

                // Category dropdown
                let categoryOptions = '';
                $.each(product.categoryList || [], function(i, cat) {
                    let selected = (cat.cId === product.category.cId) ? 'selected' : '';
                    categoryOptions += `<option value="${cat.cId}" ${selected}>${cat.cName}</option>`;
                });
                modal.find('select[name="categoryId"]').html(categoryOptions);

                // Stock dropdown
                let stockOptions = '';
                $.each(product.stockList || [], function(i, stock) {
                    let selected = (stock.id === product.stockStatus.id) ? 'selected' : '';
                    stockOptions += `<option value="${stock.id}" ${selected}>${stock.status}</option>`;
                });
                modal.find('select[name="stockId"]').html(stockOptions);

                // Show modal
                modal.modal('show');
            });
        });
    });
}


$(document).ready(function () {
    console.log("adminDashboard Ajax file is loaded");

    // Load products by default
    loadProducts("/productsList");

    // Left-side menu
    $("#Products").click(function (e) {
        e.preventDefault();
         $("#main-content").load("/productsList");
    });

    $("#Orders").click(function (e) {
        e.preventDefault();
        $("#main-content").load("/orders");
    });

    $("#Charts").click(function (e) {
        e.preventDefault();
        $("#main-content").load("/charts");
    });

    $("#ManageUsers").click(function (e) {
        e.preventDefault();
        $("#main-content").load("/manageUsers");
    });
});
