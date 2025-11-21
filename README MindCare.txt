MindCare API – Check-in Emocional com IA

API REST desenvolvida para o trabalho da disciplina DevOps Tools & Cloud Computing, incluindo containerização com Docker e deploy em VM Linux no Azure.

A API realiza triagem emocional básica usando IA (fallback padrão em caso de falha) e registra check-ins no banco H2 em memória.


1) Funcionalidades

- Registro de check-in emocional enviando um texto.
- Resposta simulada da IA (JSON com risco, recomendações e análise).
- Banco H2 em memória criado automaticamente.
- Usuário de teste criado ao iniciar.
- API containerizada com Docker.
- Deploy em VM Ubuntu no Microsoft Azure.


2) Arquitetura

(Imagem architecture.png incluída no repositório)


3) Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Banco H2
- Docker
- Azure CLI + VM Linux Ubuntu 22.04
- GitHub


4) Como Executar Localmente

Clonar o repositório:

    git clone https://github.com/SEU-USUARIO/mindcare-api.git
    cd mindcare-api

Rodar com Maven:

    ./mvnw spring-boot:run

Acessar:
http://localhost:8080


5) Executar via Docker

Build da imagem:

    docker build -t mindcare-api .

Rodar o container:

    docker run --name mindcare -p 8080:8080 mindcare-api


6) Acessar API na VM Azure

URL pública da API:

    http://20.185.37.6:8080

Exemplo PowerShell:

    Invoke-RestMethod -Uri "http://20.185.37.6:8080/api/checkins?userId=1" `
    -Method POST `
    -Body "Teste vindo da internet" `
    -ContentType "text/plain"

Exemplo Curl:

    curl -X POST "http://20.185.37.6:8080/api/checkins?userId=1" \
      -H "Content-Type: text/plain" \
      --data "Olá, estou me sentindo cansado hoje."


7) Endpoints

Criar Check-in:

    POST /api/checkins?userId=1
    Content-Type: text/plain

Exemplo de corpo:
    Estou me sentindo cansado hoje.

Exemplo de resposta:

    {
      "id": 1,
      "rawText": "Estou me sentindo cansado hoje.",
      "riskLevel": "LOW",
      "recommendations": "Descansar | Procurar água",
      "aiResponseJson": {
        "risk": "LOW",
        "reason": "Resposta padrão por falha na integração",
        "recommendations": ["Descansar", "Procurar água"],
        "referral": []
      }
    }


8) Deploy no Azure (resumo)

Criar resource group:

    az group create -n mindcare-rg -l eastus

Criar VM Ubuntu:

    az vm create --resource-group mindcare-rg --name mindcare-vm --image Ubuntu2204 --admin-username azureuser --generate-ssh-keys

Liberar porta:

    az vm open-port --resource-group mindcare-rg --name mindcare-vm --port 8080

Instalar Docker:

    sudo apt update
    sudo apt install docker.io -y
    sudo systemctl enable docker
    sudo systemctl start docker
    sudo usermod -aG docker $USER

Push imagem para Docker Hub:

    docker tag mindcare-api guusthy/mindcare-api:latest
    docker push guusthy/mindcare-api:latest

Rodar na VM:

    docker pull guusthy/mindcare-api:latest
    docker run -d --name mindcare --restart always -p 8080:8080 guusthy/mindcare-api:latest


9) Evidências

A pasta “evidence/” contém:

- az_vm_create.json
- docker_images.txt
- docker_ps.txt
- architecture.png
- api_response.json (opcional)




