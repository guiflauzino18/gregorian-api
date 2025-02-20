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
   .Deve retornar ACTIVE

- Criar o namespace, secrets e configMap, deployments, services e ingress em arquivo yaml

- Aplicar os arquivos yml no cluster
 * kubectl apply -f k8s/namespace.yml
   .Fazer para todos os arquivos yml

- Verificar se pods subiram
 * kubectl get pods -n gregorian
   .-n = namespace

- Verificar services
 * kubectl get services -n gregorian
   .-n = namespace

- Verificar Ingress
 * kubectl get ingress -n gregorian
   -n = namespace

