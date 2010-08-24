#!/usr/local/bin/ruby

def random_letters( size )
	( 0...size ).map{ ( 65 + rand( 25 ) ).chr }.join
end

def make_test_file( size )
	test_file = File.new("#{ size }.txt", "w")
	size.times{ test_file.puts( "#{ random_letters( 8 ) } #{ rand( 100 ) } #{ rand( 100 ) } #{ rand( 100 ) }" ) }
	test_file.close
end

make_test_file( 100 )
make_test_file( 1000 )
make_test_file( 10000 )
