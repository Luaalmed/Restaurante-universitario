CREATE SCHEMA restaurante_universitario;

-- Define o schema como o padrão para as operações seguintes
SET search_path TO restaurante_universitario;

-- Criação dos tipos ENUM para as tabelas dentro do schema
CREATE TYPE tipo_usuario_enum AS ENUM ('cliente', 'admin');
CREATE TYPE categoria_enum AS ENUM ('prato', 'lanche', 'bebida');
CREATE TYPE status_pedido_enum AS ENUM ('pendente', 'em_preparacao', 'pronto', 'entregue');
CREATE TYPE forma_pagamento_enum AS ENUM ('cartao', 'dinheiro', 'pix');

---

-- Tabela de Usuários (Clientes e Administradores)
CREATE TABLE usuarios (
id SERIAL PRIMARY KEY,
ra VARCHAR(20) UNIQUE,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
senha VARCHAR(255) NOT NULL,
tipo_usuario tipo_usuario_enum NOT NULL,
data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Cardápio
CREATE TABLE cardapio (
id SERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
descricao TEXT,
preco DECIMAL(10, 2) NOT NULL,
categoria categoria_enum NOT NULL,
disponivel BOOLEAN DEFAULT TRUE,
data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Pedidos
CREATE TABLE pedidos (
id SERIAL PRIMARY KEY,
id_usuario INT REFERENCES usuarios(id),
data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
status status_pedido_enum DEFAULT 'pendente',
total DECIMAL(10, 2) NOT NULL,
forma_pagamento forma_pagamento_enum NOT NULL
);
-- Tabela de Itens do Pedido
CREATE TABLE itens_pedido (
id SERIAL PRIMARY KEY,
id_pedido INT REFERENCES pedidos(id),
id_cardapio INT REFERENCES cardapio(id),
quantidade INT DEFAULT 1,
preco_unitario DECIMAL(10, 2) NOT NULL
);

-- Tabela de Avaliações do Cardápio
CREATE TABLE avaliacoes_cardapio (
id SERIAL PRIMARY KEY,
id_usuario INT REFERENCES usuarios(id),
id_cardapio INT REFERENCES cardapio(id),
nota INT CHECK (nota BETWEEN 1 AND 5),
comentario TEXT,
data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




--mudança na tabela de cardápio para gerenciamento do estoque
ALTER TABLE restaurante_universitario.cardapio
ADD COLUMN quantidade_estoque INT DEFAULT 0;

