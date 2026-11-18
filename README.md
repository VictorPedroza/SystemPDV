# SystemPDV

**SystemPDV** é um sistema de Ponto de Venda (PDV) desenvolvido em Java, com interface gráfica baseada em Swing e integração com banco de dados MySQL.

O projeto foi realizado com um trabalho para Disciplina de Programação para Bando de Dados, no curso de Análise e Desenvolvimento de Sistemas na Fatec Itu - Dom Amaury Castanho (4° Semestre)

---

## Funcionalidades

- **Autenticação de usuários:** acesso ao sistema por meio de login.
- **Controle de permissões:** separação entre os modos Administrativo e Caixa.
- **Gerenciamento de produtos:** cadastro, consulta e manutenção de produtos.
- **Controle de estoque:** acompanhamento da quantidade disponível de cada produto.
- **Registro de vendas:** processamento e armazenamento de vendas e seus respectivos itens.
- **Configurações do sistema:** personalização do nome da loja e das informações de conexão com o banco de dados.

---

## Tecnologias utilizadas

- **Java 8:** linguagem principal de desenvolvimento.
- **Java Swing:** construção da interface gráfica.
- **MySQL:** armazenamento e gerenciamento dos dados.
- **FlatLaf:** personalização visual da interface.
- **Apache Ant:** automação do processo de build.
- **Apache NetBeans:** ambiente de desenvolvimento integrado (IDE).

---

## Como executar o projeto

### 1. Preparação
- Instale o **Java 8 (JDK)**.
- Instale e configure o **MySQL Server**.
- Abra o projeto no **Apache NetBeans**.

### 2. Configuração do banco de dados
Crie um banco de dados MySQL para o sistema e certifique-se de que o usuário configurado tenha as permissões necessárias para acessá-lo.

### 3. Execução
Execute a classe principal `Main` pelo Apache NetBeans.

Na primeira inicialização, o sistema criará automaticamente o diretório e o arquivo de configuração:
```text
%USERPROFILE%\SystemPDV-data\application.properties
```

Edite esse arquivo e configure as seguintes propriedades:

```properties
store.name=SystemPDV
default.system=CAIXA
db.url=jdbc:mysql://localhost:3306/seu_banco
db.root=seu_usuario
db.password=sua_senha
```

#### Descrição das propriedades:

| Propriedade | Descrição |
| :--- | :--- |
| `store.name` | Nome da loja exibido pelo sistema. |
| `default.system` | Define o modo de inicialização (`CAIXA` ou `ADMINISTRATIVO`). |
| `db.url` | URL de conexão com o banco de dados MySQL. |
| `db.root` | Usuário utilizado na conexão com o banco. |
| `db.password` | Senha do usuário do banco de dados. |

### 4. Modos de operação
O SystemPDV disponibiliza dois modos de operação:
- **ADMINISTRATIVO:** destinado aos usuários com permissões de administrador, permitindo o acesso às funcionalidades administrativas.
- **CAIXA:** destinado à operação de vendas, permitindo autenticação de usuários sem a necessidade de privilégios administrativos.

---

## Estrutura do banco de dados

O banco de dados utiliza quatro tabelas principais para armazenar as informações do sistema: usuários, produtos, vendas e itens das vendas.

### 1. Tabela `users`
Armazena os dados dos usuários e suas respectivas permissões de acesso.

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);
```

### 2. Tabela `products`
Armazena as informações dos produtos comercializados, incluindo preço e quantidade disponível em estoque.

```sql
CREATE TABLE IF NOT EXISTS products (
    sku VARCHAR(50) NOT NULL,
    name VARCHAR(150) NOT NULL,
    category VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    PRIMARY KEY (sku)
);
```

### 3. Tabela `sales`
Registra as vendas realizadas, associando cada operação ao usuário responsável.

```sql
CREATE TABLE sales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    total_amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    client VARCHAR(14),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### 4. Tabela `sale_items`
Armazena os produtos vinculados a cada venda, incluindo quantidade, preço unitário e subtotal.

```sql
CREATE TABLE sale_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sale_id INT NOT NULL,
    product_sku VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (sale_id) REFERENCES sales(id) ON DELETE CASCADE,
    FOREIGN KEY (product_sku) REFERENCES products(sku)
);
```

### 5. Trigger de atualização de estoque
O sistema utiliza uma trigger para atualizar automaticamente o estoque após a inserção de um item em uma venda. A operação verifica se existe quantidade suficiente do produto. Caso contrário, a operação é interrompida com uma mensagem de erro.

```sql
DELIMITER /

CREATE TRIGGER update_stock_after_insert_sales_item
AFTER INSERT ON sale_items
FOR EACH ROW
BEGIN
    UPDATE products
    SET stock = stock - NEW.quantity
    WHERE sku = NEW.product_sku
      AND stock >= NEW.quantity;

    IF ROW_COUNT() = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Estoque insuficiente ou produto não encontrado';
    END IF;
END; /

DELIMITER ;
```

---

## Relacionamentos entre as tabelas

A estrutura do banco de dados estabelece os seguintes relacionamentos:

- `users` → `sales`: um usuário pode estar associado a várias vendas.
- `sales` → `sale_items`: uma venda pode conter vários itens.
- `products` → `sale_items`: um produto pode estar presente em vários itens de venda.

Esses relacionamentos são garantidos por meio de chaves estrangeiras (*Foreign Keys*).

---

## Observações

- O banco de dados deve estar disponível antes da utilização do sistema.
- As credenciais de conexão devem ser configuradas corretamente no arquivo `application.properties`.
- O modo Administrativo exige um usuário com permissão de administrador.
- O controle de estoque é realizado automaticamente pela trigger após a inserção de itens de venda.

---

## Licença

Consulte o repositório para obter informações sobre os termos de uso e distribuição do projeto.