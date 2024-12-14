package com.ll.blog.domain.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.management.Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@Import(RestDocsConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsTestSupport {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected RestDocumentationResultHandler restDocs;

  @Autowired
  protected ObjectMapper objectMapper;

  protected static Attribute constraints(
      final String value) {
    return new Attribute("constraints", value);
  }

  @BeforeEach
  void setUp(final WebApplicationContext context,
      final RestDocumentationContextProvider provider) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(provider))
        .alwaysDo(MockMvcResultHandlers.print()) // print 적용
        .alwaysDo(restDocs) // RestDocsConfiguration 클래스의 bean 적용
        .build();
  }
}
