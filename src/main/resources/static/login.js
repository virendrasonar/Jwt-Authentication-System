function togglePassword(id, eye) {

    const input = document.getElementById(id);

    if (input.type === "password") {
        input.type = "text";
        eye.innerHTML = "🙈";
    } else {
        input.type = "password";
        eye.innerHTML = "👁";
    }

}

async function login() {

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    if (email === "" || password === "") {

        document.getElementById("message").style.color = "red";
        document.getElementById("message").innerText =
            "Please enter email and password.";

        return;
    }

    try {

        const response = await fetch("http://localhost:8080/auth/login", {

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

            document.getElementById("message").style.color = "lime";
            document.getElementById("message").innerText =
                "Login Successful! Redirecting...";

            setTimeout(() => {
                window.location.href = "dashboard.html";
            }, 1000);

        } else {

            document.getElementById("message").style.color = "red";
            document.getElementById("message").innerText =
                data.message || "Invalid Email or Password";

        }

    } catch (error) {

        document.getElementById("message").style.color = "red";
        document.getElementById("message").innerText =
            "Unable to connect to server.";

        console.error(error);

    }

}
