package com.ll.blog.domain.restdocs;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeHeaders;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

@TestConfiguration
public class RestDocsConfiguration {

  @Bean
  public RestDocumentationResultHandler restDocumentationResultHandler() {
    return MockMvcRestDocumentation.document(
        "{class-name}/{method-name}",
        preprocessRequest(removeHeaders("Host", "Content-Length"),
            prettyPrint()),
        preprocessResponse(removeHeaders("Content-Length", "X-Frame-Options",
                "Expires", "Pragma", "Cache-Control", "X-XSS-Protection", "X-Content-Type-Options"),
            prettyPrint()));
  }
}
