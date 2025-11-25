document.getElementById('orderForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    try {
        const productIdValue = document.getElementById('productId').value;
        const quantityValue = document.getElementById('quantity').value;
        const countryValue = document.getElementById('country').value;

        document.getElementById('notifications').innerHTML = '';

        const quantityTrimmed = quantityValue.trim();

        if (quantityTrimmed === '') {
            showNotification('Quantity must not be empty', true);
            return;
        }

        const quantityNum = parseFloat(quantityTrimmed);

        if (isNaN(quantityNum)) {
            showNotification('Quantity must be an integer', true);
            return;
        }

        if (!Number.isInteger(quantityNum)) {
            showNotification('Quantity must be an integer', true);
            return;
        }

        const sku = productIdValue.trim();
        const quantity = parseInt(quantityValue);
        const country = countryValue.trim();

        if (!sku) {
            showNotification('SKU must not be empty', true);
            return;
        }

        if (quantity <= 0) {
            showNotification('Quantity must be positive', true);
            return;
        }

        if (!country) {
            showNotification('Country must not be empty', true);
            return;
        }

        console.log('Creating order with:', { sku, quantity, country });

        const response = await fetch('/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                sku: sku,
                quantity: quantity,
                country: country
            })
        });

        console.log('Order creation response status:', response.status, response.statusText);

        if (response.ok) {
            const order = await response.json();
            console.log('Order created successfully:', order);
            showNotification('Success! Order has been created with Order Number ' + order.orderNumber, false);
        } else if (response.status === 404) {
            try {
                const errorData = await response.json();
                console.log('404 error data:', errorData);

                if (errorData.detail) {
                    showNotification(errorData.detail, true);
                } else if (errorData.message) {
                    showNotification(errorData.message, true);
                } else {
                    showNotification('Resource not found.', true);
                }
            } catch (e) {
                console.error('Error parsing 404 response:', e);
                showNotification('Resource not found.', true);
            }
        } else if (response.status === 400 || response.status === 422) {
            try {
                const errorData = await response.json();
                console.log('Validation error data:', errorData);

                let displayMessage = '';

                if (errorData.detail) {
                    displayMessage = errorData.detail;

                    if (errorData.errors && errorData.errors.length > 0) {
                        const fieldErrors = errorData.errors.map(e => `${e.field}: ${e.message}`).join('; ');
                        displayMessage += '\n' + fieldErrors;
                    }
                } else if (errorData.message) {
                    displayMessage = errorData.message;
                } else {
                    displayMessage = Object.entries(errorData).map(([field, msg]) => `${field}: ${msg}`).join('; ');
                }

                showNotification(displayMessage, true);
            } catch (e) {
                console.error('Error parsing validation error response:', e);
                showNotification('Validation error. Please check your input.', true);
            }
        } else {
            console.error('Order creation failed with status:', response.status);
            console.error('Response status text:', response.statusText);

            response.text().then(body => {
                console.error('Response body:', body);
            }).catch(e => {
                console.error('Could not read response body:', e);
            });

            showNotification('Error placing order. Please try again. (Status: ' + response.status + ')', true);
        }
    } catch (error) {
        console.error('Exception during order creation:', error);
        console.error('Error message:', error.message);
        console.error('Error stack:', error.stack);
        showNotification('Error placing order. Please try again. (Exception: ' + error.message + ')', true);
    }
});

