module CG
  def CG.generate_code(msg)
    msg.comments.each do |comment| puts "##{comment}" end
    print "Struct.new( \"#{msg.name}\""
    msg.fields.each do |field| print ", :#{field.name}" end
    puts " )"
  end
end
