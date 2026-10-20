ALTER TABLE artwork
  ADD COLUMN source VARCHAR(32) NULL AFTER id,
  ADD COLUMN external_image_id VARCHAR(255) NULL AFTER source;
UPDATE artwork SET source = 'artic', external_image_id = LOWER(BIN_TO_UUID(id)) WHERE source IS NULL;
ALTER TABLE artwork
  MODIFY COLUMN source VARCHAR(32) NOT NULL,
  MODIFY COLUMN external_image_id VARCHAR(255) NOT NULL,
  ADD CONSTRAINT uk_artwork_source_external_image_id UNIQUE (source, external_image_id);

ALTER TABLE game ADD COLUMN source VARCHAR(255) NULL;
UPDATE game SET source = 'artic' WHERE source IS NULL;

UPDATE IGNORE artwork SET external_image_id = '107958b1-8367-df7c-be17-bb3a076479d8'
 WHERE source = 'artic' AND external_image_id = 'd0ff5b36-bb38-b156-6042-5c8545352c2f';
UPDATE IGNORE artwork SET external_image_id = '0a2d277c-34e2-8c29-e142-4daea2546217'
 WHERE source = 'artic' AND external_image_id = '416b87c8-515f-0066-5c44-b7562162f2c0';
