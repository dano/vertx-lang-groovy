/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.groovy.core;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * Represents the result of an action that may, or may not, have occurred yet.
 * <p>
*/
@CompileStatic
public class Future<T> {
  private final def io.vertx.core.Future delegate;
  public Future(Object delegate) {
    this.delegate = (io.vertx.core.Future) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * Create a future that hasn't completed yet
   * @return the future
   */
  public static <T> Future<T> future() {
    def ret = InternalHelper.safeCreate(io.vertx.core.Future.future(), io.vertx.groovy.core.Future.class);
    return ret;
  }
  /**
   * Create a succeeded future with a null result
   * @return the future
   */
  public static <T> Future<T> succeededFuture() {
    def ret = InternalHelper.safeCreate(io.vertx.core.Future.succeededFuture(), io.vertx.groovy.core.Future.class);
    return ret;
  }
  /**
   * Created a succeeded future with the specified result.
   * @param result the result
   * @return the future
   */
  public static <T> Future<T> succeededFuture(T result) {
    def ret = InternalHelper.safeCreate(io.vertx.core.Future.succeededFuture(result != null ? InternalHelper.unwrapObject(result) : null), io.vertx.groovy.core.Future.class);
    return ret;
  }
  /**
   * Create a failed future with the specified failure message.
   * @param failureMessage the failure message
   * @return the future
   */
  public static <T> Future<T> failedFuture(String failureMessage) {
    def ret = InternalHelper.safeCreate(io.vertx.core.Future.failedFuture(failureMessage), io.vertx.groovy.core.Future.class);
    return ret;
  }
  /**
   * Has the future completed?
   * <p>
   * It's completed if it's either succeeded or failed.
   * @return true if completed, false if not
   */
  public boolean isComplete() {
    def ret = delegate.isComplete();
    return ret;
  }
  /**
   * Set a handler for the result.
   * <p>
   * If the future has already been completed it will be called immediately. Otherwise it will be called when the
   * future is completed.
   * @param handler the Handler that will be called with the result
   * @return a reference to this, so it can be used fluently
   */
  public Future<T> setHandler(Handler<AsyncResult<T>> handler) {
    delegate.setHandler(handler != null ? new Handler<AsyncResult<java.lang.Object>>() {
      public void handle(AsyncResult<java.lang.Object> ar) {
        if (ar.succeeded()) {
          handler.handle(io.vertx.core.Future.succeededFuture((Object) InternalHelper.wrapObject(ar.result())));
        } else {
          handler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    } : null);
    return this;
  }
  /**
   * Set the result. Any handler will be called, if there is one, and the future will be marked as completed.
   * @param result the result
   */
  public void complete(T result) {
    delegate.complete(result != null ? InternalHelper.unwrapObject(result) : null);
  }
  /**
   * Set a null result. Any handler will be called, if there is one, and the future will be marked as completed.
   */
  public void complete() {
    delegate.complete();
  }
  /**
   * Set the failure. Any handler will be called, if there is one, and the future will be marked as completed.
   * @param throwable the failure cause
   */
  public void fail(Throwable throwable) {
    delegate.fail(throwable);
  }
  /**
   * Set the failure. Any handler will be called, if there is one, and the future will be marked as completed.
   * @param failureMessage the failure message
   */
  public void fail(String failureMessage) {
    delegate.fail(failureMessage);
  }
  /**
   * The result of the operation. This will be null if the operation failed.
   * @return the result or null if the operation failed.
   */
  public T result() {
    def ret = (T) InternalHelper.wrapObject(delegate.result());
    return ret;
  }
  /**
   * A Throwable describing failure. This will be null if the operation succeeded.
   * @return the cause or null if the operation succeeded.
   */
  public Throwable cause() {
    def ret = delegate.cause();
    return ret;
  }
  /**
   * Did it succeed?
   * @return true if it succeded or false otherwise
   */
  public boolean succeeded() {
    def ret = delegate.succeeded();
    return ret;
  }
  /**
   * Did it fail?
   * @return true if it failed or false otherwise
   */
  public boolean failed() {
    def ret = delegate.failed();
    return ret;
  }
  /**
   * Compose this future with another future.
   *
   * When this future succeeds, the handler will be called with the value.
   *
   * When this future fails, the failure will be propagated to the <code>next</code> future.
   * @param handler the handler
   * @param next the next future
   */
  public <U> void compose(Handler<T> handler, Future<U> next) {
    delegate.compose(handler != null ? new Handler<java.lang.Object>(){
      public void handle(java.lang.Object event) {
        handler.handle((Object) InternalHelper.wrapObject(event));
      }
    } : null, next != null ? (io.vertx.core.Future<U>)next.getDelegate() : null);
  }
  /**
   * @return an handler completing this future
   * @return 
   */
  public Handler<AsyncResult<T>> completer() {
    if (cached_0 != null) {
      return cached_0;
    }
    def ret = new Handler<AsyncResult<Object>>() {
      public void handle(AsyncResult<Object> ar_) {
        if (ar_.succeeded()) {
          delegate.completer().handle(io.vertx.core.Future.succeededFuture(InternalHelper.unwrapObject(ar_.result())));
        } else  {
          delegate.completer().handle(io.vertx.core.Future.failedFuture(ar_.cause()));
        }
      }
    };
    cached_0 = ret;
    return ret;
  }
  private Handler<AsyncResult<T>> cached_0;
}
