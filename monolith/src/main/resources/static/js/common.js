// Common notification functions shared across all pages

function showNotification(message, isError = false, containerElementId = 'notifications') {
    const notificationsDiv = document.getElementById(containerElementId);

    notificationsDiv.innerHTML = '';

    const notif = document.createElement('div');
    notif.setAttribute('role', 'alert');
    notif.className = `notification ${isError ? 'error' : 'success'}`;
    notif.textContent = message;

    notificationsDiv.appendChild(notif);

    if (!isError) {
        setTimeout(() => {
            if (notif.parentNode) {
                notif.remove();
            }
        }, 5000);
    }
}

async function handleApiResponse(response, options = {}) {
    const { onSuccess } = options;

    if (response.ok) {
        const data = await response.json();
        if (onSuccess) {
            return onSuccess(data);
        }
        return data;
    }

    const errorData = await safeParseJson(response);
    let displayMessage = '';

    if (errorData?.detail) {
        displayMessage = errorData.detail;

        if (errorData.errors && errorData.errors.length > 0) {
            const fieldErrors = errorData.errors.map(e => `${e.field}: ${e.message}`).join('\n');
            displayMessage += '\n' + fieldErrors;
        }
    } else {
        displayMessage = `An unexpected error occurred. (Status: ${response.status})`;
    }

    showNotification(displayMessage, true);
    const error = new Error(displayMessage);
    error.alreadyHandled = true;
    throw error;
}

async function safeParseJson(response) {
    try {
        return await response.json();
    } catch (e) {
        console.error('Error parsing JSON response:', e);
        return null;
    }
}

async function handleApiCall(apiCallFn, errorMessage = 'An error occurred. Please try again.') {
    try {
        return await apiCallFn();
    } catch (error) {
        // If the error already has a notification shown (from handleApiResponse), just re-throw it
        if (error.message && (error.message.includes('Status:') || error.alreadyHandled)) {
            throw error;
        }
        console.error('Exception during API call:', error);
        showNotification(`${errorMessage} (Exception: ${error.message})`, true);
        throw error;
    }
}

