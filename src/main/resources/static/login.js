function togglePassword(id, eye) {
    const input = document.getElementById(id);

    if (input.type === "password") {
        input.type = "text";
        eye.innerHTML = "Hide";
    } else {
        input.type = "password";
        eye.innerHTML = "Show";
    }
}

const DEMO_ADMIN_CREDENTIALS = Object.freeze({
    email: "admin@example.com",
    password: "Admin@123"
});

function fillAdminFields(credentials, messageText) {
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");
    const message = document.getElementById("message");

    if (!emailInput || !passwordInput) {
        return;
    }

    emailInput.value = credentials.email;
    passwordInput.value = credentials.password;

    if (message) {
        message.style.color = "#2563eb";
        message.innerText = messageText;
    }
}

async function fillAdminLoginDefaults() {
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");

    if (!emailInput || !passwordInput) {
        return;
    }

    try {
        const response = await fetch(apiUrl("/auth/admin-login-defaults"));

        if (!response.ok || response.status === 204) {
            fillAdminFields(DEMO_ADMIN_CREDENTIALS, "Demo admin credentials filled.");
            return;
        }

        const defaults = await response.json();
        fillAdminFields({
            email: defaults.email || DEMO_ADMIN_CREDENTIALS.email,
            password: defaults.password || DEMO_ADMIN_CREDENTIALS.password
        }, "Admin credentials filled.");
    } catch (error) {
        fillAdminFields(DEMO_ADMIN_CREDENTIALS, "Demo admin credentials filled.");
    }
}

async function login() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const message = document.getElementById("message");

    if (email === "" || password === "") {
        message.style.color = "red";
        message.innerText = "Please enter email and password.";
        return;
    }

    try {
        const response = await fetch(apiUrl("/auth/login"), {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        const data = await response.json();

        if (response.ok) {
            localStorage.setItem("token", data.accessToken);
            localStorage.setItem("refreshToken", data.refreshToken);
            localStorage.setItem("role", data.role);

            message.style.color = "lime";
            message.innerText = "Login successful. Redirecting...";

            setTimeout(() => {
                window.location.href = "dashboard.html";
            }, 1000);
        } else {
            message.style.color = "red";
            message.innerText = data.message || "Invalid email or password";
        }
    } catch (error) {
        message.style.color = "red";
        message.innerText = "Unable to connect to server.";
        console.error(error);
    }
}
