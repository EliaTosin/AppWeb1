# Creazione di un item

**URL** : `/items`

**Method** : `POST`

**Auth required** : NO

**Data constraints**


Due formati: JSON e x-www-form-urlencoded

- JSON:
```json
{
    "title":"Nome titolo",
    "description":"Nome descrizione",
    "author":"Nome autore",
    "category": "Nome categoria",
    "id": "id numerico dell'item"
}
```

**Data example**

```json
{
    "title":"Vendo Bici",
    "description":"ottimo affare",
    "author":"alberto",
    "category": "bici",
    "id": 1
}
```
- x-www-form-urlencoded:

Usando postman e specificando questo formato, compileremo i campi in questo modo:

title           Nome titolo
description     Nome descrizione
author          Nome autore
category        Nome categoria
id              id numerico dell'item

**Data example**

title           Vendo Bici
description     ottimo affare
author          alberto
category        bici
id              1


## Success Response

**Code** : `200 OK`

**Content example**

**Nota** : entrambi i formati scelti torneranno una visualizzazione json dell'item aggiunto
```json
{
    "title":"Vendo Bici",
    "description":"ottimo affare",
    "author":"alberto",
    "category": "bici",
    "id": 1
}
```

## Error Response

**Condition** : Se l'utente utilizza un id già precedentemente usato

**Code** : `400 BAD REQUEST`

**Content** :

Errore! Impossibile aggiungere l'oggetto!

Id gia' usato!

**Condition** : Se l'utente sceglie come id un campo vuoto o null

**Code** : `500`

**Content** :

Errore! Impossibile aggiungere l'oggetto!

**Condition** : Se l'utente sceglie un formato diverso

**Code** : `400 BAD REQUEST`

**Content** :

Errore! Impossibile gestire la richiesta!

# Lettura di un item

**URL** : `/items/"id_oggetto"`

**Esempio URL** : `/items/1`

**Method** : `GET`

**Auth required** : NO


## Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "title":"Vendo Bici",
    "description":"ottimo affare",
    "author":"alberto",
    "category": "bici",
    "id": 1
}
```

## Error Response

**Condition** : Se l'utente utilizza un id non esistente

**Code** : `404`

**Content** :

Errore! Impossibile trovare la risorsa!

**Condition** : Se l'utente scrive lo / senza specificare l'id

**Code** : `201`

**Content** :

Lista di tutti gli oggetti presenti stampati in json

# Lettura di tutti gli item

**URL** : `/items"`

**Method** : `GET`

**Auth required** : NO


## Success Response

**Code** : `200 OK`

**Content example**

```json
{
    "title":"Vendo Bici",
    "description":"ottimo affare",
    "author":"alberto",
    "category": "bici",
    "id": 1
}
{
    "title":"Vendo Auto",
    "description":"solo 30.000 Km",
    "author":"paolo",
    "category": "auto",
    "id": 2
}
```

# Aggiornamento/Modifica completa di un item

**URL** : `/items`

**Method** : `PUT`

**Auth required** : NO

**Data constraints**

- Da postman inserendo i parametri nell'header:

title           Nome titolo
description     Nome descrizione
author          Nome autore
category        Nome categoria
id              id numerico dell'item

**Data example**

title           Vendo Bici
description     ottimo affare
author          alberto
category        bici
id              1


## Success Response

**Code** : `201`

**Content example**

**Nota** : tornerà una visualizzazione json dell'item aggiornato relativo all'id dato
```json
{
    "title":"Vendo Bici",
    "description":"ottimo affare",
    "author":"alberto",
    "category": "bici",
    "id": 1
}
```

## Error Response

**Condition** : Se l'utente utilizza un id non esistente

**Code** : `404`

**Content** :

Errore! Impossibile trovare la risorsa!

**Condition** : Se l'utente sceglie come id un campo vuoto o null

**Code** : `500`

**Content** :

Errore! Impossibile aggiornare l'oggetto!

# Eliminazione di un item

**URL** : `/items`

**Method** : `DELETE`

**Auth required** : NO

**Data constraints**

Usando postman, compileremo i campi dell'header in questo modo:

id              id numerico dell'item

**Data example**

id              1

## Success Response

**Code** : `204`

## Error Response

**Condition** : Se l'utente utilizza un id non esistente

**Code** : `404`

**Content** :

Errore! Impossibile trovare la risorsa!

**Condition** : Se l'utente sceglie come id un campo vuoto o null

**Code** : `500`

**Content** :

Errore! Impossibile eliminare l'oggetto!
