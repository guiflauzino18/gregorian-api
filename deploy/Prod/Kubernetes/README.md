# Dados para acesso à aplicação:
- Nome do banco: gregorian
- usuário do banco: gregorian
- Senha do usuário do banco: pass123

Ao subir o ambiente criar um dns cliente.gregorian.com e api.gregorian.com que aponte para o External IP do Ingress.
Ex.: Edite o arquivo C:\Windows\System32\Drivers\etc\hosts no windows e adicione  dns e o ip do ingress. No linux: /etc/hosts

- Acesse cliente.gregorian.com no navegador e insira os dados de acesso abaixo

- Para logar no frontend:
  * usuário: sysadmin
  * senha: sysadmin!

# Instruções para deploy da aplicação no EKS

- Instalação eksctl
  * curl --location "https://github.com/weaveworks/eksctl/releases/latest/download/eksctl_$(uname -s)_amd64.tar.gz" | tar xz -C /tmp
  * sudo mv /tmp/eksctl /usr/local/bin
  * eksctl version

- Instalação kubectl
  * curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
  * chmod +x kubectl
  * sudo mv kubectl /usr/local/bin/
  * kubectl version --client

- Instalar AWS CLI ou usar Cloudshell

- Criar Cluster
  * eksctl create cluster --name gregorian-cluster --region us-east-2 --nodegroup-name gregorian-nodes --node-type t3.medium --nodes 2
  . Cria um cluster chamado gregorian-cluster na região Ohio com nodegroup de nome gregorian-nodes com tipo EC2 t3.medium e 2 instancias (nodes)

- Configurar Credenciais do Cluster
  * aws eks update-kubeconfig --region us-east-2 --name gregorian-cluster

- Testar conexão kubectl com cluster
  * kubectl get nodes
  * aws eks describe-cluster --name gregorian-cluster --query "cluster.status"
   . Deve retornar ACTIVE

- Instalar o Ingress Controller (Ex.: Nginx)
  * kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml --namespace=ingress-nginx

- Criar o namespace, secrets e configMap, deployments, services e ingress em arquivo yaml

- Configurar certificado tls
  - OBS: para o letsencrypt passar no desafio de verificação de dominio, deve haver um registro DNS que aponte para o IP do ingress

  - Instalar cert-manager
    * helm repo add jetstack https://charts.jetstack.io
    * helm repo update
    * helm install cert-manager jetstack/cert-manager --namespace gregorian --set installCRDs=true
  - Verficar se foi instalado
    * kubectl get pods -n cert-manager
  - Configurar CluesterIssuer responsável por definir como os certificados serão emitidos. Possui duas opções: Staging e Production.
    * criar arquivo yml chamado letsencrypt-issuer.yml e aplicar no cluster
  - Solicitar um certificado
    * Criar um arquivo yml para gerar o certificado. Ex.: certificate.yml
  - Aplicar o tls no ingress
    * Conferir arquivo ingress.yml


- Aplicar os arquivos yml no cluster
  * kubectl apply -f k8s/namespace.yml # Aplicar este primeiro para os demais não dar erro de namespace
   . Fazer para todos os arquivos yml
   . Fazer também para os yml do cert-manager

- Verificar se pods subiram
  * kubectl get pods -n gregorian
   . -n = namespace

- Verificar services
  * kubectl get services -n gregorian
   . -n = namespace

- Verificar Ingress
  * kubectl get ingress -n gregorian
   . -n = namespace


#Comandos úteis
- Ver pods e execução
  * kubectl get pods -n gregorian

- Acessar terminal do pod
  * kubectl exec mysql-6c78c795db-4rfrs -i -t -n gregorian -- bash

- Ver Ingress
  * kubectl get ingress -n gregorian

- Ver logs
  * kubectl logs -l app=mysql -n gregorian

- Ver recursos dos Nodes
  * kubectl top node

- Ver recursos dos Pods
  * kubectl top pod -n gregorian