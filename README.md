# atividade-Solutis-Consumindo-e-produzindo-mensagens-na-fila-SQS

evidencia 1: ![image](https://github.com/user-attachments/assets/77d52eca-208c-4150-aae3-6c2b14a85546)

No Spring, o envio e o consumo de mensagens de uma fila SQS usando LocalStack envolve dois processos principais:

1. Envio de Mensagens:
   
O Spring utiliza o Amazon SQS client do SDK da AWS para enviar mensagens para a fila. O cliente SQS é configurado para se conectar ao LocalStack em vez da AWS real, apontando para a URL da fila criada localmente. O processo é simples:

Configuração do cliente: O cliente SQS é configurado com as credenciais e a URL do LocalStack.
Envio da mensagem: A mensagem é enviada para a fila usando o método sendMessage(), que permite especificar o corpo da mensagem e a URL da fila.

2. Consumo de Mensagens:

Para consumir mensagens, o Spring utiliza o SQS Listener, que fica "ouvindo" a fila e processa as mensagens assim que elas são recebidas.

Listener: Um método anotado com @SqsListener é configurado para escutar uma determinada fila. Sempre que uma nova mensagem chega, o listener processa automaticamente.

Processamento: A mensagem recebida é tratada no método do listener, onde você pode implementar a lógica de negócio para lidar com o conteúdo da mensagem.

Essa integração permite simular completamente o fluxo de envio e recebimento de mensagens em filas, sem depender da AWS real, usando LocalStack para desenvolvimento local.

evidencia 2: ![image](https://github.com/user-attachments/assets/d0098fa0-304f-4ea9-8aad-e6bfab2384d6)


