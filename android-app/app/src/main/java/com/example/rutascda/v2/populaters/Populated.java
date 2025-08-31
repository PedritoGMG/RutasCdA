package com.example.rutascda.v2.populaters;

import com.example.rutascda.Ubicacion;
import com.example.rutascda.v2.entities.Actividad;
import com.example.rutascda.v2.entities.Enigma;
import com.example.rutascda.v2.entities.Ruta;

import java.util.ArrayList;
import java.util.List;

public class Populated {

    private static ArrayList<Ruta> rutasPopulated = null;

    public static ArrayList<Ruta> instance() {
        if (rutasPopulated == null) {
            rutasPopulated=getRutas();
        }
        return rutasPopulated;
    }
    private static ArrayList<Ruta> getRutas(){
        ArrayList<Ruta> rutas = new ArrayList<>();
        for (int i = 1; i <= 4 ; i++) {
            ArrayList<Actividad> actividades = new ArrayList<>();
            for (int j = 1; j <= 4; j++) {
                actividades.add(new Actividad((j+(4*i))-4, i, getNombreActividades(j-1), getUbicacion(j-1), getEnigma(i-1, j-1)));
            }
            rutas.add(new Ruta(i, getNombreRuta(i-1), actividades));
        }
        return rutas;
    }
    private static Ubicacion getUbicacion(int index){
        List<Ubicacion> ubicaciones = List.of(
                new Ubicacion(38.331531, -6.976234),
                new Ubicacion(38.331531, -6.976234),
                new Ubicacion(38.331531, -6.976234),
                new Ubicacion(38.331531, -6.976234)
        );
        return ubicaciones.get(index);
    }
    private static String getNombreRuta(int index){
        List<String> nombreRutas = List.of("Fitness", "Cuentos", "Acertijos", "Matematicas");
        return nombreRutas.get(index);
    }
    private static String getNombreActividades(int index){
        List<String> nombreActividades = List.of("La Puerta", "El Paisaje", "La Casopla", "El Río");
        return nombreActividades.get(index);
    }
    private static Enigma getEnigma(int x, int y){
        Enigma[][] enigmas = {
                // Fitness Enigmas
                {
                        new Enigma(
                                "La importancia de calentar",
                                "Es fundamental calentar antes de hacer ejercicio. ¿Qué es lo que se logra principalmente con un buen calentamiento? \n\nPosibles respuestas: 1. Mejorar el rendimiento, 2. Prevenir lesiones, 3. Aumentar la masa muscular, 4. Reducir el cansancio.",
                                "2"
                        ),
                        new Enigma(
                                "La frecuencia cardíaca",
                                "Durante el ejercicio, la frecuencia cardíaca se eleva. ¿Por qué es importante controlar la frecuencia cardíaca mientras se entrena? \n\nPosibles respuestas: 1. Para no cansarse tanto, 2. Para no sobrecargar el corazón, 3. Para asegurarse de estar en la zona óptima de entrenamiento, 4. Para mantener la energía constante.",
                                "3"
                        ),
                        new Enigma(
                                "El equilibrio en la dieta",
                                "En el contexto del fitness, ¿qué macronutriente es el más importante para la recuperación muscular después del ejercicio? \n\nPosibles respuestas: 1. Carbohidratos, 2. Grasas saludables, 3. Proteínas, 4. Vitaminas y minerales.",
                                "3"
                        ),
                        new Enigma(
                                "El descanso y la recuperación",
                                "¿Por qué es importante darle al cuerpo tiempo para descansar después de una rutina de entrenamiento intensa? \n\nPosibles respuestas: 1. Para descansar mentalmente, 2. Para permitir que los músculos se reparen y crezcan, 3. Para reducir el dolor muscular, 4. Para mejorar la resistencia.",
                                "2"
                        )
                },
                // Cuentos Enigmas
                {
                        new Enigma(
                                "El cazador y el ciervo",
                                "Un cazador persigue a un ciervo por el bosque. Después de varios días de búsqueda, el cazador se encuentra con un río. Si no sabe nadar y el ciervo puede cruzar el río, ¿qué debería hacer el cazador para atraparlo? \n\nPosibles respuestas: 1. Volver a su casa, 2. Esperar al otro lado del río, 3. Construir una balsa, 4. Pedir ayuda.",
                                "2"
                        ),
                        new Enigma(
                                "El jardín misterioso",
                                "En un jardín misterioso hay una estatua de un león con una llave colgando del cuello. Si tienes que encontrar la puerta secreta en el jardín, ¿qué harías con la llave? \n\nPosibles respuestas: 1. Esperar que alguien más la abra, 2. Buscar la puerta y usar la llave, 3. Llevarla a otro lugar, 4. Dejarla en su lugar.",
                                "2"
                        ),
                        new Enigma(
                                "La moneda dorada",
                                "Un niño encuentra una moneda dorada en el suelo, pero decide no recogerla. ¿Por qué podría haber tomado esta decisión? \n\nPosibles respuestas: 1. Porque le da miedo tocarla, 2. Porque sabe que no necesita la moneda o no le interesa, 3. Porque no la ve, 4. Porque cree que está maldita.",
                                "2"
                        ),
                        new Enigma(
                                "El puente de los tres caminos",
                                "Un hombre se encuentra en un cruce de caminos con tres opciones. Uno lleva a un pueblo lleno de riquezas, otro a una zona peligrosa y el último a un abismo. En el cartel que indica los caminos, las letras están desordenadas. El hombre debe escoger un camino sin equivocarse. ¿Cómo lo logra? \n\nPosibles respuestas: 1. Elige el camino que parece más seguro, 2. Toma el camino con la letra que está en orden alfabético, 3. Pregunta a un local, 4. Observa el camino menos transitado.",
                                "2"
                        )
                },
                // Acertijos Enigmas
                {
                        new Enigma(
                                "El prisionero y la cuerda",
                                "Un prisionero tiene una cuerda de 10 metros de largo y una vela. Si enciende la vela a un extremo de la cuerda, ¿cómo puede hacer para medir exactamente 5 metros de la cuerda sin cortarla? \n\nPosibles respuestas: 1. Cortando la cuerda por la mitad, 2. Quemando la cuerda por ambos extremos simultáneamente, 3. Midiendo con la vista, 4. Usando otro tipo de marcador.",
                                "2"
                        ),
                        new Enigma(
                                "La luz en el pasillo",
                                "Hay un pasillo con 3 interruptores. Uno de ellos enciende una bombilla en la habitación del final del pasillo. ¿Cómo puedes saber cuál es el interruptor correcto si solo puedes ir al final una vez? \n\nPosibles respuestas: 1. Prueba los tres interruptores, 2. Enciende un interruptor, espera unos minutos, apágalo, enciende otro y ve si la bombilla está caliente, 3. Usa un espejo, 4. Pide ayuda a alguien más.",
                                "2"
                        ),
                        new Enigma(
                                "El reloj y el tiempo",
                                "Un reloj da la hora correcta dos veces al día, pero su dueño no sabe cuándo. ¿Por qué el reloj da la hora correcta dos veces? \n\nPosibles respuestas: 1. Porque el reloj tiene un fallo, 2. Porque el reloj está parado, pero siempre marca la misma hora dos veces al día, 3. Porque el dueño no sabe usarlo bien.",
                                "2"
                        ),
                        new Enigma(
                                "El misterio de la caja cerrada",
                                "Tienes una caja cerrada con llave, y tienes tres llaves. Solo una de ellas abre la caja. No puedes probar todas las llaves a la vez, pero puedes hacer una sola prueba. ¿Cómo puedes estar seguro de cuál es la llave correcta? \n\nPosibles respuestas: \n1. Prueba la primera llave. Si no funciona, prueba la siguiente. \n2. Prueba la primera llave. Si no funciona, haz un sonido con la segunda llave para ver si es la correcta. \n3. Intenta visualizar cuál es la llave correcta usando la memoria. \n4. Usa las llaves en orden hasta que encuentres la correcta.",
                                "1"
                        )
                },
                // Matemáticas Enigmas
                {
                        new Enigma(
                                "Problema de la colección de cromos",
                                "Juan tenía una colección de cromos de fútbol que había ido reuniendo a lo largo de los años. Un día, decidió organizarlos en un álbum que tenía 10 páginas, y en cada página podía colocar exactamente 4 cromos.\n" +
                                        "Después de llenarlo por completo, su amigo Pedro le regaló 12 cromos más para que los agregara a su colección. Como ya no le quedaba espacio en el álbum, Juan decidió comprar un segundo álbum con la misma cantidad de páginas y capacidad por página.\n" +
                                        "Si Juan coloca todos sus cromos en los dos álbumes de la manera más eficiente posible, sin dejar espacios vacíos innecesarios, ¿cuántos cromos le sobrarán, si es que sobran?",
                                "0"
                        ),
                        new Enigma(
                                "El problema de las canicas",
                                "Carlos tiene 20 canicas, pero su hermana le quita 7. Si después Carlos compra 5 canicas más, ¿cuántas canicas tiene ahora?",
                                "18"
                        ),
                        new Enigma(
                                "El cambio de monedas",
                                "Juan compra una bebida que cuesta 3.50 euros. Si le da al cajero 5 euros, ¿cuánto recibirá de cambio?",
                                "1.50"
                        ),
                        new Enigma(
                                "El reparto de galletas",
                                "Ana tiene 30 galletas y las quiere repartir equitativamente entre 6 amigos. ¿Cuántas galletas recibirá cada uno?",
                                "5"
                        )
                }
        };
        return enigmas[x][y];
    }

}
