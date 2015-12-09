CREATE TABLE IF NOT EXISTS  produto_mi(codigo int primary key, local_estoque varchar(250),nome varchar(250) not null, valor_custo decimal not null,valor_venda decimal,quantidade int default 0);
ALTER TABLE produto ADD CONSTRAINT nome_produto_mi_unique UNIQUE(nome);

CREATE TABLE IF NOT EXISTS  movimentacao_produto_mi(
codigo bigint auto_increment primary key,
tipo_movimentacao_codigo bigint not null,
produto_codigo int not null,
data_movimentacao timestamp not null,
quantidade int not null,
nota_fiscal varchar(100),
data_recebimento_produto timestamp);

ALTER TABLE movimentacao_produto_mi ADD CONSTRAINT FK_PROD_MOV_mi FOREIGN KEY (produto_codigo) REFERENCES produto_mi(CODIGO);
ALTER TABLE movimentacao_produto_mi ADD CONSTRAINT FK_TIPO_MOV_MOV_mi FOREIGN KEY (tipo_movimentacao_codigo) REFERENCES tipo_movimentacao(CODIGO);

CREATE TABLE IF NOT EXISTS  inventario_mi(
codigo bigint auto_increment primary key,
data_inventario timestamp not null);

CREATE TABLE IF NOT EXISTS  inventario_produto_mi(
codigo bigint auto_increment primary key,
inventario_codigo bigint not null,
produto_codigo int not null,
quantidade int,
quantidade_estoque int,
divergencia int,
quantidade_final int);

ALTER TABLE inventario_produto_mi ADD CONSTRAINT FK_PROD_INV_mi FOREIGN KEY (produto_codigo) REFERENCES produto_mi(CODIGO);
ALTER TABLE inventario_produto_mi ADD CONSTRAINT FK_INV_PROD_INV_mi FOREIGN KEY (inventario_codigo) REFERENCES inventario_mi(CODIGO);
