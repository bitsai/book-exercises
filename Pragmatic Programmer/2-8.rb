#!/usr/bin/env ruby

while str = gets
  if str =~ /^(\d{1,2})(am|pm)$/ then
    puts "Hour: #{$1}"
    puts "Suffix: #{$2}"
  elsif str =~ /^(\d{1,2}):(\d{2})$/ then
    puts "Hour: #{$1}"
    puts "Minute: #{$2}"
  elsif str =~ /^(\d{1,2}):(\d{2})(am|pm)$/ then
    puts "Hour: #{$1}"
    puts "Minute: #{$2}"
    puts "Suffix: #{$3}"
  else
    puts "Unknown format"
  end
end
