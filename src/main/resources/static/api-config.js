(function () {
    const apiBaseUrl = window.location.origin;

    window.APP_CONFIG = Object.freeze({
        API_BASE_URL: apiBaseUrl
    });

    window.apiUrl = function (path) {
        const normalizedPath = path.startsWith("/") ? path : "/" + path;
        return apiBaseUrl + normalizedPath;
    };
})();
