function validatePrescriptionForm() {
    let isValid = true;
    clearErrors();

    const prescriptionDate = document.getElementById('prescriptionDate');
    const patientName = document.getElementById('patientName');
    const patientAge = document.getElementById('patientAge');
    const patientGender = document.getElementById('patientGender');

    if (!prescriptionDate.value) {
        showError('prescriptionDate', 'Prescription date is required');
        isValid = false;
    }

    if (!patientName.value || patientName.value.trim().length < 2) {
        showError('patientName', 'Patient name must be at least 2 characters');
        isValid = false;
    } else if (patientName.value.trim().length > 100) {
        showError('patientName', 'Patient name must not exceed 100 characters');
        isValid = false;
    }

    if (!patientAge.value) {
        showError('patientAge', 'Patient age is required');
        isValid = false;
    } else {
        const age = parseInt(patientAge.value);
        if (age < 0 || age > 120) {
            showError('patientAge', 'Age must be between 0 and 120');
            isValid = false;
        }
    }

    if (!patientGender.value) {
        showError('patientGender', 'Patient gender is required');
        isValid = false;
    }

    const diagnosis = document.getElementById('diagnosis');
    if (diagnosis.value && diagnosis.value.length > 2000) {
        showError('diagnosis', 'Diagnosis must not exceed 2000 characters');
        isValid = false;
    }

    const medicines = document.getElementById('medicines');
    if (medicines.value && medicines.value.length > 2000) {
        showError('medicines', 'Medicines must not exceed 2000 characters');
        isValid = false;
    }

    return isValid;
}

function showError(fieldId, message) {
    const field = document.getElementById(fieldId);
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error';
    errorDiv.textContent = message;
    field.parentElement.appendChild(errorDiv);
    field.style.borderColor = '#e74c3c';
}

function clearErrors() {
    const errors = document.querySelectorAll('.error');
    errors.forEach(error => error.remove());

    const inputs = document.querySelectorAll('.form-control');
    inputs.forEach(input => input.style.borderColor = '#ddd');
}

function confirmDelete(prescriptionId) {
    if (confirm('Are you sure you want to delete this prescription? This action cannot be undone.')) {
        document.getElementById('deleteForm-' + prescriptionId).submit();
    }
    return false;
}

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('prescriptionForm');
    if (form) {
        form.addEventListener('submit', function(event) {
            if (!validatePrescriptionForm()) {
                event.preventDefault();
            }
        });

        const inputs = form.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            input.addEventListener('input', function() {
                const errorDiv = this.parentElement.querySelector('.error');
                if (errorDiv) {
                    errorDiv.remove();
                    this.style.borderColor = '#ddd';
                }
            });
        });
    }
});
