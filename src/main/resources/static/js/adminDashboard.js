// ---------------- Product Category Filter ----------------
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
		// Initialize the filter buttons after content loads
		initProductFilters();
	});
}

// ---------------- Update Product Modal Handler ----------------

$(document).on("click", ".updateProductBtn", function() {
    let productId = $(this).data("id");
    console.log("Clicked product:", productId);

    // Load fragment from separate file via AJAX
    $("#updateProductContent").load("/editProduct/" + productId, function(response, status, xhr) {
        if (status === "success") {
            console.log("Fragment loaded successfully!");
            $("#updateProductModal").modal("show");
        } else {
            console.error("Error loading fragment: " + xhr.status + " " + xhr.statusText);
        }
    });
});



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
	console.log("adminDashboard Ajax file is loaded");

	// Load products by default
	loadProducts();

	// Left-side menu navigation
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
/*------- */
function editProduct() {

}