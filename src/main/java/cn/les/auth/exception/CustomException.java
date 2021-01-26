package cn.les.auth.exception;

import cn.les.auth.entity.ResultJson;
import lombok.Getter;

/**
 * @author Joetao
 * Created at 2018/8/24.
 */
@Getter
public class CustomException extends RuntimeException{
    private final ResultJson<?> resultJson;

    public CustomException(ResultJson<?> resultJson) {
        this.resultJson = resultJson;
    }
}
