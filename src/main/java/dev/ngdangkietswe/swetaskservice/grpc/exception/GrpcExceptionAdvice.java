package dev.ngdangkietswe.swetaskservice.grpc.exception;

import dev.ngdangkietswe.sweprotobufshared.proto.exception.advice.BaseGrpcExceptionAdvice;
import org.lognet.springboot.grpc.recovery.GRpcServiceAdvice;

/**
 * @author ngdangkietswe
 * @since 12/30/2024
 */

@GRpcServiceAdvice
public class GrpcExceptionAdvice extends BaseGrpcExceptionAdvice {
}
