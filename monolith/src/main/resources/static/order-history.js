// UI Controller for Order History page

document.getElementById('searchForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const orderNumber = document.getElementById('orderNumber').value;
    await displayOrderDetails(orderNumber);
});

async function displayOrderDetails(orderNumber) {
    await handleApiCall(async () => {
        const order = await orderService.getOrder(orderNumber);
        renderOrderDetails(order);
    }, 'Error retrieving order. Please try again.');
}

function renderOrderDetails(order) {
    const resultDiv = document.getElementById('orderDetails');

    const cancelButtonHtml = order.status === 'PLACED'
        ? `<button type="button" id="cancelButton" aria-label="Cancel Order">Cancel Order</button>`
        : '';

    resultDiv.innerHTML = `
        <h2>Order Details</h2>
        <div>
            <label for="displayOrderNumber">Order Number:</label>
            <input type="text" id="displayOrderNumber" aria-label="Display Order Number" value="${order.orderNumber}" readonly />
        </div>
        <div>
            <label for="displaySku">SKU:</label>
            <input type="text" id="displaySku" aria-label="Display SKU" value="${order.sku}" readonly />
        </div>
        <div>
            <label for="displayCountry">Country:</label>
            <input type="text" id="displayCountry" aria-label="Display Country" value="${order.country}" readonly />
        </div>
        <div>
            <label for="displayQuantity">Quantity:</label>
            <input type="text" id="displayQuantity" aria-label="Display Quantity" value="${order.quantity}" readonly />
        </div>
        <div>
            <label for="displayUnitPrice">Unit Price:</label>
            <input type="text" id="displayUnitPrice" aria-label="Display Unit Price" value="$${order.unitPrice.toFixed(2)}" readonly />
        </div>
        <div>
            <label for="displayOriginalPrice">Original Price:</label>
            <input type="text" id="displayOriginalPrice" aria-label="Display Original Price" value="$${order.originalPrice.toFixed(2)}" readonly />
        </div>
        <div>
            <label for="displayDiscountRate">Discount Rate:</label>
            <input type="text" id="displayDiscountRate" aria-label="Display Discount Rate" value="${(order.discountRate * 100).toFixed(2)}%" readonly />
        </div>
        <div>
            <label for="displayDiscountAmount">Discount Amount:</label>
            <input type="text" id="displayDiscountAmount" aria-label="Display Discount Amount" value="$${order.discountAmount.toFixed(2)}" readonly />
        </div>
        <div>
            <label for="displaySubtotalPrice">Subtotal Price:</label>
            <input type="text" id="displaySubtotalPrice" aria-label="Display Subtotal Price" value="$${order.subtotalPrice.toFixed(2)}" readonly />
        </div>
        <div>
            <label for="displayTaxRate">Tax Rate:</label>
            <input type="text" id="displayTaxRate" aria-label="Display Tax Rate" value="${(order.taxRate * 100).toFixed(2)}%" readonly />
        </div>
        <div>
            <label for="displayTaxAmount">Tax Amount:</label>
            <input type="text" id="displayTaxAmount" aria-label="Display Tax Amount" value="$${order.taxAmount.toFixed(2)}" readonly />
        </div>
        <div>
            <label for="displayTotalPrice">Total Price:</label>
            <input type="text" id="displayTotalPrice" aria-label="Display Total Price" value="$${order.totalPrice.toFixed(2)}" readonly />
        </div>
        <div>
            <label for="displayStatus">Status:</label>
            <input type="text" id="displayStatus" aria-label="Display Status" value="${order.status}" readonly />
        </div>
        ${cancelButtonHtml}
    `;

    if (order.status === 'PLACED') {
        const cancelBtn = document.getElementById('cancelButton');
        cancelBtn.addEventListener('click', () => handleCancelOrder(order.orderNumber));
    }
}

async function handleCancelOrder(orderNumber) {
    await handleApiCall(async () => {
        await orderService.cancelOrder(orderNumber);
        showNotification('Order cancelled successfully!', false);
        await displayOrderDetails(orderNumber);
    }, 'Error cancelling order. Please try again.');
}

