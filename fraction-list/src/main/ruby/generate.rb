
require('fileutils')

puts "Generating fraction list at #{Dir.pwd} "

dirs = Dir['../*']

fractions = []

for d in dirs do
  if ( File.exists?( File.join(d, "api") ) && File.exists?( File.join(d, "modules") ) && File.exists?( File.join(d,"runtime") ) ) 
    name = File.basename( d );
    fractions << name
  end
end

targetDir = File.join( '.', 'target' );

FileUtils.mkdir_p( targetDir )

File.open( File.join( targetDir, 'fraction-list.txt' ), 'w' ) do |f|
  for fraction in fractions do
    f.puts "org.wildfly.swarm:wildfly-swarm-#{fraction}"
  end
end
