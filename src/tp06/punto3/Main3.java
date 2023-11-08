package tp06.punto3;


public class Main3 {
    public static void main(String[] args) {
        int cantEstudiantes = 5, limiteSala = 2;
        ControlSalaEstudio sala = new ControlSalaEstudio(limiteSala);
        Estudiante[] estudiantes = new Estudiante[cantEstudiantes];
        Thread[] hilosEstudiantes = new Thread[cantEstudiantes];
        for (int j = 0; j < cantEstudiantes; j++) {
            estudiantes[j] = new Estudiante(sala);
            hilosEstudiantes[j] = new Thread(estudiantes[j], "Estudiante " + j);
            hilosEstudiantes[j].start();
        }
    }
}
