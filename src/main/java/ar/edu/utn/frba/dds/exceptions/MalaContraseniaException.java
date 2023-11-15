package ar.edu.utn.frba.dds.exceptions;

public class MalaContraseniaException extends RuntimeException {
  public MalaContraseniaException(String mensaje) {
    super(mensaje);
  }
}
