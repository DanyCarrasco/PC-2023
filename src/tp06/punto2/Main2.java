package tp06.punto2;

public class Main2 {
    public static void main(String[] args) {
        int cantEstudiantes = 5, limiteSala = 2, i = 0;
        ControlSalaEstudio sala = new ControlSalaEstudio(limiteSala);
        Estudiante[] estudiantes = new Estudiante[cantEstudiantes];
        //Thread[] hilosEstudiantes = new Thread[cantEstudiantes];
        while(i < cantEstudiantes){
            estudiantes[i] = new Estudiante(sala);
            Thread hilo = new Thread(estudiantes[i],"Estudiante "+i);
            hilo.start();
            i++;
        }
    }
}
