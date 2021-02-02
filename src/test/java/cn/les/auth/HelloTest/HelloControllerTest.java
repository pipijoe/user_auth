package cn.les.auth.HelloTest;

import cn.les.auth.config.security.JwtAuthenticationTokenFilter;
import cn.les.auth.entity.ResultJson;
import cn.les.auth.entity.vo.UserVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
    public void getUserMenu() throws Exception {
        mockMvc.perform(get("/api/v1/menus").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andDo(
                        document("{ClassName}/{methodName}",
                                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                                requestHeaders(headerWithName("Authorization").description("token")),
                                responseFields(fieldWithPath("code").description("返回自定义码"),
                                        fieldWithPath("msg").description("code描述信息"),
                                        fieldWithPath("data").description("请求具体返回内容"),
                                        fieldWithPath("data.menus").description("菜单"),
                                        fieldWithPath("data.menus.[].id").description("菜单id"),
                                        fieldWithPath("data.menus.[].parentId").description("菜单父id"),
                                        fieldWithPath("data.menus.[].menuName").description("菜单名称"),
                                        fieldWithPath("data.menus.[].menuIcon").description("菜单图标"),
                                        fieldWithPath("data.menus.[].type").description("菜单类型"),
                                        fieldWithPath("data.menus.[].children").description("子菜单菜单"),
                                        fieldWithPath("data.menus.[].children.[].id").ignored(),
                                        fieldWithPath("data.menus.[].children.[].parentId").ignored(),
                                        fieldWithPath("data.menus.[].children.[].menuName").ignored(),
                                        fieldWithPath("data.menus.[].children.[].menuIcon").ignored(),
                                        fieldWithPath("data.menus.[].children.[].type").ignored(),
                                        fieldWithPath("data.menus.[].children.[].children").ignored(),
                                        fieldWithPath("data.id").description("用户id"),
                                        fieldWithPath("data.name").description("用户账号"),
                                        fieldWithPath("data.nickname").description("用户昵称")
                                )
                        )
                );
    }

    @Test
    public void addUser() throws Exception{
        HashMap<Object, Object> reqBody = new HashMap<>();
        reqBody.put("username", "test");
        reqBody.put("nickname", "TEST");
        reqBody.put("password", "12345678");
        reqBody.put("roleIds", new ArrayList<Long>());

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqBody)))
                .andDo(
                        document("{ClassName}/{methodName}",
                                requestFields(fieldWithPath("username").description("登录名"),
                                        fieldWithPath("password").description("密码"),
                                        fieldWithPath("nickname").description("昵称"),
                                        fieldWithPath("roleIds").description("角色id列表")
                                ),
                                responseFields(fieldWithPath("code").description("返回自定义码"),
                                        fieldWithPath("msg").description("code描述信息"),
                                        fieldWithPath("data").description("用户id")
                                )
                        )
                );
    }

    @Test
    public void addRole() throws Exception{
        HashMap<Object, Object> reqBody = new HashMap<>();
        reqBody.put("roleName", "ROLE_admin");
        reqBody.put("roleNameZh", "管理员");
        reqBody.put("menuIds", new ArrayList<Long>());

        mockMvc.perform(post("/api/v1/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqBody)))
                .andDo(
                        document("{ClassName}/{methodName}",
                                requestFields(fieldWithPath("roleName").description("角色名"),
                                        fieldWithPath("roleNameZh").description("角色中文名"),
                                        fieldWithPath("menuIds").description("菜单id列表")
                                ),
                                responseFields(fieldWithPath("code").description("返回自定义码"),
                                        fieldWithPath("msg").description("code描述信息"),
                                        fieldWithPath("data").description("角色id")
                                )
                        )
                );
    }

    @Test
    public void addUserRole() throws Exception{
        List<Long> roleIds = new ArrayList<>();
        roleIds.add(2L);

        mockMvc.perform(post("/api/v1/users/{id}/roles", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roleIds)))
                .andDo(
                        document("{ClassName}/{methodName}",
                                pathParameters(parameterWithName("id").description("用户id")),
                                requestFields(
                                        fieldWithPath("[]").description("角色id列表")),
                                responseFields(fieldWithPath("code").description("返回自定义码"),
                                        fieldWithPath("msg").description("code描述信息"),
                                        fieldWithPath("data").description("角色id")
                                )
                        )
                );
    }

    @Test
    public void addMenu() throws Exception {
        HashMap<Object, Object> reqBody = new HashMap<>();
        reqBody.put("parentId", 0);
        reqBody.put("menuName", "任务管理");
        reqBody.put("type", 0);
        reqBody.put("description", "");
        reqBody.put("path", "/tasks");
        reqBody.put("menuIcon", "");
        reqBody.put("sort", 1);
        reqBody.put("permissionId", new ArrayList<Long>());

        mockMvc.perform(post("/api/v1/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reqBody)))
                .andDo(
                        document("{ClassName}/{methodName}",
                                requestFields(fieldWithPath("parentId").description("父菜单id"),
                                        fieldWithPath("menuName").description("菜单名称"),
                                        fieldWithPath("type").description("菜单类型，0：菜单组，1：功能菜单"),
                                        fieldWithPath("description").description("菜单描述"),
                                        fieldWithPath("path").description("菜单路径"),
                                        fieldWithPath("menuIcon").description("菜单Icon"),
                                        fieldWithPath("sort").description("菜单层级，1最高"),
                                        fieldWithPath("permissionId").description("权限id列表")
                                ),
                                responseFields(fieldWithPath("code").description("返回自定义码"),
                                        fieldWithPath("msg").description("code描述信息"),
                                        fieldWithPath("data").description("菜单id")
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
