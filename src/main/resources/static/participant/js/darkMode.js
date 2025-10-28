document.addEventListener("DOMContentLoaded", function () {
    const body = document.body;
    const toggleButton = document.getElementById("darkModeToggle");

    function updateIcon() {
        if (body.classList.contains("dark-mode")) {
            toggleButton.textContent = "☀️"; // Dark mode aktivdirsə gün simvolu
        } else {
            toggleButton.textContent = "🌑"; // Dark mode deaktivdirsə ay simvolu
        }
    }

    // LocalStorage-dan state oxu
    if (localStorage.getItem("darkMode") === "enabled") {
        body.classList.add("dark-mode");
    } else {
        body.classList.remove("dark-mode");
    }
    updateIcon();

    if (toggleButton) {
        toggleButton.addEventListener("click", function () {
            body.classList.toggle("dark-mode");

            if (body.classList.contains("dark-mode")) {
                localStorage.setItem("darkMode", "enabled");
            } else {
                localStorage.setItem("darkMode", "disabled");
            }
            updateIcon();
        });
    } else {
        console.log("Dark mode toggle button not found!");
    }
});
