/*document.addEventListener('DOMContentLoaded', () => {
    const botonesBorrar = document.querySelectorAll('.borrar');
    const precioFinalElement = document.getElementById('precio-final');

    botonesBorrar.forEach(boton => {
        boton.addEventListener('click', (event) => {
            event.preventDefault(); // Prevenir el envío del formulario

            const entradaId = event.target.getAttribute('data-id');
            const fila = event.target.closest('tr'); // Obtener la fila de la entrada
            const cantidadInput = fila.querySelector('.cantidad'); // Obtener el input de cantidad
            const subtotalElement = fila.querySelector('.subtotal-entrada'); // Obtener el elemento subtotal

            let cantidad = parseInt(cantidadInput.value);
            if (cantidad > 1) {
                cantidad--; // Disminuir cantidad
                cantidadInput.value = cantidad; // Actualizar el input de cantidad
            } else {
                fila.remove(); // Eliminar la fila si la cantidad es 0
            }

            // Actualizar el subtotal
            const precioEntrada = parseFloat(subtotalElement.textContent); // Suponiendo que aquí tienes el precio
            subtotalElement.textContent = (cantidad * precioEntrada).toFixed(2);

            // Actualizar precio final
            actualizarPrecioFinal();
        });
    });

    function actualizarPrecioFinal() {
        let total = 0;
        const subtotales = document.querySelectorAll('.subtotal-entrada');
        subtotales.forEach(subtotal => {
            total += parseFloat(subtotal.textContent);
        });
        precioFinalElement.textContent = total.toFixed(2);
    }
});
*/



/*
let entradas = document.getElementById("entradasCarrito");
const entradasCarrito = entradas.value;
const entradasArray = JSON.parse(entradasCarrito);


console.log(entradasArray);



let boton = document.getElementById("tachito");


boton.addEventListener("click", function(e) {
    //le pedimos el atributo que colocamos en el html para poder obtener el id de esa entrada
    //el data id nos ayuda a agregar informacion adicional a un elemento sin modificar su funcionamiento/aspecto
    e.preventDefault();
    let idEntrada = boton.getAttribute("data-id");

    entradas.forEach((entrada, index) => {
        if (entrada.id === parseInt(idEntrada)) { // Compara el ID de la entrada con el ID del botón
            if (entrada.cantidad > 1) {
                entrada.cantidad--; // Si la cantidad es mayor a 1, la reducimos

                let precioUnitario = entrada.subtotal;
                let nuevaCantidad = entrada.cantidad;
                let nuevoSubtotal = precioUnitario * nuevaCantidad;

                actualizarCantidadYSubtotal(idEntrada, nuevaCantidad, nuevoSubtotal);

            } else {
                entradas.splice(index, 1); // Si la cantidad es 1 o menos, eliminamos la entrada del array
                boton.closest("tr").remove();
            }
        }
    });

    actualizarPrecioTotal();
});

function actualizarCantidadYSubtotal(idEntrada, nuevaCantidad, nuevoSubtotal){

    const fila = document.querySelector(`tr[data-id="${idEntrada}"]`);

    fila.querySelector(".cantidad-entrada").textContent = nuevaCantidad;
    fila.querySelector(".subtotal-entrada").textContent = `$${nuevoSubtotal.toFixed(2)}`;


}



function actualizarPrecioTotal(){
        // Calcula el precio total sumando los subtotales de cada entrada en el carrito
        const precioTotal = entradas.reduce((total, item) => total + item.subtotal, 0);
        // Actualiza el precio total en el DOM
        document.getElementById("precio-final").textContent = `$${precioTotal.toFixed(2)}`;

}*/