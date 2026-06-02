1. Ante una interrupción de comunicación entre dos nodos, ¿qué propiedad del teorema CAP privilegia su implementación y por qué? Justifíquelo con el comportamiento observado en su prototipo.

Cuando dos nodos pierden la comunicación entre sí, el sistema prioriza la disponibilidad. Esto significa que los nodos que siguen conectados continúan funcionando y procesando mensajes en lugar de detenerse por completo. Durante las pruebas realizadas se observó que, aunque un nodo dejara de responder, los demás podían seguir intercambiando información. Por esta razón, la solución busca mantener el servicio disponible aun cuando exista una falla de comunicación.

2. ¿Qué falacias de la computación distribuida tuvo que considerar al delimitar los mensajes y al definir los tiempos de espera de los latidos?

Al desarrollar el sistema se tuvo en cuenta que la comunicación entre equipos no siempre funciona de manera perfecta. Los mensajes pueden tardar más de lo esperado o incluso no llegar a su destino. Por ello se configuraron tiempos de espera para detectar cuando un nodo deja de responder y evitar que el sistema quede esperando indefinidamente. También se consideró que la red puede cambiar y que los nodos pueden desconectarse en cualquier momento.

3. ¿Qué tipos de transparencia (ubicación, acceso, fallos, replicación) ofrece o no ofrece su solución? Argumente cada caso.

 Los usuarios pueden utilizar los servicios sin preocuparse por los detalles internos de la comunicación. Sin embargo, no ofrece completamente transparencia de ubicación, ya que es necesario conocer la dirección y el puerto de cada nodo. En cuanto a la transparencia ante fallos, el sistema permite que los nodos restantes continúen funcionando cuando uno presenta problemas, aunque no recupera automáticamente el nodo afectado. Finalmente, no existe transparencia de replicación porque los datos no se copian ni sincronizan entre varios nodos.

4. Proponga un acuerdo de nivel de servicio (SLA) de disponibilidad para este sistema y calcule el tiempo de inactividad anual admisible que implicaría.

Se propone un nivel de disponibilidad del 99,9 % anual. Esto significa que el sistema debería estar funcionando correctamente durante casi todo el año. Considerando que un año tiene 525 600 minutos, el tiempo máximo de inactividad permitido sería de aproximadamente 525,6 minutos, es decir, cerca de 8 horas y 46 minutos en todo el año. Si el tiempo de falla supera ese valor, no se estaría cumpliendo el acuerdo de disponibilidad establecido.

5. Si reemplazara el algoritmo Bully por un consenso tipo Raft, ¿qué ganaría y qué costo introduciría?

Se obtendría una coordinación más confiable entre los nodos y una mejor capacidad para manejar fallos. Sin embargo, también aumentaría la complejidad del proyecto, ya que sería necesario intercambiar más mensajes y mantener información adicional para alcanzar acuerdos entre los nodos. Para este prototipo, Bully resulta una opción adecuada debido a su sencillez, mientras que Raft suele emplearse en sistemas más grandes donde la confiabilidad es una prioridad.
