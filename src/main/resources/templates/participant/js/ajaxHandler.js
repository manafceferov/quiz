// document.addEventListener("DOMContentLoaded", () => {
//     console.log("AJAX Handler yükləndi");  // Konsolda gör
//
//     document.querySelectorAll("form[data-ajax='true']").forEach(form => {
//         console.log("Form tapıldı:", form);
//         form.addEventListener("submit", e => {
//             console.log("Submit dayandırıldı");
//             e.preventDefault();
//
//             const formData = new FormData(this);
//             const url = this.getAttribute("action") || window.location.pathname;
//             const method = (this.getAttribute("method") || "get").toUpperCase();
//
//             const fetchOptions = { method };
//             if (method === "POST") {
//                 fetchOptions.body = formData;
//             }
//
//             fetch(url, fetchOptions)
//                 .then(res => {
//                     if (res.headers.get("content-type")?.includes("application/json")) {
//                         return res.json();
//                     } else {
//                         return res.text();
//                     }
//                 })
//                 .then(data => {
//                     const targetSelector = this.dataset.target;
//                     if (targetSelector) {
//                         const target = document.querySelector(targetSelector);
//                         if (target) {
//                             target.innerHTML = (typeof data === "string")
//                                 ? data
//                                 : JSON.stringify(data, null, 2);
//                         }
//                     }
//                 })
//                 .catch(err => console.error("AJAX xətası:", err));
//         });
//     });
//
//     // 🔹 Linkləri reloadsuz etmək (SPA effekti)
//     document.querySelectorAll("a[data-ajax='true']").forEach(link => {
//         link.addEventListener("click", function (e) {
//             e.preventDefault();
//             const href = this.getAttribute("href");
//
//             fetch(href)
//                 .then(res => res.text())
//                 .then(html => {
//                     const parser = new DOMParser();
//                     const doc = parser.parseFromString(html, "text/html");
//                     const content = doc.querySelector("#content");
//                     if (content) {
//                         document.querySelector("#content").innerHTML = content.innerHTML;
//                     }
//                     window.history.pushState({}, "", href);
//                 });
//         });
//     });
//
// });
