USE [Fs3]
GO

/****** Object:  Table [dbo].[StudentQuestionAnswer]    Script Date: 23/05/2022 10:18:33 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[StudentQuestionAnswer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[questionId] [int] NOT NULL,
	[state] [int] NULL,
	[QuestionaireId] [int] NULL,
 CONSTRAINT [PK_StudentQuestionAnswer] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[StudentQuestionAnswer]  WITH CHECK ADD  CONSTRAINT [FK_StudentQuestionAnswer_Questionaire] FOREIGN KEY([QuestionaireId])
REFERENCES [dbo].[Questionaire] ([id])
GO

ALTER TABLE [dbo].[StudentQuestionAnswer] CHECK CONSTRAINT [FK_StudentQuestionAnswer_Questionaire]
GO

ALTER TABLE [dbo].[StudentQuestionAnswer]  WITH CHECK ADD  CONSTRAINT [FK_StudentQuestionAnswer_StudentQuestion] FOREIGN KEY([questionId])
REFERENCES [dbo].[StudentQuestion] ([id])
GO

ALTER TABLE [dbo].[StudentQuestionAnswer] CHECK CONSTRAINT [FK_StudentQuestionAnswer_StudentQuestion]
GO

