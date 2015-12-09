CREATE TABLE IF NOT EXISTS  produto_arc(codigo int primary key, local_estoque varchar(250),nome varchar(250) not null, valor_custo decimal not null,valor_venda decimal not null,quantidade int default 0);
ALTER TABLE produto_arc ADD CONSTRAINT nome_produto_arc_unique UNIQUE(nome);

CREATE TABLE IF NOT EXISTS  movimentacao_produto_arc(
codigo bigint auto_increment primary key,
tipo_movimentacao_codigo bigint not null,
produto_codigo int not null,
data_movimentacao timestamp not null,
quantidade int not null,
nota_fiscal varchar(100),
data_recebimento_produto timestamp);

ALTER TABLE movimentacao_produto_arc ADD CONSTRAINT FK_PROD_MOV_ARC FOREIGN KEY (produto_codigo) REFERENCES produto_arc(CODIGO);
ALTER TABLE movimentacao_produto_arc ADD CONSTRAINT FK_TIPO_MOV_MOV_ARC FOREIGN KEY (tipo_movimentacao_codigo) REFERENCES tipo_movimentacao(CODIGO);

CREATE TABLE IF NOT EXISTS  inventario_arc(
codigo bigint auto_increment primary key,
data_inventario timestamp not null);

CREATE TABLE IF NOT EXISTS  inventario_produto_arc(
codigo bigint auto_increment primary key,
inventario_codigo bigint not null,
produto_codigo int not null,
quantidade int,
quantidade_estoque int,
divergencia int,
quantidade_final int);

ALTER TABLE inventario_produto_arc ADD CONSTRAINT FK_PROD_INV_ARC FOREIGN KEY (produto_codigo) REFERENCES produto_arc(CODIGO);
ALTER TABLE inventario_produto_arc ADD CONSTRAINT FK_INV_PROD_INV_ARC FOREIGN KEY (inventario_codigo) REFERENCES inventario_arc(CODIGO);
