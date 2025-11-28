-- Insert Manufacturers
INSERT INTO manufacturers (brand, origin, website, known_for, key_models) VALUES
-- Australian Made
('Lotus Caravans', 'Australian Made', 'https://www.lotuscaravans.com.au', 'Family-friendly layouts, good off-road capability', 'Trooper, Freelander, Sprint'),
('Track Trailer', 'Australian Made', 'https://www.tracktrailer.com.au', 'Compact off-road designs, innovative layouts', 'Tvan'),
('New Age Caravans', 'Australian Made', 'https://www.newagecaravans.com.au', 'Value pricing, family layouts', 'Manta Ray, Gecko'),
('Jayco', 'Australian Made', 'https://www.jayco.com.au', 'Large manufacturer, good build quality, family-focused', 'Journey Outback, Crosstrak'),
('Jurgens', 'Australian Made', 'https://www.jurgens.com.au', 'Light on weight caravans', 'Lunagazer'),
('Coromal', 'Australian Made', 'https://www.coromal.com.au', 'Australian-made, diverse layouts', 'Adventure Seeker, Soul Seeker, Thrill Seeker'),
('MDC Camper Trailers', 'Australian Made', 'https://www.https://www.marketdirect.com.au/', 'Budget-friendly options, good value', 'Off-road range'),
('Innovative Caravans', 'Australian Made', 'https://www.innovativecaravans.com.au', 'Semi-custom builds, family layouts', 'Custom builds'),
('Paramount Caravans', 'Australian Made', 'https://www.paramountcaravans.com.au', 'Solid construction, family-friendly', 'Off-road family range'),
('Crusader Caravans', 'Australian Made', 'https://www.crusadercaravans.com.au', 'Family layouts, multiple bunk options', 'Musketeer'),
('Kedron Caravans', 'Australian Made', 'https://www.kedron.com.au', 'Premium Australian-made, excellent build quality', 'ATV Series'),
('JAWA Caravans', 'Australian Made', 'https://www.jawacaravans.com.au', 'Off-road capability, rugged construction', 'Outback series'),
('Snowy River Caravans', 'Australian Made', 'https://www.snowyrivercaravans.com.au', 'Lightweight, good for smaller tow vehicles', 'Compact off-road models'),
('Compass Caravans', 'Australian Made', 'https://www.compasscaravans.com.au', 'Custom builds, family-friendly', 'Off-road family range'),
('Fortis Caravans', 'Australian Made', 'https://www.fortiscaravans.com.au', 'Military-grade construction, extreme off-road', 'Offroad range'),
('Goldstream RV', 'Australian Made', 'https://www.goldstreamrv.com.au', 'Off-road focus, family layouts', 'Storm, Tornado'),
('Traveller Caravans', 'Australian Made', 'https://www.travellercaravans.com.au', 'Modern layouts, good value, off-road capable', 'Utopia, Prodigy'),
('Kingston Caravans', 'Australian Made', 'https://www.kingstoncaravans.com.au', 'Off-road focused, family-friendly options', 'Terrain Series'),
('Legend Caravans', 'Australian Made', 'https://www.legendcaravans.com.au', 'Off-road capability', 'Ranger, Explorer'),
-- Imported
('Evernew', 'Imported', 'https://www.evernewcaravans.com.au', 'Budget-friendly imports, good value', 'Family off-road series'),
('Retreat Caravans', 'Imported', 'https://www.retreatcaravans.com.au', 'Mid-range imported option, modern features', 'Hamilton, Fraser'),
('On The Move', 'Imported', 'https://www.onthemove.com.au', 'Growing brand, competitive pricing, off-road focus', 'BLADE, TRAXX'),
('Kingdom Caravans', 'Imported', 'https://www.kingdomcaravans.com.au', 'Budget pricing, family layouts available', 'Off-road family range'),
('Condor Caravans', 'Imported', 'https://www.condorcaravans.com.au', 'Value pricing, family-focused models', 'RV Family Series'),
('Concept Caravans', 'Imported', 'https://www.conceptcaravans.com.au', 'European-style layouts, modern design', 'Ascot, Euro Range'),
('Avida', 'Imported', 'https://www.avida.com.au', 'Mix of imported and local assembly', 'Emerald, Sapphire');

-- Insert High Priority Caravans
INSERT INTO caravans (make, model, origin, price, website, external_length, tare_weight, atm, ball_weight, main_bed, bunk_beds, bunk_type, priority, status, features, notes) VALUES
-- High Priority Models
('Lotus Caravans', 'Trooper 16ft Family', 'Australian Made', 85000, 'https://www.lotuscaravans.com.au/caravans/trooper/', '16ft (4.88m)', 1850, 2500, 220, 'Queen', 2, '2x Singles', 'HIGH', 'RESEARCHING', 'Compact family layout, Australian-made, off-road capable', 'Known for good reputation, check specific family layout availability'),

('Jurgens', 'Lunagazer J17', 'Australian Made', 80000, 'https://www.jurgens.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'HIGH', 'RESEARCHING', 'Light on weight, established brand, family-focused', 'Verify off-road package included'),

('New Age Caravans', 'Manta Ray 16ft Family', 'Australian Made', 75000, 'https://www.newagecaravans.com.au', '16ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'HIGH', 'RESEARCHING', 'Good value, popular family layouts', 'Often under $100k budget'),

('Crusader Caravans', 'Musketeer 17ft Bunk', 'Australian Made', 82000, 'https://www.crusadercaravans.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Bunks', 'HIGH', 'RESEARCHING', 'Known for family layouts with multiple bunk options', 'Musketeer series designed for families'),

('Coromal', 'Adventure Seeker 16.6ft', 'Australian Made', 85000, 'https://www.coromal.com.au', '16.6ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'HIGH', 'RESEARCHING', 'Australian-made, diverse layouts, established brand', 'Confirm hard top on specific model'),

-- Medium Priority Australian-Made
('MDC Camper Trailers', 'Off-Road Family 16ft', 'Australian Made', 62000, 'https://www.https://www.marketdirect.com.au/', '16ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'MEDIUM', 'RESEARCHING', 'Budget-friendly, off-road capable', 'Check build quality in person'),

('Goldstream RV', 'Storm 17ft', 'Australian Made', 82000, 'https://www.goldstreamrv.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Bunks', 'MEDIUM', 'RESEARCHING', 'Off-road focus, family layouts', 'Check for bunk bed configurations'),

('Traveller Caravans', 'Utopia 16ft Family', 'Australian Made', 77000, 'https://www.travellercaravans.com.au', '16ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'MEDIUM', 'RESEARCHING', 'Modern layouts, good value, off-road capable', 'Check which series has bunk beds'),

('Paramount Caravans', 'Family Off-Road 17ft', 'Australian Made', 80000, 'https://www.paramountcaravans.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Bunks', 'MEDIUM', 'RESEARCHING', 'Solid construction, family-focused designs', 'Verify specifications'),

('Track Trailer', 'Tvan Family Room', 'Australian Made', 90000, 'https://www.tracktrailer.com.au/tvan', '16ft', NULL, NULL, NULL, 'Queen', 2, 'Family Room', 'MEDIUM', 'RESEARCHING', 'Compact design, innovative layouts', 'Verify bunks in family room configuration, confirm hard top'),

('Kingston Caravans', 'Terrain 17ft', 'Australian Made', 80000, 'https://www.kingstoncaravans.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'MEDIUM', 'RESEARCHING', 'Off-road focused, family-friendly options', 'Verify specifications'),

('Legend Caravans', 'Ranger 16ft', 'Australian Made', 85000, 'https://www.legendcaravans.com.au', '16ft', NULL, NULL, NULL, 'Queen', 2, '2x Bunks', 'MEDIUM', 'RESEARCHING', 'Off-road capability, Australian-made', 'Verify bunk configurations available'),

('Compass Caravans', 'Off-Road Family 17ft', 'Australian Made', 82000, 'https://www.compasscaravans.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'MEDIUM', 'RESEARCHING', 'Custom builds, family-friendly layouts', 'May offer bunk configurations'),

-- Budget-Friendly Imported Brands
('Evernew', 'E16 Family Offroad', 'Imported', 57000, 'https://www.evernewcaravans.com.au', '16ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'BUDGET', 'RESEARCHING', 'Budget-friendly, significant cost savings', 'Inspect build quality carefully, check warranty. Well under budget'),

('Retreat Caravans', 'Hamilton 17ft', 'Imported', 67000, 'https://www.retreatcaravans.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Bunks', 'BUDGET', 'RESEARCHING', 'Mid-range imported option, modern features', 'Check for family bunk layouts'),

('On The Move', 'BLADE 16ft Family', 'Imported', 62000, 'https://www.onthemove.com.au', '16ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'BUDGET', 'RESEARCHING', 'Growing brand, competitive pricing, off-road focus', 'Newer brand, research owner reviews'),

('Kingdom Caravans', 'Off-Road Family 17ft', 'Imported', 55000, 'https://www.kingdomcaravans.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Bunks', 'BUDGET', 'RESEARCHING', 'Budget pricing, family layouts available', 'Inspect quality carefully, lower price point'),

('Condor Caravans', 'RV Family 16ft', 'Imported', 60000, 'https://www.condorcaravans.com.au', '16ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'BUDGET', 'RESEARCHING', 'Value pricing, family-focused models', 'Check build quality and warranty'),

('Concept Caravans', 'Ascot 17ft Family', 'Imported', 72000, 'https://www.conceptcaravans.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Bunks', 'BUDGET', 'RESEARCHING', 'European-style layouts, modern design', 'Check off-road variants, verify bunk options'),

-- Premium Options
('Jayco', 'Journey Outback 17ft', 'Australian Made', 95000, 'https://www.jayco.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'PREMIUM', 'RESEARCHING', 'Premium quality, excellent reputation, family-focused', 'May exceed budget depending on options'),

('Kedron', 'ATV 16ft Family', 'Australian Made', 105000, 'https://www.kedron.com.au', '16ft', NULL, NULL, NULL, 'Queen', 2, '2x Bunks', 'PREMIUM', 'RESEARCHING', 'Premium Australian-made, excellent build quality', 'Likely over budget, but check compact models'),

('JAWA Caravans', 'Outback 17ft', 'Australian Made', 95000, 'https://www.jawacaravans.com.au', '17ft', NULL, NULL, NULL, 'Queen', 2, '2x Singles', 'PREMIUM', 'RESEARCHING', 'Rugged off-road capability, Australian-made', 'Check if within budget');

-- Example caravan from comparison table
INSERT INTO caravans (make, model, origin, price, website, external_length, external_width, external_height, tare_weight, atm, ball_weight, main_bed, bunk_beds, bunk_type, priority, status, features, notes) VALUES
('Lotus Caravans', 'Trooper 16 Example', 'Australian Made', 85000, 'https://www.lotuscaravans.com.au', '16ft (4.88m)', '7.2ft (2.2m)', '9ft (2.75m)', 1850, 2500, 220, 'Queen', 2, '2x Singles', 'HIGH', 'RESEARCHING', '300W Solar, 200Ah AGM Battery, Single Axle', 'Example data only - verify with dealer');
