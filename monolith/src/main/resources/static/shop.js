// UI Controller for Shop page

document.getElementById('orderForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const orderData = collectFormData();

    if (!validateFormData(orderData)) {
        return;
    }

    await handleApiCall(async () => {
        const order = await orderService.placeOrder(orderData.sku, orderData.quantity, orderData.country);
        showNotification('Success! Order has been created with Order Number ' + order.orderNumber, false);
    }, 'Error placing order. Please try again.');
});

function collectFormData() {
    const skuValue = document.getElementById('sku').value;
    const quantityValue = document.getElementById('quantity').value;
    const countryValue = document.getElementById('country').value;

    return {
        sku: skuValue.trim(),
        quantity: parseInt(quantityValue),
        country: countryValue.trim(),
        quantityValue: quantityValue
    };
}

function validateFormData(data) {
    document.getElementById('notifications').innerHTML = '';

    const quantityTrimmed = data.quantityValue.trim();

    if (quantityTrimmed === '') {
        showNotification('Quantity must not be empty', true);
        return false;
    }

    const quantityNum = parseFloat(quantityTrimmed);

    if (isNaN(quantityNum)) {
        showNotification('Quantity must be an integer', true);
        return false;
    }

    if (!Number.isInteger(quantityNum)) {
        showNotification('Quantity must be an integer', true);
        return false;
    }

    if (!data.sku) {
        showNotification('SKU must not be empty', true);
        return false;
    }

    if (data.quantity <= 0) {
        showNotification('Quantity must be positive', true);
        return false;
    }

    if (!data.country) {
        showNotification('Country must not be empty', true);
        return false;
    }

    return true;
}

