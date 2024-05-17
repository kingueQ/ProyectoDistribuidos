package logica;

import bd.Conexion;
import cliente.SocketCliente;
import servicios.*;
import dominio.*;
import interfaces.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class Logica implements ILogica {

    private final IExpedienteService expedienteService;
    private final IPacienteService pacienteService;
    private final IMedicoService medicoService;
    private final IAuthService authService;
    private final IConexion conexion;
    private static final Logger LOGGER = Logger.getLogger(Logica.class.getName());
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Constructor de la clase Logica.
     * Inicializa los servicios y establece la conexión a la base de datos.
     */
    public Logica() {
        conexion = new Conexion();
        Connection conn = conexion.getConexion();
        expedienteService = new ExpedienteService(conn);
        pacienteService = new PacienteService(conn);
        medicoService = new MedicoService(conn);
        authService = new AuthService(pacienteService, medicoService);
    }

    /**
     * Inserta un nuevo expediente para un paciente.
     * 
     * @param idPaciente El ID del paciente.
     * @return true si el expediente se inserta correctamente, false en caso contrario.
     */
    @Override
    public boolean insertarExpediente(String idPaciente) {
        try {
            int pacienteId = Integer.parseInt(idPaciente);
            Expediente expediente = new Expediente("", "", "", pacienteId);
            expediente.setMedicos("");
            expediente.setAcceso(false);
            return expedienteService.agregarExpediente(expediente);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar expediente: ID de paciente inválido", e);
            return false;
        }
    }

    /**
     * Inserta un nuevo paciente en el sistema.
     * 
     * @param nombre El nombre del paciente.
     * @param curp El CURP del paciente.
     * @param fechaNac La fecha de nacimiento del paciente en formato dd/MM/yyyy.
     * @param tutor El nombre del tutor del paciente.
     * @param pass La contraseña del paciente.
     * @return true si el paciente se inserta correctamente, false en caso contrario.
     */
    @Override
    public boolean insertarPaciente(String nombre, String curp, String fechaNac, String tutor, String pass) {
        try {
            LocalDate fechaNacimiento = LocalDate.parse(fechaNac, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Paciente paciente = new Paciente(nombre, curp, Date.valueOf(fechaNacimiento), tutor, pass);
            return pacienteService.agregarPaciente(paciente);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al insertar paciente", e);
            return false;
        }
    }

    /**
     * Inserta un nuevo médico en el sistema.
     * 
     * @param cedula La cédula del médico.
     * @param nombre El nombre del médico.
     * @param pass La contraseña del médico.
     * @param especialidad La especialidad del médico.
     * @return true si el médico se inserta correctamente, false en caso contrario.
     */
    @Override
    public boolean insertarMedico(String cedula, String nombre, String pass, String especialidad) {
        try {
            Medico medico = new Medico(cedula, nombre, pass, especialidad);
            return medicoService.agregarMedico(medico);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al insertar médico", e);
            return false;
        }
    }

    /**
     * Elimina un expediente del sistema.
     * 
     * @param idExpediente El ID del expediente a eliminar.
     * @return true si el expediente se elimina correctamente, false en caso contrario.
     */
    @Override
    public boolean eliminarExpediente(String idExpediente) {
        try {
            return expedienteService.eliminarExpediente(Integer.parseInt(idExpediente));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar expediente: ID inválido", e);
            return false;
        }
    }

    /**
     * Elimina un paciente del sistema.
     * 
     * @param curp El CURP del paciente a eliminar.
     * @return true si el paciente se elimina correctamente, false en caso contrario.
     */
    @Override
    public boolean eliminarPaciente(String curp) {
        return pacienteService.eliminarPaciente(curp);
    }

    /**
     * Elimina un médico del sistema.
     * 
     * @param cedula La cédula del médico a eliminar.
     * @return true si el médico se elimina correctamente, false en caso contrario.
     */
    @Override
    public boolean eliminarMedico(String cedula) {
        return medicoService.eliminarMedico(cedula);
    }

    /**
     * Actualiza la información de un expediente.
     * 
     * @param idExpediente El ID del expediente a actualizar.
     * @param idPaciente El ID del paciente asociado al expediente.
     * @param medicosAcceso Lista de médicos con acceso al expediente.
     * @param imagenes Imágenes asociadas al expediente.
     * @param documentos Documentos asociados al expediente.
     * @param textos Textos asociados al expediente.
     * @return true si el expediente se actualiza correctamente, false en caso contrario.
     */
    @Override
    public boolean actualizarExpediente(String idExpediente, String idPaciente, String medicosAcceso, String imagenes, String documentos, String textos) {
        try {
            int expedienteId = Integer.parseInt(idExpediente);
            int pacienteId = Integer.parseInt(idPaciente);
            Expediente expediente = new Expediente(imagenes, textos, documentos, pacienteId);
            expediente.setId(expedienteId);
            expediente.setMedicos(medicosAcceso);
            expediente.setAcceso(false);
            return expedienteService.actualizarExpediente(expediente);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar expediente: ID inválido", e);
            return false;
        }
    }

    /**
     * Actualiza la información de un paciente.
     * 
     * @param nombre El nombre del paciente.
     * @param curp El CURP del paciente.
     * @param fechaNac La fecha de nacimiento del paciente en formato dd/MM/yyyy.
     * @param tutor El nombre del tutor del paciente.
     * @param pass La contraseña del paciente.
     * @return true si el paciente se actualiza correctamente, false en caso contrario.
     */
    @Override
    public boolean actualizarPaciente(String nombre, String curp, String fechaNac, String tutor, String pass) {
        try {
            LocalDate fechaNacimiento = LocalDate.parse(fechaNac, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Paciente paciente = new Paciente(nombre, curp, Date.valueOf(fechaNacimiento), tutor, pass);
            return pacienteService.actualizarPaciente(paciente);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar paciente", e);
            return false;
        }
    }

    /**
     * Actualiza la información de un médico.
     * 
     * @param cedula La cédula del médico.
     * @param nombre El nombre del médico.
     * @param pass La contraseña del médico.
     * @param especialidad La especialidad del médico.
     * @return true si el médico se actualiza correctamente, false en caso contrario.
     */
    @Override
    public boolean actualizarMedico(String cedula, String nombre, String pass, String especialidad) {
        try {
            Medico medico = new Medico(cedula, nombre, pass, especialidad);
            return medicoService.actualizarMedico(medico);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar médico", e);
            return false;
        }
    }

    /**
     * Consulta todos los pacientes registrados en el sistema.
     * 
     * @return Una cadena con la información de todos los pacientes.
     */
    @Override
    public String consultarPacientes() {
        List<Paciente> pacientes = pacienteService.obtenerPacientes();
        StringBuilder result = new StringBuilder();
        for (Paciente paciente : pacientes) {
            result.append(paciente.getId()).append("!").append(paciente.getCurp()).append("!").append(paciente.getNombre()).append("!").append(paciente.getFechaNacimiento()).append("!").append(paciente.getTutor()).append("!?");
        }
        return result.toString();
    }

    /**
     * Consulta todos los expedientes registrados en el sistema.
     * 
     * @return Una cadena con la información de todos los expedientes.
     */
    @Override
    public String consultarExpedientes() {
        List<Expediente> expedientes = expedienteService.obtenerExpedientes();
        StringBuilder result = new StringBuilder();
        for (Expediente expediente : expedientes) {
            result.append(expediente.getId()).append("!").append(expediente.getImagenes()).append("!").append(expediente.getDocumentos()).append("!").append(expediente.getTextos()).append("!").append(expediente.getMedicos()).append("!").append(expediente.getAcceso()).append("!?");
        }
        return result.toString();
    }

    /**
     * Consulta todos los médicos registrados en el sistema.
     * 
     * @return Una cadena con la información de todos los médicos.
     */
    @Override
    public String consultarMedicos() {
        List<Medico> medicos = medicoService.obtenerMedicos();
        StringBuilder result = new StringBuilder();
        for (Medico medico : medicos) {
            result.append(medico.getId()).append("!").append(medico.getNombre()).append("!").append(medico.getCedula()).append("!").append(medico.getEspecialidad()).append("!").append(medico.getPass()).append("!?");
        }
        return result.toString();
    }

    /**
     * Consulta la información de un paciente específico.
     * 
     * @param curp El CURP del paciente a consultar.
     * @return Una cadena con la información del paciente, o un mensaje indicando que no se encontró.
     */
    @Override
    public String consultarPaciente(String curp) {
        Optional<Paciente> pacienteOpt = Optional.ofNullable(pacienteService.consultarPaciente(curp));
        return pacienteOpt.map(paciente -> paciente.getId() + "!" + paciente.getCurp() + "!" + paciente.getNombre() + "!" + paciente.getFechaNacimiento() + "!" + paciente.getTutor() + "!" + paciente.getPass())
                .orElse("Paciente no encontrado");
    }

    /**
     * Consulta la información de un médico específico.
     * 
     * @param cedula La cédula del médico a consultar.
     * @return Una cadena con la información del médico, o un mensaje indicando que no se encontró.
     */
    @Override
    public String consultarMedico(String cedula) {
        Optional<Medico> medicoOpt = Optional.ofNullable(medicoService.consultarMedico(cedula));
        return medicoOpt.map(medico -> medico.getId() + "!" + medico.getNombre() + "!" + medico.getCedula() + "!" + medico.getEspecialidad() + "!" + medico.getPass())
                .orElse("Médico no encontrado");
    }

    /**
     * Consulta el expediente asociado a un paciente específico.
     * 
     * @param curp El CURP del paciente a consultar.
     * @return Una cadena con la información del expediente, o un mensaje indicando que no se encontró.
     */
    @Override
    public String consultarExpediente(String curp) {
        Paciente paciente = pacienteService.consultarPaciente(curp);
        if (paciente != null) {
            Expediente expediente = expedienteService.consultarExpediente(paciente.getId());
            return expediente != null ? expediente.getId() + "!" + expediente.getImagenes() + "!" + expediente.getDocumentos() + "!" + expediente.getTextos() + "!" + expediente.getMedicos() + "!" + expediente.getAcceso() : "Expediente no encontrado";
        } else {
            return "Paciente no encontrado";
        }
    }

    /**
     * Autentica un paciente en el sistema.
     * 
     * @param curp El CURP del paciente.
     * @param pass La contraseña del paciente.
     * @return true si la autenticación es exitosa, false en caso contrario.
     */
    @Override
    public boolean autenticarPaciente(String curp, String pass) {
        return authService.autenticarPaciente(curp, pass);
    }

    /**
     * Autentica un médico en el sistema.
     * 
     * @param cedula La cédula del médico.
     * @param pass La contraseña del médico.
     * @return true si la autenticación es exitosa, false en caso contrario.
     */
    @Override
    public boolean autenticarMedico(String cedula, String pass) {
        return authService.autenticarMedico(cedula, pass);
    }

    /**
     * Autentica un usuario en el sistema, generando un token JWT si la autenticación es exitosa.
     * 
     * @param credencial El CURP del paciente o la cédula del médico.
     * @param pass La contraseña del usuario.
     * @return true si la autenticación es exitosa, false en caso contrario.
     */
    public boolean autenticar(String credencial, String pass) {
        if (credencial.length() == 18) {
            boolean autenticacionPaciente = this.autenticarPaciente(credencial, pass);
            if (autenticacionPaciente) {
                String token = generarToken(credencial);
                System.out.println("Token generado para paciente " + credencial + ": " + token);
                return true;
            }
        } else {
            boolean autenticacionMedico = this.autenticarMedico(credencial, pass);
            if (autenticacionMedico) {
                String token = generarToken(credencial);
                System.out.println("Token generado para médico " + credencial + ": " + token);
                return true;
            }
        }
        return false;
    }

    /**
     * Genera un token JWT para un sujeto dado con claims personalizados.
     * 
     * @param sujeto El sujeto (CURP del paciente o cédula del médico).
     * @return El token JWT generado.
     */
    public static String generarToken(String sujeto) {
        return Jwts.builder()
                .setSubject(sujeto)
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 86400000)) // Expira en 24 horas
                .claim("role", "user") // Agregar claims personalizados
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Asegurarse de usar un algoritmo seguro
                .compact();
    }

    /**
     * Valida un token JWT.
     * 
     * @param token El token JWT a validar.
     * @return Los claims del token si es válido.
     */
    public static Claims validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Envía un mensaje a través de un socket cliente.
     * 
     * @param nombre El nombre del destinatario.
     * @param mensaje El mensaje a enviar.
     * @return true si el mensaje se envía correctamente, false en caso contrario.
     */
    @Override
    public boolean enviar(String nombre, String mensaje) {
        SocketCliente cliente = new SocketCliente("localhost", 1234);
        String result = cliente.enviarMensaje("enviar!" + nombre + "!" + mensaje);
        return "true".equalsIgnoreCase(result);
    }

    /**
     * Recibe un mensaje a través de un socket cliente.
     * 
     * @param nombre El nombre del destinatario.
     * @return El mensaje recibido.
     */
    @Override
    public String recibir(String nombre) {
        SocketCliente cliente = new SocketCliente("localhost", 1234);
        return cliente.enviarMensaje("recibir!" + nombre);
    }

    /**
     * Cambia el estado de acceso de un expediente asociado a un paciente.
     * 
     * @param curp El CURP del paciente.
     * @return true si el estado de acceso se cambia correctamente, false en caso contrario.
     */
    @Override
    public boolean cambiarAcceso(String curp) {
        Paciente paciente = pacienteService.consultarPaciente(curp);
        if (paciente != null) {
            Expediente expediente = expedienteService.consultarExpediente(paciente.getId());
            if (expediente != null) {
                boolean nuevoAcceso = !expediente.getAcceso();
                return expedienteService.cambiarAcceso(paciente.getId(), nuevoAcceso);
            }
        }
        return false;
    }

    /**
     * Modifica la lista de médicos con acceso a un expediente.
     * 
     * @param curp El CURP del paciente.
     * @param medicos La nueva lista de médicos con acceso.
     * @return true si los médicos se modifican correctamente, false en caso contrario.
     */
    @Override
    public boolean modificarMedicos(String curp, String medicos) {
        Paciente paciente = pacienteService.consultarPaciente(curp);
        if (paciente != null) {
            Expediente expediente = expedienteService.consultarExpediente(paciente.getId());
            if (expediente != null) {
                return expedienteService.modificarMedicos(paciente.getId(), medicos);
            }
        }
        return false;
    }
}

