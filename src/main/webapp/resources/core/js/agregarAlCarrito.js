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