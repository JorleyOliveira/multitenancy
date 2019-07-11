#Referencia
1.
https://dzone.com/articles/spring-boot-hibernate-multitenancy-implementation
https://github.com/alonsegal/springboot-schema-per-tenant/issues

2. 
https://sunitkatkar.blogspot.com/2018/04/building-saas-style-multi-tenant-web.html
https://sunitkatkar.blogspot.com/2018/04/building-saas-style-multi-tenant-web2.html
https://sunitkatkar.blogspot.com/2018/05/adding-tenants-without-application.html
git clone https://github.com/sunitk/multitenancy.git

3. case Multitenancy application
Separation strategy by column (discriminator column)
https://github.com/felipefmb/springboot_multitenancy.git

4.
https://tech.asimio.net/2017/01/17/Multitenant-applications-using-Spring-Boot-JPA-Hibernate-and-Postgres.html
https://bitbucket.org/asimio/springboot-hibernate-multitenancy/src/master/


#Datasources Benchmark
https://beansroasted.wordpress.com/2017/07/29/connection-pool-analysis/

{
            "org.apache.tomcat.jdbc.pool.DataSource",
            "com.zaxxer.hikari.HikariDataSource",
            "org.apache.commons.dbcp.BasicDataSource",
            "org.apache.commons.dbcp2.BasicDataSource" }
            

# Adding tenants without application restart in SaaS style multi-tenant web app with Spring Boot 2 and Spring Security 5
SaaS application style multi-tenancy with database per tenant using Spring Boot 2 + JPA + Hibernate + Spring Security 5. This app
is built with MySQL as the database. It can be adapted to use any other database like Microsoft SQL Server.

**Note:** This app reads the tenant information from a separate database table and does not require an application restart when new tenants are added.

This repository contains code which accompanies the blog post [Adding tenants without application restart in SaaS style multi-tenant web app
with Spring Boot 2 and Spring Security 5](https://sunitkatkar.blogspot.com/2018/05/adding-tenants-without-application.html)