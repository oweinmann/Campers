# Research Caravans Command

You are performing a comprehensive caravan research workflow. Follow these steps:

## Step 1: Query Exclusion Lists

First, query the database to get all excluded caravans and manufacturers:

```sql
-- Get all excluded caravans (make and model combinations to exclude)
SELECT make, model FROM excludedCaravans;

-- Get all excluded manufacturers (entire brands to exclude)
SELECT brand FROM excludedManufacturers;
```

Store these lists and ensure NO caravans from these makes/models or manufacturers are included in your research.

## Step 2: Review Requirements

From mdFiles/readme.md, the caravan requirements are:
- **Condition**: New only (not used)
- **Length**: Minimum 15 feet, maximum 18 feet
- **Price**: Maximum $100,000 AUD
- **Sleeping**: At least 2 bunk beds for children
- **Outdoor Kitchen**: Must have an outdoor kitchen
- **Roof**: Hard top only (no hybrid roofs)

## Step 3: Clear Caravans Table

Execute SQL to clear the existing caravans table:

```sql
DELETE FROM caravans;
```

## Step 4: Web Search for Caravans

Perform web searches focusing on:
- Australian off-road caravan manufacturers
- New caravans for sale in Australia
- Family-friendly off-road caravans with bunk beds
- Off-road caravans with outdoor kitchens
- Caravans 15-18 feet in length

**IMPORTANT**:
- Only research caravans that meet ALL requirements
- Skip any manufacturers in the excludedManufacturers list
- Skip any make/model combinations in the excludedCaravans list
- Focus on finding detailed specifications for each caravan

Gather these specifications for each caravan:
- Make and model
- External dimensions (length, width, height)
- Weight (Tare, ATM, ball weight, GTM)
- Axle configuration (single or twin axle)
- Solar panel size/capacity
- Battery size/capacity
- Main bed dimensions
- Bunk bed count and type
- Price
- Features (outdoor kitchen, etc.)
- Origin (Australian Made or Imported)
- Website/source URL
- Any additional notes

## Step 5: Insert Results into Database

For each caravan found, insert into the caravans table with SQL like:

```sql
INSERT INTO caravans (
    make, model, origin, price, website,
    external_length, external_width, external_height,
    tare_weight, atm, ball_weight, gtm,
    main_bed, bunk_beds, bunk_type,
    priority, status, features, notes
) VALUES (
    'Make Name', 'Model Name', 'Australian Made', 85000, 'https://example.com',
    '16ft (4.88m)', '7.2ft (2.2m)', '9ft (2.75m)',
    1850, 2500, 220, 2300,
    'Queen', 2, '2x Singles',
    'HIGH', 'RESEARCHING', 'Outdoor kitchen, 300W solar, 200Ah battery', 'Notes here'
);
```

## Step 6: Summary

After completing the research, provide a summary:
- Total number of caravans found and added
- Breakdown by manufacturer
- Price range of caravans found
- Any notable findings or recommendations
- List of any manufacturers/models that were excluded

Remember: Quality over quantity. Focus on caravans that genuinely meet all requirements.
