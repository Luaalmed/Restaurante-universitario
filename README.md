# 🥗 Restaurante Universitário - (Nome)

## 🚀 Sobre o Projeto

Este projeto é um sistema de **Restaurante Universitário** desenvolvido com base em conceitos de **Engenharia de Software** e metodologias ágeis. O objetivo é modernizar e otimizar a experiência de alimentação na universidade, oferecendo um site completo para alunos e um painel de gerenciamento robusto para administradores.

---

### **Objetivos do Semestre**

* **Aplicação de Conceitos:** Integrar na prática os fundamentos da Engenharia de Software.
* **MVP Funcional:** Desenvolver um Produto Mínimo Viável (MVP) que atenda às funcionalidades essenciais do sistema.
* **Produção de Artefatos:** Criar documentação completa, incluindo requisitos, modelagem, arquitetura e planos de teste.
* **Processos Ágeis:** Adotar metodologias ágeis para garantir flexibilidade e entregas contínuas.
* **Divisão de Responsabilidades:** Assegurar a participação ativa e equitativa de todos os membros da equipe.

---

### **Equipe de Desenvolvimento**

* **Ester Pereira dos Santos Nascimento**
* **Gabrielly Thamirys Aparecida Bomfim**
* **Luana De Almeida Ferreira**
* **Victor Pietoso Frison**

---

## ✨ Funcionalidades Principais

Nosso sistema abrange uma ampla gama de funcionalidades, divididas entre a experiência do **aluno** e a do **administrador**.

### **Para os Alunos (Usuários)**

* **Login e Cadastro:** Login seguro via **RA** e senha. O sistema também permite que administradores acessem via e-mail.
* **Visualização e Pesquisa:**
    * **Visualizar Cardápio:** Navegação intuitiva por pratos, lanches e bebidas.

* **Pedidos e Pagamentos:**
    * **Fazer Pedido Online:** Selecione itens e envie o pedido diretamente para a cozinha.
    * **Formas de Pagamento:** Opções seguras com **cartão**, **dinheiro** ou **PIX**.
* **Monitoramento e Suporte:**
    * **Acompanhar Pedido:** Acompanhe o status do seu pedido em tempo real.
    * **Cancelar Pedido:** Solicite o cancelamento de acordo com as regras do sistema.

### **Para os Administradores**

* **Cadastro e Gerenciamento:**
    * **Gerenciamento do Cardápio:** Adicione, atualize e remova pratos, lanches e bebidas.
* **Operações do Restaurante:**
    * **Gerenciar Estoque:** Baixa de estoque por item vendido (ex: um croissant) em vez de ingredientes.
---

### **Tecnologias Utilizadas**

* **Banco de Dados:** PostgreSQL
* **Backend: Java**
* **Front-end: Jframe**


---

## 🚀 Guia de Execução Local do Projeto

Este guia detalha os passos necessários para configurar e rodar o projeto **Restaurante Universitário** em sua máquina para fins de avaliação ou desenvolvimento local.

### **1. Pré-Requisitos (O que você precisa ter)**

Certifique-se de que os seguintes aplicativos e ferramentas estão instalados em sua máquina:

| Software | Função | Onde Obter |
| :--- | :--- | :--- |
| **NetBeans IDE** | Ambiente de Desenvolvimento Java. | *https://netbeans.apache.org/front/main/index.html* |
| **Java Development Kit (JDK)** | Versão necessária para rodar o código (Recomendado JDK 17+). | *https://www.oracle.com/br/java/technologies/downloads/* |
| **PostgreSQL** | Servidor de Banco de Dados. | *https://www.postgresql.org/download/* |

---

### **2. Configuração do Banco de Dados (PostgreSQL)**

O primeiro passo é preparar o ambiente de dados para o sistema.

1.  **Criação do Banco de Dados:**
    * Abra o seu cliente PostgreSQL (pgAdmin ou similar).
    * Crie um novo banco de dados com o nome exato: **`restaurante_universitario`**.

2.  **Criação das Estruturas:**
    * Localize o arquivo de script SQL do projeto (ex: `restaurante_universitario.sql`).
    * Execute o conteúdo completo deste arquivo no banco de dados `restaurante_universitario`. Este script irá criar todas as tabelas, schemas e tipos de dados necessários.

---

### **3. Configuração do Projeto no NetBeans**

Agora, vamos abrir o código e garantir a comunicação com o banco de dados.

1.  **Abrir o Projeto:**
    * **Se baixado via ZIP:** Descompacte o arquivo. No NetBeans, vá em **`File`** $\rightarrow$ **`Open Project...`** e selecione a pasta raiz.
    * **Se clonado via Git:** No NetBeans, vá em **`File`** $\rightarrow$ **`Open Project...`** e selecione a pasta clonada.

2.  **Adicionar o Driver do PostgreSQL (JDBC):**
    * **Obrigatório:** O projeto precisa do conector Java-PostgreSQL.
    * No painel **`Projects`**, clique com o botão direito em **`Libraries`**.
    * Selecione **`Add JAR/Folder...`** e adicione o arquivo `.jar` do driver JDBC do PostgreSQL (ex: `postgresql-42.x.x.jar`).

3.  **Ajustar as Credenciais de Conexão:**
    * Localize e abra o arquivo **`Conexao.java`** (geralmente em `src/DAO/`).
    * Edite as constantes `USER` e `PASS` para corresponderem ao seu usuário e senha do PostgreSQL:

    ```java
    // Certifique-se de que a porta (5433) e o nome do BD estão corretos!
    private static final String URL = "jdbc:postgresql://localhost:5433/restaurante_universitario";
    private static final String USER = "seu_usuario_postgres"; // EX: "postgres"
    private static final String PASS = "sua_senha_do_postgres"; // Sua senha local
    ```

---

### **4. Execução do Aplicativo**

Com o banco de dados e o código configurados, o projeto está pronto para ser executado.

1.  No NetBeans, localize o arquivo principal do projeto: **`RestauranteUniversitario.java`** (dentro do seu pacote principal).
2.  Clique com o botão direito sobre ele e selecione **`Run File`**.

A aplicação da tela inicial deverá ser carregada.
