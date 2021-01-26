package cn.les.auth.exception;

import cn.les.auth.entity.ResultCode;
import cn.les.auth.entity.ResultJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Joetao
 * 异常处理类
 * controller层异常无法捕获处理，需要自己处理
 * Created at 2018/8/27.
 */
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 处理所有自定义异常
     *
     * @param e 自定义异常
     * @return 返回自定义异常的ResultJson
     */
    @ExceptionHandler(cn.les.auth.exception.CustomException.class)
    public ResultJson<?> handleCustomException(HttpServletRequest req, CustomException e){
        String uri = req.getRequestURI();
        String params = getRequestData(req);
        log.error("==CustomException== {} {} {} {}", uri, params, e.getResultJson().getMsg(), e.getResultJson().getData());
        return e.getResultJson();
    }

    /**
     * 处理参数校验异常
     *
     * @param e 参数校验异常
     * @return 参数异常提示
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultJson<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        if (e.getBindingResult().getFieldError() == null) {
            return ResultJson.failure(ResultCode.BAD_REQUEST, "参数错误");
        }
        log.error(e.getBindingResult().getFieldError().getField() + e.getBindingResult().getFieldError().getDefaultMessage());
        return ResultJson.failure(ResultCode.BAD_REQUEST, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    private static String getRequestData(HttpServletRequest request) {
        List<String> params = new ArrayList<>();
        Map<String, String[]> paramsMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
            params.add(entry.getKey() + ":" + String.join(",", entry.getValue()));
        }
        return params.toString();
    }
}
