<h3>Sobre</h3>
Gregorian é um projeto pessoal de prática e aprefeiçoamento de desenvolvimento utilizando o framework Spring. A aplicação será uma API que posteriormente será consumida por uma aplicação web e futuramente uma aplicação mobile e se trata de uma agenda para consultas com recursos como cadastro de pacientes, definição de agendas, configurações, permissões e elaborações de "laudos".

<h3>Deploy & Environment</h3>
Aplicação será executada em Docker e será implantada na AWS utilizandos os serviços de EC2 com Load Balancer e Autoscaling. Para fins de testes será implantada também utilizando o serviço de ECS, mas por questões de custos será utilizado majoritariamente o EC2.

Para banco de dados será utilizado o MySQL na versão 8.0 utilizando o serviço de RDS.

Deploy da aplicação, assim como a construção do ambiente, será implementando o recurso CI/CD utilizando a ferramenta GitHub Actions.

<h3>Sobre este Reposítório</h3>
Gregorian-api é o projeto da construção da API, utilizando Spring Framework e Banco de Dados MySQL.

# Como testar

Clone este repositório e configure as credenciais de acesso da AWS. Aqui está sendo usado uma role atribuída a um provedor de identidade do GitHub.
Alternativamente pode-se executar o docker-compose.yml dentro da pasta deploy/Build/docker-compose. Ex.: docker compose up -d.

Em seguida crie um dns cliente.gregorian.com e api.gregorian.com apontando para o servidor na AWS ou na máquina que for executado o docker. Ex.: edite o arquivo c:\Windows\System32\Drivers\etc\hosts no windows a adicione a linha: `127.0.0.1 cliente.gregorian.com` <br>
Faça o mesmo para api.gregorian.com

Acesse http://cliente.gregorian.com no navegador. <br>
Login: sysadmin <br>
Senha: sysadmin! <br>
