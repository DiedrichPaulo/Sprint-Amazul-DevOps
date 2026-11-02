# NuclearQuest

Challenge FIAP - AMAZUL

## Integrantes do grupo

André Souza Matsuda   RM 566733 |
Guilherme Barca       RM 568517 |
Guilherme Oliveira    RM 566842 |
Kaliel Conceição      RM 567587 |
Paulo Henrique Muniz  RM 567618 |

## Sobre

API RESTful de um quiz gamificado que apresenta, de forma simples e acessível, as aplicações e os
benefícios da tecnologia nuclear (saúde, energia, segurança, pesquisa). Onde o usuário responde perguntas
de múltipla escolha, recebe na hora o resultado (acertou ou não e qual era a resposta correta) e
tem as respostas registradas em um banco de dados.

## Tecnologias

Spring Boot 4.0.5 | Spring Data JPA | Spring Validation | Springdoc | H2

## Como executar

1 - Com JDK 21: `./mvnw spring-boot:run` (Windows: `.\mvnw.cmd spring-boot:run`)
2 - Swagger: http://localhost:8080/swagger-ui.html

## Regras de negócio

1 - Cada pergunta deve ter de 2 a 6 alternativas, com exatamente uma correta.
2 - A categoria de uma pergunta não pode ser alterada.
3 - Perguntas já respondidas não podem ser removidas.
4 - A alternativa respondida deve pertencer à pergunta.

## Banco de dados

Modelo baseado no script Oracle da disciplina de banco de dados. Por enquanto a API usa H2; a integração com o Oracle está prevista para a próxima entrega.