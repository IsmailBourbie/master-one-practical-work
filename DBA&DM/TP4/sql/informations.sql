SELECT TABLE_SCHEMA, TABLE_NAME, PARTITION_NAME, PARTITION_METHOD, TABLE_ROWS
FROM information_schema.partitions
WHERE TABLE_SCHEMA = 'employees'
  AND TABLE_NAME = 'salaries'
  AND PARTITION_NAME IS NOT NULL;