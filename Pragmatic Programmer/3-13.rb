#!/usr/bin/env ruby

Struct.new("Message", :comments, :name, :fields)
Struct.new("MessageField", :name, :type, :length)

def msgs_from_file(file)
  return File.read(file).split(/^\s*$/).map { |text| msg_from_text(text) }
end

def msg_from_text(text)
  msg = Struct::Message.new([], "", [])

  text.split(/\n/).each do |line|
    if line =~ /#(.*)/ then msg.comments << $1 end
    if line =~ /M\s*(.*)/ then msg.name = $1 end
    if line =~ /F\s*(\w+)\s+(\w+)$/ then msg.fields << Struct::MessageField.new($1, $2) end
    if line =~ /F\s*(\w+)\s+(\w+)\[(\d+)\]$/ then msg.fields << Struct::MessageField.new($1, $2, $3) end
  end
  
  return msg
end

# Main
file, lang = ARGV[0], ARGV[1]

require "cg_#{lang}"

msgs_from_file(file).each do |msg| CG.generate_code(msg) end
