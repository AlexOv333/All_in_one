Топология включает в себя 2 хоста между которыми подключен линк.
Один из хостов имеет название s1, так как на нем запущена программа reflector.p4, которая выполняет функционал пересылки пакета.
Для тестирования решения необходимо запустить скрипт send_recieve.py на хосте.

Для запуска топологии необходимо ввести команду: sudo python3 ./network.py (она запустит Mininet)
<br/>
Далее необходимо зайти на хост под имененм s1 командой xterm s1
<br/>
После выполнения этих шагов, запустить компиляцию программы на p4: существует 2 способа<br/> - p4c --target bmv2 --arch v1model --std p4-16 reflector.p4 <br/>
                                                                                        - p4c-bm2-ss reflector.p4 -o reflector.json (необходима проверка совместимости)<br/>
Далее запустить саму машину BMv2: simple_switch -i 0@s1-eth0 reflector.json (имя интерфейса задается самостоятельно, пока не очень понятно где и как)<br/>
После этих действий запустить на хотсте h1 скрипт send_recieve.py и проверить пинг.               
