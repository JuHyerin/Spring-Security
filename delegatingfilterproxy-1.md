# DelegatingFilterProxy



실질적으로 서블릿 필터는 `DelegatingFilterProxy` 하나이며 이 클래스는 스프링 시큐리티의 실제적인 구현을 가지고 있지는 않습니다. `DelegatingFilterProxy`가 하는 일은 **Spring의 애플리케이션 컨텍스트에서 얻은 Filter Bean를 대신 실행하는 것**입니다. 이를 통해 Bean은 Spring 웹 애플리케이션 컨텍스트 라이프 사이클 지원 및 구성 유연성을 활용할 수 있습니다. **Bean은 javax.servlet.Filter를 구현해야하며 filter-name 요소에 정의된 이름과 동일한 이름의 id을 가져야합니다**. 예를 들면 아래와 같이 정의되어 있어야 합니다.

왜냐하면 `DelegatingFilterProxy`가 초기화 될때 자신의 이름과 같은 id를 가진 Bean을 찾아서 대리자로 등록하기 때문입니다.

전체적인 `DelegatingFilterProxy` 구성을 json 형태로 정리해보면 아래와 유사할 것입니다.

```java
{
  "ServletFilterChain": {
    "servletFilters": [
      {
        "DelegatingFilterProxy": {
          "FilterChainProxy": {
            "securityFilterChainList": [
              {
                "SecurityFilterChain": [
                  {
                    "SpringSecurityFilter": "SecurityContextPersistenceFilter"
                  },
                  {
                    "SpringSecurityFilter": "ConcurrentSessionFilter"
                  },
                  {
                    "SpringSecurityFilter": "UsernamePasswordAuthenticationFilter"
                  }
                ]
              }
            ]
          }
        }
      }
    ]
  }
}
```

`ServletFilterChain`은 `web.xml` 에 정의된 filter 갯수대로 _servletFilters_ 를 생성할 것입니다. 앞선 필터 선언에 의하면 `DelegatingFilterProxy` 필터 하나가 추가될 것입니다. `DelegatingFilterProxy`는 자신의 이름과 동일한 id를 가진 Bean을 Application Context에서 가져와 대리자로 등록합니다. 위 경우에는 `FilterChainProxy` 가 대리자로 등록되었습니다. `FilterChainProxy` 는 `SecuriyFilterChain` 리스트를 가지고 있습니다. `SecurityFilterChain` 은 네임스페이스 설정에서 `<http>` 요소당 하나가 만들어진다고 보면됩니다. `SecurityFilterChain` 은 실제 일을 하는 스프링시큐리티 리스트를 가지고 있습니다. 이 목록 등록된 필터가 순서대로 호출되면서 스프링시큐리티 기능이 동작을 합니다.

> [https://thecodinglog.github.io/spring/2020/04/28/spring-filter-chain.html](https://thecodinglog.github.io/spring/2020/04/28/spring-filter-chain.html)



