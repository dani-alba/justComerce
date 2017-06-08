CREATE DATABASE  IF NOT EXISTS `justcomerce` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `justcomerce`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: justcomerce
-- ------------------------------------------------------
-- Server version	5.7.16-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `detallesventa`
--

DROP TABLE IF EXISTS `detallesventa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detallesventa` (
  `idVenta` int(8) NOT NULL,
  `referencia` int(8) NOT NULL,
  `cantidad` int(5) NOT NULL,
  `precio` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idVenta`,`referencia`),
  KEY `fk_detallesVenta_ventas1_idx` (`idVenta`),
  KEY `fk_detallesVenta_productos1_idx` (`referencia`),
  CONSTRAINT `fk_detallesVenta_productos1` FOREIGN KEY (`referencia`) REFERENCES `productos` (`referencia`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_detallesVenta_ventas1` FOREIGN KEY (`idVenta`) REFERENCES `ventas` (`idVenta`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallesventa`
--

LOCK TABLES `detallesventa` WRITE;
/*!40000 ALTER TABLE `detallesventa` DISABLE KEYS */;
INSERT INTO `detallesventa` VALUES (1,2,1,35.45),(1,3,3,167.70),(1,5,5,177.00);
/*!40000 ALTER TABLE `detallesventa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historial_trabajadores`
--

DROP TABLE IF EXISTS `historial_trabajadores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `historial_trabajadores` (
  `usuario` varchar(45) NOT NULL,
  `accion` enum('Despido','Contrato') NOT NULL,
  `idTrabajador` int(6) NOT NULL,
  `dni` varchar(9) DEFAULT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historial_trabajadores`
--

LOCK TABLES `historial_trabajadores` WRITE;
/*!40000 ALTER TABLE `historial_trabajadores` DISABLE KEYS */;
INSERT INTO `historial_trabajadores` VALUES ('root@localhost','Contrato',3,'9524','2017-05-29'),('root@localhost','Contrato',4,'95247418W','2017-05-29'),('root@localhost','Contrato',5,'74737335P','2017-05-29'),('root@localhost','Despido',3,'9524','2017-05-29'),('root@localhost','Despido',5,'74737335P','2017-05-29'),('root@localhost','Despido',4,'95247418W','2017-05-29'),('root@localhost','Contrato',3,'41178237A','2017-05-29'),('root@localhost','Contrato',4,'09258176D','2017-06-02'),('root@localhost','Despido',4,'09258176D','2017-06-02'),('root@localhost','Contrato',4,'54014660B','2017-06-04'),('root@localhost','Contrato',5,'02444904G','2017-06-04'),('root@localhost','Despido',4,'54014660B','2017-06-04'),('root@localhost','Despido',3,'41178237A','2017-06-05'),('root@localhost','Despido',5,'02444904G','2017-06-05'),('root@localhost','Contrato',3,'65420532E','2017-06-05'),('root@localhost','Contrato',4,'67407053X','2017-06-05'),('root@localhost','Contrato',5,'53159722Y','2017-06-05'),('root@localhost','Contrato',6,'53014583C','2017-06-05'),('root@localhost','Despido',6,'53014583C','2017-06-05'),('root@localhost','Despido',4,'67407053X','2017-06-05'),('root@localhost','Contrato',5,'81433703H','2017-06-05'),('root@localhost','Contrato',6,'12639257K','2017-06-05'),('root@localhost','Despido',5,'81433703H','2017-06-05'),('root@localhost','Contrato',6,'29194891W','2017-06-05'),('root@localhost','Contrato',7,'40845791E','2017-06-05'),('root@localhost','Contrato',8,'70081183E','2017-06-05');
/*!40000 ALTER TABLE `historial_trabajadores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidencias`
--

DROP TABLE IF EXISTS `incidencias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incidencias` (
  `idIncidencia` int(5) NOT NULL AUTO_INCREMENT,
  `idTienda` int(5) NOT NULL,
  `idTrabajador` int(5) DEFAULT NULL,
  `tipo` enum('Retraso','Robo','Cliente','Stock','Otros') NOT NULL,
  `fecha` datetime NOT NULL,
  `descripcion` text,
  `leido` enum('Leido','No leido') NOT NULL DEFAULT 'No leido',
  PRIMARY KEY (`idIncidencia`),
  KEY `fk_idIncidencia_trabajadores1_idx` (`idTrabajador`),
  KEY `fk_Incidencias_tiendas1_idx` (`idTienda`),
  CONSTRAINT `fk_Incidencias_tiendas1` FOREIGN KEY (`idTienda`) REFERENCES `tiendas` (`idTienda`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_idIncidencia_trabajadores1` FOREIGN KEY (`idTrabajador`) REFERENCES `trabajadores` (`idTrabajador`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidencias`
--

LOCK TABLES `incidencias` WRITE;
/*!40000 ALTER TABLE `incidencias` DISABLE KEYS */;
INSERT INTO `incidencias` VALUES (12,1,4,'Stock','2017-06-05 00:00:00','El producto Barra de labios MAC no tenia stock disponible a fecha de 2017-06-05','Leido'),(13,1,8,'Robo','2017-06-05 00:00:00','Entraron dos ladroens y a mano armada nos sustrajeron 1500€.','Leido'),(14,1,8,'Otros','2017-06-05 00:00:00','Instalaciones:: Una gotera en el techo a provocado que un cliente se resbale.','Leido');
/*!40000 ALTER TABLE `incidencias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productos`
--

DROP TABLE IF EXISTS `productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `productos` (
  `referencia` int(8) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `categoria` varchar(45) NOT NULL,
  `descripcion` varchar(60) DEFAULT NULL,
  `precioCompra` decimal(8,2) NOT NULL,
  `precioVenta` decimal(8,2) NOT NULL,
  `IVA` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`referencia`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productos`
--

LOCK TABLES `productos` WRITE;
/*!40000 ALTER TABLE `productos` DISABLE KEYS */;
INSERT INTO `productos` VALUES (1,'Gafas RayBan','Ropa','Las ultima linea de las gafas de moda.',85.99,124.99,21.00),(2,'Camiseta RipCurl','Ropa','Roja con dibujos playeros.',9.97,35.45,18.00),(3,'Bañador QuickSilver','Ropa','Liquidación de stock.',14.30,55.90,18.00),(4,'Crema LÒreal','Cosmeticos','Perfecta para manos secas.',14.50,16.70,21.00),(5,'Barra de labios MAC','Cosmeticos','Rojo carmín.',12.47,35.40,21.00),(6,'Galletas Oreo','Alimentacion','Cubiertas de chocolate blanco.',1.23,4.45,16.00),(7,'Rosquilletas Velarte','Alimentacion','Super crujientes.',0.23,1.50,16.00),(8,'Piña','Alimentacion','',1.34,6.50,5.00),(9,'Calzoncillos Calvin Klein','Ropa','100% seda',12.45,45.23,21.00),(10,'Cereales Chocapic','Alimentacion','Sabor a chocolate.',0.56,2.45,16.00);
/*!40000 ALTER TABLE `productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `productoscasiagotados`
--

DROP TABLE IF EXISTS `productoscasiagotados`;
/*!50001 DROP VIEW IF EXISTS `productoscasiagotados`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `productoscasiagotados` AS SELECT 
 1 AS `idTienda`,
 1 AS `referencia`,
 1 AS `categoria`,
 1 AS `descripcion`,
 1 AS `precioCompra`,
 1 AS `precioVenta`,
 1 AS `IVA`,
 1 AS `stock`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `tiendas`
--

DROP TABLE IF EXISTS `tiendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiendas` (
  `idTienda` int(5) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `direccion` varchar(60) NOT NULL,
  `ciudad` varchar(30) NOT NULL,
  PRIMARY KEY (`idTienda`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiendas`
--

LOCK TABLES `tiendas` WRITE;
/*!40000 ALTER TABLE `tiendas` DISABLE KEYS */;
INSERT INTO `tiendas` VALUES (1,'Setem','C/ Dénia','Valencia'),(2,'Gaia vegana','C/ Cuba','Valencia'),(3,'Kubelik','C/ Carrer de buenos aires','Valencia');
/*!40000 ALTER TABLE `tiendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiendas_productos`
--

DROP TABLE IF EXISTS `tiendas_productos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiendas_productos` (
  `idTienda` int(5) NOT NULL,
  `idProducto` int(8) NOT NULL,
  `stock` int(5) DEFAULT NULL,
  PRIMARY KEY (`idTienda`,`idProducto`),
  KEY `fk_tiendas_productos_productos1_idx` (`idProducto`),
  KEY `fk_tiendas_productos_tiendas1_idx` (`idTienda`),
  CONSTRAINT `fk_tiendas_productos_productos1` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`referencia`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_tiendas_productos_tiendas1` FOREIGN KEY (`idTienda`) REFERENCES `tiendas` (`idTienda`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiendas_productos`
--

LOCK TABLES `tiendas_productos` WRITE;
/*!40000 ALTER TABLE `tiendas_productos` DISABLE KEYS */;
INSERT INTO `tiendas_productos` VALUES (1,1,20),(1,2,44),(1,3,10),(1,4,35),(1,5,0),(1,6,50),(1,7,90),(1,8,34),(2,9,30),(2,10,14);
/*!40000 ALTER TABLE `tiendas_productos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `totalfacturado`
--

DROP TABLE IF EXISTS `totalfacturado`;
/*!50001 DROP VIEW IF EXISTS `totalfacturado`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `totalfacturado` AS SELECT 
 1 AS `nombre`,
 1 AS `cantidadFacurada`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `trabajadores`
--

DROP TABLE IF EXISTS `trabajadores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trabajadores` (
  `idTrabajador` int(5) NOT NULL AUTO_INCREMENT,
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido1` varchar(45) DEFAULT NULL,
  `apellido2` varchar(45) DEFAULT NULL,
  `puesto` enum('Gerente','Dependiente','Distribuidor') NOT NULL,
  `salarioBrutoAnual` decimal(8,2) NOT NULL,
  `fechaAlta` date DEFAULT NULL,
  `nick` varchar(45) NOT NULL,
  `password` varchar(250) NOT NULL,
  `horaEntrada` time NOT NULL,
  `horaSalida` time NOT NULL,
  `idTienda` int(5) NOT NULL,
  PRIMARY KEY (`idTrabajador`),
  UNIQUE KEY `nick_UNIQUE` (`nick`),
  UNIQUE KEY `dni_UNIQUE` (`dni`),
  KEY `fk_trabajadores_tiendas1_idx` (`idTienda`),
  CONSTRAINT `fk_trabajadores_tiendas1` FOREIGN KEY (`idTienda`) REFERENCES `tiendas` (`idTienda`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trabajadores`
--

LOCK TABLES `trabajadores` WRITE;
/*!40000 ALTER TABLE `trabajadores` DISABLE KEYS */;
INSERT INTO `trabajadores` VALUES (1,'23315533L','Daniel','Alba','Diaz','Gerente',24.51,'2017-05-22','Daniel','dAVyNyIhl/JYrjtkV5lYlCDpp7DQJdonyyhoylRcxo+frpSG5vdDCh47MmKxHaaH','08:00:00','18:00:00',1),(2,'12345678P','Rasul','Akhmeddibirov',NULL,'Gerente',15487.40,'2017-05-17','Rasul','AXmtiFPFQPECpbZgCGOQhoDZQVUw4Erz8kpqEBzLE4e6r0o7eovK9e/H9tZC0XAv','08:40:00','09:17:00',2),(3,'65420532E','Guillermo','Perez','Vazquez','Gerente',25152.00,'2017-06-05','Guillermo','tZcBECiuOk4Rk08oJkhS90p+eV38wD4XFp05c8nek/EMe8SXgGazX7YPUJ4xgqLG','06:12:00','19:10:00',3),(4,'53159722Y','Roberto','Martinez','Ramos','Dependiente',246587.00,'2017-06-05','Roberto','av1CRRlX1MNePBtNoPCrMAoPpyIZ2ymZga2OCDCcpdIy8t4vQ6AVDqONqpu9Y2jF','05:00:00','17:00:00',1),(5,'12639257K','Sergio','Peris','Gonzalez','Dependiente',18844.00,'2017-06-05','Sergio','+gMV48qTSgicW1ApX3Aq4QuHjdqe67Q/Az+hynjZTvnurF85fxkLiS221iDUEKRe','07:00:00','14:00:00',2),(6,'29194891W','Maria','Valero','Marin','Dependiente',14758.00,'2017-06-05','Maria','r8JfHiEtALTQQ0eFCYUGWH29nF3SF17709YWYtUnrUYvqzw1rmUgfpYOLm2IcpgF','04:00:00','14:00:00',2),(7,'40845791E','Sara','Garcia','Lopez','Dependiente',35478.00,'2017-06-05','Sara','lsz5lKwRYQ7UuuFlgWb6qTLHzhyt2TKVr2Z1EVIaVXzK9k/Rkmr4U3tHi3/k8wfR','05:00:00','17:00:00',2),(8,'70081183E','David','Bartual','Olmos','Dependiente',29987.00,'2017-06-05','David','bbVKhFFSF5aIaTzuW54g7pmBvefwz1ivribygu2lhkEmOpSF4yYk9oT8vVb8YqGC','05:00:00','16:00:00',1);
/*!40000 ALTER TABLE `trabajadores` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER tr_trabajadores_bi BEFORE INSERT ON trabajadores FOR EACH ROW
BEGIN
	INSERT INTO historial_trabajadores VALUES(CURRENT_USER(),'Contrato',NEW.idTrabajador,NEW.dni,CURRENT_DATE());
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER tr_trabajadores_ad AFTER DELETE ON trabajadores FOR EACH ROW
BEGIN
	INSERT INTO historial_trabajadores VALUES(CURRENT_USER(),'Despido',OLD.idTrabajador,OLD.dni,CURRENT_DATE());
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `ventas`
--

DROP TABLE IF EXISTS `ventas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ventas` (
  `idVenta` int(8) NOT NULL AUTO_INCREMENT,
  `idTienda` int(5) NOT NULL,
  `idTrabajador` int(5) NOT NULL,
  `fechaVenta` datetime DEFAULT NULL,
  PRIMARY KEY (`idVenta`),
  KEY `fk_ventas_tiendas1_idx` (`idTienda`),
  KEY `fk_ventas_trabajadores1_idx` (`idTrabajador`),
  CONSTRAINT `fk_ventas_tiendas1` FOREIGN KEY (`idTienda`) REFERENCES `tiendas` (`idTienda`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ventas_trabajadores1` FOREIGN KEY (`idTrabajador`) REFERENCES `trabajadores` (`idTrabajador`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ventas`
--

LOCK TABLES `ventas` WRITE;
/*!40000 ALTER TABLE `ventas` DISABLE KEYS */;
INSERT INTO `ventas` VALUES (1,1,4,'2017-06-05 23:29:17');
/*!40000 ALTER TABLE `ventas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'justcomerce'
--
/*!50003 DROP FUNCTION IF EXISTS `contraseñaParaCambiar` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `contraseñaParaCambiar`(p_user VARCHAR(45)) RETURNS tinyint(1)
BEGIN
DECLARE v_dni VARCHAR(9) DEFAULT NULL;
DECLARE v_pass VARCHAR(250) DEFAULT NULL;
DECLARE v_cambiar BOOLEAN DEFAULT FALSE;
	SELECT dni, password INTO v_dni,v_pass FROM trabajadores WHERE nick=p_user;
    IF v_dni = v_pass THEN
		SET v_cambiar=TRUE;
	END IF;
RETURN v_cambiar;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `insertarVenta` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `insertarVenta`(v_idTienda INT(5), v_idTrab INT(5), v_fechaVenta DATE, v_idVenta INT(8), v_referencia INT(8), v_cantidad INT(5)) RETURNS tinyint(1)
BEGIN
	DECLARE p_insertado INT(1) DEFAULT 0;
    DECLARE p_cantidad INT(11);
    DECLARE p_precioFinal DECIMAL(9,2);
    
    SET p_cantidad = (SELECT stock FROM tiendas_productos WHERE idTienda = v_idTienda AND idProducto = v_referencia);
    SET p_precioFinal = (SELECT round((((precioVenta*IVA)/100) + precioVenta)*v_cantidad, 2) AS 'precioFinal' FROM productos WHERE referencia = v_referencia);
    
	IF v_idVenta NOT IN (SELECT idVenta FROM ventas) AND p_cantidad >= v_cantidad THEN
		INSERT INTO ventas (idTienda, idTrabajador, fechaVenta) VALUES (v_idTienda, v_idTrab, v_fechaVenta);        
    END IF;
	       
	IF p_cantidad >= v_cantidad THEN
		INSERT INTO detallesventa VALUES (v_idVenta, v_referencia, v_cantidad,  p_precioFinal);

		UPDATE tiendas_productos
		SET stock = stock - v_cantidad
		WHERE idTienda = v_idTienda
		AND idProducto = v_referencia;
        SET p_insertado = 1;
    END IF;
        
    RETURN p_insertado;
        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `cambiarContraseña` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cambiarContraseña`(p_user VARCHAR(45), p_password VARCHAR(250))
BEGIN
UPDATE trabajadores
	SET password = p_password
    WHERE nick=p_user;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertarTrabajador` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertarTrabajador`(p_id INT(5),p_dni VARCHAR(9), p_nombre VARCHAR(45),p_apellido1 VARCHAR(45), p_apellido2 VARCHAR(45), p_puesto VARCHAR(30), p_salario DECIMAL(8,2), p_fecha DATE, p_nick VARCHAR(45), p_pass VARCHAR(250), p_horaEntrada TIME, p_horaSalida TIME, p_idTienda INT(5))
BEGIN
INSERT INTO trabajadores VALUES(p_id,p_dni,p_nombre,p_apellido1,p_apellido2,p_puesto,p_salario,p_fecha,p_nick,p_pass,p_horaEntrada,p_horaSalida,p_idTienda);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `listaProducto` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `listaProducto`(v_idVenta INT(8))
BEGIN

	SELECT p.referencia, nombre, cantidad, precio, fechaVenta
	FROM detallesventa dv INNER JOIN productos p INNER JOIN ventas v
	WHERE dv.referencia = p.referencia
	AND dv.idVenta = v.idVenta
	AND v.idVenta = v_idVenta;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `productoscasiagotados`
--

/*!50001 DROP VIEW IF EXISTS `productoscasiagotados`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `productoscasiagotados` AS select `tp`.`idTienda` AS `idTienda`,`p`.`referencia` AS `referencia`,`p`.`categoria` AS `categoria`,`p`.`descripcion` AS `descripcion`,`p`.`precioCompra` AS `precioCompra`,`p`.`precioVenta` AS `precioVenta`,`p`.`IVA` AS `IVA`,`tp`.`stock` AS `stock` from (`tiendas_productos` `tp` join `productos` `p`) where ((`tp`.`idProducto` = `p`.`referencia`) and (`tp`.`stock` < 100)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `totalfacturado`
--

/*!50001 DROP VIEW IF EXISTS `totalfacturado`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `totalfacturado` AS select `t`.`nombre` AS `nombre`,sum(`dt`.`precio`) AS `cantidadFacurada` from ((`ventas` `v` join `detallesventa` `dt`) join `trabajadores` `t`) where ((`v`.`idVenta` = `dt`.`idVenta`) and (`v`.`idTrabajador` = `t`.`idTrabajador`)) group by `t`.`nombre` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-05 23:48:22
