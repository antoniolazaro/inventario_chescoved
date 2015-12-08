CREATE TABLE IF NOT EXISTS  produto(codigo int primary key, local_estoque varchar(250),nome varchar(250) not null, valor_custo decimal not null,valor_venda decimal not null,quantidade int default 0);
ALTER TABLE produto ADD CONSTRAINT nome_produto_unique UNIQUE(nome);

CREATE TABLE IF NOT EXISTS  tipo_movimentacao(codigo int primary key,nome varchar(50) not null);
ALTER TABLE tipo_movimentacao ADD CONSTRAINT nome_tipo_movimentacao_unique UNIQUE(nome);

CREATE TABLE IF NOT EXISTS  movimentacao_produto(
codigo bigint auto_increment primary key,
tipo_movimentacao_codigo bigint not null,
produto_codigo int not null,
data_movimentacao timestamp not null,
quantidade int not null,
nota_fiscal varchar(100),
data_recebimento_produto timestamp);

ALTER TABLE movimentacao_produto ADD CONSTRAINT FK_PROD_MOV FOREIGN KEY (produto_codigo) REFERENCES PRODUTO(CODIGO);
ALTER TABLE movimentacao_produto ADD CONSTRAINT FK_TIPO_MOV_MOV FOREIGN KEY (tipo_movimentacao_codigo) REFERENCES tipo_movimentacao(CODIGO);

CREATE TABLE IF NOT EXISTS  inventario(
codigo bigint auto_increment primary key,
data_inventario timestamp not null);

CREATE TABLE IF NOT EXISTS  inventario_produto(
codigo bigint auto_increment primary key,
inventario_codigo bigint not null,
produto_codigo int not null,
quantidade int,
quantidade_estoque int,
divergencia int,
quantidade_final int);

ALTER TABLE inventario_produto ADD CONSTRAINT FK_PROD_INV FOREIGN KEY (produto_codigo) REFERENCES PRODUTO(CODIGO);
ALTER TABLE inventario_produto ADD CONSTRAINT FK_INV_PROD_INV FOREIGN KEY (inventario_codigo) REFERENCES inventario(CODIGO);

CREATE TABLE IF NOT EXISTS usuario(codigo bigint auto_increment primary key,login varchar(50),senha varchar(50));
ALTER TABLE usuario ADD CONSTRAINT login_unique UNIQUE(login);