describe("Formulario - Validación del registro", () => {
    let form, nombre, apellido, correo, correoRep, telefono, dni, aceptacionTerminos, botonEnviar;


    afterEach(() => {
        document.body.innerHTML = ""; // Limpiar el DOM después de cada test
    });

    it("debe mostrar errores si los campos están vacíos", () => {
        spyOn(form, "submit"); // Espía para evitar el envío real del formulario

        validacionFormularioRegistro();

        expect(document.getElementById("errorNombre").innerHTML).toContain("El nombre debe contener solo letras");
        expect(document.getElementById("errorApellido").innerHTML).toContain("El apellido debe contener solo letras");
        expect(document.getElementById("errorCorreo").innerHTML).toContain("El email debe cumplir el formato de un email");
        expect(document.getElementById("errorCorreoRep").innerHTML).toContain("El email debe cumplir el formato de un email y coincidir con el primero");
        expect(document.getElementById("errorTelefono").innerHTML).toContain("El telefono debe cumplir con los 10 digitos");
        expect(document.getElementById("errorDNI").innerHTML).toContain("El DNI debe cumplir el formato de los 8 digitos");
        expect(document.getElementById("errorTYC").innerHTML).toContain("Debe marcar los terminos y condiciones");

        expect(form.submit).not.toHaveBeenCalled();
    });

    it("debe enviar el formulario si los campos son válidos", () => {
        spyOn(form, "submit");

        nombre.value = "Juan";
        apellido.value = "Pérez";
        correo.value = "juan.perez@example.com";
        correoRep.value = "juan.perez@example.com";
        telefono.value = "1123456789";
        dni.value = "12345678";
        aceptacionTerminos.checked = true;

        validacionFormularioRegistro();

        expect(document.getElementById("errorNombre").innerHTML).toBe("");
        expect(document.getElementById("errorApellido").innerHTML).toBe("");
        expect(document.getElementById("errorCorreo").innerHTML).toBe("");
        expect(document.getElementById("errorCorreoRep").innerHTML).toBe("");
        expect(document.getElementById("errorTelefono").innerHTML).toBe("");
        expect(document.getElementById("errorDNI").innerHTML).toBe("");
        expect(document.getElementById("errorTYC").innerHTML).toBe("");

        expect(form.submit).toHaveBeenCalled();
    });
});
describe('Eventos de botones .borrar', () => {
    let mockRow, mockBoton, mockCantidadInput, mockSubtotalElement, mockPrecioUnitario, mockPrecioFinal;

    beforeEach(() => {
        // Configuración del DOM
        const container = document.createElement('div');
        container.style.display = 'none';

        const row = document.createElement('tr');

        const cantidadEntrada = document.createElement('td');
        cantidadEntrada.className = 'cantidad-entrada';
        cantidadEntrada.textContent = '2';

        const subtotalEntrada = document.createElement('td');
        subtotalEntrada.className = 'subtotal-entrada';
        subtotalEntrada.textContent = '200.00';

        const precioEntrada = document.createElement('input');
        precioEntrada.className = 'precioEntrada';
        precioEntrada.type = 'hidden';
        precioEntrada.value = '100.00';

        const botonBorrar = document.createElement('button');
        botonBorrar.className = 'borrar';
        botonBorrar.textContent = 'Borrar';

        row.appendChild(cantidadEntrada);
        row.appendChild(subtotalEntrada);
        row.appendChild(precioEntrada);
        row.appendChild(botonBorrar);

        container.appendChild(row);

        const precioFinal = document.createElement('span');
        precioFinal.id = 'precio-final-valor-original';
        precioFinal.textContent = '200.00';
        container.appendChild(precioFinal);

        document.body.appendChild(container);

        mockRow = row;
        mockBoton = botonBorrar;
        mockCantidadInput = cantidadEntrada;
        mockSubtotalElement = subtotalEntrada;
        mockPrecioUnitario = parseFloat(precioEntrada.value);
        mockPrecioFinal = precioFinal;

        // Vincular lógica al botón
        mockBoton.addEventListener('click', () => {
            const cantidadActual = parseInt(mockCantidadInput.textContent);
            if (cantidadActual === 1) {
                mockRow.remove();
            } else {
                mockCantidadInput.textContent = cantidadActual - 1;
                mockSubtotalElement.textContent = ((cantidadActual - 1) * mockPrecioUnitario).toFixed(2);
            }
            actualizarPrecioFinal();
        });

    });

    it('reduce la cantidad y actualiza el subtotal', () => {
        expect(parseInt(mockCantidadInput.textContent)).toBe(2);
        expect(parseFloat(mockSubtotalElement.textContent)).toBe(200.00);
        mockBoton.click();
        expect(parseInt(mockCantidadInput.textContent)).toBe(1);
        expect(parseFloat(mockSubtotalElement.textContent)).toBe(100.00);
    });

    it('elimina la fila si la cantidad es 1', () => {
        mockCantidadInput.textContent = '1';
        mockBoton.click();
        expect(document.body.contains(mockRow)).toBe(false);
    });

    it('no elimina la fila si es la ultima entrada', () => {
        mockCantidadInput.textContent = '2';

        expect(parseInt(mockCantidadInput.textContent)).toBe(2);
        expect(parseFloat(mockSubtotalElement.textContent)).toBe(200.00);
        
        mockBoton.click();

        expect(parseInt(mockCantidadInput.textContent)).toBe(1);
        expect(parseFloat(mockSubtotalElement.textContent)).toBe(100.00);
        
        mockBoton.click();

        expect(parseInt(mockCantidadInput.textContent)).toBe(1);
        expect(parseFloat(mockSubtotalElement.textContent)).toBe(100.00);
        
    });

});