# soapapi
API for Consume SOAP Services 

# Configure your SOAP service in separate class like sample/ServiceManagerImpl.java

# Consume the SOAP Service configured like in sample/Test.java

# POM dependencies

		<!-- https://mvnrepository.com/artifact/org.apache.axis2/axis2-jaxws -->
		<dependency>
			<groupId>org.apache.axis2</groupId>
			<artifactId>axis2-jaxws</artifactId>
			<version>${axis2.version}</version>
		</dependency>
		
		<dependency>
			<groupId>org.apache.axis2</groupId>
			<artifactId>axis2-kernel</artifactId>
			<version>${axis2.version}</version>
		</dependency>
		
		<dependency>
			<groupId>org.apache.axis2</groupId>
			<artifactId>axis2-adb</artifactId>
			<version>${axis2.version}</version>
		</dependency>
		
		<dependency>
			<groupId>org.apache.axis2</groupId>
			<artifactId>axis2-transport-http</artifactId>
			<version>${axis2.version}</version>
		</dependency>
		
		<dependency>
			<groupId>org.apache.axis2</groupId>
			<artifactId>axis2-transport-local</artifactId>
			<version>${axis2.version}</version>
		</dependency>
		
		<dependency>
			<groupId>org.apache.axis2</groupId>
			<artifactId>axis2-xmlbeans</artifactId>
			<version>${axis2.version}</version>
		</dependency>
