CREATE PROCEDURE usp_User_GetRecommendedItemsForUser
@User int,
@Method tinyint = 1
AS
/*
@Method
1,Euclidean Distance Score 
2,Pearson Correlation Score

*/
SET NOCOUNT ON 

CREATE TABLE #Neighborhood (
    UserID int,
    SimValue decimal(20,5)
)

INSERT INTO #Neighborhood
EXEC usp_User_GetNeighborhoodForUser
@User = @User,
@Method = @Method

SELECT TOP 5 ItemID,
    SimValue
FROM (
    SELECT f.ItemID, 
        SimValue = SUM(f.Score * n.SimValue) * 1.0 / SUM(n.SimValue)
    FROM tbl_Fund f
    INNER JOIN #Neighborhood n
    ON n.UserID = f.UserID
    WHERE f.UserID <> @User
        AND NOT EXISTS (SELECT 1 FROM tbl_Fund WHERE UserID = @User AND ItemID = f.ItemID)
        AND n.SimValue > 0
    GROUP BY f.ItemID
) A
ORDER BY SimValue DESC

DROP TABLE #Neighborhood

SET NOCOUNT OFF