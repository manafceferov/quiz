document.addEventListener("DOMContentLoaded", function () {
    const body = document.body;
    const toggleButton = document.getElementById("darkModeToggle");

    if (localStorage.getItem("darkMode") === "enabled") {
        body.classList.add("dark-mode");
    } else {
        body.classList.remove("dark-mode");
    }

    if (toggleButton) {
        toggleButton.addEventListener("click", function () {
            body.classList.toggle("dark-mode");

            if (body.classList.contains("dark-mode")) {
                localStorage.setItem("darkMode", "enabled");
            } else {
                localStorage.setItem("darkMode", "disabled");
            }
        });
    } else {
        console.log("Dark mode toggle button not found!");
    }
});