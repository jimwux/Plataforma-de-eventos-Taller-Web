describe("Formulario - Validación del registro", () => {
    let form;

    beforeEach(() => {
        // Inicializar el formulario
        inicializarFormularioPagoParaTest();
        form = document.getElementById("formulario"); // Captura el formulario
    });

    afterEach(() => {
        // Limpiar el DOM después de cada test
        form.remove();
    });

    it("debe mostrar errores si los campos están vacíos", () => {
        // Espía para evitar el envío real del formulario
        spyOn(form, "submit");

        // Llamar la validación del formulario
        validacionFormularioRegistro();

        // Verificar los mensajes de error
        expect(document.getElementById("errorNombre").innerHTML).toContain("El nombre debe contener solo letras");
        expect(document.getElementById("errorApellido").innerHTML).toContain("El apellido debe contener solo letras");
        expect(document.getElementById("errorCorreo").innerHTML).toContain("El email debe cumplir el formato de un email");
        expect(document.getElementById("errorCorreoRep").innerHTML).toContain("El email debe cumplir el formato de un email y coincidir con el primero");
        expect(document.getElementById("errorTelefono").innerHTML).toContain("El telefono debe cumplir con los 10 digitos");
        expect(document.getElementById("errorDNI").innerHTML).toContain("El DNI debe cumplir el formato de los 8 digitos");
        expect(document.getElementById("errorTYC").innerHTML).toContain("Debe marcar los terminos y condiciones");

        // Verificar que el formulario no se haya enviado
        expect(form.submit).not.toHaveBeenCalled();
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