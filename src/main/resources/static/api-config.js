(function () {
    const isLocalPreview = window.location.protocol === "file:"
        || window.location.hostname === "localhost"
        || window.location.hostname === "127.0.0.1";
    const apiBaseUrl = isLocalPreview && window.location.port !== "8080"
        ? "http://localhost:8080"
        : window.location.origin;

    window.APP_CONFIG = Object.freeze({
        API_BASE_URL: apiBaseUrl
    });

    window.apiUrl = function (path) {
        const normalizedPath = path.startsWith("/") ? path : "/" + path;
        return apiBaseUrl + normalizedPath;
    };
})();
