-- ============================================
-- Script de Datos de Prueba
-- Sistema de Gestión de Historias Clínicas
-- ============================================

-- Usar la base de datos
USE clinica_db;

-- ============================================
-- INSERTAR PACIENTES DE PRUEBA
-- ============================================

INSERT INTO pacientes (PAC_DNI, PAC_Nombre, PAC_Apellido_Paterno, PAC_Apellido_Materno, PAC_Direccion, PAC_Telefono) VALUES
('12345678', 'Juan', 'Pérez', 'García', 'Av. Los Rosales 123, Lima', '987654321'),
('87654321', 'María', 'López', 'Martínez', 'Jr. Las Flores 456, Lima', '912345678'),
('11223344', 'Carlos', 'Rodríguez', 'Sánchez', 'Calle Los Pinos 789, Callao', '998877665'),
('44332211', 'Ana', 'González', 'Torres', 'Av. La Marina 321, San Miguel', '965432187'),
('55667788', 'Luis', 'Fernández', 'Ramírez', 'Jr. Comercio 159, Miraflores', '923456789');

-- ============================================
-- INSERTAR MÉDICOS DE PRUEBA
-- ============================================

INSERT INTO medicos (MED_Cmp, MED_Nombre, MED_Apellidos, ESPE_Nombre) VALUES
('12345', 'María', 'González Rodríguez', 'Cardiología'),
('67890', 'Pedro', 'Martínez Silva', 'Medicina General'),
('11111', 'Carmen', 'Torres Vega', 'Pediatría'),
('22222', 'Roberto', 'Díaz Mendoza', 'Traumatología'),
('33333', 'Laura', 'Vargas Castro', 'Ginecología'),
('44444', 'Jorge', 'Quispe Huamán', 'Neurología');

-- ============================================
-- INSERTAR HISTORIAS CLÍNICAS DE PRUEBA
-- ============================================

INSERT INTO historias_clinicas (PAC_DNI, MED_Cmp, HIST_Fecha_Atencion, HIST_Diagnostico, HIST_Analisis, HIST_Tratamiento) VALUES
(
    '12345678',
    '12345',
    '2025-11-13',
    'Hipertensión arterial grado 1. Paciente presenta presión arterial elevada de manera constante durante los últimos 3 meses. Antecedentes familiares de hipertensión.',
    'Presión arterial: 145/95 mmHg. Frecuencia cardíaca: 82 lpm. Peso: 78 kg. Talla: 1.70 m. IMC: 27.0 (Sobrepeso). Electrocardiograma sin alteraciones significativas. Perfil lipídico: Colesterol total 210 mg/dL.',
    'Enalapril 10mg cada 12 horas por vía oral. Dieta hiposódica (reducir consumo de sal a menos de 5g/día). Realizar actividad física moderada 30 minutos diarios. Control de presión arterial en casa. Control médico en 30 días con resultados de laboratorio.'
),
(
    '87654321',
    '67890',
    '2025-11-12',
    'Infección respiratoria aguda de vías superiores. Rinitis viral. Faringitis leve sin signos de infección bacteriana.',
    'Temperatura: 37.8°C. Frecuencia respiratoria: 18 rpm. Saturación de oxígeno: 98%. Faringe eritematosa sin exudado. Rinorrea clara. No se observan signos de dificultad respiratoria.',
    'Paracetamol 500mg cada 8 horas por 5 días. Ibuprofeno 400mg cada 8 horas si persiste el dolor. Abundantes líquidos (mínimo 2 litros/día). Reposo relativo. Evitar cambios bruscos de temperatura. Regresar si presenta fiebre alta persistente o dificultad respiratoria.'
),
(
    '11223344',
    '22222',
    '2025-11-10',
    'Esguince de tobillo izquierdo grado II. Lesión de ligamentos laterales por mecanismo de inversión durante actividad deportiva.',
    'Dolor intenso en región lateral del tobillo izquierdo. Edema moderado. Equimosis presente. Limitación funcional para la marcha. Radiografía: Sin evidencia de fractura ósea. Bostezo articular positivo.',
    'Inmovilización con férula de yeso tipo bota durante 2 semanas. Reposo absoluto las primeras 72 horas con elevación del miembro. Crioterapia (hielo) 20 minutos cada 3-4 horas. Diclofenaco 50mg cada 8 horas por 7 días. Iniciar fisioterapia después de retirar férula. Control en 15 días.'
),
(
    '44332211',
    '33333',
    '2025-11-08',
    'Embarazo de 12 semanas confirmado. Embarazo único evolutivo. Sin complicaciones aparentes en primer trimestre.',
    'Última regla: 21/08/2025. Test de embarazo positivo. Ecografía transvaginal: Embrión único con actividad cardíaca presente (FCF: 160 lpm). Longitud cráneo-caudal: 58mm compatible con 12 semanas. Saco gestacional bien implantado. Sin sangrado ni dolor. Presión arterial: 110/70 mmHg.',
    'Ácido fólico 1mg diario durante todo el embarazo. Sulfato ferroso 60mg diario. Dieta balanceada rica en proteínas, frutas y verduras. Evitar alcohol, tabaco y medicamentos no autorizados. Control prenatal mensual. Próxima cita en 4 semanas para ecografía morfológica. Realizar exámenes de laboratorio completo.'
),
(
    '55667788',
    '44444',
    '2025-11-05',
    'Cefalea tensional crónica. Episodios recurrentes de dolor de cabeza bilateral de tipo opresivo, asociado a estrés laboral.',
    'Cefalea bifrontal y occipital de intensidad moderada (6/10 en escala EVA). No presenta aura. No náuseas ni vómitos. Duración promedio de 4-6 horas. Frecuencia: 3-4 veces por semana. Examen neurológico: Sin déficit motor ni sensitivo. Reflejos osteotendinosos conservados. Pares craneales normales.',
    'Paracetamol 1g cada 8 horas en caso de dolor agudo. Técnicas de relajación y manejo de estrés. Establecer horarios regulares de sueño (7-8 horas diarias). Ejercicio físico regular. Evitar ayuno prolongado. Reducir consumo de cafeína. Terapia cognitivo-conductual recomendada. Control en 1 mes, considerar profilaxis si persisten los episodios.'
),
(
    '12345678',
    '12345',
    '2025-10-15',
    'Control de hipertensión arterial. Paciente con diagnóstico previo de HTA grado 1 en tratamiento farmacológico.',
    'Presión arterial actual: 130/85 mmHg (mejoría respecto a control anterior). Frecuencia cardíaca: 75 lpm. Paciente refiere buena adherencia al tratamiento. Sin efectos adversos reportados. Peso actual: 76 kg (pérdida de 2 kg desde último control).',
    'Continuar con Enalapril 10mg cada 12 horas. Mantener dieta hiposódica y ejercicio regular. Monitoreo domiciliario de presión arterial 2 veces por semana. Felicitaciones por la mejoría y la pérdida de peso. Próximo control en 2 meses.'
),
(
    '87654321',
    '11111',
    '2025-09-20',
    'Control de niño sano de 5 años. Desarrollo psicomotor adecuado para la edad. Vacunas completas según esquema nacional.',
    'Peso: 19 kg (percentil 50). Talla: 110 cm (percentil 60). Perímetro cefálico: 50 cm. Desarrollo psicomotor: Lenguaje fluido, motricidad fina y gruesa adecuadas. Dentición: 20 piezas dentales presentes. Sin caries. Audición y visión normales. Vacunas completas.',
    'Continuar con alimentación balanceada. Incluir frutas y verduras diariamente. Limitar consumo de azúcares y alimentos procesados. Fomentar actividad física y juego al aire libre. Higiene dental 3 veces al día. Próxima cita de control en 6 meses. Vacuna de refuerzo a los 6 años.'
);

-- ============================================
-- VERIFICAR DATOS INSERTADOS
-- ============================================

-- Contar registros insertados
SELECT 'Pacientes' as Tabla, COUNT(*) as Total FROM pacientes
UNION ALL
SELECT 'Médicos' as Tabla, COUNT(*) as Total FROM medicos
UNION ALL
SELECT 'Historias Clínicas' as Tabla, COUNT(*) as Total FROM historias_clinicas;

-- Mostrar resumen de historias clínicas
SELECT 
    hc.HIST_Id as 'ID Historia',
    p.PAC_Nombre as 'Paciente',
    m.MED_Nombre as 'Médico',
    m.ESPE_Nombre as 'Especialidad',
    hc.HIST_Fecha_Atencion as 'Fecha',
    LEFT(hc.HIST_Diagnostico, 50) as 'Diagnóstico (resumen)'
FROM historias_clinicas hc
INNER JOIN pacientes p ON hc.PAC_DNI = p.PAC_DNI
INNER JOIN medicos m ON hc.MED_Cmp = m.MED_Cmp
ORDER BY hc.HIST_Fecha_Atencion DESC;
