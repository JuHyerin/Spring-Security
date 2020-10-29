# SpringSecurityAdapter

2. SpringSecurityAdaptor

  
****-&gt; 스프링 시큐리티에 필요한 내용을 정의하는 configuration을 생성해야한다.  
-&gt; WebSecurityConfigurerAdapter 클래스를 상속받아서 configure 메서드를 재정의 해야한다.  
-&gt; @EnableWebSecurity, @EnableGlobalAuthentication와 같은 애노테이션을 사용하여 스프링 시큐리티 사용을 정의 해야 한다.  
-&gt; public void configure\(WebSecurity web\) throws Exception 메서드를 재정의하여 로그인 상관 없이 허용을 해줘야할 리소스 위치를 정의한다.  
-&gt; protected void configure\(HttpSecurity http\) throws Exception  메소드를 재정의하여 로그인 URL, 권한분리, logout URL 등등을 설정할 수 있다. \(자세한 설명은 메서드에 주석으로 확인\)  
  
출처: [https://wedul.site/170](https://wedul.site/170) \[wedul\]

