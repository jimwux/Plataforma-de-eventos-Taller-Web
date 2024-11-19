describe('validacionFormularioRegistroConLosCamposVacios', () => {
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
    

});
describe('validacionFormularioRegistroCuandoLosDatosEstanCargadosCorrectamente', () => {
    let mockForm;

    beforeEach(() => {
        const form = document.createElement('form');
        form.id = 'formulario';
        form.style.display = 'none';

        const fields = [
            { id: 'nombre', type: 'text', value: 'Juan' },
            { id: 'apellido', type: 'text', value: 'Perez' },
            { id: 'correo', type: 'text', value: 'juan96@gmail.com' },
            { id: 'correoRep', type: 'text', value: 'juan96@gmail.com' },
            { id: 'telefono', type: 'text', value: '1122334455' },
            { id: 'dni', type: 'text', value: '12345678' },
            { id: 'aceptacionTerminos', type: 'checkbox', value: true },
        ];

        fields.forEach(field => {
            const input = document.createElement('input');
            input.id = field.id;
            input.type = field.type;
            input.value = field.value;
            if (field.type === 'checkbox') {
                input.checked = field.value === true; // Asegurar que los checkboxes estén marcados
            }
            form.appendChild(input);
        });

        document.body.appendChild(form);
        mockForm = form;
    });

    it('envía el formulario si todos los datos son correctos', () => {
        // Espiar el método submit
        spyOn(mockForm, 'submit');

        // Ejecutar la función que valida y envía el formulario
        validacionFormularioRegistro();

        // Comprobar que el método submit fue llamado
        expect(mockForm.submit).toHaveBeenCalled();
    });
});
 /*    


describe('Eventos de botones .borrar', () => {
    beforeEach(() => {
        document.body.innerHTML = `
            <tr>
                <td class="cantidad-entrada">2</td>
                <td class="subtotal-entrada">200.00</td>
                <input class="precioEntrada" value="100.00">
                <input class="cantidad" value="2">
                <button class="borrar">Borrar</button>
            </tr>
            <span id="precio-final-valor-original">400.00</span>
        `;
    });

    it('reduce la cantidad y actualiza el subtotal', () => {
        const boton = document.querySelector('.borrar');
        const fila = boton.closest('tr');
        const cantidadInput = fila.querySelector('.cantidad-entrada');
        const subtotalElement = fila.querySelector('.subtotal-entrada');
        const precioUnitario = parseFloat(fila.querySelector('.precioEntrada').value);

        boton.click();

        expect(parseInt(cantidadInput.textContent)).toBe(1);
        expect(parseFloat(subtotalElement.textContent)).toBe(100.00);
    });

    it('elimina la fila si la cantidad es 1', () => {
        const boton = document.querySelector('.borrar');
        const fila = boton.closest('tr');
        fila.querySelector('.cantidad-entrada').textContent = '1';

        boton.click();

        expect(document.contains(fila)).toBe(false);
    });

    it('actualiza el precio final al eliminar una entrada', () => {
        actualizarPrecioFinal();
        const precioFinal = document.getElementById('precio-final-valor-original');
        expect(parseFloat(precioFinal.textContent)).toBe(300.00); // Asumiendo una entrada eliminada
    });
});
*/