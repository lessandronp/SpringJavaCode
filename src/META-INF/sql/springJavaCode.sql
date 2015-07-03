/*TRUNCATE TABLE permissao_tela_dpto, tela, usuario, departamento;*/

/* Departamentos */
insert  into departamento (id_departamento, nome) 
select 1, 'Gerência' from departamento
where (select count(*) from departamento where nome = 'Gerência') = 0;

insert  into departamento (id_departamento, nome) 
select 1, 'TI' from departamento
where (select count(*) from departamento where nome = 'TI') = 0;

/* Usuários */

insert  into usuario (id_usuario, login, nome, senha, id_depto)
select 1,'admin','Administrador', '307446D9DEB709CC9742429AE33B40C9', 2 from usuario
where (select count(*) from usuario where login = 'admin') = 0;

insert  into usuario (id_usuario, login, nome, senha, id_depto)
select 2,'gerente','Gerente', '307446D9DEB709CC9742429AE33B40C9', 1 from usuario
where (select count(*) from usuario where login = 'gerente') = 0;
