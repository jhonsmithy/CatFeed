# CatFeed

Интерфейс:

кнопка три точки с выбором:
- настройки
- добавить устройство
- Вкладка локального доступа
- Вкладка удаленного доступа
- 
Алгоритм добавление устройства:
1. добавить устройство (нажать на кнопку в меню три точки)
2. выдать лист Вифи сетей (возможность обновить лист)
3. выбрать сеть (в будущем автоматически подключаться к шаблонными именам сетей)
4. после успешого подключения отправить название сети и пароль к ней в устройство websockets
5. подключится к основной сети
6. обновить интерфейс на экране в локальном браузере-вкладке

При запуске определить локальные сервера, если их нет, то запустить удаленно.
1. Вывести панель с управлением

При запуске приложения в локальной сети:
1. В меню ссылка на спиок устройств(адресов). Загрузить из сохраненного списка 
2. Пропинговать список. offline/online 
3. открыть интерфейс по ип адресу из списка адресов в браузере-вкладке
4. дать возможность изменять имена устройств и в списке тоже(передавать настройки по WS)
5. дать возможность удалять устройства
6. слушать UDP и добавлять в список ип адресов устройства  (+)
7. удалять старые адреса из списка (через три дня?)


При запуске приложения в режиме удаленно:
1. Принимать mqtt пакеты
2. отправить HELLO
3. строить интерфейс на основе пакетов от устройств
4. передавть команды из интерфейса в обоих направлениях

В настройках хранить имя вифи сети и ее пароль

Регистрация при первом входе в приложение. Назначение
1. получение логин/пароль mqtt брокера
2. сохранить в настройках пароли имя вифи сети
3. передавать данные из по п1 и 2 в устройства через websockets при локальном подключении