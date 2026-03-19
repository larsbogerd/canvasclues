-- Seed: game board
INSERT IGNORE INTO game_cards (uuid, game_id, alt_text, image_url, is_spymaster_pick)
VALUES
  (UUID_TO_BIN('05316ced-9f2b-42a1-8e24-16e2f519e257'), 1, 'Stacks of Wheat (End of Summer) – Claude Monet (French, 1840–1926), 1890/91', 'https://www.artic.edu/iiif/2/a38e2828-ec6f-ece1-a30f-70243449197b/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('0779a09c-6f86-4d14-a0fb-40de1013f9c9'), 1, 'The Bedroom – Vincent van Gogh (Dutch, 1853–1890), 1889', 'https://www.artic.edu/iiif/2/25c31d8d-21a4-9ea1-1d73-6a2eca4dda7e/full/!500,500/0/default.jpg', true),
  (UUID_TO_BIN('0daad8c3-35a5-4eed-8d4f-6628e1ea2654'), 1, 'The Assumption of the Virgin – El Greco (Doménikos Theotokópoulos) (Greek, 1541–1614), 1577', 'https://www.artic.edu/iiif/2/47fd1564-93f5-f30b-7786-013421133b4a/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('226181d7-8122-4567-ba90-c885ae9cb3ba'), 1, 'At the Moulin Rouge – Henri de Toulouse-Lautrec (French, 1864–1901), 1892/95', 'https://www.artic.edu/iiif/2/defb4004-b500-218d-3d9b-9a02423f097d/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('2e41d850-ea3c-452c-a803-54529c9e41d3'), 1, 'Veranda Post (Òpó Ògògá) – Olowe of Ise (died 1938), Yorùbá', 'https://www.artic.edu/iiif/2/fec5d023-0b46-45df-eb57-cbf64e24121d/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('3d0ea3a9-2ac7-4cf7-a61f-a1d7bee37082'), 1, 'Water Lilies – Claude Monet (French, 1840–1926), 1906', 'https://www.artic.edu/iiif/2/3c27b499-af56-f0d5-93b5-a7f2f1ad5813/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('4fdcd66f-a7ce-45c0-84d4-f683e57d6d7a'), 1, 'A Sunday on La Grande Jatte – 1884 – Georges Seurat (French, 1859–1891), 1884–86', 'https://www.artic.edu/iiif/2/2d484387-2509-5e8e-2c43-22f9981972eb/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('5a9988af-d2a2-4ac6-8298-ad88824b520b'), 1, 'Self-Portrait – Vincent van Gogh (Dutch, 1853–1890), 1887', 'https://www.artic.edu/iiif/2/47c5bcb8-62ef-e5d7-55e7-f5121f409a30/full/!500,500/0/default.jpg', true),
  (UUID_TO_BIN('71e7b296-2147-4789-8a2c-4f06c99e8319'), 1, 'Buddha Shakyamuni Seated in Meditation (Dhyanamudra) – India', 'https://www.artic.edu/iiif/2/0675f9a9-1a7b-c90a-3bb6-7f7be2afb678/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('7608ed35-f502-4558-907b-94a18eaaf695'), 1, 'Paris Street; Rainy Day – Gustave Caillebotte (French, 1848–1894), 1877', 'https://www.artic.edu/iiif/2/f8fd76e9-c396-5678-36ed-6a348c904d27/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('7e25d3bf-d972-4cd7-adf8-0b1713956dd9'), 1, 'Bust of Anne-Marie-Louise Thomas de Domangeville de Sérilly', 'https://www.artic.edu/iiif/2/fae6d055-fe95-17e2-a00a-c2307ace531c/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('887d2aff-7dff-478e-859f-fe2b5c077616'), 1, 'Under the Wave off Kanagawa (Kanagawa oki nami ura), also known as the Great Wave – Katsushika Hokusai (Japanese, 1760–1849), 1830/33', 'https://www.artic.edu/iiif/2/2fa24f36-cc26-41b6-4b49-12bba2a6c1c8/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('89374af2-56f4-4209-9001-46af26a9a346'), 1, 'Two Sisters (On the Terrace) – Pierre-Auguste Renoir (French, 1841–1919), 1881', 'https://www.artic.edu/iiif/2/3a608f55-d76e-fa96-d0b1-0789fbc48f1e/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('a71fe056-43fe-44c3-ad93-5e107646ff1d'), 1, 'Arrival of the Normandy Train, Gare Saint-Lazare – Claude Monet (French, 1840–1926), 1877', 'https://www.artic.edu/iiif/2/0f1cc0e0-e42e-be16-3f71-2022da38cb93/full/!500,500/0/default.jpg', false),
  (UUID_TO_BIN('c35890a2-f2ce-443f-be38-8cc1297959e0'), 1, 'The Drinkers - Vincent van Gogh (Dutch, 1853–1890), 1890', 'https://www.artic.edu/iiif/2/d0ff5b36-bb38-b156-6042-5c8545352c2f/full/!500,500/0/default.jpg', true),
  (UUID_TO_BIN('c9ca8451-8d89-4030-904a-05f624d97785'), 1, 'The Basket of Apples – Paul Cezanne (French, 1839–1906), c. 1893', 'https://www.artic.edu/iiif/2/52ac8996-3460-cf71-cb42-5c4d0aa29b74/full/!500,500/0/default.jpg', false);

-- Seed: hint table
INSERT IGNORE INTO hint (hint_id, hint_content, game_id)
VALUES (1, 'vangogh', 1);

