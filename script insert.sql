INSERT INTO ge.t_listado(int_cod_num, txt_nom_corto, var_cod_version)
	VALUES (1, 'Tipo', '1'), (2, 'Estados', '1'), (3, 'Tipo uso', '1'), (4, 'Tipo actividad', '1'), (5, 'Tipo beneficiario', '1')
	, (6, 'Tipo recurso', '1'), (7, 'Tipo compromiso no monetario', '1'), (8, 'Tipo norma conducta', '1'), (9, 'Tipo compromiso monetario', '1'), 
	(10, 'Periodicidad', '1'), (11, 'Forma pago', '1'), (12, 'Actividades', '1'), (13, 'Recursos', '1');


-- Insert Tipo actividad
-- Insert Actividad
do $$ 
declare
	tipoActividadId INT := 0;
begin 

	INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
		VALUES (4, 'Alojamiento para visitante');
		
	SELECT last_value into tipoActividadId FROM ge.t_listado_detalle_srl_id_seq;

	INSERT INTO ge.t_listado_detalle(int_id_listado_detalle, txt_nom_corto)
		VALUES (tipoActividadId, 'Hospedaje');
	
	INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
		VALUES  (4, 'Transporte de pasajeros');

	SELECT last_value into tipoActividadId FROM ge.t_listado_detalle_srl_id_seq;

	INSERT INTO ge.t_listado_detalle(int_id_listado_detalle, txt_nom_corto)
		VALUES (tipoActividadId, 'Cuatrimoto'), (tipoActividadId, 'Botes');

	INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
		VALUES  (4, 'Subsistencia');

	SELECT last_value into tipoActividadId FROM ge.t_listado_detalle_srl_id_seq;

	INSERT INTO ge.t_listado_detalle(int_id_listado_detalle, txt_nom_corto)
		VALUES (tipoActividadId, 'Pesca'), (tipoActividadId, 'Recolección');

	INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
		VALUES  (4, 'Aprovechamiento');

	SELECT last_value into tipoActividadId FROM ge.t_listado_detalle_srl_id_seq;

	INSERT INTO ge.t_listado_detalle(int_id_listado_detalle, txt_nom_corto)
		VALUES (tipoActividadId, 'Caza');
end $$;


-- Insert Tipo recurso
-- Insert Recursos
do $$
declare
	tipoRecursodId INT := 0;
begin
	INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
		VALUES (6, 'Paisaje');	

	SELECT last_value into tipoRecursodId FROM ge.t_listado_detalle_srl_id_seq;

	INSERT INTO ge.t_listado_detalle(int_id_listado_detalle, txt_nom_corto)
		VALUES (tipoRecursodId, 'Bosque');
		
		
	INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
		VALUES (6, 'Animal');

	SELECT last_value into tipoRecursodId FROM ge.t_listado_detalle_srl_id_seq;

	INSERT INTO ge.t_listado_detalle(int_id_listado_detalle, txt_nom_corto)
		VALUES (tipoRecursodId, 'Taricaya'), (tipoRecursodId, 'Castaña');
		
		
	INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
		VALUES (6, 'Vegetal');	

	SELECT last_value into tipoRecursodId FROM ge.t_listado_detalle_srl_id_seq;

	INSERT INTO ge.t_listado_detalle(int_id_listado_detalle, txt_nom_corto)
		VALUES (tipoRecursodId, 'Eucalipto');

end $$;
 

-- ######################## Start Insert Listado ########################

-- Insert Tipo
INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
	VALUES (1, 'Recursos'), (1, 'Turismo');

-- Insert Estado
INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
	VALUES (2, 'Pendiente'), (2, 'Aprobado'), (2, 'Desactivado');
	
-- Insert Tipo uso
INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
	VALUES (3, 'Acceso exclusivo'), (3, 'Libre acceso');

-- Insert Tipo compromiso no monetario
INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
	VALUES (7, 'Administrativo'), (7, 'Social'), (7, 'Ambiental');

-- Insert tipo norma conducta
INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
	VALUES (8, 'Tipo');
	
-- Insert Tipo compromiso monetario
INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
	VALUES (9, 'Administrativo'), (9, 'Social'), (9, 'Ambiental');
	
-- Insert Periodicidad
INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
	VALUES (10, 'Permanente'), (10, 'Anual');
	
-- Insert Forma pago
INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto)
	VALUES (11, 'Post'), (11, 'Anticipado'), (11, 'Cumplimiento');
	
-- Insert Tipo beneficiario
INSERT INTO ge.t_listado_detalle(int_id_tipo_listado, txt_nom_corto, txt_nom_largo)
	VALUES (5, 'Natural', '8'), (5, 'Juridica', '11');

-- ######################## End Insert Listado ########################

--INSERT INTO od.t_config_anp(int_id_anp, txt_nom, var_cod_version)
--	VALUES (1, 'RN Islas Ballestas', '1'), (2, 'RN Paracas', '1');


insert into od.t_anp (txt_nom) 
	VALUES ('RN Islas Ballestas'), ('RN Paracas'), ('RN Huascarán'), ('RN Titicaca'), ('RN Tambopata');
	
insert into od.t_sector (txt_nom, txt_cod, txt_anp_cod) 
	VALUES ('Sector A', 'RN101', '1'),('Sector B', 'RN102', '1'),('Sector Norte', 'RN201', '2'),('Sector Sur', 'RN202', '2'),
			('Sector 1', 'RN301', '3'), ('Sector 2', 'RN302', '3'),('Sector A', 'RN401', '4'), ('Sector B', 'RN402', '4'), 
			('Sector C', 'RN403', '4'), ('Sector Oeste', 'RN501', '5'),('Sector Este', 'RN502', '5');

INSERT INTO od.t_modalidad (var_cod_modalidad,dte_fec_fin,bol_flg_activo,txt_base_legal_crea,txt_base_legal_ruta,txt_nom_largo,txt_nom_corto,txt_nom_corto_titulo,dte_fec_inicio,int_id_grupo) VALUES
	 ('code 2','2023-05-30',true,'prueba.txt','backend/pe.sernanp.ws_api','Descripcion','nombre','titulo corto','2023-05-01',1),
	 ('code 3','2023-05-30',true,'prueba.txt','backend/pe.sernanp.ws_api','Descripcion','nombre','titulo corto','2023-05-01',1),
	 ('code 4','2023-05-30',true,'prueba.txt','backend/pe.sernanp.ws_api','Descripcion','nombre','titulo corto','2023-05-01',1),
	 ('code 2123','2023-05-30',true,'prueba.txt','backend/pe.sernanp.ws_api','Descripcion','nombre','titulo corto','2023-05-01',1),
	 ('code 326','2023-05-30',true,'prueba.txt','backend/pe.sernanp.ws_api','Descripcion','nombre','titulo corto','2023-05-01',1),
	 ('code 3455','2023-05-30',true,'test.txt','C:\fakepath\test.txt','description','short name','short title','2023-05-01',3),
	 ('code 363','2023-05-30',true,'test.txt','C:\fakepath\test.txt','description','short name','short title','2023-05-01',1),
	 ('code 859','2023-05-30',true,'test.txt','C:\fakepath\test.txt','description','short name','short title','2023-05-01',1),
	 ('code 5647','2023-05-30',true,'test.txt','C:\fakepath\test.txt','description','short name','short title','2023-05-01',1),
	 ('code 858','2023-05-30',true,'test.txt','C:\fakepath\test.txt','description','short name','short title','2023-05-01',1);
	