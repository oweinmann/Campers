-- Migration script to create excluded tables and migrate deleted caravans
-- Run this script to transition from soft deletes to the excluded table structure

-- Step 1: Drop old excluded table if it exists
DROP TABLE IF EXISTS excluded;

-- Step 2: Create the excludedCaravans table
CREATE TABLE IF NOT EXISTS excludedCaravans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    date_excluded TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_make_model (make, model)
);

-- Step 3: Create the excludedManufacturers table
CREATE TABLE IF NOT EXISTS excludedManufacturers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(255) NOT NULL UNIQUE,
    date_excluded TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_brand (brand)
);

-- Step 4: Migrate existing deleted caravans to the excludedCaravans table
INSERT INTO excludedCaravans (make, model, date_excluded)
SELECT make, model, updated_at
FROM caravans
WHERE deleted = true;

-- Step 5: Delete the migrated caravans from the caravans table
DELETE FROM caravans WHERE deleted = true;

-- Step 6: Remove the deleted column from caravans table
ALTER TABLE caravans DROP COLUMN deleted;

-- Verify migration
SELECT 'Caravans remaining:' as status, COUNT(*) as count FROM caravans
UNION ALL
SELECT 'Excluded caravan entries:' as status, COUNT(*) as count FROM excludedCaravans
UNION ALL
SELECT 'Excluded manufacturer entries:' as status, COUNT(*) as count FROM excludedManufacturers;
