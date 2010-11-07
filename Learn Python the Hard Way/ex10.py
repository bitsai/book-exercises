tabby_cat = "\tI'm tabbed in."
persian_cat = "I'm split\non a line."
backslash_cat = "I'm \\ a \\ cat."

fat_cat = '''
I'll do a list:
\t* Cat food
\t* Fishies
\t* Catnip\n\t* Grass
'''

print tabby_cat
print persian_cat
print backslash_cat
print fat_cat

complex_formatter = "A\nmore\ncomplicated\n%s"
print complex_formatter % "format string"

r_formatter = "%%r: %r"
print r_formatter % ('escaped single quote: \'')
print r_formatter % ("escaped double quote: \"")

s_formatter = "%%s: %s"
print s_formatter % ('escaped single quote: \'')
print s_formatter % ("escaped double quote: \"")
