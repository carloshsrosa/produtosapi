  create table produto (
    id numeric(18) not null primary key,
    nome varchar(50) not null,
    descricao varchar(300),
    preco numeric(18,2)
  );