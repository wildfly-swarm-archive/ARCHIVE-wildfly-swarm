
require('fileutils')

puts "Generating pom.xml list at #{Dir.pwd} for #{ARGV[0]}"

version = ARGV[0];
fractionList = File.open( File.join( '..', 'fraction-list', 'target', 'fraction-list.txt' ) ).read;

classesDir = File.join( '.', 'target', 'classes' );
FileUtils.mkdir_p( classesDir )

File.open( File.join( classesDir, 'pom.xml' ), 'w' ) do |f|
  f.puts '<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"'
  f.puts 'xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">'
  f.puts '  <modelVersion>4.0.0</modelVersion>'
  f.puts ''
  f.puts '  <groupId>\${groupId}</groupId>'
  f.puts '  <artifactId>\${artifactId}</artifactId>'
  f.puts ''
  f.puts '  <version>\${version}</version>'
  f.puts ''
  f.puts '  <properties>'
  f.puts "    <version.wildfly-swarm>#{version}</version.wildfly-swarm>"
  f.puts '  </properties>'
  f.puts ''
  f.puts '  <plugins>'
  f.puts '    <plugin>'
  f.puts '      <groupId>org.wildfly.swarm</groupId>'
  f.puts '      <artifactId>wildfly-swarm-plugin</artifactId>'
  f.puts '      <version>${version.wildfly-swarm}</version>'
  f.puts '      <configuration>'
  f.puts '        <mainClass>com.mycorp.Main</mainClass>'
  f.puts '      </configuration>'
  f.puts '      <executions>'
  f.puts '        <execution>'
  f.puts '          <goals>'
  f.puts '            <goal>package</goal>'
  f.puts '          </goals>'
  f.puts '        </execution>'
  f.puts '      </executions>'
  f.puts '    </plugin>'
  f.puts '  </plugins>'
  f.puts ''
  f.puts '  <dependencies>'

  fractionList.each_line do |l|
    parts = l.split(':')
    f.puts( '    <!--' )
    f.puts( '    <dependency>')
    f.puts( "      <groupId>#{parts[0].strip}</groupId>")
    f.puts( "      <artifactId>#{parts[1].strip}</artifactId>")
    f.puts( '      <version>${version.wildfly-swarm}</version>')
    f.puts( '    </dependency>')
    f.puts( '    -->' )
  end

  f.puts '    <dependency>'
  f.puts '      <groupId>junit</groupId>'
  f.puts '      <artifactId>junit</artifactId>'
  f.puts '      <version>3.8.1</version>'
  f.puts '      <scope>test</scope>'
  f.puts '    </dependency>'
  f.puts '  </dependencies>'
  f.puts '</project>'
end
