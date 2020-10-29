---
description: 'AuthenticationManager, ProviderManager, AuthenticationProvider'
---

# AuthenticationManager

* **AuthenticationManager \(Interface\)**

authenticate\(Authentication\):Authentication → Authentication 객체를 받아 인증하고 인증되었다면 인증된 Authentication 객체를 돌려주는 메서드를 구현하도록 하는 인터페이스다.

이 메서드를 통해 인증되면 isAuthenticated\(boolean\)값을 TRUE로 바꿔준다.

* **ProviderManager \(Class\)**

AuthenticationManager의 구현체로 스프링에서 **인증을 담당하는 클래스**로 볼 수 있다.

\(스프링 시큐리티가 생성하고 등록하고 관리하는 스프링 빈이므로 직접 구현할 필요가 없음\)

하지만 **직접 인증 과정을 진행하는게 아니라 멤버 변수로 가지고 있는 AuthenticationProvider들을에게 인증을 위임처리하고** 그 중에 하나의 AuthenticationProvider\(명확하게는 AuthenticationProvider를 구현한 클래스\)객체가 인증 과정을 거쳐서 인증에 성공하면 요청에 대해서 ProviderManager가 인증이 되었다고 알려주는 방식이다.

인증이 되었다고 알려주는 건 AuthenticationManager 인터페이스의 메서드인 authenticate\(\) 메서드의 리턴 값인 Authentication객체 안에 인증 값을 넣어주는 것으로 처리한다.

* **AuthenticationProvider \(Interface\)**

**authenticate\(Authentication\):Authentication** → 앞서 AuthenticationManager에서 봤던 메서드와 똑같은 메서드로 인증과정이 이 메서드를 통해 진행된다.

**supports\(Class&lt;?&gt;\):boolean** → 앞에서 필터에서 보내준 Authentication 객체를 이 AuthenticationProvider가

~~소화~~

인증 가능한 클래스인지 확인하는 메서드다.

→ UsernamePasswordAuthenticationToken이 ProviderManager에 도착한다면 ProviderManager는 자기가 갖고 있는 AuthenticationProvider 목록을 순회하면서 '너가 이거 해결해줄 수 있어?' 하고 물어보고\(supports\(\)\) 해결 가능하다고 TRUE를 리턴해주는 AuthenticationProvider에게 authenticate\(\) 메서드를 실행한다. \(아래에 ProviderManager.class 내의 authenticate\(\) 메서드를 가져왔으니 앞서 말한 동작을 확인해보면 도움될 것이다.\)  
  
출처: [https://jeong-pro.tistory.com/205](https://jeong-pro.tistory.com/205) \[기본기를 쌓는 정아마추어 코딩블로그\]

