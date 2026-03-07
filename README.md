# 🏦 Desafio Backend Itaú - API de Transações

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

API RESTful desenvolvida para o [Desafio de Programação do Itaú Unibanco](https://github.com/rafaellbarros/desafio-itau-vaga-99-junior). O sistema recebe transações financeiras e calcula estatísticas em tempo real, mantendo um limite rigoroso de processamento apenas para os últimos 60 segundos.

## Tecnologias e Decisões de Arquitetura

Neste projeto, optei por focar fortemente em **performance, segurança de concorrência e boas práticas de engenharia**:

* **Armazenamento em Memória (Thread-Safe):** Como exigido pelo desafio, não utilizamos banco de dados. Para garantir a segurança em um ambiente concorrente (múltiplas requisições simultâneas), a lista de transações foi implementada utilizando `CopyOnWriteArrayList`.
* **Cálculo O(N) Otimizado:** As estatísticas são calculadas utilizando a API de Streams do Java em conjunto com `DoubleSummaryStatistics`, garantindo precisão e baixa alocação de memória.
* **Validações e Tratamento de Erros:** Implementação de um `@RestControllerAdvice` global para interceptar exceções (como datas no futuro ou valores negativos) e retornar respostas limpas (`422 Unprocessable Entity` e `400 Bad Request` sem corpo), em estrita conformidade com os requisitos.
* **Observabilidade:** Configuração de logs estruturados (SLF4J) nos services para rastreabilidade de eventos e regras de negócio.
* **Testes Automatizados:** Cobertura de testes unitários isolados com JUnit 5 para as regras de negócio de estatísticas e transações, validando o comportamento da janela de tempo (sliding window de 60s).
* **Containerização:** Dockerfile otimizado com *Multi-stage Build* (compilação com JDK e execução com JRE) para garantir uma imagem leve e pronta para produção.

## Como Executar

Você pode rodar a aplicação de duas formas:

### Opção 1: Usando Docker (Recomendado)
Certifique-se de ter o Docker instalado e rodando. No terminal, na raiz do projeto:
```bash
# 1. Construir a imagem
docker build -t itau-challenge-api .

# 2. Rodar o container
docker run -p 8080:8080 itau-challenge-api

### Opção 2: Usando Gradle local
./gradlew bootRun
A API disponível em http://localhost:8080.
```

## Endpoints
Criar Transação
-    POST /transacao
````json
{
    "valor": 150.50,
    "dataHora": "2026-03-07T12:00:00.000-03:00"
}

Respostas: 
201 Created (Sucesso)
422 Unprocessable Entity (Regra de negócio violada) 
400 Bad Request (JSON inválido)
````

2. Obter Estatísticas (Últimos 60 segundos)
- GET /estatistica
````json
{
"count": 1,
"sum": 150.50,
"avg": 150.50,
"min": 150.50,
"max": 150.50
}

Resposta:
200 OK
````

3. Limpar Transações
- DELETE /transacao
- Resposta: 200 OK