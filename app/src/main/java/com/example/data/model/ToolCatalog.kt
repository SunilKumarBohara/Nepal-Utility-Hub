package com.example.data.model

object ToolCatalog {
    val allTools: List<ToolItem> = listOf(
        // FINANCE
        ToolItem(
            slug = "emi-calculator",
            name = "EMI Calculator",
            category = ToolCategory.FINANCE,
            description = "Calculate Equated Monthly Installment for home, personal, or vehicle loans in Nepal with amortization schedule.",
            isPopular = true,
            keywords = listOf("emi", "loan", "bank", "interest", "installment", "npr", "mortgage"),
            formulaExplanation = "EMI is calculated using reducing balance interest formula.",
            formulaFormula = "E = P × r × (1 + r)^n / ((1 + r)^n - 1)",
            workedExample = "For NPR 10,00,000 at 10% annual interest for 5 years (60 months), monthly EMI is NPR 21,247.",
            faqs = listOf(
                "What is EMI?" to "Equated Monthly Installment is a fixed payment amount made by a borrower to a lender at a specified date each calendar month.",
                "How does loan tenure affect EMI?" to "A longer tenure reduces your monthly EMI but increases the total interest paid over the life of the loan."
            )
        ),
        ToolItem(
            slug = "loan-calculator",
            name = "Loan Calculator",
            category = ToolCategory.FINANCE,
            description = "Calculate total loan payments, interest charges, and monthly commitments across any loan principal.",
            isPopular = true,
            keywords = listOf("loan", "interest", "bank", "principal", "borrow"),
            formulaExplanation = "Computes interest on fixed or reducing terms.",
            formulaFormula = "Total = Principal + Total Interest",
            workedExample = "NPR 5,00,000 borrowed at 12% for 3 years results in total repayment of NPR 5,97,858.",
            faqs = listOf(
                "Can I prepay my loan in Nepal?" to "Yes, most Nepali commercial banks allow prepayment with minimal or zero penalty after 1-2 years."
            )
        ),
        ToolItem(
            slug = "sip-calculator",
            name = "SIP Calculator",
            category = ToolCategory.FINANCE,
            description = "Calculate estimated returns on Systematic Investment Plans in Nepali mutual funds and equity schemes.",
            isPopular = true,
            keywords = listOf("sip", "investment", "mutual fund", "returns", "shares", "nepse"),
            formulaExplanation = "Calculates compounded future value of monthly recurring investments.",
            formulaFormula = "M = P × ({[1 + i]^n - 1} / i) × (1 + i)",
            workedExample = "Investing NPR 5,000 monthly for 10 years at 12% expected annual return yields NPR 11,61,695 (Invested: NPR 6,00,000).",
            faqs = listOf(
                "Are returns from mutual funds in Nepal guaranteed?" to "No, returns depend on the stock market (NEPSE) performance and fund asset allocation."
            )
        ),
        ToolItem(
            slug = "simple-interest",
            name = "Simple Interest Calculator",
            category = ToolCategory.FINANCE,
            description = "Calculate simple interest on deposits, co-operative savings, and informal lending.",
            isPopular = false,
            keywords = listOf("simple interest", "si", "ptr", "loan", "sahakari"),
            formulaExplanation = "Interest calculated solely on the original principal amount.",
            formulaFormula = "I = (P × T × R) / 100",
            workedExample = "Principal NPR 1,00,000 for 2 years at 9% per annum gives Interest = NPR 18,000.",
            faqs = listOf(
                "Where is simple interest used in Nepal?" to "Commonly used in fixed deposit interest payouts and agricultural loans."
            )
        ),
        ToolItem(
            slug = "compound-interest",
            name = "Compound Interest Calculator",
            category = ToolCategory.FINANCE,
            description = "Calculate interest on interest with quarterly, semi-annual, or annual compounding frequencies.",
            isPopular = false,
            keywords = listOf("compound interest", "ci", "growth", "wealth"),
            formulaExplanation = "Interest calculated on the initial principal and the accumulated interest of previous periods.",
            formulaFormula = "A = P(1 + r/n)^(nt)",
            workedExample = "NPR 1,00,000 invested at 10% compounded annually for 5 years matures to NPR 1,61,051.",
            faqs = listOf(
                "How often do Nepali banks compound interest on savings?" to "Most Nepali banks compound interest quarterly on savings accounts."
            )
        ),
        ToolItem(
            slug = "salary-calculator",
            name = "Salary & Tax Calculator",
            category = ToolCategory.FINANCE,
            description = "Calculate net take-home pay, Social Security Fund (SSF), and Nepal income tax brackets (Single / Married).",
            isPopular = true,
            keywords = listOf("salary", "tax", "income tax", "ssf", "cit", "provident fund", "pay"),
            formulaExplanation = "Calculates progressive slab tax based on Nepal Inland Revenue Department (IRD) regulations.",
            formulaFormula = "Tax = Slab1 (1%) + Slab2 (10%) + Slab3 (20%) + Slab4 (30%) + Surcharge",
            workedExample = "For an individual earning NPR 80,000 gross monthly, net pay after SSF and IRD tax deductions is calculated accurately.",
            faqs = listOf(
                "Is SSF deduction mandatory in Nepal?" to "Yes, for formal private sector employees, 11% employee contribution and 20% employer contribution is standard."
            )
        ),
        ToolItem(
            slug = "profit-loss",
            name = "Profit & Loss Calculator",
            category = ToolCategory.FINANCE,
            description = "Calculate profit or loss percentage and absolute amounts for trading and retail business.",
            isPopular = false,
            keywords = listOf("profit", "loss", "margin", "trading", "retail"),
            formulaExplanation = "Determines profitability relative to Cost Price.",
            formulaFormula = "Profit % = ((Selling Price - Cost Price) / Cost Price) × 100",
            workedExample = "Cost NPR 1,200 sold at NPR 1,500 gives 25% profit margin.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "discount-calculator",
            name = "Discount Calculator",
            category = ToolCategory.FINANCE,
            description = "Quickly calculate savings and final price after percentage discounts and festival sales.",
            isPopular = true,
            keywords = listOf("discount", "sale", "dashain offer", "savings", "daraz"),
            formulaExplanation = "Subtracts discount rate from original price.",
            formulaFormula = "Final = Price × (1 - Discount/100)",
            workedExample = "NPR 4,500 jacket with 20% Dashain discount costs NPR 3,600 (You save NPR 900).",
            faqs = listOf()
        ),
        ToolItem(
            slug = "investment-return",
            name = "Investment Return (ROI) Calculator",
            category = ToolCategory.FINANCE,
            description = "Calculate Return on Investment (ROI) and annualized returns on real estate, stocks, or business ventures.",
            isPopular = false,
            keywords = listOf("roi", "return", "investment", "cagr", "shares"),
            formulaExplanation = "Measures the gain or loss generated on an investment relative to its cost.",
            formulaFormula = "ROI = ((Final Value - Initial Investment) / Initial Investment) × 100",
            workedExample = "An investment of NPR 2,00,000 sold for NPR 2,70,000 yields a 35% ROI.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "loan-affordability",
            name = "Loan Affordability Calculator",
            category = ToolCategory.FINANCE,
            description = "Determine the maximum loan amount you qualify for based on monthly income and debt-to-income ratio in Nepal.",
            isPopular = false,
            keywords = listOf("affordability", "eligibility", "home loan", "income"),
            formulaExplanation = "Based on NRB's 50% Debt Service to Gross Income (DTI) regulatory cap.",
            formulaFormula = "Max Monthly EMI = Monthly Income × 0.50 - Existing EMIs",
            workedExample = "Monthly salary NPR 1,00,000 qualifies for maximum EMI of approx NPR 50,000.",
            faqs = listOf(
                "What is NRB's DTI guideline?" to "Nepal Rastra Bank mandates that personal and home loan EMIs should generally not exceed 50% of verifiable monthly gross income."
            )
        ),

        // STUDENT TOOLS
        ToolItem(
            slug = "gpa-calculator",
            name = "GPA Calculator",
            category = ToolCategory.STUDENT,
            description = "Calculate Grade Point Average (GPA) for Tribhuvan University (TU), Pokhara University (PU), Kathmandu University (KU), and NEB +2.",
            isPopular = true,
            keywords = listOf("gpa", "grades", "student", "neb", "tu", "pu", "ku", "see"),
            formulaExplanation = "Calculates weighted average of grade points multiplied by course credit hours.",
            formulaFormula = "GPA = Σ(Grade Points × Credit Hours) / Σ(Credit Hours)",
            workedExample = "Adding 5 subjects with 3 credits each and grades A, B+, A-, A, B yields GPA 3.53.",
            faqs = listOf(
                "How is GPA calculated in NEB?" to "NEB uses letter grades A+ (4.0), A (3.6), B+ (3.2), B (2.8), C+ (2.4), C (2.0), D (1.6), and NG (Not Graded)."
            )
        ),
        ToolItem(
            slug = "cgpa-calculator",
            name = "CGPA Calculator",
            category = ToolCategory.STUDENT,
            description = "Calculate Cumulative GPA across multiple semesters or academic years.",
            isPopular = true,
            keywords = listOf("cgpa", "semester", "college", "degree", "bachelor"),
            formulaExplanation = "Sum of (Semester GPA × Semester Credits) divided by Total Credits.",
            formulaFormula = "CGPA = Σ(Semester GPA × Credits) / Σ(Total Credits)",
            workedExample = "Semester 1 (GPA 3.4, 18 credits) & Semester 2 (GPA 3.7, 18 credits) yields CGPA 3.55.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "gpa-to-percentage",
            name = "GPA to Percentage Converter",
            category = ToolCategory.STUDENT,
            description = "Convert GPA to equivalent percentage using standard university formulas (TU, KU, PU, and general scale).",
            isPopular = false,
            keywords = listOf("gpa to percentage", "convert gpa", "percentage", "marks"),
            formulaExplanation = "Standard TU formula multiplies GPA by 25 or uses grading scale bands.",
            formulaFormula = "Percentage ≈ (GPA / 4.0) × 100 or TU Conversion Table",
            workedExample = "GPA 3.6 on a 4.0 scale equates to approximately 90%.",
            faqs = listOf(
                "Do all universities use the same formula?" to "No, conversion guidelines differ. TU, KU, and foreign universities have official conversion transcripts."
            )
        ),
        ToolItem(
            slug = "attendance-calculator",
            name = "Attendance Calculator",
            category = ToolCategory.STUDENT,
            description = "Track your class attendance percentage and calculate how many more classes you must attend or can safely miss.",
            isPopular = true,
            keywords = listOf("attendance", "college", "bunk", "classes", "percentage"),
            formulaExplanation = "Calculates current % and determines shortfall or safe margin to maintain requirement (e.g. 75% or 80%).",
            formulaFormula = "Attendance % = (Classes Attended / Total Classes) × 100",
            workedExample = "If attended 42 out of 50 classes (84%), with a 75% requirement, you can miss up to 6 upcoming classes.",
            faqs = listOf(
                "What is the minimum attendance required in Nepali colleges?" to "Most bachelor programs in TU and PU mandate a minimum of 75% to 80% attendance to qualify for board exams."
            )
        ),
        ToolItem(
            slug = "study-timer",
            name = "Study Timer (Pomodoro)",
            category = ToolCategory.STUDENT,
            description = "Boost academic focus with a customizable Pomodoro study timer, short & long breaks, and completion sounds.",
            isPopular = true,
            keywords = listOf("pomodoro", "timer", "study", "focus", "clock"),
            formulaExplanation = "25 minutes of deep focus followed by 5 minutes of mindful rest.",
            formulaFormula = "25m Study + 5m Break × 4 Cycles + 15m Long Break",
            workedExample = "Run structured 25-minute study sprints with haptic vibration alerts.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "word-counter",
            name = "Word & Character Counter",
            category = ToolCategory.STUDENT,
            description = "Count words, characters, sentences, paragraphs, and estimated reading time for essays and assignments.",
            isPopular = false,
            keywords = listOf("word count", "character count", "essay", "article", "thesis"),
            formulaExplanation = "Splits text by whitespace and sentence delimiters.",
            formulaFormula = "Reading time = Total Words / 200 words per minute",
            workedExample = "Instantly analyzes typed or pasted text with live character and word counters.",
            faqs = listOf()
        ),

        // HEALTH & LIFESTYLE
        ToolItem(
            slug = "bmi-calculator",
            name = "BMI Calculator",
            category = ToolCategory.HEALTH,
            description = "Calculate Body Mass Index (BMI), ideal weight range, and WHO classification for adults.",
            isPopular = true,
            keywords = listOf("bmi", "weight", "height", "health", "diet", "fitness"),
            formulaExplanation = "BMI is calculated by dividing body weight in kg by height in meters squared.",
            formulaFormula = "BMI = Weight (kg) / (Height (m))²",
            workedExample = "Height 170 cm (1.7m) and Weight 68 kg gives BMI 23.53 (Normal Weight).",
            faqs = listOf(
                "What is a healthy BMI range?" to "18.5 - 24.9 is considered Normal weight, 25.0 - 29.9 Overweight, and 30+ Obese."
            )
        ),
        ToolItem(
            slug = "calorie-calculator",
            name = "Calorie & BMR Calculator",
            category = ToolCategory.HEALTH,
            description = "Estimate your Basal Metabolic Rate (BMR) and Total Daily Energy Expenditure (TDEE) based on activity level.",
            isPopular = false,
            keywords = listOf("calorie", "bmr", "tdee", "diet", "nutrition", "gym"),
            formulaExplanation = "Uses the Mifflin-St Jeor equation for basal metabolic expenditure.",
            formulaFormula = "Men: 10W + 6.25H - 5A + 5 | Women: 10W + 6.25H - 5A - 161",
            workedExample = "A 25-year-old male of 70kg and 175cm has a BMR of ~1,680 calories/day.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "age-calculator",
            name = "Age Calculator",
            category = ToolCategory.HEALTH,
            description = "Calculate exact age in years, months, days, total days lived, and countdown to your next birthday.",
            isPopular = true,
            keywords = listOf("age", "birthday", "dob", "birth", "years", "calendar"),
            formulaExplanation = "Calculates exact calendar difference between birth date and today.",
            formulaFormula = "Exact Age = Current Date - Date of Birth",
            workedExample = "Born 15 Jan 1998 on today's date calculates years, months, days and total days lived.",
            faqs = listOf()
        ),

        // DATE & TIME
        ToolItem(
            slug = "nepali-date-converter",
            name = "Nepali Date Converter (BS ↔ AD)",
            category = ToolCategory.DATE_TIME,
            description = "Accurate Bikram Sambat (वि.सं.) to Gregorian (ई.सं.) calendar conversion with Tithi and weekday.",
            isPopular = true,
            keywords = listOf("bikram sambat", "bs to ad", "ad to bs", "nepali date", "miti", "patro"),
            formulaExplanation = "Converts between solar Gregorian calendar and lunar-solar Bikram Sambat calendar.",
            formulaFormula = "Day Offset Mapping via Nepal Calendar Table",
            workedExample = "Baisakh 1, 2081 BS corresponds to April 13, 2024 AD.",
            faqs = listOf(
                "Why do Nepali months have varying days?" to "Nepali months vary between 29 to 32 days each year based on astronomical solar transit across zodiac signs."
            )
        ),
        ToolItem(
            slug = "date-difference",
            name = "Date Difference Calculator",
            category = ToolCategory.DATE_TIME,
            description = "Find the exact number of days, weeks, months, and years between two calendar dates.",
            isPopular = false,
            keywords = listOf("date diff", "days between", "duration", "calendar"),
            formulaExplanation = "Computes duration between start and end dates.",
            formulaFormula = "Difference = End Date - Start Date (in days)",
            workedExample = "Difference between 1 Jan 2025 and 15 Oct 2025 is 287 days.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "add-subtract-days",
            name = "Add / Subtract Days",
            category = ToolCategory.DATE_TIME,
            description = "Add or subtract days, weeks, or months to any start date to find project deadlines or notice periods.",
            isPopular = false,
            keywords = listOf("add days", "subtract days", "deadline", "date math"),
            formulaExplanation = "Shifts calendar date by specified integer days.",
            formulaFormula = "Target Date = Base Date ± N Days",
            workedExample = "Today + 45 days calculates exact future date.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "stopwatch",
            name = "Stopwatch & Lap Counter",
            category = ToolCategory.DATE_TIME,
            description = "High precision digital stopwatch with lap recording and reset controls.",
            isPopular = false,
            keywords = listOf("stopwatch", "timer", "laps", "sports", "running"),
            formulaExplanation = "Real-time millisecond elapsed timer.",
            formulaFormula = "Elapsed = Current Time - Start Time",
            workedExample = "Precision timing with split-second lap intervals.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "nepal-time-converter",
            name = "Nepal Time Converter (NPT)",
            category = ToolCategory.DATE_TIME,
            description = "Convert Nepal Standard Time (UTC+5:45) to USA, UK, Australia, Gulf, and other global timezones.",
            isPopular = true,
            keywords = listOf("nepal time", "npt", "timezone", "gmt", "utc+5:45", "kathmandu"),
            formulaExplanation = "Nepal Standard Time is GMT +5 hours 45 minutes, calculated from Mt. Gaurishankar meridian.",
            formulaFormula = "NPT = UTC + 5h 45m",
            workedExample = "12:00 PM in Nepal is 6:15 AM in London (GMT).",
            faqs = listOf(
                "Why is Nepal time +5:45?" to "Nepal time is calibrated to the meridian of Mount Gaurishankar (86°15'E longitude)."
            )
        ),

        // MATH
        ToolItem(
            slug = "percentage-calculator",
            name = "Percentage Calculator",
            category = ToolCategory.MATH,
            description = "Calculate percentage of a number, percentage increase/decrease, and what % X is of Y.",
            isPopular = true,
            keywords = listOf("percentage", "percent", "pct", "math", "ratio"),
            formulaExplanation = "Standard percentage ratio calculations.",
            formulaFormula = "P = (Value / Total) × 100 | Increase % = ((New - Old)/Old) × 100",
            workedExample = "15% of NPR 8,500 is NPR 1,275.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "fraction-calculator",
            name = "Fraction Calculator",
            category = ToolCategory.MATH,
            description = "Add, subtract, multiply, and divide fractions with step-by-step simplification.",
            isPopular = false,
            keywords = listOf("fraction", "math", "numerator", "denominator"),
            formulaExplanation = "Performs arithmetic on rational numbers with GCD reduction.",
            formulaFormula = "(a/b) + (c/d) = (ad + bc) / bd",
            workedExample = "1/3 + 2/5 = (5 + 6)/15 = 11/15.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "average-calculator",
            name = "Average & Mean Calculator",
            category = ToolCategory.MATH,
            description = "Calculate mean, median, mode, range, and standard deviation for any series of numbers.",
            isPopular = false,
            keywords = listOf("average", "mean", "median", "statistics", "mode"),
            formulaExplanation = "Arithmetic average is sum of all values divided by count.",
            formulaFormula = "Mean = Σx / n",
            workedExample = "Average of [10, 25, 40, 65] is 35.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "random-number",
            name = "Random Number Generator",
            category = ToolCategory.MATH,
            description = "Generate unbiased random integers between any minimum and maximum ranges for lucky draws and games.",
            isPopular = false,
            keywords = listOf("random", "generator", "lucky draw", "lottery", "dice"),
            formulaExplanation = "Cryptographically secure pseudorandom integer generation.",
            formulaFormula = "Random = Min + floor(R × (Max - Min + 1))",
            workedExample = "Pick a random winner between ticket 1 and 500.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "scientific-calculator",
            name = "Scientific & Basic Calculator",
            category = ToolCategory.MATH,
            description = "Complete calculator supporting basic arithmetic, trigonometric functions (sin, cos, tan), square roots, powers, and logarithms.",
            isPopular = true,
            keywords = listOf("calculator", "math", "scientific", "sqrt", "trigonometry"),
            formulaExplanation = "Supports standard order of mathematical operations (BODMAS).",
            formulaFormula = "Standard mathematical evaluation engine.",
            workedExample = "Evaluate sqrt(144) + 12 * 5 = 72.",
            faqs = listOf()
        ),

        // CONVERSION
        ToolItem(
            slug = "area-converter",
            name = "Area & Nepal Land Unit Converter",
            category = ToolCategory.CONVERSION,
            description = "Convert traditional Nepali land units (Ropani, Aana, Paisa, Daam in Hilly regions & Bigha, Kattha, Dhur in Terai) to Sq. Feet and Sq. Meters.",
            isPopular = true,
            keywords = listOf("ropani", "aana", "paisa", "daam", "bigha", "kattha", "dhur", "land", "jagga", "area"),
            formulaExplanation = "Standard Nepal Department of Land Management official conversion matrix.",
            formulaFormula = "1 Ropani = 16 Aana = 64 Paisa = 256 Daam = 5,476 sq ft | 1 Bigha = 20 Kattha = 400 Dhur = 72,900 sq ft",
            workedExample = "4 Aana land in Kathmandu equals 1,369 sq. feet (127.18 sq. meters).",
            faqs = listOf(
                "What is 1 Aana in square feet?" to "1 Aana equals exactly 342.25 square feet (31.80 sq. meters).",
                "What is 1 Kattha in square feet?" to "1 Kattha equals 3,645 square feet."
            )
        ),
        ToolItem(
            slug = "length-converter",
            name = "Length & Distance Converter",
            category = ToolCategory.CONVERSION,
            description = "Convert between meters, kilometers, feet, inches, yards, centimeters, and miles.",
            isPopular = false,
            keywords = listOf("length", "distance", "meters", "feet", "inches", "km"),
            formulaExplanation = "Standard metric and imperial distance conversion factors.",
            formulaFormula = "1 meter = 3.28084 feet = 39.3701 inches",
            workedExample = "100 meters = 328.08 feet.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "weight-converter",
            name = "Weight & Mass Converter",
            category = ToolCategory.CONVERSION,
            description = "Convert between kilograms, grams, pounds, ounces, and traditional Nepali units (Tola, Dharni, Pau).",
            isPopular = false,
            keywords = listOf("weight", "kg", "lbs", "tola", "grams", "dharni", "pau"),
            formulaExplanation = "Includes 1 Tola = 11.664 grams, 1 Dharni = 2.392 kg, 1 Pau = 199.3 grams.",
            formulaFormula = "1 kg = 2.20462 lbs = 85.735 Tola",
            workedExample = "1 Tola gold = 11.664 grams.",
            faqs = listOf(
                "How many grams is 1 Tola in Nepal?" to "1 Tola equals exactly 11.6638 grams."
            )
        ),
        ToolItem(
            slug = "temperature-converter",
            name = "Temperature Converter",
            category = ToolCategory.CONVERSION,
            description = "Convert temperature between Celsius, Fahrenheit, and Kelvin scales.",
            isPopular = false,
            keywords = listOf("temperature", "celsius", "fahrenheit", "kelvin", "weather"),
            formulaExplanation = "Thermal equilibrium conversion formula.",
            formulaFormula = "°F = (°C × 9/5) + 32 | K = °C + 273.15",
            workedExample = "37°C body temperature equals 98.6°F.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "speed-converter",
            name = "Speed Converter",
            category = ToolCategory.CONVERSION,
            description = "Convert between km/h, mph, meters per second, and knots.",
            isPopular = false,
            keywords = listOf("speed", "kmh", "mph", "mps", "velocity"),
            formulaExplanation = "Speed conversion units.",
            formulaFormula = "1 km/h = 0.621371 mph",
            workedExample = "60 km/h = 37.28 mph.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "data-storage-converter",
            name = "Data Storage Converter",
            category = ToolCategory.CONVERSION,
            description = "Convert Bytes, KB, MB, GB, TB, and Petabytes in both decimal and binary (1024) bases.",
            isPopular = false,
            keywords = listOf("data", "storage", "gb", "mb", "tb", "bytes", "internet"),
            formulaExplanation = "1 GB = 1024 MB in binary and 1000 MB in decimal systems.",
            formulaFormula = "1 GB = 1,024 MB = 1,048,576 KB",
            workedExample = "500 GB hard drive equals 512,000 MB.",
            faqs = listOf()
        ),

        // SHOPPING & BUSINESS
        ToolItem(
            slug = "vat-calculator",
            name = "Nepal VAT Calculator (13%)",
            category = ToolCategory.BUSINESS,
            description = "Calculate Value Added Tax (VAT) in Nepal: add VAT to base price or extract VAT from gross total.",
            isPopular = true,
            keywords = listOf("vat", "tax", "ird", "13%", "bill", "invoice", "nepal vat"),
            formulaExplanation = "Standard Nepal VAT rate is 13% across goods and taxable services.",
            formulaFormula = "VAT = Base × 0.13 | Base = Gross / 1.13",
            workedExample = "Item priced at NPR 10,000 with 13% VAT becomes NPR 11,300 (VAT is NPR 1,300).",
            faqs = listOf(
                "What items are exempt from VAT in Nepal?" to "Basic agricultural products, fresh vegetables, medicines, and educational books are generally VAT-exempt."
            )
        ),
        ToolItem(
            slug = "markup-calculator",
            name = "Markup & Margin Calculator",
            category = ToolCategory.BUSINESS,
            description = "Calculate cost markup percentage and gross profit margin for retail shopkeepers and wholesalers.",
            isPopular = false,
            keywords = listOf("markup", "margin", "wholesale", "retail", "business"),
            formulaExplanation = "Markup is profit based on cost; margin is profit based on selling price.",
            formulaFormula = "Markup % = ((Price - Cost) / Cost) × 100 | Margin % = ((Price - Cost) / Price) × 100",
            workedExample = "Cost NPR 800 with 25% markup sells for NPR 1,000 (Margin is 20%).",
            faqs = listOf()
        ),
        ToolItem(
            slug = "break-even",
            name = "Break-Even Calculator",
            category = ToolCategory.BUSINESS,
            description = "Determine the units and sales revenue needed to cover fixed and variable business costs.",
            isPopular = false,
            keywords = listOf("break even", "bep", "fixed cost", "variable cost", "startup"),
            formulaExplanation = "Point where total revenues equal total business expenses.",
            formulaFormula = "Break-even Units = Fixed Costs / (Price per Unit - Variable Cost per Unit)",
            workedExample = "Fixed costs NPR 50,000 with NPR 200 contribution margin requires selling 250 units.",
            faqs = listOf()
        ),

        // NEPAL SPECIFIC
        ToolItem(
            slug = "bike-emi-calculator",
            name = "Bike & Scooter EMI Calculator",
            category = ToolCategory.NEPAL,
            description = "Calculate down payment, monthly installment, and finance interest for two-wheelers in Nepal (Bajaj, Honda, Yamaha, TVS).",
            isPopular = true,
            keywords = listOf("bike emi", "scooter emi", "honda", "yamaha", "bajaj", "two wheeler", "finance"),
            formulaExplanation = "Based on typical 40% - 50% down payment terms and 12% - 16% hire-purchase interest rates in Nepal.",
            formulaFormula = "Loan = On-Road Price - Down Payment; EMI = Loan × r × (1+r)^n / ((1+r)^n - 1)",
            workedExample = "For a NPR 3,80,000 motorcycle with 40% down payment (NPR 1,52,000) at 14% for 24 months, monthly EMI is approx NPR 10,950.",
            faqs = listOf(
                "What is the minimum down payment for bikes in Nepal?" to "Typically 40% to 50% of the total on-road price is required as down payment."
            )
        ),
        ToolItem(
            slug = "car-emi-calculator",
            name = "Car & EV EMI Calculator",
            category = ToolCategory.NEPAL,
            description = "Calculate auto loan financing for ICE vehicles and Electric Vehicles (EV) in Nepal with NRB loan-to-value limits.",
            isPopular = true,
            keywords = listOf("car emi", "ev loan", "byd", "tata", "hyundai", "auto loan", "nrb"),
            formulaExplanation = "Incorporates NRB guidelines (EV loans up to 80% LTV, fossil fuel vehicles up to 50% LTV).",
            formulaFormula = "Loan Amount = Car Price × LTV Ratio",
            workedExample = "For a NPR 45,00,000 EV with 20% down payment (NPR 9,00,000) at 10.5% for 7 years, monthly EMI is NPR 60,650.",
            faqs = listOf(
                "Why are EV loans easier in Nepal?" to "NRB permits banks to finance up to 80% of an EV's value compared to only 50% for petrol/diesel cars."
            )
        ),
        ToolItem(
            slug = "nepal-fuel-cost",
            name = "Nepal Fuel Cost & Mileage Calculator",
            category = ToolCategory.NEPAL,
            description = "Calculate total fuel expense for road trips across Prithvi, Tribhuvan, or BP Highway based on vehicle mileage and current NOC petrol/diesel rates.",
            isPopular = true,
            keywords = listOf("fuel cost", "petrol", "diesel", "mileage", "noc", "road trip", "highway"),
            formulaExplanation = "Computes liters required and multiplies by official Nepal Oil Corporation (NOC) per-liter tariff.",
            formulaFormula = "Cost = (Distance in km / Mileage in km/L) × Fuel Price per Liter",
            workedExample = "Kathmandu to Pokhara (200 km) with a car averaging 14 km/L at NPR 170/L petrol costs NPR 2,428.",
            faqs = listOf(
                "Who regulates fuel prices in Nepal?" to "Nepal Oil Corporation (NOC) adjusts fuel prices fortnightly based on Indian Oil Corporation (IOC) rates."
            )
        ),
        ToolItem(
            slug = "gold-investment",
            name = "Gold & Silver Investment Calculator",
            category = ToolCategory.NEPAL,
            description = "Calculate fine gold (छापावाल सुन), tejabi gold, and silver value in Nepali Tola and grams with making charges and VAT.",
            isPopular = true,
            keywords = listOf("gold", "silver", "sun chandi", "tola", "jewelry", "fenegosida"),
            formulaExplanation = "Prices tracked per Tola (11.664g) and 10 grams as per Federation of Nepal Gold and Silver Dealers' Association (FENEGOSIDA).",
            formulaFormula = "Total Value = (Weight in Tola × Rate per Tola) + Making Charges + 13% VAT",
            workedExample = "2 Tolas fine gold at NPR 1,60,000/Tola with 8% making charge equals NPR 3,45,600.",
            faqs = listOf(
                "What is Chhapawal vs Tejabi gold?" to "Chhapawal is 24K pure gold (99.9% purity), while Tejabi is 22K (approx 91.6% purity)."
            )
        ),
        ToolItem(
            slug = "nepal-holidays",
            name = "Nepal Public Holiday Countdown",
            category = ToolCategory.NEPAL,
            description = "Upcoming Nepali national holidays, festivals (Dashain, Tihar, Chhath, Holi), and government gazette holidays.",
            isPopular = true,
            keywords = listOf("holidays", "dashain", "tihar", "festival", "bida", "sarkari bida"),
            formulaExplanation = "Tracks dates from the official Ministry of Home Affairs government gazette.",
            formulaFormula = "Countdown = Festival Date - Today",
            workedExample = "Live day countdown to Dashain and Tihar vacation.",
            faqs = listOf()
        ),
        ToolItem(
            slug = "trip-budget-calculator",
            name = "Nepal Trip & Trek Budget Calculator",
            category = ToolCategory.NEPAL,
            description = "Estimate costs for popular Nepal trips (Pokhara, Mustang, Annapurna Circuit, Everest Base Camp, Chitwan) including lodging, permits, food, and transport.",
            isPopular = false,
            keywords = listOf("trip", "trek", "budget", "pokhara", "ebc", "mustang", "annapurna"),
            formulaExplanation = "Combines daily food & stay estimates with transportation and TIMS/National Park permits.",
            formulaFormula = "Budget = (Days × Daily Expense) + Transportation + Permits",
            workedExample = "4-day Pokhara trip for 2 people estimated at NPR 28,000 total.",
            faqs = listOf()
        )
    )
}
