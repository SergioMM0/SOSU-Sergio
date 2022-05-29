USE [Fs3]
GO

/****** Object:  Table [dbo].[SickPatient]    Script Date: 23/05/2022 10:18:00 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SickPatient](
	[patientid] [int] NOT NULL,
	[caseid] [int] NOT NULL,
	[SickPatientid] [int] IDENTITY(1,1) NOT NULL,
	[Groupid] [int] NULL,
	[graded] [int] NULL,
 CONSTRAINT [PK_SickPatient] PRIMARY KEY CLUSTERED 
(
	[SickPatientid] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[SickPatient]  WITH CHECK ADD  CONSTRAINT [FK_SickPatient_Case] FOREIGN KEY([caseid])
REFERENCES [dbo].[Case] ([id])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[SickPatient] CHECK CONSTRAINT [FK_SickPatient_Case]
GO

