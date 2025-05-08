-- ========================================
-- 1) LOCATIONS
-- ========================================
INSERT INTO locations (name, country, city, location_code) VALUES
('Sabiha Gökçen Airport',    'Turkey',   'Istanbul', 'SAW'),
('Istanbul Airport',         'Turkey',   'Istanbul', 'IST'),
('Taksim City Center',       'Turkey',   'Istanbul', 'CCIST'),
('London Heathrow',          'UK',       'London',   'LHR'),
('Wembley Stadium',          'UK',       'London',   'WEB'),
('Bursa Yenişehir Airport',  'Turkey',   'Bursa',    'YEI'),
('Frankfurt Airport',        'Germany',  'Frankfurt','FRA'),
('Munich Airport',           'Germany',  'Munich',   'MUC'),
('Brandenburg Gate',         'Germany',  'Berlin',   'BRLN');

-- ========================================
-- 2) TRANSPORTATIONS
--    (origin_location / destination_location join on location_code)
-- ========================================
INSERT INTO transportations (origin_location, destination_location, transportation_type) VALUES
-- original 6
('CCIST','IST','BUS'),
('CCIST','IST','UBER'),
('CCIST','IST','SUBWAY'),
('IST',  'LHR','FLIGHT'),
('LHR',  'WEB','UBER'),
('LHR',  'WEB','BUS'),

-- 9 more for richer graph
('SAW','YEI','BUS'),
('SAW','YEI','UBER'),
('YEI','IST','UBER'),
('IST','MUC','FLIGHT'),
('MUC','FRA','FLIGHT'),
('FRA','BRLN','SUBWAY'),
('FRA','BRLN','BUS'),
('BRLN','LHR','UBER'),
('IST','BRLN','FLIGHT');

-- ========================================
-- 3) OPERATING DAYS
--    (transportation_operating_days)
--    reference IDs are assigned in insertion order: the first INSERT above creates IDs 1–6,
--    the second block creates IDs 7–15.
-- ========================================
INSERT INTO transportation_operating_days (transportation_id, day_of_week) VALUES
-- original 6
(1,2),(1,3),(1,5),
(2,2),(2,3),(2,7),
(3,3),(3,5),
(4,2),(4,3),(4,7),
(5,3),(5,5),
(6,1),(6,4),(6,6),

-- **add Thursday (4) to the two missing originals (IDs 4 & 5):**
(4,4),
(5,4),

-- for the 9 new transportations (IDs 7–15):
(7,1),(7,4),(7,6),         -- SAW→YEI (BUS)
(8,2),(8,5),              -- SAW→YEI (UBER)
(9,3),(9,7),              -- YEI→IST (UBER)
(10,1),(10,2),(10,3),(10,4),(10,5),  -- IST→MUC (FLIGHT)
(11,2),(11,4),(11,6),     -- MUC→FRA (FLIGHT)
(12,1),(12,3),(12,5),     -- FRA→BRLN (SUBWAY)
(13,2),(13,6),(13,7),     -- FRA→BRLN (BUS)
(14,4),(14,5),            -- BRLN→LHR (UBER)
(15,3),(15,4),(15,5);
