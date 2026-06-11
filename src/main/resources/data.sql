-- Seed: game board
INSERT IGNORE INTO game (id, state, spy_score, created_at, play_count, likes, dislikes, game_mode)
VALUES (1, 'READY', 60, '2026-03-25 13:00:00', 50, 3, 2, 'makkelijk'),
       (2, 'READY', 80, '2026-03-31 16:30:00', 5, 4,3, 'gemiddeld'),
       (3, 'READY', 100, '2026-04-01 10:20:00', 10, 4, 8, 'moeilijk');


-- Seed: artwork table
INSERT IGNORE INTO artwork (id, title, artist_display, date_display, medium_display, place_of_origin, dimensions, department_title)
VALUES
    (UUID_TO_BIN('a38e2828-ec6f-ece1-a30f-70243449197b'), 'Stacks of Wheat (End of Summer)', 'Claude Monet (French, 1840–1926)', '1890/91', 'Oil on canvas', 'France', '60 × 100.5 cm (23 5/8 × 39 9/16 in.); Framed: 75.6 × 116.6 × 7.4 cm (29 3/4 × 45 7/8 × 2 7/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('6644829f-f292-c5c4-a73c-0356a6fdbf0d'), 'The Bedroom', 'Vincent van Gogh (Dutch, 1853–1890)', '1889', 'Oil on canvas', 'Saint-Rémy-de-Provence', '73.6 × 92.3 cm (29 × 36 5/8 in.); Framed: 88.9 × 108 × 8.9 cm (35 × 42 1/2 × 3 1/2 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('47fd1564-93f5-f30b-7786-013421133b4a'), 'The Assumption of the Virgin', 'El Greco (Doménikos Theotokópoulos) (Greek, 1541–1614)', '1577', 'Oil on canvas', 'Greece', '403.2 × 211.8 cm (158 3/4 × 83 7/16 in.); Framed: 461.6 × 256.5 × 14 cm (181 3/4 × 101 × 5 9/16 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('defb4004-b500-218d-3d9b-9a02423f097d'), 'At the Moulin Rouge', 'Henri de Toulouse-Lautrec (French, 1864–1901)', '1892/95', 'Oil on canvas', 'France', '123 × 141 cm (48 7/16 × 55 1/2 in.); Framed: 136.9 × 154.7 × 8.5 cm (53 7/8 × 60 7/8 × 3 5/16 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('fec5d023-0b46-45df-eb57-cbf64e24121d'), 'Veranda Post (Òpó Ògògá)', 'Olowe of Ise (died 1938)', 'Yorùbá', 'Wood and pigment', 'Ikere', '152.5 × 31.8 × 40.6 cm (60 × 12 1/2 × 16 in.)', 'Arts of Africa'),
    (UUID_TO_BIN('3c27b499-af56-f0d5-93b5-a7f2f1ad5813'), 'Water Lilies', 'Claude Monet (French, 1840–1926)', '1906', 'Oil on canvas', 'France', '89.9 × 94.1 cm (35 3/8 × 37 1/16 in.); Framed: 103.2 × 107 × 5.8 cm (40 5/8 × 42 1/8 × 2 1/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('2d484387-2509-5e8e-2c43-22f9981972eb'), 'A Sunday on La Grande Jatte – 1884', 'Georges Seurat (French, 1859–1891)', '1884–86', 'Oil on canvas', 'France', '207.5 × 308.1 cm (81 3/4 × 121 1/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('47c5bcb8-62ef-e5d7-55e7-f5121f409a30'), 'Self-Portrait', 'Vincent van Gogh (Dutch, 1853–1890)', '1887', 'Oil on artist''s board, mounted on cradled panel', 'Netherlands', '41 × 32.5 cm (16 1/8 × 12 13/16 in.); Framed: 61.6 × 53.4 × 8.9 cm (24 1/4 × 21 × 3 1/2 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('0675f9a9-1a7b-c90a-3bb6-7f7be2afb678'), 'Buddha Shakyamuni Seated in Meditation (Dhyanamudra)', 'India', 'Unknown date', 'Granite', 'Nagapattinam', '160 × 120.2 × 56.3 cm (63 × 47 5/16 × 22 3/16 in.)', 'Arts of Asia'),
    (UUID_TO_BIN('f8fd76e9-c396-5678-36ed-6a348c904d27'), 'Paris Street; Rainy Day', 'Gustave Caillebotte (French, 1848–1894)', '1877', 'Oil on canvas', 'Paris', '212.2 × 276.2 cm (83 1/2 × 108 3/4 in.); Framed: 241.3 × 306.1 × 10.2 cm (95 × 120 1/2 × 4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('fae6d055-fe95-17e2-a00a-c2307ace531c'), 'Bust of Anne-Marie-Louise Thomas de Domangeville de Sérilly', 'Unknown artist', 'Unknown date', 'Marble', 'France', 'H.: 89.9 cm (35 1/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('2fa24f36-cc26-41b6-4b49-12bba2a6c1c8'), 'Under the Wave off Kanagawa (Kanagawa oki nami ura), also known as the Great Wave', 'Katsushika Hokusai (Japanese, 1760–1849)', '1830/33', 'Color woodblock print; oban', 'Japan', '25.4 × 37.6 cm (10 × 14 3/4 in.)', 'Arts of Asia'),
    (UUID_TO_BIN('3a608f55-d76e-fa96-d0b1-0789fbc48f1e'), 'Two Sisters (On the Terrace)', 'Pierre-Auguste Renoir (French, 1841–1919)', '1881', 'Oil on canvas', 'France', '100.4 × 80.9 cm (39 1/2 × 31 7/8 in.); Framed: 119.1 × 100.1 × 7.7 cm (46 7/8 × 39 3/8 × 3 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('0f1cc0e0-e42e-be16-3f71-2022da38cb93'), 'Arrival of the Normandy Train, Gare Saint-Lazare', 'Claude Monet (French, 1840–1926)', '1877', 'Oil on canvas', 'France', '60.3 × 80.2 cm (23 3/4 × 31 1/2 in.); Framed: 80.7 × 100.4 × 10.2 cm (31 3/4 × 39 1/2 × 4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('d0ff5b36-bb38-b156-6042-5c8545352c2f'), 'The Drinkers', 'Vincent van Gogh (Dutch, 1853–1890)', '1890', 'Oil on canvas', 'Netherlands', '59.4 × 73.4 cm (23 3/8 × 28 7/8 in.); Framed: 78.9 × 93.3 cm (31 1/16 × 36 3/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('52ac8996-3460-cf71-cb42-5c4d0aa29b74'), 'The Basket of Apples', 'Paul Cezanne (French, 1839–1906)', 'c. 1893', 'Oil on canvas', 'France', '65 × 80 cm (25 7/16 × 31 1/2 in.); Framed: 88 × 104.2 × 9.6 cm (34 5/8 × 41 × 3 3/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('65db9e21-83c3-1cc6-7240-1e1996d87f52'), 'Equestrienne (At the Cirque Fernando)', 'Henri de Toulouse-Lautrec (French, 1864–1901)', '1887–88', 'Oil on canvas', 'France', '100.3 × 161.3 cm (39 1/2 × 63 1/2 in.); Framed: 123.2 × 181 × 8.3 cm (48 1/2 × 71 1/4 × 3 1/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('d0e36029-27fc-bf4e-357a-55cfbaf7bdfd'), 'Woman before an Aquarium', 'Henri Matisse (French, 1869–1954)', '1921–23', 'Oil on canvas', 'France', '81.2 × 100.2 cm (31 15/16 × 39 7/16 in.)', 'Modern Art'),
    (UUID_TO_BIN('d7df2633-3b40-f570-c906-211503a37cde'), 'The Girl by the Window', 'Edvard Munch (Norwegian, 1863–1944)', '1893', 'Oil on canvas', 'Norway', '96.5 × 65.4 cm (38 × 25 3/4 in.); Framed: 110.5 × 79.4 × 8.9 cm (43 1/2 × 31 1/4 × 3 1/2 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('416b87c8-515f-0066-5c44-b7562162f2c0'), 'English Dining Room of the Georgian Period, 1770-90', 'Designed by Narcissa Niblack Thorne (American, 1882-1966)', 'c. 1937', 'Miniature room, mixed media', 'United States', 'Scale 1 inch = 1 foot: 50.8 × 89.5 × 65.1 cm (20 × 35 1/4 × 25 11/16 in.); Interior, Scale 1 inch = 1 foot: 50.8 × 89.6 × 65.1 cm (20 × 35 1/4 × 25 5/8 in.)', 'Applied Arts of Europe'),
    (UUID_TO_BIN('aa870b0d-5a1b-660a-6dc6-56c12109cf6e'), 'Landscape with Saint John on Patmos', 'Nicolas Poussin (French, 1594–1665)', '1640', 'Oil on canvas', 'France', '100.3 × 136.4 cm (39 1/2 × 53 5/8 in.); Framed: 128.6 × 164.1 cm (50 5/8 × 64 5/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('93270aaf-16a5-1604-c6c8-0f3c003eb7a1'), 'Coffin and Mummy of Paankhaenamun', 'Egyptian; Thebes (now Luxor), Egypt', 'Third Intermediate Period, Dynasty 22, reign of Osorkon I (about 924–889 BCE)', 'Cartonnage, gold leaf, pigment, and mummified human remains', 'Thebes', '170.2 × 43.2 × 31.7 cm (67 × 17 × 12 1/2 in.)', 'Arts of Africa'),
    (UUID_TO_BIN('8641479e-c93e-f1a8-9925-19be061706da'), 'Fishing Boats with Hucksters Bargaining for Fish', 'Joseph Mallord William Turner (English, 1775–1851)', '1837–38', 'Oil on canvas', 'England', '174.5 × 224.9 cm (68 3/4 × 88 1/2 in.); Framed: 216.6 × 268 cm (85 1/4 × 105 1/2 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('321c45f5-22a3-84a2-44cc-cf66642d4cf2'), 'Acrobats at the Cirque Fernando (Francisca and Angelina Wartenberg)', 'Pierre-Auguste Renoir (French, 1841–1919)', '1879', 'Oil on canvas', 'France', '131.2 × 99.2 cm (51 1/2 × 39 1/16 in.); Framed: 160.1 × 129 × 10.2 cm (63 × 50 3/4 × 4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('2ae64c8a-266d-470a-a25a-c83127564636'), 'Hercules and the Lernaean Hydra', 'Gustave Moreau (French, 1826-1898)', '1875–76', 'Oil on canvas', 'France', '179.3 × 154 cm (70 9/16 × 60 5/8 in.); Framed: 226.7 × 201.9 cm (89 1/4 × 79 1/2 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('cf50f037-5fb2-e197-0e56-3ae701edb3e2'), 'Shiva as Lord of the Dance (Nataraja)', 'India Tamil Nadu, Chola period', 'about 10th/11th century', 'Bronze', 'Tamil Nadu', '69.3 × 61.8 × 24.1 cm (27 1/4 × 24 1/4 × 9 1/2 in.)', 'Arts of Asia'),
    (UUID_TO_BIN('237c25a2-6051-a8e7-1610-a01938d4deab'), 'The Watermill with the Great Red Roof', 'Meindert Hobbema (Dutch, 1638–1709)', 'c. 1665', 'Oil on canvas', 'Holland', '81.3 × 110 cm (32 × 43 1/4 in.); Framed: 101.3 × 130.5 × 8.3 cm (39 7/8 × 51 3/8 × 3 1/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('5dca7347-c6dc-24dd-d073-d705b9cdc575'), 'The Herring Net', 'Winslow Homer (American, 1836–1910)', '1885', 'Oil on canvas', 'Prouts Neck', '76.5 × 122.9 cm (30 1/8 × 48 3/8 in.)', 'Arts of the Americas'),
    (UUID_TO_BIN('0f951635-312c-0829-451b-553f461c5518'), 'Cupid Chastised', 'Bartolomeo Manfredi (Italian, 1582–1622)', '1613', 'Oil on canvas', 'Italy', '175.3 × 130.6 cm (69 × 51 3/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('d4ca6321-8656-3d3f-a362-2ee297b2b813'), 'The Bay of Marseille, Seen from L''Estaque', 'Paul Cezanne (French, 1839–1906)', 'c. 1885', 'Oil on canvas', 'France', '80.2 × 100.6 cm (31 5/8 × 39 5/8 in.); Framed: 107.4 × 127.4 × 10.5 cm (42 1/4 × 50 1/8 × 4 1/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('77951d0c-d781-eac6-f4ff-f13cc5cfc0cf'), 'The Annunciation', 'Jean Hey (Master of Moulins; Netherlandish, c. 1480-c.1504)', '1490–95', 'Oil on panel', 'Netherlands', '72.5 × 50.1 cm (28 1/2 × 19 11/16 in.); Framed: 86.3 × 65.4 cm (34 × 25 3/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('f0b3ff64-d68e-3fd2-ffc9-5470eb9fea6e'), 'Grey and Silver: Old Battersea Reach', 'James McNeill Whistler (American, 1834–1903)', '1863', 'Oil on canvas', 'London', '50.8 × 68.6 cm (20 × 27 in.)', 'Arts of the Americas'),
    (UUID_TO_BIN('72227c9e-413c-8930-477d-5a90e0a2123c'), 'Madame de Pastoret and Her Son', 'Jacques-Louis David (French, 1748–1825)', '1791–92', 'Oil on canvas', 'France', '129.8 × 96.6 cm (51 1/8 × 38 in.); French: 156.3 × 123.6 cm (61 1/2 × 48 5/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('239e0072-de08-cb26-1484-4776fc27b0fb'), 'Portrait Vessel', 'Ceramist unknown (Moche), Southern Moche region, north coast, Peru', '600–700', 'Earthenware and mineral slips', 'Peruvian North Coast', '34.7 × 20.6 × 22.6 cm (13 11/16 × 8 1/8 × 8 15/16 in.)', 'Arts of the Americas'),
    (UUID_TO_BIN('7abbfd97-fb03-2366-cfe5-b53738a3d93e'), 'Roses in a Bowl', 'Henri Fantin-Latour (French, 1836–1904)', '1881', 'Oil on canvas', 'France', '26.2 × 31.4 cm (10 5/16 × 12 3/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('d9bde524-38b2-4262-3338-e4d06a50746d'), 'Still Life with Dead Game, Fruits, and Vegetables in a Market', 'Frans Snyders (Flemish, 1579-1657)', '1614', 'Oil on canvas', 'Flanders', '212 × 308 cm (83 1/2 × 121 1/4 in.); Framed: 251.5 × 348 × 10.2 cm (99 × 137 × 4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('d63b9402-c42b-cce3-aa63-6d213ad621cd'), 'Virgin and Child with the Young Saint John the Baptist', 'Correggio (Antonio Allegri; Italian, 1489–1534)', 'c. 1515', 'Oil on panel', 'Italy', '64.2 × 50.2 cm (25 1/4 × 19 3/4 in.); Framed: 79.4 × 63.9 × 8.3 cm (31 1/4 × 25 1/8 × 3 1/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('5490fd49-dd52-4fd7-7d41-d871dcffae55'), 'The Captive Slave (Ira Aldridge)', 'John Philip Simpson (English, 1782–1847)', '1827', 'Oil on canvas', 'England', '127 × 101.5 cm (50 × 40 in.); Framed: 167.1 × 141.7 cm (65 3/4 × 55 3/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('4822cd01-44ac-041a-36b8-c0542377b750'), 'Madame Cezanne in a Yellow Chair', 'Paul Cezanne (French, 1839–1906)', '1888–90', 'Oil on canvas', 'France', '80.9 × 64.9 cm (31 13/16 × 25 9/16 in.); Framed: 106.7 × 90.2 × 11.2 cm (42 × 35 1/2 × 4 3/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('9365a024-75ce-75e9-756f-1c2c96eadec9'), 'Stela of Amenemhat and Hemet', 'Egyptian; probably Thebes (now Luxor), Egypt', 'Middle Kingdom, early Dynasty 12, about 1956–1877 BCE', 'Limestone and pigment', 'Egypt', '31.1 × 41.7 × 6.7 cm (12 1/4 × 16 3/8 × 2 5/8 in.)', 'Arts of Africa'),
    (UUID_TO_BIN('4e37b182-cc1e-ef86-3eb2-de7d5b9db717'), 'Bust of a Youth (Saint John the Baptist)', 'Francesco Mochi (Italian, 1580–1654)', '1630-40', 'Marble on variegated black marble socle', 'Italy', '40.5 × 33 × 29 cm (15 7/8 × 13 × 11 3/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('b146368d-a855-63ac-6115-04b835c60bb0'), 'At Mouquin''s', 'William Glackens (American, 1870–1938)', '1905', 'Oil on canvas', 'United States', '122.4 × 92.1 cm (48 1/8 × 36 1/4 in.)', 'Arts of the Americas'),
    (UUID_TO_BIN('62fd0e14-d7fe-0d01-ab00-1eed84b4f294'), 'The Fountains', 'Hubert Robert (French, 1733-1808)', '1787–88', 'Oil on canvas', 'France', '255.3 × 221.2 cm (100 1/2 × 88 1/8 in.); Framed: 265.5 × 233.4 cm (104 1/2 × 91 7/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('83092988-7180-21d1-b8a3-cf39ddf39a68'), 'The Landing Place', 'Hubert Robert (French, 1733-1808)', '1788', 'Oil on canvas', 'France', '255 × 222.9 cm (100 7/8 × 87 3/4 in.); Framed: 265.5 × 233.4 cm (104 1/2 × 91 7/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('f0447c50-f4c9-ecb2-2695-22669e1469cd'), 'St. Gerardo Sagredo, Bishop of Csanád', 'Bernardo Strozzi (Italian, 1581–1664)', '1633', 'Oil on canvas', 'Italy', '130.3 × 100 cm (51 1/4 × 39 3/8 in.); Framed: 158.8 × 130.8 × 10.2 cm (62 1/2 × 51 1/2 × 4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('38bb6be4-223e-af7a-7e18-f9ad0c388d3c'), 'The Interior of the Palm House on the Pfaueninsel Near Potsdam', 'Carl Blechen (German, 1798–1840)', '1834', 'Oil on canvas', 'Germany', '135 × 126 cm (52 1/2 × 50 in.); Framed: 155 × 145.5 cm (61 × 57 1/4 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('67b46720-1589-0d13-3597-f03c7b618094'), 'The Gulf of Salerno', 'Joseph Wright of Derby (English, 1734-1797)', '1783–85', 'Oil on canvas', 'England', '41.6 × 59.4 cm (16 3/8 × 23 3/8 in.); Framed: 51.5 × 67.7 cm (20 1/4 × 26 5/8 in.)', 'Painting and Sculpture of Europe'),
    (UUID_TO_BIN('fa81bfb9-5a07-5e3c-33b4-4efde68981c8'), 'In the Sea', 'Arnold Böcklin (Swiss, 1827–1901)', '1883', 'Oil on panel', 'Switzerland', '86.5 × 115 cm (34 3/8 × 45 3/4 in.); Framed: 130.8 × 158.8 × 17.8 cm (51 1/2 × 62 1/2 × 7 in.)', 'Painting and Sculpture of Europe');

-- Seed: artwork metadata (fake usage stats for dashboard previews)
-- Same timestamps for all artworks so the first_used / last_used window is uniform.
UPDATE artwork
SET first_used_at = '2026-01-15 10:00:00',
    last_used_at  = '2026-04-20 14:30:00';

-- Tier S: very popular (famous works)
UPDATE artwork
SET times_loaded             = 42,
    times_spymaster_pick     = 18,
    times_correct_guess  = 12,
    times_bad_guess          = 4
WHERE id IN (
             UUID_TO_BIN('a38e2828-ec6f-ece1-a30f-70243449197b'), -- Stacks of Wheat (Monet)
             UUID_TO_BIN('6644829f-f292-c5c4-a73c-0356a6fdbf0d'), -- The Bedroom (Van Gogh)
             UUID_TO_BIN('3c27b499-af56-f0d5-93b5-a7f2f1ad5813'), -- Water Lilies (Monet)
             UUID_TO_BIN('2d484387-2509-5e8e-2c43-22f9981972eb'), -- La Grande Jatte (Seurat)
             UUID_TO_BIN('2fa24f36-cc26-41b6-4b49-12bba2a6c1c8'), -- The Great Wave (Hokusai)
             UUID_TO_BIN('f8fd76e9-c396-5678-36ed-6a348c904d27')  -- Paris Street Rainy Day (Caillebotte)
    );

-- Tier A: popular
UPDATE artwork
SET times_loaded             = 28,
    times_spymaster_pick     = 11,
    times_correct_guess  = 7,
    times_bad_guess          = 3
WHERE id IN (
             UUID_TO_BIN('47fd1564-93f5-f30b-7786-013421133b4a'),
             UUID_TO_BIN('defb4004-b500-218d-3d9b-9a02423f097d'),
             UUID_TO_BIN('47c5bcb8-62ef-e5d7-55e7-f5121f409a30'),
             UUID_TO_BIN('3a608f55-d76e-fa96-d0b1-0789fbc48f1e'),
             UUID_TO_BIN('0f1cc0e0-e42e-be16-3f71-2022da38cb93'),
             UUID_TO_BIN('d0ff5b36-bb38-b156-6042-5c8545352c2f'),
             UUID_TO_BIN('52ac8996-3460-cf71-cb42-5c4d0aa29b74'),
             UUID_TO_BIN('65db9e21-83c3-1cc6-7240-1e1996d87f52'),
             UUID_TO_BIN('d0e36029-27fc-bf4e-357a-55cfbaf7bdfd'),
             UUID_TO_BIN('d7df2633-3b40-f570-c906-211503a37cde')
    );

-- Tier B: moderate
UPDATE artwork
SET times_loaded             = 15,
    times_spymaster_pick     = 6,
    times_correct_guess  = 3,
    times_bad_guess          = 2
WHERE id IN (
             UUID_TO_BIN('fec5d023-0b46-45df-eb57-cbf64e24121d'),
             UUID_TO_BIN('0675f9a9-1a7b-c90a-3bb6-7f7be2afb678'),
             UUID_TO_BIN('fae6d055-fe95-17e2-a00a-c2307ace531c'),
             UUID_TO_BIN('416b87c8-515f-0066-5c44-b7562162f2c0'),
             UUID_TO_BIN('aa870b0d-5a1b-660a-6dc6-56c12109cf6e'),
             UUID_TO_BIN('93270aaf-16a5-1604-c6c8-0f3c003eb7a1'),
             UUID_TO_BIN('8641479e-c93e-f1a8-9925-19be061706da'),
             UUID_TO_BIN('321c45f5-22a3-84a2-44cc-cf66642d4cf2'),
             UUID_TO_BIN('2ae64c8a-266d-470a-a25a-c83127564636'),
             UUID_TO_BIN('cf50f037-5fb2-e197-0e56-3ae701edb3e2'),
             UUID_TO_BIN('237c25a2-6051-a8e7-1610-a01938d4deab'),
             UUID_TO_BIN('5dca7347-c6dc-24dd-d073-d705b9cdc575'),
             UUID_TO_BIN('0f951635-312c-0829-451b-553f461c5518')
    );

-- Tier C: rare
UPDATE artwork
SET times_loaded             = 7,
    times_spymaster_pick     = 2,
    times_correct_guess  = 1,
    times_bad_guess          = 1
WHERE id IN (
             UUID_TO_BIN('d4ca6321-8656-3d3f-a362-2ee297b2b813'),
             UUID_TO_BIN('77951d0c-d781-eac6-f4ff-f13cc5cfc0cf'),
             UUID_TO_BIN('f0b3ff64-d68e-3fd2-ffc9-5470eb9fea6e'),
             UUID_TO_BIN('72227c9e-413c-8930-477d-5a90e0a2123c'),
             UUID_TO_BIN('239e0072-de08-cb26-1484-4776fc27b0fb'),
             UUID_TO_BIN('7abbfd97-fb03-2366-cfe5-b53738a3d93e'),
             UUID_TO_BIN('d9bde524-38b2-4262-3338-e4d06a50746d'),
             UUID_TO_BIN('d63b9402-c42b-cce3-aa63-6d213ad621cd'),
             UUID_TO_BIN('5490fd49-dd52-4fd7-7d41-d871dcffae55'),
             UUID_TO_BIN('4822cd01-44ac-041a-36b8-c0542377b750'),
             UUID_TO_BIN('9365a024-75ce-75e9-756f-1c2c96eadec9'),
             UUID_TO_BIN('4e37b182-cc1e-ef86-3eb2-de7d5b9db717')
    );

-- Tier D: very rare (edge-case data for dashboard)
UPDATE artwork
SET times_loaded             = 3,
    times_spymaster_pick     = 1,
    times_correct_guess  = 0,
    times_bad_guess          = 1
WHERE id IN (
             UUID_TO_BIN('b146368d-a855-63ac-6115-04b835c60bb0'),
             UUID_TO_BIN('62fd0e14-d7fe-0d01-ab00-1eed84b4f294'),
             UUID_TO_BIN('83092988-7180-21d1-b8a3-cf39ddf39a68'),
             UUID_TO_BIN('f0447c50-f4c9-ecb2-2695-22669e1469cd'),
             UUID_TO_BIN('38bb6be4-223e-af7a-7e18-f9ad0c388d3c'),
             UUID_TO_BIN('67b46720-1589-0d13-3597-f03c7b618094'),
             UUID_TO_BIN('fa81bfb9-5a07-5e3c-33b4-4efde68981c8')
    );

-- Seed: card table
-- Each game has 12 PLAYABLE + 4 ASSASSIN, matching CardService.cardRandomizer.
-- Spymaster picks must be PLAYABLE (CardService rejects ASSASSIN picks).
INSERT IGNORE INTO card (id, game_id, artwork_id, is_spymaster_pick, type)
VALUES
    (UUID_TO_BIN('05316ced-9f2b-42a1-8e24-16e2f519e257'), 1, UUID_TO_BIN('a38e2828-ec6f-ece1-a30f-70243449197b'), false, 'PLAYABLE'),
    (UUID_TO_BIN('0779a09c-6f86-4d14-a0fb-40de1013f9c9'), 1, UUID_TO_BIN('6644829f-f292-c5c4-a73c-0356a6fdbf0d'), true,  'PLAYABLE'),
    (UUID_TO_BIN('0daad8c3-35a5-4eed-8d4f-6628e1ea2654'), 1, UUID_TO_BIN('47fd1564-93f5-f30b-7786-013421133b4a'), false, 'PLAYABLE'),
    (UUID_TO_BIN('226181d7-8122-4567-ba90-c885ae9cb3ba'), 1, UUID_TO_BIN('defb4004-b500-218d-3d9b-9a02423f097d'), false, 'PLAYABLE'),
    (UUID_TO_BIN('2e41d850-ea3c-452c-a803-54529c9e41d3'), 1, UUID_TO_BIN('fec5d023-0b46-45df-eb57-cbf64e24121d'), false, 'ASSASSIN'),
    (UUID_TO_BIN('3d0ea3a9-2ac7-4cf7-a61f-a1d7bee37082'), 1, UUID_TO_BIN('3c27b499-af56-f0d5-93b5-a7f2f1ad5813'), false, 'PLAYABLE'),
    (UUID_TO_BIN('4fdcd66f-a7ce-45c0-84d4-f683e57d6d7a'), 1, UUID_TO_BIN('2d484387-2509-5e8e-2c43-22f9981972eb'), false, 'PLAYABLE'),
    (UUID_TO_BIN('5a9988af-d2a2-4ac6-8298-ad88824b520b'), 1, UUID_TO_BIN('47c5bcb8-62ef-e5d7-55e7-f5121f409a30'), true,  'PLAYABLE'),
    (UUID_TO_BIN('71e7b296-2147-4789-8a2c-4f06c99e8319'), 1, UUID_TO_BIN('0675f9a9-1a7b-c90a-3bb6-7f7be2afb678'), false, 'ASSASSIN'),
    (UUID_TO_BIN('7608ed35-f502-4558-907b-94a18eaaf695'), 1, UUID_TO_BIN('f8fd76e9-c396-5678-36ed-6a348c904d27'), false, 'PLAYABLE'),
    (UUID_TO_BIN('7e25d3bf-d972-4cd7-adf8-0b1713956dd9'), 1, UUID_TO_BIN('fae6d055-fe95-17e2-a00a-c2307ace531c'), false, 'PLAYABLE'),
    (UUID_TO_BIN('887d2aff-7dff-478e-859f-fe2b5c077616'), 1, UUID_TO_BIN('2fa24f36-cc26-41b6-4b49-12bba2a6c1c8'), false, 'ASSASSIN'),
    (UUID_TO_BIN('89374af2-56f4-4209-9001-46af26a9a346'), 1, UUID_TO_BIN('3a608f55-d76e-fa96-d0b1-0789fbc48f1e'), false, 'PLAYABLE'),
    (UUID_TO_BIN('a71fe056-43fe-44c3-ad93-5e107646ff1d'), 1, UUID_TO_BIN('0f1cc0e0-e42e-be16-3f71-2022da38cb93'), false, 'PLAYABLE'),
    (UUID_TO_BIN('c35890a2-f2ce-443f-be38-8cc1297959e0'), 1, UUID_TO_BIN('d0ff5b36-bb38-b156-6042-5c8545352c2f'), true,  'PLAYABLE'),
    (UUID_TO_BIN('c9ca8451-8d89-4030-904a-05f624d97785'), 1, UUID_TO_BIN('52ac8996-3460-cf71-cb42-5c4d0aa29b74'), false, 'ASSASSIN'),
    (UUID_TO_BIN('cd257504-634f-455b-9f50-0004026c717e'), 2, UUID_TO_BIN('65db9e21-83c3-1cc6-7240-1e1996d87f52'), false, 'ASSASSIN'),
    (UUID_TO_BIN('f36df9fe-a662-459a-8a91-b63de19f7a57'), 2, UUID_TO_BIN('d0e36029-27fc-bf4e-357a-55cfbaf7bdfd'), false, 'ASSASSIN'),
    (UUID_TO_BIN('d6c0e2ef-a8bf-429b-b4b1-5f9cd51f9089'), 2, UUID_TO_BIN('d7df2633-3b40-f570-c906-211503a37cde'), false, 'ASSASSIN'),
    (UUID_TO_BIN('bcd4248f-4301-4579-a469-bc9485b90600'), 2, UUID_TO_BIN('416b87c8-515f-0066-5c44-b7562162f2c0'), false, 'PLAYABLE'),
    (UUID_TO_BIN('b80155e2-2b30-4a4f-bc86-2c21ec424a25'), 2, UUID_TO_BIN('aa870b0d-5a1b-660a-6dc6-56c12109cf6e'), false, 'ASSASSIN'),
    (UUID_TO_BIN('f0942a86-c00b-4f85-9dd9-3f5f43b36f77'), 2, UUID_TO_BIN('93270aaf-16a5-1604-c6c8-0f3c003eb7a1'), false, 'PLAYABLE'),
    (UUID_TO_BIN('aa33538b-7ab4-4269-87cf-de48c5c36ce8'), 2, UUID_TO_BIN('8641479e-c93e-f1a8-9925-19be061706da'), true,  'PLAYABLE'),
    (UUID_TO_BIN('a25a798b-0c99-4e5b-99a0-9b0b09a3a465'), 2, UUID_TO_BIN('321c45f5-22a3-84a2-44cc-cf66642d4cf2'), false, 'PLAYABLE'),
    (UUID_TO_BIN('7cbce956-1367-443e-bbb6-c3996c75b5d7'), 2, UUID_TO_BIN('2ae64c8a-266d-470a-a25a-c83127564636'), false, 'PLAYABLE'),
    (UUID_TO_BIN('5119f8dc-c10b-4e2c-badb-388585b94c71'), 2, UUID_TO_BIN('cf50f037-5fb2-e197-0e56-3ae701edb3e2'), false, 'ASSASSIN'),
    (UUID_TO_BIN('4fe6eaac-b195-400b-a4cc-14a375cca885'), 2, UUID_TO_BIN('237c25a2-6051-a8e7-1610-a01938d4deab'), false, 'PLAYABLE'),
    (UUID_TO_BIN('4591aa0e-49e9-4e79-b883-b004e9cdd6ae'), 2, UUID_TO_BIN('5dca7347-c6dc-24dd-d073-d705b9cdc575'), true,  'PLAYABLE'),
    (UUID_TO_BIN('4071607c-d838-41e4-af4a-e85f0713831f'), 2, UUID_TO_BIN('0f951635-312c-0829-451b-553f461c5518'), false, 'ASSASSIN'),
    (UUID_TO_BIN('0dc1295c-99c8-499a-bf0b-60a42bf161d8'), 2, UUID_TO_BIN('d4ca6321-8656-3d3f-a362-2ee297b2b813'), true,  'PLAYABLE'),
    (UUID_TO_BIN('a3f5c512-e446-4b35-a7c8-22fcd74c3dc8'), 2, UUID_TO_BIN('77951d0c-d781-eac6-f4ff-f13cc5cfc0cf'), false, 'PLAYABLE'),
    (UUID_TO_BIN('eed5af4c-84b2-428e-9933-b2f97856677c'), 2, UUID_TO_BIN('f0b3ff64-d68e-3fd2-ffc9-5470eb9fea6e'), true,  'PLAYABLE'),
    (UUID_TO_BIN('021b46c8-0211-4463-b45f-f62d86488335'), 3, UUID_TO_BIN('72227c9e-413c-8930-477d-5a90e0a2123c'), true,  'PLAYABLE'),
    (UUID_TO_BIN('0efceed6-5504-4fb0-9412-2a5a6b7e93dc'), 3, UUID_TO_BIN('239e0072-de08-cb26-1484-4776fc27b0fb'), false, 'ASSASSIN'),
    (UUID_TO_BIN('8c394982-39f8-41d6-84d9-700d823d64cc'), 3, UUID_TO_BIN('7abbfd97-fb03-2366-cfe5-b53738a3d93e'), false, 'ASSASSIN'),
    (UUID_TO_BIN('4d0eff4d-0cb1-4223-9203-78282b7537cb'), 3, UUID_TO_BIN('d9bde524-38b2-4262-3338-e4d06a50746d'), false, 'ASSASSIN'),
    (UUID_TO_BIN('5709c820-2a44-4b12-92ec-7b169b6f2729'), 3, UUID_TO_BIN('d63b9402-c42b-cce3-aa63-6d213ad621cd'), true,  'PLAYABLE'),
    (UUID_TO_BIN('6dda92ed-bce2-4483-8ede-c2aff101f703'), 3, UUID_TO_BIN('5490fd49-dd52-4fd7-7d41-d871dcffae55'), true,  'PLAYABLE'),
    (UUID_TO_BIN('74af3481-bf70-4ed6-b8b0-d357043facfa'), 3, UUID_TO_BIN('4822cd01-44ac-041a-36b8-c0542377b750'), true,  'PLAYABLE'),
    (UUID_TO_BIN('9a91a883-303a-4de4-943e-4fc343a9ce1f'), 3, UUID_TO_BIN('9365a024-75ce-75e9-756f-1c2c96eadec9'), false, 'ASSASSIN'),
    (UUID_TO_BIN('9c70792d-bd9d-4652-9795-892318ef70bb'), 3, UUID_TO_BIN('4e37b182-cc1e-ef86-3eb2-de7d5b9db717'), false, 'ASSASSIN'),
    (UUID_TO_BIN('ac94511a-7ef5-482b-a719-eca5f81515c2'), 3, UUID_TO_BIN('b146368d-a855-63ac-6115-04b835c60bb0'), true,  'PLAYABLE'),
    (UUID_TO_BIN('c315728b-201a-41ae-9b8c-49deb09bb291'), 3, UUID_TO_BIN('62fd0e14-d7fe-0d01-ab00-1eed84b4f294'), false, 'ASSASSIN'),
    (UUID_TO_BIN('13f8b684-38fc-4f43-873a-3c28e2f3ec33'), 3, UUID_TO_BIN('83092988-7180-21d1-b8a3-cf39ddf39a68'), false, 'ASSASSIN'),
    (UUID_TO_BIN('ad8786ce-d340-4cca-af29-4d77ad6621bb'), 3, UUID_TO_BIN('f0447c50-f4c9-ecb2-2695-22669e1469cd'), false, 'PLAYABLE'),
    (UUID_TO_BIN('dc196af3-6ca5-4493-b3a4-21e35c8d564e'), 3, UUID_TO_BIN('38bb6be4-223e-af7a-7e18-f9ad0c388d3c'), false, 'ASSASSIN'),
    (UUID_TO_BIN('d6d90125-4526-491c-bf80-d445309d7fab'), 3, UUID_TO_BIN('67b46720-1589-0d13-3597-f03c7b618094'), false, 'PLAYABLE'),
    (UUID_TO_BIN('e6c965c3-ee03-4247-b360-57c25d9a7211'), 3, UUID_TO_BIN('fa81bfb9-5a07-5e3c-33b4-4efde68981c8'), false, 'PLAYABLE');

-- Seed: hint table
INSERT IGNORE INTO hint (id, content, game_id)
VALUES (UUID_TO_BIN('cd5c469d-a519-4972-bc84-d0ec474f312b'), 'vangogh', 1),
       (UUID_TO_BIN('079d8865-d7e1-45c9-8fcf-a0b0914331aa'), 'water', 2),
       (UUID_TO_BIN('3a3e6807-59cd-469e-b418-2d7e277894d3'), 'zittend', 3);