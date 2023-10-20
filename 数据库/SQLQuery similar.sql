CREATE PROCEDURE neighbor
@User int,
@Method tinyint = 1
AS
/*
@Method
1,Euclidean Distance Score 
2,Pearson Correlation Score
*/
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