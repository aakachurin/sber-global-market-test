# language: ru
Функциональность: Получение погоды

  Структура сценария: Получить ошибку при получении погоды
    Дано пользователю задан ключ API "<Ключ>"
    Когда пользователь запрашивает текущую погоду для города "<Город>"
    Тогда API должно вернуть ошибку:
      | HTTP код   | <HTTP код>   |
      | Код ошибки | <Код ошибки> |
      | Описание   | <Описание>   |

    Примеры:
      | Ключ      | Город     | HTTP код | Код ошибки | Описание                                 |
      |           | Paris     | 401      | 1002       | API key not provided.                    |
      | API   api | Moscow    | 401      | 2006       | API key provided is invalid              |
      | default   | Frencheee | 400      | 1006       | No location found matching parameter 'q' |
      | default   |           | 400      | 1003       | Parameter 'q' not provided.              |