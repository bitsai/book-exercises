#!/usr/bin/env ruby

require 'fileutils'

def using_strict(file)
  return (File.read(file) =~ /^\s*use strict;/m) ? true : false
end

def make_strict(file)
  new_content = File.read(file).sub(/^(?!#)/, "\nuse strict;\n")
  output = File.open(file, "w")
  output.puts(new_content)
  output.close
end

# Main
dir = ARGV[0]

Dir["#{ dir }/*.pl"].each do |file|
  if !using_strict(file) then
    FileUtils.copy(file, "#{ file }.bak")
    make_strict(file)
  end
end
