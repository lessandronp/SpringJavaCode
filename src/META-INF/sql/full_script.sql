/*
Navicat PGSQL Data Transfer

Source Server         : default
Source Server Version : 90104
Source Host           : localhost:5432
Source Database       : quickstart
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90104
File Encoding         : 65001

Date: 2015-07-03 19:04:15
*/


-- ----------------------------
-- Sequence structure for departamento_seq
-- ----------------------------
--DROP SEQUENCE "public"."departamento_seq";
CREATE SEQUENCE "public"."departamento_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 20
 CACHE 1;
SELECT setval('"public"."departamento_seq"', 20, true);

-- ----------------------------
-- Sequence structure for hibernate_sequence
-- ----------------------------
--DROP SEQUENCE "public"."hibernate_sequence";
CREATE SEQUENCE "public"."hibernate_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

-- ----------------------------
-- Sequence structure for permissao_tela_dpto_seq
-- ----------------------------
--DROP SEQUENCE "public"."permissao_tela_dpto_seq";
CREATE SEQUENCE "public"."permissao_tela_dpto_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 25
 CACHE 1;
SELECT setval('"public"."permissao_tela_dpto_seq"', 25, true);

-- ----------------------------
-- Sequence structure for tela_seq
-- ----------------------------
--DROP SEQUENCE "public"."tela_seq";
CREATE SEQUENCE "public"."tela_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 20
 CACHE 1;
SELECT setval('"public"."tela_seq"', 20, true);

-- ----------------------------
-- Sequence structure for usuario_seq
-- ----------------------------
--DROP SEQUENCE "public"."usuario_seq";
CREATE SEQUENCE "public"."usuario_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 18
 CACHE 1;
SELECT setval('"public"."usuario_seq"', 18, true);

-- ----------------------------
-- Table structure for departamento
-- ----------------------------
--DROP TABLE IF EXISTS "public"."departamento";
CREATE TABLE "public"."departamento" (
"id_departamento" int8 NOT NULL,
"nome" varchar(50) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of departamento
-- ----------------------------
INSERT INTO "public"."departamento" VALUES ('1', 'Gerência');
INSERT INTO "public"."departamento" VALUES ('2', 'TI');
INSERT INTO "public"."departamento" VALUES ('6', 'Administrador');

-- ----------------------------
-- Table structure for permissao_tela_dpto
-- ----------------------------
--DROP TABLE IF EXISTS "public"."permissao_tela_dpto";
CREATE TABLE "public"."permissao_tela_dpto" (
"id_permissao_tela_dpto" int8 NOT NULL,
"permite_editar" bool NOT NULL,
"permite_excluir" bool NOT NULL,
"permite_incluir" bool NOT NULL,
"permite_visualizar" bool NOT NULL,
"id_depto" int8 NOT NULL,
"id_tela" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of permissao_tela_dpto
-- ----------------------------
INSERT INTO "public"."permissao_tela_dpto" VALUES ('7', 'f', 'f', 'f', 't', '1', '4');
INSERT INTO "public"."permissao_tela_dpto" VALUES ('8', 't', 't', 't', 't', '6', '4');
INSERT INTO "public"."permissao_tela_dpto" VALUES ('23', 't', 't', 't', 't', '6', '19');
INSERT INTO "public"."permissao_tela_dpto" VALUES ('24', 't', 't', 't', 't', '6', '3');
INSERT INTO "public"."permissao_tela_dpto" VALUES ('25', 't', 't', 't', 't', '6', '20');

-- ----------------------------
-- Table structure for tela
-- ----------------------------
--DROP TABLE IF EXISTS "public"."tela";
CREATE TABLE "public"."tela" (
"id_tela" int8 NOT NULL,
"nome" varchar(50) COLLATE "default" NOT NULL,
"codigo" varchar(30) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of tela
-- ----------------------------
INSERT INTO "public"."tela" VALUES ('3', 'Gerência de Permissão da Tela por Departamentos', 'gerenciaPermissaoTelaDeptos');
INSERT INTO "public"."tela" VALUES ('4', 'Gerência de telas', 'gerenciaTelas');
INSERT INTO "public"."tela" VALUES ('19', 'Gerência de Usuários', 'gerenciaUsuarios');
INSERT INTO "public"."tela" VALUES ('20', 'Gerência de Departamentos', 'gerenciaDepartamentos');

-- ----------------------------
-- Table structure for usuario
-- ----------------------------
--DROP TABLE IF EXISTS "public"."usuario";
CREATE TABLE "public"."usuario" (
"id_usuario" int8 NOT NULL,
"login" varchar(20) COLLATE "default" NOT NULL,
"nome" varchar(255) COLLATE "default" NOT NULL,
"senha" varchar(32) COLLATE "default" NOT NULL,
"id_depto" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of usuario
-- ----------------------------
INSERT INTO "public"."usuario" VALUES ('1', 'admin', 'Administrador', '307446D9DEB709CC9742429AE33B40C9', '6');
INSERT INTO "public"."usuario" VALUES ('2', 'gerente', 'Gerente', '307446D9DEB709CC9742429AE33B40C9', '1');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table departamento
-- ----------------------------
ALTER TABLE "public"."departamento" ADD PRIMARY KEY ("id_departamento");

-- ----------------------------
-- Primary Key structure for table permissao_tela_dpto
-- ----------------------------
ALTER TABLE "public"."permissao_tela_dpto" ADD PRIMARY KEY ("id_permissao_tela_dpto");

-- ----------------------------
-- Primary Key structure for table tela
-- ----------------------------
ALTER TABLE "public"."tela" ADD PRIMARY KEY ("id_tela");

-- ----------------------------
-- Primary Key structure for table usuario
-- ----------------------------
ALTER TABLE "public"."usuario" ADD PRIMARY KEY ("id_usuario");

-- ----------------------------
-- Foreign Key structure for table "public"."permissao_tela_dpto"
-- ----------------------------
ALTER TABLE "public"."permissao_tela_dpto" ADD FOREIGN KEY ("id_tela") REFERENCES "public"."tela" ("id_tela") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."permissao_tela_dpto" ADD FOREIGN KEY ("id_depto") REFERENCES "public"."departamento" ("id_departamento") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."permissao_tela_dpto" ADD FOREIGN KEY ("id_depto") REFERENCES "public"."departamento" ("id_departamento") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."permissao_tela_dpto" ADD FOREIGN KEY ("id_tela") REFERENCES "public"."tela" ("id_tela") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Key structure for table "public"."usuario"
-- ----------------------------
ALTER TABLE "public"."usuario" ADD FOREIGN KEY ("id_depto") REFERENCES "public"."departamento" ("id_departamento") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "public"."usuario" ADD FOREIGN KEY ("id_depto") REFERENCES "public"."departamento" ("id_departamento") ON DELETE NO ACTION ON UPDATE NO ACTION;
