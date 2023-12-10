from datetime import datetime, timedelta
import pandas as pd
def calculate_easter(year):
    """Returns the date of Easter Sunday for a given year."""
    a = year % 19
    b = year // 100
    c = year % 100
    d = b // 4
    e = b % 4
    f = (b + 8) // 25
    g = (b - f + 1) // 3
    h = (19 * a + b - d - g + 15) % 30
    i = c // 4
    k = c % 4
    l = (32 + 2 * e + 2 * i - h - k) % 7
    m = (a + 11 * h + 22 * l) // 451
    month = (h + l - 7 * m + 114) // 31
    day = ((h + l - 7 * m + 114) % 31) + 1
    return datetime(year, month, day)

# Holidays and events for 2009, 2010, 2011
years = [2009, 2010, 2011]
holidays = []

for year in years:
    holidays.extend([
        ("New Year's Day", datetime(year, 1, 1), datetime(year, 1, 1)),
        ("Chinese New Year", datetime(year, 1, 26), datetime(year, 2, 1)),  # Approximate date for 2009-2011
        ("Valentine's Day", datetime(year, 2, 14), datetime(year, 2, 14)),
        ("Easter", calculate_easter(year) - timedelta(days=2), calculate_easter(year) + timedelta(days=1)),
        ("Mother's Day", datetime(year, 5, 10), datetime(year, 5, 10)),  # Second Sunday of May
        ("Father's Day", datetime(year, 6, 21), datetime(year, 6, 21)),  # Third Sunday of June
        ("Independence Day", datetime(year, 7, 4), datetime(year, 7, 4)),
        ("Back to School", datetime(year, 8, 15), datetime(year, 9, 15)),  # Approximate period
        ("Halloween", datetime(year, 10, 31), datetime(year, 10, 31)),
        ("Black Friday", datetime(year, 11, 27), datetime(year, 11, 30))  # Approximate for 2009-2011
    ])

# Convert to DataFrame for better visualization and usage
holidays_df = pd.DataFrame(holidays, columns=['Holiday', 'Start Date', 'End Date'])
holidays_df.to_csv('holidays_2009-2011.csv',index=False)
print(holidays_df)