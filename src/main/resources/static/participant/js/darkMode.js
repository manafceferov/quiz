document.addEventListener("DOMContentLoaded", function () {
    const body = document.body;
    const toggleButton = document.getElementById("darkModeToggle");

    // 🌙 Dark mode-u localStorage-dan oxu və tətbiq et
    if (localStorage.getItem("darkMode") === "enabled") {
        body.classList.add("dark-mode");
    } else {
        body.classList.remove("dark-mode");
    }

    // 🔁 İkonu yenilə
    function updateIcon() {
        if (body.classList.contains("dark-mode")) {
            toggleButton.textContent = "☀️"; // Gün simvolu — dark aktivdirsə
        } else {
            toggleButton.textContent = "🌑"; // Ay simvolu — light aktivdirsə
        }
    }

    // 🧠 Əgər düymə varsa (login olmadan da işləsin)
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
