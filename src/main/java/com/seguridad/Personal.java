package com.seguridad;

public class Personal {
    private int idPersonal;
    private String nombres;
    private String apellidos;
    private String dni;
    private String telefono;
    private String cargo;
    private String turno;
    private String estado;

    public Personal() {}

    public Personal(int idPersonal, String nombres, String apellidos, String dni,
                    String telefono, String cargo, String turno, String estado) {
        this.idPersonal = idPersonal;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.telefono = telefono;
        this.cargo = cargo;
        this.turno = turno;
        this.estado = estado;
    }

    public int getIdPersonal() { return idPersonal; }
    public void setIdPersonal(int idPersonal) { this.idPersonal = idPersonal; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
