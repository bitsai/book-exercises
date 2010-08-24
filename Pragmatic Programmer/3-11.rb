#!/usr/bin/env ruby

Struct.new("Type", :name, :states)

def print_header(type)
  file = File.new("#{type.name}.h", "w")
  file.puts "extern const char* #{type.name.upcase}_names[];"
  file.puts "typedef enum {"
  type.states.each do |state| file.puts "\t#{state}," end
  file.puts "} #{type.name.upcase};"
  file.close
end

def print_source(type)
  file = File.new("#{type.name}.c", "w")
  file.puts "const char* #{type.name.upcase}_names[] = {"
  type.states.each do |state| file.puts "\t\"#{state}\"," end
  file.puts "};"
  file.close
end

# Main
type = Struct::Type.new(nil, [])

while s = gets do
  s.chomp!
  if type.name == nil then type.name = s else type.states << s end
end

print_header(type)
print_source(type)
