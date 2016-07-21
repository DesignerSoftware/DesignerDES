  CREATE ROLE "PRODUCCION" LOGIN
  ENCRYPTED PASSWORD 'PRODUCCION'
  SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;
  
  
  --CAMBIAR AL USUARIO PRODUCCION
  
  CREATE ROLE "KIOSKO" LOGIN
  ENCRYPTED PASSWORD 'KSK'
  NOSUPERUSER NOINHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;

  -- EJECUTAR CON SUPER USUARIO
  CREATE ROLE "ROLKIOSKO" WITH PASSWORD 'RLKSK'
  NOSUPERUSER NOINHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
  
  
  GRANT "ROLKIOSKO" TO "KIOSKO";
    
  CREATE DATABASE "KiosKoDesigner"
  WITH OWNER = "PRODUCCION" 
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'Spanish_Colombia.1252'
       LC_CTYPE = 'Spanish_Colombia.1252'
       CONNECTION LIMIT = -1;
  
  --CAMBIAR A LA BASE DE DATOS CREADA
  
  --CREAR TABLAS
  
  -- Table: public.ciudades

	CREATE TABLE public.ciudades
	(
	  secuencia numeric(38,0) NOT NULL,
	  codigo smallint,
	  codigoalternativo character varying(255),
	  nombre character varying(255),
	  CONSTRAINT ciudades_pkey PRIMARY KEY (secuencia)
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.ciudades
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.ciudades TO "PRODUCCION";
	
	-- Table: public.tiposdocumentos

	CREATE TABLE public.tiposdocumentos
	(
	  secuencia numeric(38,0) NOT NULL,
	  nombrecorto character varying(255),
	  nombrelargo character varying(255),
	  CONSTRAINT tiposdocumentos_pkey PRIMARY KEY (secuencia)
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.tiposdocumentos
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.tiposdocumentos TO "PRODUCCION";		
	
	-- Table: public.preguntaskioskos

	CREATE TABLE public.preguntaskioskos
	(
	  secuencia numeric(38,0) NOT NULL,
	  codigo bigint,
	  pregunta character varying(255),
	  CONSTRAINT preguntaskioskos_pkey PRIMARY KEY (secuencia)
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.preguntaskioskos
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.preguntaskioskos TO "PRODUCCION";	
	
	
	-- Table: public.personas

	CREATE TABLE public.personas
	(
	  secuencia bigint NOT NULL,
	  certificadojudicial character varying(255),
	  claselibretamilitar smallint,
	  digitoverificaciondocumento smallint,
	  distritomilitar smallint,
	  email character varying(255),
	  factorrh character varying(255),
	  fechaexpmatricula date,
	  fechafallecimiento date,
	  fechanacimiento date,
	  fechavencimientocertificado date,
	  gruposanguineo character varying(255),
	  nombre character varying(255),
	  numerodocumento bigint,
	  numerolibretamilitar bigint,
	  numeromatriculaprof character varying(255),
	  observacion character varying(255),
	  pathfoto character varying(255),
	  placavehiculo character varying(255),
	  primerapellido character varying(255),
	  segundoapellido character varying(255),
	  segundonombre character varying(255),
	  sexo character varying(255),
	  vehiculoempresa character varying(255),
	  viviendapropia character varying(255),
	  ciudaddocumento numeric(38,0),
	  ciudadnacimiento numeric(38,0),
	  tipodocumento numeric(38,0),
	  CONSTRAINT personas_pkey PRIMARY KEY (secuencia),
	  CONSTRAINT fk_personas_ciudaddocumento FOREIGN KEY (ciudaddocumento)
		  REFERENCES public.ciudades (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_personas_ciudadnacimiento FOREIGN KEY (ciudadnacimiento)
		  REFERENCES public.ciudades (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_personas_tipodocumento FOREIGN KEY (tipodocumento)
		  REFERENCES public.tiposdocumentos (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.personas
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.personas TO "PRODUCCION";
	
	-- Table: public.empresas

	CREATE TABLE public.empresas
	(
	  secuencia bigint NOT NULL,
	  codigo smallint,
	  codigoalternativo character varying(255),
	  logo character varying(255),
	  manualadministrativo text,
	  nit bigint,
	  nombre character varying(255),
	  reglamento text,
	  CONSTRAINT empresas_pkey PRIMARY KEY (secuencia)
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.empresas
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.empresas TO "PRODUCCION";
	
	
	-- Table: public.empleados

	CREATE TABLE public.empleados
	(
	  secuencia numeric(38,0) NOT NULL,
	  codigoalternativo character varying(255),
	  codigoempleado bigint,
	  fechacreacion timestamp without time zone,
	  usuariobd character varying(255),
	  empresa bigint,
	  persona bigint,
	  CONSTRAINT empleados_pkey PRIMARY KEY (secuencia),
	  CONSTRAINT fk_empleados_empresa FOREIGN KEY (empresa)
		  REFERENCES public.empresas (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_empleados_persona FOREIGN KEY (persona)
		  REFERENCES public.personas (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.empleados
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.empleados TO "PRODUCCION";
	
-- Table: public.conexioneskioskos

	CREATE TABLE public.conexioneskioskos
	(
	  secuencia numeric(38,0) NOT NULL,
	  activo character varying(255),
	  dirigidoa character varying(255),
	  enviocorreo character varying(255),
	  fechadesde timestamp without time zone,
	  fechahasta timestamp without time zone,
	  observaciones character varying(255),
	  pwd text,
	  respuesta1 character varying(255),
	  respuesta2 character varying(255),
	  seudonimo character varying(255),
	  ultimaconexion timestamp without time zone,
	  pregunta1 numeric(38,0),
	  pregunta2 numeric(38,0),
	  empleado numeric(38,0),
	  CONSTRAINT conexioneskioskos_pkey PRIMARY KEY (secuencia),
	  CONSTRAINT fk_conexioneskioskos_empleado FOREIGN KEY (empleado)
		  REFERENCES public.empleados (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_conexioneskioskos_pregunta1 FOREIGN KEY (pregunta1)
		  REFERENCES public.preguntaskioskos (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_conexioneskioskos_pregunta2 FOREIGN KEY (pregunta2)
		  REFERENCES public.preguntaskioskos (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.conexioneskioskos
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.conexioneskioskos TO "PRODUCCION";
	
	-- Table: public.conficorreokiosko

	CREATE TABLE public.conficorreokiosko
	(
	  secuencia numeric(38,0) NOT NULL,
	  autenticado character varying(255),
	  clave character varying(255),
	  puerto character varying(255),
	  remitente character varying(255),
	  servidorsmtp character varying(255),
	  starttls character varying(255),
	  usarssl character varying(255),
	  empresa bigint,
	  CONSTRAINT conficorreokiosko_pkey PRIMARY KEY (secuencia),
	  CONSTRAINT fk_conficorreokiosko_empresa FOREIGN KEY (empresa)
		  REFERENCES public.empresas (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.conficorreokiosko
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.conficorreokiosko TO "PRODUCCION";
	


	-- Table: public.generaleskiosko

	CREATE TABLE public.generaleskiosko
	(
	  secuencia numeric(38,0) NOT NULL,
	  pathfoto character varying(255),
	  pathreportes character varying(255),
	  ubicareportes character varying(255),
	  CONSTRAINT generaleskiosko_pkey PRIMARY KEY (secuencia)
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.generaleskiosko
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.generaleskiosko TO "PRODUCCION";

	
	-- Table: public.opcioneskioskos

	CREATE TABLE public.opcioneskioskos
	(
	  secuencia bigint NOT NULL,
	  ayuda character varying(255),
	  clase character varying(255),
	  codigo character varying(255),
	  descripcion character varying(255),
	  extension character varying(255),
	  nombrearchivo character varying(255),
	  tiporeporte character varying(255),
	  empresa bigint,
	  opcionkioskopadre bigint,
	  CONSTRAINT opcioneskioskos_pkey PRIMARY KEY (secuencia),
	  CONSTRAINT fk_opcioneskioskos_empresa FOREIGN KEY (empresa)
		  REFERENCES public.empresas (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION,
	  CONSTRAINT fk_opcioneskioskos_opcionkioskopadre FOREIGN KEY (opcionkioskopadre)
		  REFERENCES public.opcioneskioskos (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.opcioneskioskos
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.opcioneskioskos TO "PRODUCCION";
	
	-- Table: public.parametrizaclave

	CREATE TABLE public.parametrizaclave
	(
	  secuencia numeric(38,0) NOT NULL,
	  formato character varying(255),
	  mensajevalidacion character varying(255),
	  empresa bigint,
	  CONSTRAINT parametrizaclave_pkey PRIMARY KEY (secuencia),
	  CONSTRAINT fk_parametrizaclave_empresa FOREIGN KEY (empresa)
		  REFERENCES public.empresas (secuencia) MATCH SIMPLE
		  ON UPDATE NO ACTION ON DELETE NO ACTION
	)
	WITH (
	  OIDS=FALSE
	);
	ALTER TABLE public.parametrizaclave
	  OWNER TO "PRODUCCION";
	GRANT ALL ON TABLE public.parametrizaclave TO "PRODUCCION";		
	
	
  
  CREATE EXTENSION pgcrypto SCHEMA "public"; --EJECUTAR CON SUPER USUARIO
  
  GRANT SELECT ON ciudades TO "ROLKIOSKO";
  GRANT SELECT, UPDATE, INSERT ON conexioneskioskos TO "ROLKIOSKO";
  GRANT SELECT ON conficorreokiosko TO "ROLKIOSKO";
  GRANT SELECT ON empleados TO "ROLKIOSKO";
  GRANT SELECT ON empresas TO "ROLKIOSKO";
  GRANT SELECT ON generaleskiosko TO "ROLKIOSKO";
  GRANT SELECT ON opcioneskioskos TO "ROLKIOSKO";
  GRANT SELECT ON parametrizaclave TO "ROLKIOSKO";
  GRANT SELECT ON personas TO "ROLKIOSKO";
  GRANT SELECT ON preguntaskioskos TO "ROLKIOSKO";
  GRANT SELECT ON tiposdocumentos TO "ROLKIOSKO";

  --SELECT SESSION_USER, CURRENT_USER;

  CREATE SEQUENCE SECUENCIA START 1;
  GRANT USAGE ON SEQUENCE SECUENCIA TO "ROLKIOSKO";
  
	--INSERT
	
   CREATE TRIGGER INSERT_CONEXIONESKIOSKOS
    BEFORE INSERT ON CONEXIONESKIOSKOS
    FOR EACH ROW
    EXECUTE PROCEDURE INSERTAR_ENCRIPTAR_CONEXIONESKIOSKO();


  CREATE FUNCTION INSERTAR_ENCRIPTAR_CONEXIONESKIOSKO()
	RETURNS trigger AS '
	BEGIN
	  NEW.SECUENCIA := nextval(''SECUENCIA'');
	  NEW.PWD := crypt(NEW.PWD, gen_salt(''md5''));
	  NEW.RESPUESTA1 := crypt(NEW.RESPUESTA1, gen_salt(''md5''));
	  NEW.RESPUESTA2 := crypt(NEW.RESPUESTA2, gen_salt(''md5''));
	  RETURN NEW;
	END' LANGUAGE 'plpgsql'
	
	
	--UPDATE
	
	CREATE TRIGGER UPDATE_CONEXIONESKIOSKOS
    BEFORE UPDATE ON CONEXIONESKIOSKOS
    FOR EACH ROW
    EXECUTE PROCEDURE UPDATE_ENCRIPTAR_CONEXIONESKIOSKO();


	CREATE FUNCTION UPDATE_ENCRIPTAR_CONEXIONESKIOSKO()
	RETURNS trigger AS '
	BEGIN
	  IF NEW.PWD != OLD.PWD THEN
		NEW.PWD := crypt(NEW.PWD, gen_salt(''md5''));
	  END IF;
	  IF NEW.RESPUESTA1 != OLD.RESPUESTA1 THEN
		NEW.RESPUESTA1 := crypt(NEW.RESPUESTA1, gen_salt(''md5''));
	  END IF;
	  IF NEW.RESPUESTA2 != OLD.RESPUESTA2 THEN
		NEW.RESPUESTA2 := crypt(NEW.RESPUESTA2, gen_salt(''md5''));
	  END IF;
	  RETURN NEW;
	END' LANGUAGE 'plpgsql'

	
	