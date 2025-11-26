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
    const template = document.getElementById('orderDetailsTemplate');
    const content = template.content.cloneNode(true);

    content.querySelector('#displayOrderNumber').value = order.orderNumber;
    content.querySelector('#displaySku').value = order.sku;
    content.querySelector('#displayCountry').value = order.country;
    content.querySelector('#displayQuantity').value = order.quantity;
    content.querySelector('#displayUnitPrice').value = `$${order.unitPrice.toFixed(2)}`;
    content.querySelector('#displayOriginalPrice').value = `$${order.originalPrice.toFixed(2)}`;
    content.querySelector('#displayDiscountRate').value = `${(order.discountRate * 100).toFixed(2)}%`;
    content.querySelector('#displayDiscountAmount').value = `$${order.discountAmount.toFixed(2)}`;
    content.querySelector('#displaySubtotalPrice').value = `$${order.subtotalPrice.toFixed(2)}`;
    content.querySelector('#displayTaxRate').value = `${(order.taxRate * 100).toFixed(2)}%`;
    content.querySelector('#displayTaxAmount').value = `$${order.taxAmount.toFixed(2)}`;
    content.querySelector('#displayTotalPrice').value = `$${order.totalPrice.toFixed(2)}`;
    content.querySelector('#displayStatus').value = order.status;

    const cancelBtn = content.querySelector('#cancelButton');
    if (order.status === 'PLACED') {
        cancelBtn.addEventListener('click', () => handleCancelOrder(order.orderNumber));
    } else {
        cancelBtn.remove();
    }

    const container = document.getElementById('orderDetails');
    container.innerHTML = '';
    container.appendChild(content);
}

async function handleCancelOrder(orderNumber) {
    await handleApiCall(async () => {
        await orderService.cancelOrder(orderNumber);
        showNotification('Order cancelled successfully!', false);
        await displayOrderDetails(orderNumber);
    }, 'Error cancelling order. Please try again.');
}

