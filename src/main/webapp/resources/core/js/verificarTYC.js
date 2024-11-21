function mostrarTermYCond() {
    document.getElementById("termsPopup").classList.remove("hidden");
    document.getElementById("aceptacionTerminos").checked = false;
    document.body.style.overflow = "hidden";
}

function cerrarTerminosPopup() {
    document.getElementById("termsPopup").classList.add("hidden");
    document.body.style.overflow = "auto";
}

function aceptarTYC() {
    cerrarTerminosPopup();
    const checkbox = document.getElementById("aceptacionTerminos");
    document.getElementById("aceptacionTerminos").checked = true;
    checkbox.disabled = true;
}