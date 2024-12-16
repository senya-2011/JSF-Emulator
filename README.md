# JSF Emulator 
Реализация эмулятора jsf. Показан жизненный цикл jsf, а так же вызовы FacesServlet, которые за нас делает веб-контейнер.

> Проект реализован исходя из информации на сайте <a href="https://java-online.ru/jsf-lifecycle.xhtml" target="_blank">java-online.ru</a>


## 📂 Структура Проекта
```plaintext
📂 src
├️🗋 Main.java
├️📂 login
│   ├️🗋 Binding.java
│   ├️🗋 FacesContext.java
│   ├️🗋 FacesServlet.java
│   ├️🗋 Model.java
│   ├️🗋 Printer.java
│   ├️🗋 ViewHandler.java
│   └📂 phases
│      ├🗋 Phase.java (interface)
│      ├🗋 RestoreViewPhase.java
│      ├🗋 ApplyRequestValuesPhase.java
│      ├🗋 ProcessValidationsPhase.java
│      ├🗋 UpdateModelValuesPhase.java
│      ├🗋 InvokeApplicagtionPhase.java
│      └🗋 RenderResponsePhase.java
└📂 ui
    ├️🗋 ComponentParser.java
    ├️🗋 UIComponent.java
    ├️🗋 UIWiewRoot.java
    └🗋 UiDom.java
```

## Выполнение 
1. После запуска программы мы можем выбрать имитацию GET/POST запроса. После выбора программа имитирует отправку запроса веб-контейнеру, а тот передачу **FacesServlet**-y. После чего **FacesServlet** создает **FacesContext** для данного запроса, который в свою очередь достает *метод запроса* и *viewId*.
Дальше **FacesServlet** вызывает **Lifecycle**.execute передавая туда **FacesContext**. Так же можно заметить на втором примере вывода, что при последующих запросах происходит _биндинг_ всех элементов дерева (UICompont).
```plaintext
Введите запрос, пример "GET index" 
(есть GET и POST, а из страниц index, login, main, invalid, decode, errors)
get index

==================================================
 клиент отправил GET запрос: /index.xhtml;GET;{[a=3], [b=1], [date=15.03.2006]} 

FacesServlet получил request: /index.xhtml;GET;{[a=3], [b=1], [date=15.03.2006]}
FacesServlet создает FacesContext и передает request
FacesServlet создал новый FacesContext для запроса
FacesContext получил request: /index.xhtml;GET;{[a=3], [b=1], [date=15.03.2006]}
FacesContext: viewId запроса: /index.xhtml
FacesContext: method запроса: GET
FacesServlet вызывает Lifecycle.execute
Lifecycle принял facesContext
Lifecycle начинает вызов Phases
```
```plaintext
====== Restore View Phase ======

ViewHandler: вызов restoreView для viewId: /errors.xhtml
ViewHandler: мапа всех viewId: {/decode.xhtml=UIViewRoot [viewId: /decode.xhtml, locale: ru, components: [UIComponent: [id: decode, value: 1, valid: true], UIComponent: [id: date, value: 01.01.2025, valid: true]]], /errors.xhtml=UIViewRoot [viewId: /errors.xhtml, locale: ru, components: [UIComponent: [id: decode, value: decodeError, valid: true], UIComponent: [id: invalid, value: invalid, valid: true]]], /index.xhtml=UIViewRoot [viewId: /index.xhtml, locale: ru, components: [UIComponent: [id: a, value: 4, valid: true], UIComponent: [id: b, value: 2, valid: true], UIComponent: [id: date, value: 15.03.2006, valid: true]]]}
viewRoot был найден => это не первый запрос
FacesContext: ExternalContext.html#getRequestLocale()
UIViewRoot: поставлена локаль: ru
дальше идет биндинг всех компонетов
UIViewRoot: checkValueBinding
UIComponent id=decode: binding
ModelExample [id: decode, value: decodeError]
UIComponent id=invalid: binding
ModelExample [id: invalid, value: invalid]
```

Вывод при повторной запросе


2. Дальше вызывается RestoreView, где **ViewHandler** ищет **UIViewRoot**, если это первый запрос на эту страницу или же GET запрос (так же может быть первым), то после выполнения мы сразу переходим на **RenderResponse**
   **ViewHandler** выполняет _restoreView_, где пытается восстановить **UIViewRoot**, если же его не находит, то выполняет _createView_ тем самым создав для данной страницы **UIViewRoot**. Дальше выставляется локаль (это делается при каждом запросе)
> С <a href="https://java-online.ru/jsf-lifecycle.xhtml" target="_blank">java-online.ru</a> _если запрос не содержит POST-данных или параметров (query parameters), то вызывается FacesContext#renderResponse()_

```plaintext
====== Restore View Phase ======

ViewHandler: вызов restoreView для viewId: /index.xhtml
ViewHandler: мапа всех viewId: {}
viewRoot не был найден => первый запрос
ViewHandler: создаем новый UIViewRoot для viewId: /index.xhtml
UIViewRoot: дерево компонентов: [UIComponent: [id: a, value: 3, valid: true], UIComponent: [id: b, value: 1, valid: true], UIComponent: [id: date, value: 15.03.2006, valid: true]]
FacesContext: ExternalContext.html#getRequestLocale()
UIViewRoot: поставлена локаль: ru
тк это первый запрос переходим сразу на Render Response
FacesContext: вызов renderResponse()
```

3. К RenderResponse мы вернемся в самом конце, сейчас продолжим как будто мы отправили уже второй POST запрос. Но для первого сразу переходите к 7 пункту!
   Следующая фаза жизненного цикла jsf - **Apply Request Values**. В этой фазе, после восстановления **UIViewRoot** мы вызываем _processDecodes_, **UIViewRoot** для каждого элемента дерева, те **UIComponent**-а вызываем метод _processDecodes_, на этом этапе проходит преобразования данных и конвертация.
   Важно заметить, что в данной фазе могут возникнуть ошибки, которые передаются FacesContext, а потом выводятся в последней фазе **RenderResponse**. Под основным примером выполнения показан, пример с ошибкой.
   > С <a href="https://java-online.ru/jsf-lifecycle.xhtml" target="_blank">java-online.ru</a> _Фреймворк должен вызвать метод UIViewRoot#processDecodes, который вызовет методы UIComponent#processDecodes для всех компонентов дерева._
   > 
   > Понятно, что UIComponent не может быть реализацией потому что это абстракция, но в данном проекте я не рассматриваю конкретную реализацию, тк там добавляются интерфейсы, которые ну уж очень сложно реализовать.

   
```plaintext
====== Apply Request Values Phase ======

каждый компонент обновляет своё состояние на основании информации текущего запроса
UIViewRoot: processDecodes
UIComponent id=a: processDecodes
UIComponent id=a: преобразован в int
UIComponent id=b: processDecodes
UIComponent id=b: преобразован в int
UIComponent id=date: processDecodes
UIComponent id=date: преобразован в Date
UIViewRoot: дерево компонентов: [UIComponent: [id: a, value: 3, valid: true], UIComponent: [id: b, value: 1, valid: true], UIComponent: [id: date, value: 15.03.2006, valid: true]]
```
Пример вывода при ошибка в данной фазе

```plaintext
====== Apply Request Values Phase ======

каждый компонент обновляет своё состояние на основании информации текущего запроса
UIViewRoot: processDecodes
UIComponent id=decode: processDecodes
UIComponent id=decode: ошибка преобразования!
UIComponent id=date: processDecodes
UIComponent id=date: преобразован в Date
FacesContext#addMessage : Ошибка преобразования (ApplyRequestPhase)
UIViewRoot: дерево компонентов: [UIComponent: [id: decode, value: decodeError, valid: true], UIComponent: [id: date, value: 01.01.2025, valid: true]]
```
4. Переходим к следующей фазе **Process Validations**.
   В данной фазе для **UIViewRoot** вызывается метод _processValidators_. Который для каждого элемента дерева (**UIComponent**) вызывает одноименный метод. Если при выполнении валидации возникает ошибка, поле _valid_ у UIComponent-а меняется на false. Что показано в примере для элемента с id: invalid.
   Так же надо заметить, что при любой ошибке в любой из фаз, значение поля **UIComponent**-а не меняется.
> С <a href="https://java-online.ru/jsf-lifecycle.xhtml" target="_blank">java-online.ru</a> _Если на данном этапе жизненного цикла возникнут ошибки преобразования или валидации, то будет вызван метод FacesContext#addMessage для добавления сообщения об ошибке соответствующих компонентов, и их свойство valid будет выставлено в false._
   
```plaintext
====== Process Validations Phase ======

вызывается метод UIViewRoot#processValidators(), которые для каждого компонента дерева вызывает метод UIComponent#processValidators()
UIViewRoot: processValidators
UIComponent id=decode: processValidators
UIComponent id=invalid: processValidators
UIComponent id=invalid: ошибка валидации!
FacesContext#addMessage : Ошибка валидации (Process Validate Phase)
UIViewRoot: дерево компонентов: [UIComponent: [id: decode, value: decodeError, valid: true], UIComponent: [id: invalid, value: invalid, valid: false]]
```

5. Следующая фаза **Update Model Values**.
   В этой фазе мы обновляем значения в Model-и (Bean в jsf, но в этом проекте это просто POJO). У нас после первой фазы, где мы сделали _биндинг_ уже есть "бин" к которому привязан **UIComponent**. Поэтому мы просто передадим значения в нашу Model, как бы обновив ее.
   Для обновления модели у **UIViewRoot** вызывается метод _processUpdate_ после чего для каждого элемента дерева вызывается одноименный метод.
   Важно заметить, что в модели лежат преобразованные значения. (например можно посмотреть для элемента с id=date).
```plaintext
====== Update Model Values Phase ======

В данной фазе вызывается метод UIViewRoot#processUpdates(), после чего для каждого компонента дерева будут вызваны методы UIComponent#processUpdates()
UIViewRoot: processUpdate
UIComponent id=a: processUpdate
ModelExample [id: a, value: 4]
UIComponent id=b: processUpdate
ModelExample [id: b, value: 2]
UIComponent id=date: processUpdate
ModelExample [id: date, value: Wed Mar 15 00:00:00 MSK 2006]
UIViewRoot: дерево компонентов: [UIComponent: [id: a, value: 4, valid: true], UIComponent: [id: b, value: 2, valid: true], UIComponent: [id: date, value: 15.03.2006, valid: true]]
```
6. **Invoke Application**
   В данной фазе я сделал просто имитацию каких-то действий, потому что интерфейсы **UIComponent**-ов у меня не реализованы. Для примера при каждом post запросе, все int-овые поля модели увеличиваются на 1. Тем самым значения в модели увеличиваются и в следующем завершающем шаге, мы восстоновам новую **UIRootView**, где уже будут новые значения тк у нас есть _биндинг_
   >  С <a href="https://java-online.ru/jsf-lifecycle.xhtml" target="_blank">java-online.ru</a> _Все обновления модели выполнены и оставшиеся события передаются для обработки приложению. Вызывается метод UIViewRoot#processApplication() для обработки событий_
```plaintext
====== Invoke Application Phase ======

Все обновления модели выполнены и оставшиеся события передаются для обработки приложению.
UIViewRoot: processApplication
UIComponent id=a: processApplication
UIComponent id=a: имитируем нажатие на кнопку +1
ModelExample [id: a, value: 5]
UIComponent id=b: processApplication
UIComponent id=b: имитируем нажатие на кнопку +1
ModelExample [id: b, value: 3]
UIComponent id=date: processApplication
ModelExample [id: date, value: Wed Mar 15 00:00:00 MSK 2006]
UIViewRoot: дерево компонентов: [UIComponent: [id: a, value: 5, valid: true], UIComponent: [id: b, value: 3, valid: true], UIComponent: [id: date, value: 15.03.2006, valid: true]]
```

7. Завершающая фаза **Render Response**
    В данной фазе формируется ответ. **ViewHandler** сохраняет **UIViewRoot**, чтобы при следующем запросе загрузить уже обновленную. Так же у **UIViewRoot** выполняется метод _encodeXX_, которые идем по всем элементам дерева вызывая такой же запрос, тем самым формируя ответ.
    Так же в этой фазе все ошибки, которые мы добавляли в **FacesContext** добавляются в тег <messages>. Сформировав ответ **FacesServlet** отправляет его клиенту.
   
```plaintext
====== Render Response Phase ======

Render Response - это завершающая фаза жизненного цикла JSF, в которой формируется ответ. 
Сохранили UIViewInfo для следующих запросов
ViewHandler: сохраняем view: /errors.xhtml
UIViewRoot: encodeXX
UIComponent id=decode: encodeXX
UIComponent id=invalid: encodeXX
Ответ клиенту: 
{[decode=1], [invalid=invalid]};
<messages>
 Ошибка преобразования (ApplyRequestPhase)
Ошибка валидации (Process Validate Phase)
</messages>
Итоговые значения
---------------------------------------------------------------------------------------------------------------
UIViewRoot: дерево компонентов: [UIComponent: [id: decode, value: 1, valid: true], UIComponent: [id: invalid, value: invalid, valid: false]]
ModelExample [id: decode, value: 1]
ModelExample [id: invalid, value: invalid]
---------------------------------------------------------------------------------------------------------------
FacesServlet отправляет ответ клиенту
```
8. Клиент принимает запрос и обновляет свой "DOM".
