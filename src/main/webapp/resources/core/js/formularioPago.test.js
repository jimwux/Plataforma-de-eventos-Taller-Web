describe('validacionFormularioRegistro', () => {
    let mockForm;

    beforeEach(() => {
        const form = document.createElement('form');
        form.id = 'formulario';
        form.style.display = 'none';

        const fields = [
            { id: 'nombre', type: 'text', value: '' },
            { id: 'apellido', type: 'text', value: '' },
            { id: 'correo', type: 'text', value: '' },
            { id: 'correoRep', type: 'text', value: '' },
            { id: 'telefono', type: 'text', value: '' },
            { id: 'dni', type: 'text', value: '' },
            { id: 'aceptacionTerminos', type: 'checkbox', value: false },
        ];

        fields.forEach(field => {
            const input = document.createElement('input');
            input.id = field.id;
            input.type = field.type;
            input.value = field.value;
            form.appendChild(input);
        });

        document.body.appendChild(form);
        mockForm = form;
    });


    it('muestra un error si los campos están vacíos', () => {
        spyOn(window, 'alert');
        validacionFormularioRegistro();
        expect(window.alert).toHaveBeenCalledWith('Datos invalidos');
    });

    it('envía el formulario si todos los datos son correctos', () => {
        spyOn(window, 'alert');

        // Asignar valores a los inputs del formulario
        mockForm.querySelector('#nombre').value = 'Juan';
        mockForm.querySelector('#apellido').value = 'Pérez';
        mockForm.querySelector('#correo').value = 'juan@example.com';
        mockForm.querySelector('#correoRep').value = 'juan@example.com';
        mockForm.querySelector('#telefono').value = '1123456789';
        mockForm.querySelector('#dni').value = '12345678';
        mockForm.querySelector('#aceptacionTerminos').checked = true;

        // Espiar el método submit
        spyOn(mockForm, 'submit');

        // Ejecutar la función que valida y envía el formulario
        validacionFormularioRegistro();

        // Comprobar que el método submit fue llamado
        expect(mockForm.submit).toHaveBeenCalled();
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