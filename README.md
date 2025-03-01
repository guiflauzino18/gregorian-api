<h3>Sobre</h3>
Gregorian é um projeto pessoal para prática e aprefeiçoamento de desenvolvimento utilizando o framework Spring e infraestrutura Cloud utilizazndo ferramentas e princípios Devops. A aplicação será uma API que posteriormente será consumida por uma aplicação web e futuramente uma aplicação mobile e se trata de uma agenda para consultas com recursos como cadastro de pacientes, definição de agendas, configurações, permissões e elaborações de "laudos".

<h3>Deploy & Environment</h3>
Aplicação será executada em Docker e será implantada na AWS utilizandos os serviços como EC2 com Load Balancer e Autoscaling, EJs e ECS.

Para banco de dados será utilizado o MySQL na versão 8.0 utilizando o serviço de RDS.

Deploy da aplicação, build e a construção do ambiente será implementando usando recursos CI/CD utilizando o GitHub Actions.

<h3>Sobre este Reposítório</h3>
Gregorian-api é o projeto da construção da API, utilizando Spring Framework e Banco de Dados MySQL.

# Como testar

Clone este repositório e configure as credenciais de acesso da AWS. Aqui está sendo usado uma role atribuída a um provedor de identidade do GitHub para pipelines e para testes locais está sendo usado aws cli.
Para testes de aplicação localmente pode-se executar o docker-compose.yml dentro da pasta deploy/Staging/Build/docker-compose. Ex.: docker compose up -d.

As formas de deploy são:
- Staging: Utilizando Terraform constrói a infraestrutura na AWS usando uma instancia EC2. Ou para teste local usar o docker-compose.yml.
- Prod: Utilizando AWS EKS ou ECS com Fargate

Em seguida crie um dns cliente.gregorian.com e api.gregorian.com apontando para o servidor na AWS ou na máquina que for executado o docker. Ex.: edite o arquivo c:\Windows\System32\Drivers\etc\hosts no windows a adicione a linha: `127.0.0.1 cliente.gregorian.com` <br>
Faça o mesmo para api.gregorian.com <br>

No linux edite o arquivo em /etc/hosts. <br>

Acesse http://cliente.gregorian.com no navegador. <br>
Login: sysadmin <br>
Senha: sysadmin! <br>
