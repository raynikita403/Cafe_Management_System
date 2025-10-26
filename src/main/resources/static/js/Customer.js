$(document).ready(function() {
    console.log("✅ Customer.js loaded");

    // Menu page filters & add-to-cart
    if ($("#productContainer").length > 0) {
        initProductFilters();
        initAddToCart();
    }

    // Always init cart actions
    initCartActions();

    // Load cart items whenever modal opens
    $('#cartModal').on('show.bs.modal', function() {
        loadCartItems();
    });
});

// ---------------- Product Filters ----------------
function initProductFilters() {
    $('.category-btn').click(function() {
        $('.category-btn').removeClass('active');
        $(this).addClass('active');

        let category = $(this).data('category');
        $('#productContainer .col-md-4').each(function() {
            let prodCategory = $(this).data('category');
            if (category === 'All' || prodCategory === category) $(this).show();
            else $(this).hide();
        });
    });
}

// ---------------- Add to Cart ----------------
function initAddToCart() {
    $(document).on("click", ".add-to-cart-btn", function() {
        let productId = $(this).data("id");

        $.ajax({
            url: "/cart/add/" + productId,
            type: "POST",
            success: function(response) {
                if(response.status === "added") {
                    Swal.fire({
                        icon: 'success',
                        title: 'Added to Cart',
                        text: response.name + " has been added!",
                        timer: 1500,
                        showConfirmButton: false
                    });
                    addToCartModal(response);
                } else {
                    Swal.fire('Error', response.message, 'error');
                }
            },
            error: function(xhr) {
                Swal.fire('Error', "Failed to add: " + xhr.responseText, 'error');
            }
        });
    });
}

// ---------------- Load Cart Items ----------------
function loadCartItems() {
    $.ajax({
        url: '/cart/items',
        type: 'GET',
        success: function(items) {
            let tbody = $('#cartTableModal tbody');
            tbody.empty();

            if (!items || items.length === 0) {
                tbody.append('<tr class="no-items"><td colspan="5" class="text-center text-white">No items added</td></tr>');
                return;
            }

            items.forEach(product => addToCartModal(product));
        },
        error: function(xhr) {
            console.error("Failed to load cart items:", xhr);
        }
    });
}

// ---------------- Add Row to Modal ----------------
function addToCartModal(product) {
    let tbody = $("#cartTableModal tbody");

    // Avoid duplicate rows
    if (tbody.find("tr[data-id='" + product.cartItemId + "']").length > 0) return;

    let imgSrc = product.image || '/images/default-product.png';

    let row = `
<tr data-id="${product.cartItemId}" data-product="${product.productId}" data-price="${product.price}">
    <td><img src="${imgSrc}" width="50" height="50" class="rounded"></td>
    <td>${product.name}</td>
    <td>
        <button class="btn btn-danger btn-sm qty-decrease">-</button>
        <span class="qty-number">1</span>
        <button class="btn btn-success btn-sm qty-increase">+</button>
    </td>
    <td class="price-cell text-end">${product.price.toFixed(2)}</td>
    <td><button class="btn btn-danger btn-sm remove-btn">Remove</button></td>
</tr>
    `;
    tbody.append(row);
    updateCartTotal();
}

// ---------------- Quantity Buttons ----------------
$(document).on('click', '.qty-increase, .qty-decrease', function() {
    let row = $(this).closest('tr');
    let qtySpan = row.find('.qty-number');
    let qty = parseInt(qtySpan.text());

    if ($(this).hasClass('qty-increase')) qty++;
    else if (qty > 1) qty--;

    qtySpan.text(qty);
    updateRowPrice(row, qty);
});

function updateRowPrice(row, qty) {
    let unitPrice = parseFloat(row.data('price'));
    row.find('.price-cell').text((unitPrice * qty).toFixed(2));
    updateCartTotal();
}

// ---------------- Update Cart Total ----------------
function updateCartTotal() {
    let total = 0;
    let tbody = $('#cartTableModal tbody');

    // Calculate total from all product rows
    tbody.find('tr').not('#cartTotalRow').each(function() {
        let priceCell = $(this).find('.price-cell');
        if (priceCell.length > 0) total += parseFloat(priceCell.text());
    });

    // Remove existing total row
    $('#cartTotalRow').remove();

    // Append total row at the end (last row)
    tbody.append(`
<tr id="cartTotalRow" class="fw-bold">
    <td colspan="4" class="text-end">Total:</td>
    <td id="cartTotal" class="text-end">${total.toFixed(2)}</td>
</tr>
    `);
}

// ---------------- Cart Actions ----------------
function initCartActions() {
    // Remove
    $(document).on("click", ".remove-btn", function() {
        let row = $(this).closest("tr");
        let cartItemId = row.data("id");

        $.ajax({
            url: "/cart/remove/" + cartItemId,
            type: "POST",
            success: function() {
                row.remove();
                checkEmptyCart();
                updateCartTotal();
            },
            error: function() {
                console.error("❌ Failed to remove cart item:", cartItemId);
            }
        });
    });

    // Order
    $(document).on("click", ".order-btn", function() {
        let row = $(this).closest('tr');
        let productId = row.data('product'); 
        let quantity = parseInt(row.find('.qty-number').text());

        $.ajax({
            url: "/order/add",
            type: "POST",
            data: { productId, quantity },
            success: function() {
                row.remove();
                checkEmptyCart();
                updateCartTotal();
                Swal.fire('Success', 'Order placed!', 'success');
            },
            error: function() {
                Swal.fire('Error', 'Failed to place order', 'error');
            }
        });
    });
}

// ---------------- Empty Cart Check ----------------
function checkEmptyCart() {
    let tbody = $("#cartTableModal tbody");
    if (tbody.find("tr").length === 0) {
        tbody.append('<tr class="no-items"><td colspan="5" class="text-center">No items added</td></tr>');
    }
}