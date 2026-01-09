package com.noorimat.cache.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.60.0)",
    comments = "Source: cache.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CacheServiceGrpc {

  private CacheServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "cache.CacheService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.noorimat.cache.grpc.GetRequest,
      com.noorimat.cache.grpc.GetResponse> getGetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Get",
      requestType = com.noorimat.cache.grpc.GetRequest.class,
      responseType = com.noorimat.cache.grpc.GetResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.noorimat.cache.grpc.GetRequest,
      com.noorimat.cache.grpc.GetResponse> getGetMethod() {
    io.grpc.MethodDescriptor<com.noorimat.cache.grpc.GetRequest, com.noorimat.cache.grpc.GetResponse> getGetMethod;
    if ((getGetMethod = CacheServiceGrpc.getGetMethod) == null) {
      synchronized (CacheServiceGrpc.class) {
        if ((getGetMethod = CacheServiceGrpc.getGetMethod) == null) {
          CacheServiceGrpc.getGetMethod = getGetMethod =
              io.grpc.MethodDescriptor.<com.noorimat.cache.grpc.GetRequest, com.noorimat.cache.grpc.GetResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Get"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.noorimat.cache.grpc.GetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.noorimat.cache.grpc.GetResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CacheServiceMethodDescriptorSupplier("Get"))
              .build();
        }
      }
    }
    return getGetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.noorimat.cache.grpc.PutRequest,
      com.noorimat.cache.grpc.PutResponse> getPutMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Put",
      requestType = com.noorimat.cache.grpc.PutRequest.class,
      responseType = com.noorimat.cache.grpc.PutResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.noorimat.cache.grpc.PutRequest,
      com.noorimat.cache.grpc.PutResponse> getPutMethod() {
    io.grpc.MethodDescriptor<com.noorimat.cache.grpc.PutRequest, com.noorimat.cache.grpc.PutResponse> getPutMethod;
    if ((getPutMethod = CacheServiceGrpc.getPutMethod) == null) {
      synchronized (CacheServiceGrpc.class) {
        if ((getPutMethod = CacheServiceGrpc.getPutMethod) == null) {
          CacheServiceGrpc.getPutMethod = getPutMethod =
              io.grpc.MethodDescriptor.<com.noorimat.cache.grpc.PutRequest, com.noorimat.cache.grpc.PutResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Put"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.noorimat.cache.grpc.PutRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.noorimat.cache.grpc.PutResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CacheServiceMethodDescriptorSupplier("Put"))
              .build();
        }
      }
    }
    return getPutMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.noorimat.cache.grpc.DeleteRequest,
      com.noorimat.cache.grpc.DeleteResponse> getDeleteMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Delete",
      requestType = com.noorimat.cache.grpc.DeleteRequest.class,
      responseType = com.noorimat.cache.grpc.DeleteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.noorimat.cache.grpc.DeleteRequest,
      com.noorimat.cache.grpc.DeleteResponse> getDeleteMethod() {
    io.grpc.MethodDescriptor<com.noorimat.cache.grpc.DeleteRequest, com.noorimat.cache.grpc.DeleteResponse> getDeleteMethod;
    if ((getDeleteMethod = CacheServiceGrpc.getDeleteMethod) == null) {
      synchronized (CacheServiceGrpc.class) {
        if ((getDeleteMethod = CacheServiceGrpc.getDeleteMethod) == null) {
          CacheServiceGrpc.getDeleteMethod = getDeleteMethod =
              io.grpc.MethodDescriptor.<com.noorimat.cache.grpc.DeleteRequest, com.noorimat.cache.grpc.DeleteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Delete"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.noorimat.cache.grpc.DeleteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.noorimat.cache.grpc.DeleteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CacheServiceMethodDescriptorSupplier("Delete"))
              .build();
        }
      }
    }
    return getDeleteMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.noorimat.cache.grpc.StatsRequest,
      com.noorimat.cache.grpc.StatsResponse> getGetStatsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStats",
      requestType = com.noorimat.cache.grpc.StatsRequest.class,
      responseType = com.noorimat.cache.grpc.StatsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.noorimat.cache.grpc.StatsRequest,
      com.noorimat.cache.grpc.StatsResponse> getGetStatsMethod() {
    io.grpc.MethodDescriptor<com.noorimat.cache.grpc.StatsRequest, com.noorimat.cache.grpc.StatsResponse> getGetStatsMethod;
    if ((getGetStatsMethod = CacheServiceGrpc.getGetStatsMethod) == null) {
      synchronized (CacheServiceGrpc.class) {
        if ((getGetStatsMethod = CacheServiceGrpc.getGetStatsMethod) == null) {
          CacheServiceGrpc.getGetStatsMethod = getGetStatsMethod =
              io.grpc.MethodDescriptor.<com.noorimat.cache.grpc.StatsRequest, com.noorimat.cache.grpc.StatsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStats"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.noorimat.cache.grpc.StatsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.noorimat.cache.grpc.StatsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CacheServiceMethodDescriptorSupplier("GetStats"))
              .build();
        }
      }
    }
    return getGetStatsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CacheServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CacheServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CacheServiceStub>() {
        @java.lang.Override
        public CacheServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CacheServiceStub(channel, callOptions);
        }
      };
    return CacheServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CacheServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CacheServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CacheServiceBlockingStub>() {
        @java.lang.Override
        public CacheServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CacheServiceBlockingStub(channel, callOptions);
        }
      };
    return CacheServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CacheServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CacheServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CacheServiceFutureStub>() {
        @java.lang.Override
        public CacheServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CacheServiceFutureStub(channel, callOptions);
        }
      };
    return CacheServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void get(com.noorimat.cache.grpc.GetRequest request,
        io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.GetResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMethod(), responseObserver);
    }

    /**
     */
    default void put(com.noorimat.cache.grpc.PutRequest request,
        io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.PutResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPutMethod(), responseObserver);
    }

    /**
     */
    default void delete(com.noorimat.cache.grpc.DeleteRequest request,
        io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.DeleteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteMethod(), responseObserver);
    }

    /**
     */
    default void getStats(com.noorimat.cache.grpc.StatsRequest request,
        io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.StatsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStatsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CacheService.
   */
  public static abstract class CacheServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CacheServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CacheService.
   */
  public static final class CacheServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CacheServiceStub> {
    private CacheServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CacheServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CacheServiceStub(channel, callOptions);
    }

    /**
     */
    public void get(com.noorimat.cache.grpc.GetRequest request,
        io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.GetResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void put(com.noorimat.cache.grpc.PutRequest request,
        io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.PutResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPutMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void delete(com.noorimat.cache.grpc.DeleteRequest request,
        io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.DeleteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStats(com.noorimat.cache.grpc.StatsRequest request,
        io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.StatsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStatsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CacheService.
   */
  public static final class CacheServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CacheServiceBlockingStub> {
    private CacheServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CacheServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CacheServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.noorimat.cache.grpc.GetResponse get(com.noorimat.cache.grpc.GetRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.noorimat.cache.grpc.PutResponse put(com.noorimat.cache.grpc.PutRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPutMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.noorimat.cache.grpc.DeleteResponse delete(com.noorimat.cache.grpc.DeleteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.noorimat.cache.grpc.StatsResponse getStats(com.noorimat.cache.grpc.StatsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStatsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CacheService.
   */
  public static final class CacheServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CacheServiceFutureStub> {
    private CacheServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CacheServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CacheServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.noorimat.cache.grpc.GetResponse> get(
        com.noorimat.cache.grpc.GetRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.noorimat.cache.grpc.PutResponse> put(
        com.noorimat.cache.grpc.PutRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPutMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.noorimat.cache.grpc.DeleteResponse> delete(
        com.noorimat.cache.grpc.DeleteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.noorimat.cache.grpc.StatsResponse> getStats(
        com.noorimat.cache.grpc.StatsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStatsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET = 0;
  private static final int METHODID_PUT = 1;
  private static final int METHODID_DELETE = 2;
  private static final int METHODID_GET_STATS = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET:
          serviceImpl.get((com.noorimat.cache.grpc.GetRequest) request,
              (io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.GetResponse>) responseObserver);
          break;
        case METHODID_PUT:
          serviceImpl.put((com.noorimat.cache.grpc.PutRequest) request,
              (io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.PutResponse>) responseObserver);
          break;
        case METHODID_DELETE:
          serviceImpl.delete((com.noorimat.cache.grpc.DeleteRequest) request,
              (io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.DeleteResponse>) responseObserver);
          break;
        case METHODID_GET_STATS:
          serviceImpl.getStats((com.noorimat.cache.grpc.StatsRequest) request,
              (io.grpc.stub.StreamObserver<com.noorimat.cache.grpc.StatsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.noorimat.cache.grpc.GetRequest,
              com.noorimat.cache.grpc.GetResponse>(
                service, METHODID_GET)))
        .addMethod(
          getPutMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.noorimat.cache.grpc.PutRequest,
              com.noorimat.cache.grpc.PutResponse>(
                service, METHODID_PUT)))
        .addMethod(
          getDeleteMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.noorimat.cache.grpc.DeleteRequest,
              com.noorimat.cache.grpc.DeleteResponse>(
                service, METHODID_DELETE)))
        .addMethod(
          getGetStatsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.noorimat.cache.grpc.StatsRequest,
              com.noorimat.cache.grpc.StatsResponse>(
                service, METHODID_GET_STATS)))
        .build();
  }

  private static abstract class CacheServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CacheServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.noorimat.cache.grpc.CacheProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CacheService");
    }
  }

  private static final class CacheServiceFileDescriptorSupplier
      extends CacheServiceBaseDescriptorSupplier {
    CacheServiceFileDescriptorSupplier() {}
  }

  private static final class CacheServiceMethodDescriptorSupplier
      extends CacheServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CacheServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (CacheServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CacheServiceFileDescriptorSupplier())
              .addMethod(getGetMethod())
              .addMethod(getPutMethod())
              .addMethod(getDeleteMethod())
              .addMethod(getGetStatsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
