USE [sales]
GO
/****** Object:  StoredProcedure [dbo].[usp_User_GetNeighborhoodForUser]    Script Date: 2018/4/26 19:30:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
 
 ALTER PROCEDURE [dbo].[usp_User_GetNeighborhoodForUser]
 @User int,
 @Method tinyint = 1
 AS
 /*
 6 @Method
 7 1,Euclidean Distance Score 
 8 2,Pearson Correlation Score
 9 */
SET NOCOUNT ON  
CREATE TABLE #Result (
     UserID int,
     SimValue decimal(20,5)
 )
 
 IF @Method = 1
 BEGIN
    INSERT INTO #Result
    SELECT t2.UserID,
         sim = 1.0 /(SQRT(SUM(POWER(t1.Score - t2.Score,2))) + 1)
     FROM tbl_Fund t1
     INNER JOIN tbl_Fund t2
     ON t1.UserID = @User
         AND t1.ItemID = t2.ItemID
         AND t2.UserID <> @User
     GROUP BY t2.UserID
 END
 ELSE
 BEGIN
     INSERT INTO #Result
         SELECT UserID,
   (pSum - (Sum1*Sum2 / n)) / CASE WHEN (Sum1Sq - POWER(Sum1,2) / n) * (Sum2Sq - POWER(Sum2,2) / n) = 0 
             THEN NULL ELSE SQRT((Sum1Sq - POWER(Sum1,2) / n)*(Sum2Sq - POWER(Sum2,2) / n)) END
     FROM (
         SELECT t2.UserID,
             pSum = SUM(t1.Score * t2.Score),
             Sum1 = SUM(t1.Score),
             Sum2 = SUM(t2.Score),
             n = COUNT(t2.ItemID),
             Sum1Sq = SUM(POWER(t1.Score,2)),
            Sum2Sq = SUM(POWER(t2.Score,2))
         FROM tbl_Fund t1
         INNER JOIN tbl_Fund t2
         ON t1.UserID = @User
             AND t1.ItemID = t2.ItemID
             AND t2.UserID <> @User
         GROUP BY t2.UserID
     ) A
 END
 
 SELECT TOP 20 UserID,
     SimValue
 FROM #Result
 ORDER BY SimValue DESC
 
 DROP TABLE #Result
 SET NOCOUNT OFF


INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (1,1,2.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (1,2,3.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (1,3,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (1,4,3.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (1,5,2.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (1,6,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (2,1,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (2,2,3.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (2,3,1.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (2,4,5.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (2,5,3.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (2,6,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (3,1,2.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (3,2,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (5,1,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (3,4,3.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (3,6,4.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (4,2,3.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (4,3,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (4,4,4.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (4,5,2.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (4,6,4.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (6,1,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (6,2,4.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (6,4,5.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (6,5,3.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (6,6,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (7,2,4.50000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (7,4,4.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (7,5,1.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (5,2,4.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (5,3,2.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (5,4,3.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (5,5,2.00000)
INSERT INTO tbl_Fund(UserID,ItemID,Score) VALUES (5,6,3.00000)
