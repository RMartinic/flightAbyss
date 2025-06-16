function sortTableByColumn(table, columnIndex, isNumeric = false) {
    const tbody = table.querySelector("tbody");
    const rowsArray = Array.from(tbody.querySelectorAll("tr"));

    const prevSortedCol = parseInt(table.getAttribute("data-sort-column"));
    const prevOrder = table.getAttribute("data-sort-order");

    let ascending = true;
    if (prevSortedCol === columnIndex && prevOrder === "asc") {
        ascending = false;
    }

    table.setAttribute("data-sort-column", columnIndex);
    table.setAttribute("data-sort-order", ascending ? "asc" : "desc");

    rowsArray.sort((a, b) => {
        const aText = a.cells[columnIndex].textContent.trim();
        const bText = b.cells[columnIndex].textContent.trim();

        let comparison;
        if (isNumeric) {
            comparison = parseFloat(aText) - parseFloat(bText);
        } else {
            const aDate = new Date(aText);
            const bDate = new Date(bText);
            comparison = aDate - bDate;
        }

        return ascending ? comparison : -comparison;
    });

    tbody.innerHTML = "";
    rowsArray.forEach(row => tbody.appendChild(row));

    table.querySelectorAll("th").forEach((th, idx) => {
        const originalText = th.getAttribute("data-original-text");
        if (originalText) {
            th.textContent = originalText;
        }
    });

    const header = table.querySelectorAll("th")[columnIndex];
    if (!header.getAttribute("data-original-text")) {
        header.setAttribute("data-original-text", header.textContent);
    }

    const arrow = ascending ? " ↑" : " ↓";
    header.textContent = header.getAttribute("data-original-text") + arrow;
}

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll("table").forEach(table => {
        const headers = table.querySelectorAll("th");
        table.setAttribute("data-sort-column", -1); // initialize
        table.setAttribute("data-sort-order", "asc");

        headers.forEach((header, i) => {
            const text = header.textContent.toLowerCase();

            if (text.includes("price")) {
                header.style.cursor = "pointer";
                header.addEventListener("click", () => sortTableByColumn(table, i, true));
            }

            if (text.includes("departure time")) {
                header.style.cursor = "pointer";
                header.addEventListener("click", () => sortTableByColumn(table, i, false));
            }
        });
    });
});