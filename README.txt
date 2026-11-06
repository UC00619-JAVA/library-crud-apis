UC00619 — Desenvolvimento em Java — Sessão 12
Exercício 3 — Camadas, DTOs e validação
Projeto: library-jpa · Spring Boot 4.1.2 · Java 21 · Maven
================================================================

PEDIDOS NO POSTMAN                                  RESULTADO ESPERADO

GET  /api/books                                     200, 4 livros (BookResponse)
GET  /api/books/99                                  404 {"status":404,"code":"BOOK_NOT_FOUND","message":"Book not found: 99","fields":{}}
POST /api/books {"title":"Refactoring","author":"Martin Fowler","publishedYear":2018}
                                                    201, Location: /api/books/5
POST /api/books {"title":"","author":"Martin Fowler","publishedYear":1200}
                                                    400 {"status":400,"code":"VALIDATION_FAILED","message":"Invalid request data",
                                                         "fields":{"title":"Title is required","publishedYear":"Published year must be 1450 or later"}}
                                                    (a ordem dos campos em "fields" pode variar)
PUT  /api/books/5 {"title":"Refactoring (2nd Edition)","author":"Martin Fowler","publishedYear":2018}
                                                    200
DELETE /api/books/5                                 204 (e 404 BOOK_NOT_FOUND à segunda vez)
Extra: POST com JSON malformado (ex.: falta a } final)
                                                    400 {"status":400,"code":"INVALID_JSON","message":"The request body is not valid JSON","fields":{}}
