let formulario = document.getElementById("formulario-carrito");
let boton = document.getElementById("boton-comprar");

function validarBoton() {
    let botonDeshabilitado = false;
    let cantidades = document.getElementsByName("cantidades");

    let entradaSeleccionada = false;

    for (let i = 0; i < cantidades.length; i++) {
        if (parseInt(cantidades[i].value) > 0) {
            entradaSeleccionada = true;
        }
    }

    if (!entradaSeleccionada) {
        botonDeshabilitado = true;
    }

    boton.disabled = botonDeshabilitado;
}

formulario.addEventListener("input", validarBoton);

let cantidades = document.getElementsByName("cantidades");

// Inicializar valores al cargar la página
window.addEventListener("DOMContentLoaded", () => {
    cantidades.forEach((cantidad) => {
        // Verificar si el elemento correspondiente está marcado como "SOLD OUT"
        let label = cantidad.closest("tr").querySelector(".stock-label");
        if (label && label.textContent.includes("SOLD OUT")) {
            cantidad.value = "0"; // Establecer el valor en 0
            cantidad.disabled = true; // Deshabilitar el select
        }
    });
});