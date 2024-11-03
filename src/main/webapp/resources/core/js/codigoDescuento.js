async function aplicarDescuento() {
    try {
        const codigoDescuento = document.getElementById("codigoDescuento").value;
        const precioFinalElement = document.getElementById("precio-final-valor-original");

        if (!precioFinalElement) {
            alert("No se pudo obtener el total del carrito.");
            return;
        }

        const totalCarrito = parseFloat(precioFinalElement.innerText);

        if (isNaN(totalCarrito)) {
            alert("El total del carrito no es válido.");
            return;
        }

        const response = await fetch(`http://localhost:8080/equipomokito/aplicarDescuento?codigoDescuento=${codigoDescuento}&totalCarrito=${totalCarrito}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error("Error en la solicitud de descuento.");
        }

        const data = await response.json();

        if (data.descuentoAplicado) {
            const valorDescuento = (totalCarrito - data.totalConDescuento).toFixed(2);
            const nuevoTotal = data.totalConDescuento.toFixed(2);

            // Mostrar los elementos de descuento y precio final con descuento
            document.getElementById("descuento-aplicado").style.display = "flex";
            document.getElementById("precio-final-con-descuento").style.display = "flex";

            // Actualizar valores de descuento y precio final con descuento
            document.getElementById("valor-descuento").innerText = valorDescuento;
            document.getElementById("precio-final-valor-con-descuento").innerText = nuevoTotal;

            alert("Descuento aplicado correctamente.");
        } else {
            alert("Código de descuento no válido o expirado.");
        }
    } catch (error) {
        console.error("Error al aplicar el descuento:", error);
        alert("Hubo un problema al aplicar el descuento. Por favor, intente nuevamente.");
    }
}