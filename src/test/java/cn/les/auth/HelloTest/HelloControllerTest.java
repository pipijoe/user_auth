package cn.les.auth.HelloTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.CombinableMatcher;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.security.RunAs;
import java.util.HashMap;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Joetao
 * @time 2021/1/28 10:10 上午
 * @Email cutesimba@163.com
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HelloControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void login() throws Exception {
        HashMap<Object, Object> reqBody = new HashMap<>();
        reqBody.put("username", "admin");
        reqBody.put("password", "123456");

        mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqBody)))
//                .andExpect(jsonPath("$.token").value(CombinableMatcher
//                        .both(IsNot.not(IsNull.nullValue())).and(IsNot.not("")))
//
//                )
                .andDo(
                        document("{ClassName}/{methodName}",
                                requestFields(fieldWithPath("username").description("登录名"),
                                        fieldWithPath("password").description("密码"))
//                                responseFields(fieldWithPath("token").description("登录token"))
                        )
                );
    }

    @Test
    public void users() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andDo(
                        document("{ClassName}/{methodName}",
                                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                        )
                );
    }
}
