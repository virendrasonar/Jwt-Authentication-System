const token = localStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}

fetch("http://localhost:8080/dashboard", {

    method: "GET",

    headers: {
        "Authorization": "Bearer " + token
    }

})
.then(response => {

    if (!response.ok) {
        throw new Error("Unauthorized");
    }

    return response.json();

})
.then(data => {

    document.getElementById("welcome").innerHTML =
        "Welcome, " + data.username + " 👋";

    document.getElementById("username").innerHTML =
        data.username;

    document.getElementById("message").innerHTML =
        data.message;

})
.catch(error => {

    console.log(error);

    localStorage.removeItem("token");

    window.location.href = "login.html";

});

function logout() {

    localStorage.removeItem("token");

    window.location.href = "login.html";

}