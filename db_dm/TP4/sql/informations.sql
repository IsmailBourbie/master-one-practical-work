SELECT *
FROM information_schema.partitions
WHERE TABLE_SCHEMA = 'tp4'
  AND TABLE_NAME = 'dates'
  AND PARTITION_NAME IS NOT NULL;