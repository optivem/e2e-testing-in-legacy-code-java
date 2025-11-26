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

        return await handleApiResponse(response);
    }

    async getOrder(orderNumber) {
        const response = await fetch(`${this.baseUrl}/${orderNumber}`, {
            method: 'GET'
        });

        return await handleApiResponse(response);
    }

    async cancelOrder(orderNumber) {
        const response = await fetch(`${this.baseUrl}/${orderNumber}/cancel`, {
            method: 'POST'
        });

        if (response.status === 204) {
            return { success: true };
        }

        await handleApiResponse(response);
    }
}

const orderService = new OrderService();

