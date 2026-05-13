# 🏦 Gerenciamento de Transações - Java e JDBC

Este projeto demonstra a implementação de um sistema de gerenciamento de transações financeiras usando **Java SE** e **JDBC**. O foco principal é garantir a consistência dos dados em operações complexas por meio de controles manuais de transação.

## ✨ Recursos

### 1. Integridade Transacional
* Implementação das propriedades **ACID** (Atomicidade, Consistência, Isolamento e Durabilidade).

* Controle manual de **commit e rollback** para garantir que as transferências sejam concluídas somente se todas as etapas forem bem-sucedidas.

### 2. Implementação do JDBC Core
* Conexão direta com o banco de dados via Driver Manager.

* Uso de `PreparedStatement` para prevenir ataques de **SQL Injection**.

* Gerenciamento eficiente de recursos (tratamento adequado de fechamentos de conexão e instruções).

### 3. Tratamento de Erros
* Tratamento de exceções SQL projetado para acionar rollbacks automáticos em caso de falhas durante uma transação.

* Logs informativos sobre o status da operação.

### 4. Integração com Banco de Dados
* Utiliza **PostgreSQL** para persistência de dados.

* Modelagem de tabelas focada em contas e registro de transações.

---

## 🛠 Tecnologias Utilizadas

* **Java SE**: Linguagem de programação principal.

* **JDBC (Java Database Connectivity)**: API para interação com o banco de dados.

* **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.

* **Maven**: Ferramenta de gerenciamento de dependências e construção de sistemas.

---

## 🚀 Como executar o projeto

1. **Clone o repositório:**

``bash

git clone https://github.com

```

2. **Configure o banco de dados:**

Crie um banco de dados em sua instância do PostgreSQL e atualize sua classe de conexão (por exemplo, `DB.java`) com suas credenciais:

- URL: `jdbc:postgresql://localhost:5432/seu_banco_de_dados`

- Usuário: `seu_nome_de_usuário`

- Senha: `sua_senha`

3. **Esquema do banco de dados:**

Certifique-se de ter criado as tabelas necessárias. Exemplo básico:

``sql

CREATE TABLE accounts (

id SERIAL PRIMARY KEY,

name VARCHAR(100),

balance DECIMAL(10, 2)

);
```

4. **Execute a aplicação:**

Execute a classe principal através da sua IDE ou via terminal:

``bash

mvn compile exec:java -Dexec.mainClass="your.package.name.Main"

```

---

## 🤝 Contribuindo

Sinta-se à vontade para criar um fork deste projeto, abrir issues ou enviar pull requests com melhorias, como a implementação de diferentes níveis de isolamento de transação.

---
Desenvolvido por https://github.com/LucasSilva1717
