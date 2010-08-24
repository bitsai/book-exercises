#!/usr/bin/env ruby

def cmd_with_num(cmd, num, comment)
  puts "Cmd: #{cmd}, Num: #{num}, Comment: #{comment}"
end

def cmd_without_num(cmd, comment)
  puts "Cmd: #{cmd}, Comment: #{comment}"
end

# Main
commands = {
  "P" => Object.method(:cmd_with_num),
  "D" => Object.method(:cmd_without_num),
  "U" => Object.method(:cmd_without_num),
  "N" => Object.method(:cmd_with_num),
  "E" => Object.method(:cmd_with_num),
  "S" => Object.method(:cmd_with_num),
  "W" => Object.method(:cmd_with_num)
}

File.read(ARGV[0]).split(/\n/).each do |line|
  cmd = "", num = "", comment = ""
  
  if line =~ /^(\w) (\d*)\s+\# (.+)$/ then
    cmd = $1
    num = $2
    comment = $3
  end

  if commands[cmd] then 
    if commands[cmd].arity == 2 then commands[cmd].call(cmd, comment) end
    if commands[cmd].arity == 3 then commands[cmd].call(cmd, num, comment) end
  end
end
