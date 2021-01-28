package cn.les.auth.HelloTest;

import cn.les.auth.config.security.JwtAuthenticationTokenFilter;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.vo.UserVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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

    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    private String token;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .addFilter(authenticationTokenFilter)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
        loginBefore();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void login() throws Exception {
        HashMap<Object, Object> reqBody = new HashMap<>();
        reqBody.put("username", "admin");
        reqBody.put("password", "123456");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqBody)))
                .andDo(
                        document("{ClassName}/{methodName}",
                                requestFields(fieldWithPath("username").description("登录名"),
                                        fieldWithPath("password").description("密码")),
                                responseFields(fieldWithPath("code").description("返回自定义码"),
                                        fieldWithPath("msg").description("code描述信息"),
                                        fieldWithPath("data").description("请求具体返回内容"),
                                        fieldWithPath("data.id").description("用户id"),
                                        fieldWithPath("data.username").description("用户名"),
                                        fieldWithPath("data.token").description("请求头需要携带的token"),
                                        fieldWithPath("data.refreshToken").description("用来刷新token")
                                )
                        )
                ).andReturn();
        ResultJson<UserVO> resultJson = JSON.parseObject(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<ResultJson<UserVO>>(){});
        token = resultJson.getData().getToken();
    }



    @Test
    public void getUserIndex() throws Exception {
        mockMvc.perform(get("/api/v1/users/me").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(
                        document("{ClassName}/{methodName}",
                                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                                requestHeaders(headerWithName("Authorization").description("token")),
                                responseFields(fieldWithPath("code").description("返回自定义码"),
                                        fieldWithPath("msg").description("code描述信息"),
                                        fieldWithPath("data").description("请求具体返回内容"),
                                        fieldWithPath("data.id").description("用户id"),
                                        fieldWithPath("data.menus").description("用户菜单"),
                                        fieldWithPath("data.name").description("用户账号"),
                                        fieldWithPath("data.nickname").description("用户昵称")
                                )
                        )
                );
    }

    public void loginBefore() throws Exception{
        HashMap<Object, Object> reqBody = new HashMap<>();
        reqBody.put("username", "admin");
        reqBody.put("password", "123456");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqBody)))
                .andReturn();
        ResultJson<UserVO> resultJson = JSON.parseObject(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), new TypeReference<ResultJson<UserVO>>(){});
        token = resultJson.getData().getToken();
    }
}
