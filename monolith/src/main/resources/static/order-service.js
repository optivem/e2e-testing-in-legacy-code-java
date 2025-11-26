// Service layer for Order API operations

class OrderService {
    constructor(baseUrl = '/api/orders') {
        this.baseUrl = baseUrl;
    }

    async placeOrder(sku, quantity, country) {
        const response = await fetch(this.baseUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ sku, quantity, country })
        });

        return await handleApiResponse(response, {
            onDefaultErrorMessage: 'Error placing order. Please try again.'
        });
    }

    async getOrder(orderNumber) {
        const response = await fetch(`${this.baseUrl}/${orderNumber}`, {
            method: 'GET'
        });

        return await handleApiResponse(response, {
            on404Message: 'Order not found. Please check the order number.',
            onDefaultErrorMessage: 'Error retrieving order. Please try again.'
        });
    }

    async cancelOrder(orderNumber) {
        const response = await fetch(`${this.baseUrl}/${orderNumber}/cancel`, {
            method: 'POST'
        });

        if (response.status === 204) {
            return { success: true };
        }

        await handleApiResponse(response, {
            onDefaultErrorMessage: 'Failed to cancel order. Please try again.'
        });
    }
}

const orderService = new OrderService();

