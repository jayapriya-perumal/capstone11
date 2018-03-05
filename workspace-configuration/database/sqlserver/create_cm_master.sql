CREATE DATABASE [cm_master]
GO
ALTER DATABASE [cm_master] SET COMPATIBILITY_LEVEL = 100
GO
ALTER DATABASE [cm_master] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [cm_master] SET ANSI_NULLS OFF
GO
ALTER DATABASE [cm_master] SET ANSI_PADDING OFF
GO
ALTER DATABASE [cm_master] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [cm_master] SET ARITHABORT OFF
GO
ALTER DATABASE [cm_master] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [cm_master] SET AUTO_CREATE_STATISTICS ON
GO
ALTER DATABASE [cm_master] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [cm_master] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [cm_master] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [cm_master] SET CURSOR_DEFAULT GLOBAL
GO
ALTER DATABASE [cm_master] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [cm_master] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [cm_master] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [cm_master] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [cm_master] SET DISABLE_BROKER
GO
ALTER DATABASE [cm_master] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [cm_master] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [cm_master] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [cm_master] SET READ_WRITE
GO
ALTER DATABASE [cm_master] SET RECOVERY FULL
GO
ALTER DATABASE [cm_master] SET MULTI_USER
GO
ALTER DATABASE [cm_master] SET PAGE_VERIFY CHECKSUM
GO
USE [cm_master]
GO
IF NOT EXISTS (SELECT name FROM sys.filegroups WHERE is_default=1 AND name = N'PRIMARY') ALTER DATABASE [cm_master] MODIFY FILEGROUP [PRIMARY] DEFAULT
GO

USE [master]
GO
CREATE LOGIN [cm_master] WITH PASSWORD=N'cm_master', DEFAULT_DATABASE=[cm_master], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO
USE [cm_master]
GO
CREATE USER [cm_master] FOR LOGIN [cm_master]
GO
USE [cm_master]
GO
ALTER USER [cm_master] WITH DEFAULT_SCHEMA=[cm_master]
GO
USE [cm_master]
GO
EXEC sp_addrolemember N'db_datareader', N'cm_master'
GO
USE [cm_master]
GO
EXEC sp_addrolemember N'db_datawriter', N'cm_master'
GO
USE [cm_master]
GO
EXEC sp_addrolemember N'db_ddladmin', N'cm_master'
GO

USE [cm_master]
GO
CREATE SCHEMA [cm_master] AUTHORIZATION [cm_master]
GO
USE [cm_master]
GO
ALTER USER [cm_master] WITH DEFAULT_SCHEMA=[cm_master]
GO
