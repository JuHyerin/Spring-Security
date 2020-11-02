# FilterChainProxy

## FilterChainProxy 생성과정 

#### [DelegatingFilterProxy](https://yoon0120.tistory.com/47#DelegatingFilterProxy) <a id="DelegatingFilterProxy"></a>

![](.gitbook/assets/image%20%283%29.png)

####  <a id="DelegatingFilterProxy"></a>

![](.gitbook/assets/image%20%282%29.png)

스프링 부트 사용시 SecurityFilterAutoConfiguration 에서 DelegatingFilterProxyRegistrationBean 을 생성하는데 \(springSecurityFilterChain 빈 이름을 넘겨주며\) 이 registration bean 이 상속하는  AbstractFilterRegistrationBean 에서 DelegatingFilterProxyRegistrationBean 의 getFilter 라는 메서드를 호출함으로써 DelegatingFilterProxy 클래스를 필터로 등록합니다.

위와 같이 Servlet Filter Chain 에는 필터를 위임할 DelegatingFilterProxy 를 등록하고 프록시로서의 역할을 합니다. 실제로 스프링 시큐리티의 필터 체인은 FilterChainProxy 가 수행합니다.  
  
출처: [https://yoon0120.tistory.com/47\#Spring Security configuration](https://yoon0120.tistory.com/47#Spring%20Security%20configuration) \[재미있는 개발공부\]

## FilterChainProxy  호출 과정 

![](.gitbook/assets/image%20%281%29.png)

#### AuthenticationFilter, DelegatingFilterProxy <a id="h-tag-4"></a>

우선 클라이언트\(브라우저\)로 부터 요청\(Request\)이 오면, 그 요청은 **ApplicationFilter** 객체들로 먼저 가게된다. \(**DispatcherServlet\(Front Controller\)에는 아직 도달하지도 않은 상태**\)

ApplicationFilter들을 거치다가 **DelegatingFilterProxyRegistrationBean**이라는 필터는 만나게 된다.

이 필터는 **DelegatingFilterProxy**라는 클래스로 만들어진 스프링 빈\(Bean\)을 등록시켜주는 역할을 한다.

→ 스프링 부트에서는 @EnableAutoConfiguration annotation을 이용해서 SecurityFilterAutoConfiguration클래스를 로드하고 디폴트로 이름이 "springSecurityFilterChain" 빈을 등록해준다.

그래서 이 때, 스프링 시큐리티가 만든 DelegatingFilterProxy 클래스인 **"springSecurityFilterChain"**이라는 이름의 **스프링 빈을 등록하고** 이후에는 이 **DelegatingFilterProxy\(springSecurityFilterChain\)**가 필터로 동작하게 된다.

→ 구체적으로는 DelegatingFilterProxy가 처리를 위임하는 필터 클래스는 "FilterChainProxy"다. 이 클래스 내부에 체인으로 등록된 필터를 순서대로 수행하는 것이다.

**\(DelegatingFilterProxy → FilterChainProxy → List 구조\)**

→ 앞에서 스프링 시큐리티가 만든 DelegatingFilterProxy라고 했는데 스프링 부트를 기준으로 설명해서 그렇고 그냥 스프링에서는 아래와 같이 직접 web.xml파일에 필터를 등록을 한다.  
  
출처: [https://jeong-pro.tistory.com/205](https://jeong-pro.tistory.com/205) \[기본기를 쌓는 정아마추어 코딩블로그\]

{% hint style="info" %}
FilterChainProxy 생성-호출:  


`FilterChainProxy`는 Request마다 원하는 Filter Set을 URI를 보고 셋팅해주고, 실제로는 `WebSecurityConfigurerAdapter` configure에서 Filter Set을 등록하게 된다.

  
[https://kangwoojin.github.io/programing/spring-security-basic-filter-chain-proxy/](https://kangwoojin.github.io/programing/spring-security-basic-filter-chain-proxy/)
{% endhint %}

