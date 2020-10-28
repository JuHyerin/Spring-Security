package com.innilabs.inniboard.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2 //Application.java에 적용해도 됨
@Configuration
public class SwaggerConfig {
    //Arrays클래스는 메서드가 모두 static method
    //Arrays.asList(배열): ArrayList
    //일반배열->ArrayList
    private List<ResponseMessage> responseMessages = Arrays.asList( 
                                                        new ResponseMessageBuilder()
                                                        .code(200)
                                                        .message("성공!")
                                                        .build(),
                                                        new ResponseMessageBuilder()
                                                        .code(404)
                                                        .message("없음!")
                                                        .build(),
                                                        new ResponseMessageBuilder()
                                                        .code(500)
                                                        .message("실패!")
                                                        .build()
                                                    );
    @Bean(name = "testapi")
    public Docket testApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .groupName("Test-API")
                .apiInfo(testApiInfo())
                .useDefaultResponseMessages(false);
    }
    @Bean
    public Docket classAnnotationApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .groupName("Class-Annotation-API")
                .apiInfo(testApiInfo())
                .useDefaultResponseMessages(false);
    }
    @Bean
    public Docket methodAnnotationApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .groupName("Method-Annotation-API")
                .apiInfo(testApiInfo())
                .useDefaultResponseMessages(false);
    }
    @Bean
    public Docket boardApi(){
        return new Docket(DocumentationType.SWAGGER_2)
        .select() 
        .apis(RequestHandlerSelectors.basePackage("com.innilabs.inniboard.controller"))
        .paths(PathSelectors.ant("/api/board/**")) 
        .build()
        .groupName("Board-API")
        .apiInfo(boardApiInfo()) 
        .useDefaultResponseMessages(false)
        .globalResponseMessage(RequestMethod.GET, responseMessages)
        .globalResponseMessage(RequestMethod.POST, responseMessages)
        .globalResponseMessage(RequestMethod.PUT, responseMessages)
        .globalResponseMessage(RequestMethod.DELETE, responseMessages);
    }


    private ApiInfo testApiInfo(){ //Docket객체 생성시 매개변수로 사용
        return new ApiInfoBuilder()
        .title("Swagger Test")
        .version("test")
        .description("테스트용 스웨거 커스텀")
        .contact(new Contact("JuHyerin","https://github.com/JuHyerin","1692075@hansung.ac.kr"))
        .build();
    }   
    
    private ApiInfo boardApiInfo(){ //Docket객체 생성시 매개변수로 사용
        return new ApiInfoBuilder()
        .title("Board Project")
        .version("v1")
        .description("게시판 프로젝트 api")
        .contact(new Contact("JuHyerin","https://github.com/JuHyerin","1692075@hansung.ac.kr"))
        .build();
    }
}

