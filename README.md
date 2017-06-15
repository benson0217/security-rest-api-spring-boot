# security-rest-api-spring-boot
This is a simple rest api for Spring Boot Security applications.

#### Getting Started

- - -
take a look security-rest-api-spring-boot-sample-app project.
all you need just use @EnableSecurityRestApi in your main class and a user in admin group has ROLE_ADMIN , you can change SecurityConfiguration.class in sample-app like this 
```
    @PostConstruct
    public void dataSourceInitData() {
        Resource initSchema = new ClassPathResource("sql/group-schema.sql");
//        Resource initData = new ClassPathResource("sql/init-data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }
```
to 

```
    @PostConstruct
    public void dataSourceInitData() {
        Resource initSchema = new ClassPathResource("sql/group-schema.sql");
        Resource initData = new ClassPathResource("sql/init-data.sql");
        DatabasePopulator databasePopulator = new  ResourceDatabasePopulator(initSchema, initData);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }
```

if you want to handle authentication error 
```
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic().authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .and()
        .csrf().disable();
    }
```
