# Sistema para Analise de Credito :money_with_wings: 

Foi desenvolvido um pequeno sistema para análise de cŕedito fornecendo as seguintes funcionalidades:

* Cadastro de clientes, apresentando nome, email, CPF, RG, endereço completo, renda e senha. 
* Login, sendo esta realizada informando o email e senha
* Solicitação de empréstimo, sendo preciso do valor do empréstimo, data da primeira parcela e quandidade de parcelas 
* Acompanhamento das solicitações de empréstimo 

Este projeto foi realizado para o processo seletivo: TQI Evolution 2022 - Back-end, utilizando os conhecimentos obtidos durante o bootcamp realizando na plantaforma da Digital Innovation One. 

Foram realizados no desenvolvimento desse projeto:

* Setup inicial de projeto com o Spring Boot Initialzr
* Criação de modelo de dados para mapeamento de entidades em bacos de dados
* Desenvolvimento de operações de genênciamento (cadastro, leitura, atualização e remoção de um sistema)
* Relacionamento de cada uma das operações acima com o padrão arquitetural REST
* Um sistema de login simples, sem emcriptação de senha 
* Utilização do banco de dados Postgresql, sendo rodado através do docker 

## Foram utilizados como depêndecias desse projeto

* spring-boot-starter-data-jpa
* spring-boot-starter-validation
* spring-boot-starter-web
* postgresql
* mapstruct, na versão 1.4.2.Final
* mapstruct-processor, na versão 1.4.2.Final

## Considerações

O desenvolvimento desta solução foi relativamente simples, foram criados duas entidades para se armazenar os dados dos clientes e também as solicitações de empréstimo, toda uma camada de repositorios e serviços para ambas, criação modelos para essas duas entidades retornarem apenas os dados necessários. 

Foi desenvolvidos dois controllers, um que serviria para os usuários e outro para os empréstimos. 
O primeiro seria o principal da aplicação, em que são realizados o cadastro do cliente e suas demais funções como a de login e logout, busca, atualização e remoção dessa entidade, assim como também nesse controller a criação de empréstimo e também a busca dos empréstimos de um determinado cliente, em que nessas funções apresentadas seria necessários realizar o login primeiro, sendo apenas a função de listar todos os clientes não necessário essa verificação. 
O segundo controler, o controler dos empréstimos, apresenta apenas os métodos para listar todos os empréstimos e buscar um empréstimo por ID, onde ambos não é verificado se o usuário está logado por não achar que seria necessário no sistema ser realizado o login para conseguir vê-los. 

Durante a implementação do sistema de login e logout obtive um problema quanto a encriptação de senhas, o qual fiquei em dúvida na forma em que isso poderia ser feito, dessa forma deixando a senha sem essa criptografia. 

Pensando na forma em que um sistema como esse poderia funcionar, acabei optando por não criar as opções de atualização e remoção dos empréstimo porque acredito que quando criado um novo impréstimo o ideal seria implementar nele um sistema de mensageria em que nesse caso essa api criada funcionaria apenas como um Producer, gerando mensagens, para outro sistema, que seria o consumer, realizar a verificação desse registro e possivelmente recusá-lo ou aceitá-lo. 
