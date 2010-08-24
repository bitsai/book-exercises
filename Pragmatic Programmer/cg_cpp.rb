module CG
  def CG.generate_code(msg)
    msg.comments.each do |comment| puts "//#{comment}" end
    puts "typedef struct {"

    msg.fields.each do |field|
      print "\t#{field.type}\t#{field.name}"
      if field.length then print "[#{field.length}]" end
      puts ";"
    end

    puts "} #{msg.name};"
  end
end
