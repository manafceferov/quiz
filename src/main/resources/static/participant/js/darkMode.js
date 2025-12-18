document.addEventListener("DOMContentLoaded", function () {
    const body = document.body;
    const toggleButton = document.getElementById("darkModeToggle");

    if (localStorage.getItem("darkMode") === "enabled") {
        body.classList.add("dark-mode");
    } else {
        body.classList.remove("dark-mode");
    }

    function updateIcon() {
        if (body.classList.contains("dark-mode")) {
            toggleButton.textContent = "☀️";
        } else {
            toggleButton.textContent = "🌑";
        }
    }

    if (toggleButton) {
        updateIcon();

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
        console.warn("⚠️ Dark mode düyməsi tapılmadı, amma səhifə işləməyə davam edəcək.");
    }
});
