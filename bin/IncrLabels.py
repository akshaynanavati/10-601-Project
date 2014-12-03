#!/usr/bin/python

import csv, sys

if len(sys.argv) != 2:
    print "Usage: ./IncrLabels.py <csv-filename>"
    sys.exit(-1)

with open(sys.argv[1], 'r') as f:
    reader = csv.reader(f)
    header = reader.next()
    rows = [header]
    while True:
        try:
            row = reader.next()
            row[1] = str(int(row[1]) + 1)
            rows = rows + [row]
        except:
            break

with open(sys.argv[1] + ".mod", 'w') as f:
    writer = csv.writer(f)
    writer.writerows(rows)
