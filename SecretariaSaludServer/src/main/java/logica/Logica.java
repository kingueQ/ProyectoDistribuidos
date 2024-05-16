package logica;

import bd.Conexion;
import cliente.SocketCliente;
import servicios.*;
import dominio.*;
import interfaces.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class Logica implements ILogica {

    private final IExpedienteService expedienteService;
    private final IPacienteService pacienteService;
    private final IMedicoService medicoService;
    private final IAuthService authService;
    private final IConexion conexion;
    private static final Logger LOGGER = Logger.getLogger(Logica.class.getName());
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Constructor: inicializa los servicios con la conexión a la base de datos
    public Logica() {
        conexion = new Conexion();
        Connection conn = conexion.getConexion();
        expedienteService = new ExpedienteService(conn);
        pacienteService = new PacienteService(conn);
        medicoService = new MedicoService(conn);
        authService = new AuthService(pacienteService, medicoService);
    }

    // Inserta un nuevo expediente para un paciente
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

    // Inserta un nuevo paciente
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

    // Inserta un nuevo médico
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

    // Elimina un expediente por ID
    @Override
    public boolean eliminarExpediente(String idExpediente) {
        try {
            return expedienteService.eliminarExpediente(Integer.parseInt(idExpediente));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar expediente: ID inválido", e);
            return false;
        }
    }

    // Elimina un paciente por CURP
    @Override
    public boolean eliminarPaciente(String curp) {
        return pacienteService.eliminarPaciente(curp);
    }

    // Elimina un médico por cédula
    @Override
    public boolean eliminarMedico(String cedula) {
        return medicoService.eliminarMedico(cedula);
    }

    // Actualiza un expediente existente
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

    // Actualiza la información de un paciente existente
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

    // Actualiza la información de un médico existente
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

    // Consulta todos los pacientes y retorna una cadena con su información
    @Override
    public String consultarPacientes() {
        List<Paciente> pacientes = pacienteService.obtenerPacientes();
        StringBuilder result = new StringBuilder();
        for (Paciente paciente : pacientes) {
            result.append(paciente.getId()).append("!").append(paciente.getCurp()).append("!").append(paciente.getNombre()).append("!").append(paciente.getFechaNacimiento()).append("!").append(paciente.getTutor()).append("!?");
        }
        return result.toString();
    }

    // Consulta todos los expedientes y retorna una cadena con su información
    @Override
    public String consultarExpedientes() {
        List<Expediente> expedientes = expedienteService.obtenerExpedientes();
        StringBuilder result = new StringBuilder();
        for (Expediente expediente : expedientes) {
            result.append(expediente.getId()).append("!").append(expediente.getImagenes()).append("!").append(expediente.getDocumentos()).append("!").append(expediente.getTextos()).append("!").append(expediente.getMedicos()).append("!").append(expediente.getAcceso()).append("!?");
        }
        return result.toString();
    }

    // Consulta todos los médicos y retorna una cadena con su información
    @Override
    public String consultarMedicos() {
        List<Medico> medicos = medicoService.obtenerMedicos();
        StringBuilder result = new StringBuilder();
        for (Medico medico : medicos) {
            result.append(medico.getId()).append("!").append(medico.getNombre()).append("!").append(medico.getCedula()).append("!").append(medico.getEspecialidad()).append("!").append(medico.getPass()).append("!?");
        }
        return result.toString();
    }

    // Consulta la información de un paciente por CURP
    @Override
    public String consultarPaciente(String curp) {
        Optional<Paciente> pacienteOpt = Optional.ofNullable(pacienteService.consultarPaciente(curp));
        return pacienteOpt.map(paciente -> paciente.getId() + "!" + paciente.getCurp() + "!" + paciente.getNombre() + "!" + paciente.getFechaNacimiento() + "!" + paciente.getTutor() + "!" + paciente.getPass())
                .orElse("Paciente no encontrado");
    }

    // Consulta la información de un médico por cédula
    @Override
    public String consultarMedico(String cedula) {
        Optional<Medico> medicoOpt = Optional.ofNullable(medicoService.consultarMedico(cedula));
        return medicoOpt.map(medico -> medico.getId() + "!" + medico.getNombre() + "!" + medico.getCedula() + "!" + medico.getEspecialidad() + "!" + medico.getPass())
                .orElse("Médico no encontrado");
    }

    // Consulta el expediente de un paciente por su CURP
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

    // Autentica un paciente por CURP y contraseña
    @Override
    public boolean autenticarPaciente(String curp, String pass) {
        return authService.autenticarPaciente(curp, pass);
    }

    // Autentica un médico por cédula y contraseña
    @Override
    public boolean autenticarMedico(String cedula, String pass) {
        return authService.autenticarMedico(cedula, pass);
    }

    // Autentica una credencial (paciente o médico) y genera un token JWT
    @Override
    public boolean autenticar(String credencial, String pass) {
        if (credencial.length() == 18) { // Si la credencial tiene 18 caracteres, es un CURP (paciente)
            if (autenticarPaciente(credencial, pass)) {
                String token = generarToken(credencial);
                System.out.println("Token generado para pacient " + credencial + ": " + token);
                return true;
            }
        } else { // De lo contrario, es una cédula (médico)
            if (autenticarMedico(credencial, pass)) {
                String token = generarToken(credencial);
                System.out.println("Token generado para médico " + credencial + ": " + token);
                return true;
            }
        }
        return false;
    }

    // Genera un token JWT para un sujeto dado con claims personalizados
    public static String generarToken(String sujeto) {
        return Jwts.builder()
                .setSubject(sujeto)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expira en 24 horas
                .claim("role", "user") // Agregar claims personalizados
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Asegurarse de usar un algoritmo seguro
                .compact();
    }

    // Validar el token JWT
    public static Claims validarToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}