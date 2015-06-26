TRUNCATE TABLE permissao_tela_dpto, tela, usuario, departamento;

insert  into departamento(id_departamento, nome) 
values 
(1,'Gerência'),
(2,'TI');

insert  into tela(id_tela, nome, url) 
values 
(1,'Gerência de usuários','/paginas/admin/gerenciaUsuarios.xhtml'),
(2,'Gerência de telas','/paginas/admin/gerenciaTelas.xhtml');

insert  into usuario(id_usuario, login, nome, senha, id_depto) 
values 
(1,'admin','Administrador', '123', 2),
(2,'gerente','Gerente', 123, 1);

