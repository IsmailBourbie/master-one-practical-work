-- DELETE PARTITION:
-- ALTER TABLE dates DROP PARTITION p0;

-- Add Partition on existing table
ALTER TABLE salaries PARTITION BY RANG (salary)(
    PARTITION p0 VALUES LESS THAN (10000),
    PARTITION p1 VALUES LESS THAN (16000)
    );


-- SELECT Without partition:
SELECT * FROM salaries WHERE salary = 130410;

--SELECT WITH PARTITION
SELECT * FROM salaries PARTITION(p1) WHERE salary = 130410;

