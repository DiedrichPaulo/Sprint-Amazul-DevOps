NuclearQuest
Challenge FIAP - AMAZUL

Integrantes do grupo
André Souza Matsuda RM 566733 | Guilherme Barca RM 568517 | Guilherme Oliveira RM 566842 | Kaliel Conceição RM 567587 | Paulo Henrique Muniz Diedrich RM 567618 |

Como Docker e Cloud Computing podem ser utilizados na solução do grupo?

O Docker permite empacotar a aplicação e suas dependências em um container, garantindo maior consistência entre os ambientes de desenvolvimento e execução.

O Cloud Computing permite hospedar esse container em uma infraestrutura disponibilizada pela Microsoft Azure, possibilitando que a aplicação seja acessada remotamente pela internet.

Na solução NuclearQuest, o Docker é utilizado para padronizar a execução da API Java, enquanto a máquina virtual Azure fornece o ambiente de hospedagem e acesso externo à aplicação.

Sobre o projeto

O NuclearQuest é uma API REST desenvolvida em Java com Spring Boot, voltada para uma plataforma educacional de perguntas e respostas sobre tecnologia nuclear.

Este repositório apresenta a implementação da solução utilizando Docker e Microsoft Azure, permitindo executar a aplicação em uma máquina virtual na nuvem.

Arquitetura da solução

A aplicação é executada em um container Docker hospedado em uma máquina virtual Ubuntu 24.04 na Microsoft Azure.

O acesso externo é realizado pelo IP público da VM, utilizando a porta 8080.

O banco de dados H2 está integrado à aplicação e utiliza armazenamento em memória.

Executando com Docker

Construir a imagem

Na raiz do projeto, execute:

docker build -t nuclearquest .

Executar o container

docker run -d --name nuclearquest -p 8080:8080 nuclearquest

Verificar o container

docker ps

Acessar o Swagger

http://localhost:8080/swagger-ui/index.html

Provisionamento na Microsoft Azure

A infraestrutura foi provisionada utilizando Azure CLI.

Configurações da infraestrutura

Recurso

Configuração

Resource Group

rg-nuclearquest

Região

Brazil South

Máquina virtual

vm-nuclearquest

Sistema operacional

Ubuntu 24.04

Tamanho

Standard_D2s_v4

Usuário administrativo

azureuser

Porta da aplicação

8080

Provisionar a infraestrutura

O script de provisionamento está disponível em:

azure/script.sh

Para executar em uma assinatura Azure com o Azure CLI configurado:

chmod +x azure/script.sh
./azure/script.sh

Execução na VM Azure

A imagem Docker da aplicação foi transferida para a máquina virtual e carregada com:

sudo docker load -i nuclearquest.tar

O container foi iniciado com:

sudo docker run -d --name nuclearquest -p 8080:8080 nuclearquest:latest

Para verificar a execução:

sudo docker ps

A aplicação pode ser acessada pelo endereço:

http://IP_PUBLICO_DA_VM:8080/swagger-ui/index.html