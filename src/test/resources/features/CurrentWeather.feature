# language: ru
Функциональность: Получение погоды

  Структура сценария: Получить текущую погоду для города
    Дано пользователю задан ключ API "default"
    Когда пользователь запрашивает текущую погоду для города "<Город>"
    Тогда API должно успешно вернуть следующие значения:
      | Параметр    | Значение      |
      | Температура | <Температура> |
      | Погода      | <Погода>      |

    Примеры:
      | Город  | Температура | Погода        |
      | Paris  | 10.0        | Sunny         |
      | Moscow | -10.0       | Partly cloudy |
      | French | 24.0        | Cloudy        |
      | German | 3.0         | Overcast      |
      | Greek  | -25.0       | Fog           |