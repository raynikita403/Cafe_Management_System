$(document).ready(function() {
    console.log("✅ Orders.js loaded");

    // ---------------- Checkout Button ----------------
    $('#checkoutBtn').click(function() {
        let cartItems = [];

        $('#cartTableModal tbody tr').not('.no-items').each(function() {
            let productId = $(this).data('product');
            let quantity = parseInt($(this).find('.qty-number').text());
            let price = parseFloat($(this).data('price'));
            let name = $(this).find('td:eq(1)').text();

            if(productId && quantity > 0) {
                cartItems.push({ productId, name, price, quantity });
            }
        });

        if(cartItems.length === 0) {
            Swal.fire('Error', 'Cart is empty!', 'error');
            return;
        }

        // Load the confirm order modal dynamically
        loadOrderModal(cartItems);
    });

    // ---------------- Confirm Order ----------------
    $(document).on('click', '#confirmOrderBtn', function() {
        let orders = [];

        $('#orderTableModal tbody tr').each(function() {
            let productId = $(this).data('product-id');
            let quantity = parseInt($(this).find('td:eq(2)').text());
            if(productId && quantity > 0) orders.push({ productId, quantity });
        });

        if (orders.length === 0) {
            Swal.fire('Error', 'No items to place order!', 'error');
            return;
        }

        $.ajax({
            url: '/order/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(orders),
            success: function(res) {
                if(res.status === 'success') {
                    Swal.fire('Success', res.message, 'success');
                    $('#orderModal').modal('hide');

                    // Reload cart items if the function exists
                    if(typeof loadCartItems === 'function') loadCartItems();
                } else {
                    Swal.fire('Error', res.message, 'error');
                }
            },
            error: function(err) {
                console.error(err);
                Swal.fire('Error', 'Failed to place order!', 'error');
            }
        });
    });
});

// ---------------- Create Order Modal ----------------
function createOrderModal() {
    if ($('#orderModal').length) return;

    let modalHTML = `
<div class="modal fade" id="orderModal" tabindex="-1" aria-labelledby="orderModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content bg-dark text-white">
            <div class="modal-header border-light">
                <h5 class="modal-title" id="orderModalLabel">Confirm Your Order</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <div class="table-responsive">
                    <table class="table table-striped table-dark" id="orderTableModal">
                        <thead>
                            <tr>
                                <th>Product</th>
                                <th>Price</th>
                                <th>Qty</th>
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Rows added dynamically -->
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="3" class="text-end fw-bold">Grand Total:</td>
                                <td class="text-start fw-bold" id="orderGrandTotal">0</td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
            <div class="modal-footer border-light">
                <button type="button" class="btn btn-success text-dark" id="confirmOrderBtn">Place Order</button>
                <button type="button" class="btn btn-light text-dark" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
    `;

    $('body').append(modalHTML);
}

// ---------------- Load Order Modal with Items ----------------
function loadOrderModal(items) {
    createOrderModal();

    let tbody = $('#orderTableModal tbody');
    tbody.empty();

    let grandTotal = 0;

    items.forEach(item => {
        let total = item.price * item.quantity;
        grandTotal += total;

        let row = `
<tr data-product-id="${item.productId}">
    <td>${item.name}</td>
    <td>${item.price.toFixed(2)}</td>
    <td>${item.quantity}</td>
    <td>${total.toFixed(2)}</td>
</tr>
        `;
        tbody.append(row);
    });

    $('#orderGrandTotal').text(grandTotal.toFixed(2));
    $('#orderModal').modal('show');
}
