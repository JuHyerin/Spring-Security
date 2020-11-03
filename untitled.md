# WebSecurityConfigurerAdapter

2. SpringSecurityAdaptor

  
****-&gt; 스프링 시큐리티에 필요한 내용을 정의하는 configuration을 생성해야한다.  
-&gt; WebSecurityConfigurerAdapter 클래스를 상속받아서 configure 메서드를 재정의 해야한다.  
-&gt; @EnableWebSecurity, @EnableGlobalAuthentication와 같은 애노테이션을 사용하여 스프링 시큐리티 사용을 정의 해야 한다.  
-&gt; public void configure\(WebSecurity web\) throws Exception 메서드를 재정의하여 로그인 상관 없이 허용을 해줘야할 리소스 위치를 정의한다.  
-&gt; protected void configure\(HttpSecurity http\) throws Exception  메소드를 재정의하여 로그인 URL, 권한분리, logout URL 등등을 설정할 수 있다. \(자세한 설명은 메서드에 주석으로 확인\)  
  
출처: [https://wedul.site/170](https://wedul.site/170) \[wedul\]

```java
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/**").permitAll()
            .and() // 로그인 설정
                .formLogin()
                .loginPage("/user/login") // 로그인 페이지를 제공하는 URL을 설정
                .defaultSuccessUrl("/user/login/result") 
                // 로그인 성공 URL을 설정함
                //.successForwardUrl("/index")
                // 로그인 실패 URL을 설정함
                //.failureForwardUrl("/index")
                .permitAll()
            .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/logout/result")
                .invalidateHttpSession(true)
            .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/user/denied");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
```

* [@EnableWebSecurity](https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/config/annotation/web/configuration/EnableWebSecurity.html)
  * @Configuration 클래스에 @EnableWebSecurity 어노테이션을 추가하여 Spring Security 설정할 클래스라고 정의합니다.
  * 설정은 WebSebSecurityConfigurerAdapter 클래스를 상속받아 메서드를 구현하는 것이 일반적인 방법입니다. 
* WebSecurityConfigurerAdapter 클래스
  * [WebSecurityConfigurer](https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/config/annotation/web/WebSecurityConfigurer.html) 인스턴스를 편리하게 생성하기 위한 클래스입니다.
* passwordEncoder\(\)
  * BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체입니다.
  * Service에서 비밀번호를 암호화할 수 있도록 Bean으로 등록합니다.

  
다음으로 configure\(\) 메서드를 오버라이딩하여, Security 설정을 잡아줍니다.

* configure\(WebSecurity web\)
  * [WebSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/WebSecurity.html)는 FilterChainProxy를 생성하는 필터입니다.
  * web.ignoring\(\).antMatchers\("/css/\*\*", "/js/\*\*", "/img/\*\*", "/lib/\*\*"\);
  * * 해당 경로의 파일들은 Spring Security가 무시할 수 있도록 설정합니다.
    * 즉, 이 파일들은 무조건 통과하며, 파일 기준은 resources/static 디렉터리입니다. \( css, js 등의 디렉터리를 추가하진 않았습니다. \)
* configure\(HttpSecurity http\)
  * [HttpSecurity](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/config/annotation/web/builders/HttpSecurity.html)를 통해 HTTP 요청에 대한 웹 기반 보안을 구성할 수 있습니다.
  * authorizeRequests\(\)
    * HttpServletRequest에 따라 접근\(access\)을 제한합니다.
  * * antMatchers\(\) 메서드로 특정 경로를 지정하며, permitAll\(\), hasRole\(\) 메서드로 역할\(Role\)에 따른 접근 설정을 잡아줍니다. 여기서 롤은 권한을 의미합니다. 즉 어떤 페이지는 관리지만 접근해야 하고, 어떤 페이지는 회원만 접근해야할 때 그 권한을 부여하기 위해 역할을 설정하는 것입니다. 예를 들어,
  * * * .antMatchers\("/admin/\*\*"\).hasRole\("ADMIN"\)
        * /admin 으로 시작하는 경로는 ADMIN 롤을 가진 사용자만 접근 가능합니다.
      * .antMatchers\("/user/myinfo"\).hasRole\("MEMBER"\)
        * /user/myinfo 경로는 MEMBER 롤을 가진 사용자만 접근 가능합니다.
      * .antMatchers\("/\*\*"\).permitAll\(\)
        * 모든 경로에 대해서는 권한없이 접근 가능합니다.
      * .anyRequest\(\).authenticated\(\)
        * 모든 요청에 대해, 인증된 사용자만 접근하도록 설정할 수도 있습니다. \( 예제에는 적용 안함 \)
  * * * 
  * formlogin\(\)
    * form 기반으로 인증을 하도록 합니다. 로그인 정보는 기본적으로 HttpSession을 이용합니다.
    * /login 경로로 접근하면, Spring Security에서 제공하는 로그인 form을 사용할 수 있습니다.
    * .loginPage\("/user/login"\)
      * 기본 제공되는 form 말고, 커스텀 로그인 폼을 사용하고 싶으면 loginPage\(\) 메서드를 사용합니다.
      * 이 때 커스텀 로그인 form의 action 경로와 loginPage\(\)의 파라미터 경로가 일치해야 인증을 처리할 수 있습니다. \( login.html에서 확인 \)
  * * .defaultSuccessUrl\("/user/login/result"\)
      * 로그인이 성공했을 때 이동되는 페이지이며, 마찬가지로 컨트롤러에서 URL 매핑이 되어 있어야 합니다.
    * .usernameParameter\("파라미터명"\)
      * 로그인 form에서 아이디는 name=username인 input을 기본으로 인식하는데, usernameParameter\(\) 메서드를 통해 파라미터명을 변경할 수 있습니다. \( 예제에는 적용 안함 \)
  * logout\(\)
    * 로그아웃을 지원하는 메서드이며, WebSecurityConfigurerAdapter를 사용할 때 자동으로 적용됩니다.
    * 기본적으로 "/logout"에 접근하면 HTTP 세션을 제거합니다.
    * .logoutRequestMatcher\(new AntPathRequestMatcher\("/user/logout"\)\)
      * 로그아웃의 기본 URL\(/logout\) 이 아닌 다른 URL로 재정의합니다.
    * .invalidateHttpSession\(true\)
      * HTTP 세션을 초기화하는 작업입니다.
    * deleteCookies\("KEY명"\)
      * 로그아웃 시, 특정 쿠기를 제거하고 싶을 때 사용하는 메서드입니다. \( 예제에는 적용안함 \)
  * .exceptionHandling\(\).accessDeniedPage\("/user/denied"\);
  * * 예외가 발생했을 때 exceptionHandling\(\) 메서드로 핸들링할 수 있습니다.
    * 예제에서는 접근권한이 없을 때, 로그인 페이지로 이동하도록 명시해줬습니다
* configure\(AuthenticationManagerBuilder auth\)
  * Spring Security에서 모든 인증은 AuthenticationManager를 통해 이루어지며 AuthenticationManager를 생성하기 위해서는 AuthenticationManagerBuilder를 사용합니다.
    * 로그인 처리 즉, 인증을 위해서는 UserDetailService를 통해서 필요한 정보들을 가져오는데, 예제에서는 서비스 클래스\(memberService\)에서 이를 처리합니다.
    * 서비스 클래스에서는 UserDetailsService 인터페이스를 implements하여, loadUserByUsername\(\) 메서드를 구현하면 됩니다.
  * 비밀번호 암호화를 위해, passwordEncoder를 사용하고 있습니다.
  * CustomAuthenticationFilter를 빈으로 등록하는 과정에서 UserName파라미터와 UserPassword파라미터를 설정할 수 있다. 이러한 과정을 거치면 UsernamePasswordToken이 발급되게 된다.

