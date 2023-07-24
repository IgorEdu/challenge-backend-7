create table depoimentos(

  id bigint not null auto_increment,
  nome varchar(100) not null,
  depoimento varchar(500) not null,
  nome_imagem varchar(50) not null,
  ativo tinyint,

  primary key(id)
  
);