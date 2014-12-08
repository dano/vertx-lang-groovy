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
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
@CompileStatic
public class Future<T> {
  final def io.vertx.core.Future delegate;
  public Future(io.vertx.core.Future delegate) {
    this.delegate = delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  public static <T> Future<T> future() {
    def ret= Future.FACTORY.apply(io.vertx.core.Future.future());
    return ret;
  }
  public static <T> Future<T> succeededFuture() {
    def ret= Future.FACTORY.apply(io.vertx.core.Future.succeededFuture());
    return ret;
  }
  public static <T> Future<T> succeededFuture(T result) {
    def ret= Future.FACTORY.apply(io.vertx.core.Future.succeededFuture(InternalHelper.unwrapObject(result)));
    return ret;
  }
  public static <T> Future<T> failedFuture(String failureMessage) {
    def ret= Future.FACTORY.apply(io.vertx.core.Future.failedFuture(failureMessage));
    return ret;
  }
  /**
   * Has it completed?
   */
  public boolean isComplete() {
    def ret = ((io.vertx.core.Future) this.delegate).isComplete();
    return ret;
  }
  /**
   * Set a handler for the result. It will get called when it's complete
   */
  public void setHandler(Handler<AsyncResult<T>> handler) {
    ((io.vertx.core.Future) this.delegate).setHandler(new Handler<AsyncResult<Object>>() {
      public void handle(AsyncResult<Object> event) {
        AsyncResult<Object> f
        if (event.succeeded()) {
          f = InternalHelper.<Object>result(InternalHelper.wrapObject(event.result()))
        } else {
          f = InternalHelper.<Object>failure(event.cause())
        }
        handler.handle(f)
      }
    });
  }
  /**
   * Set the result. Any handler will be called, if there is one
   *
   * @throws IllegalStateException when the future is already completed
   */
  public void complete(T result) {
    ((io.vertx.core.Future) this.delegate).complete(InternalHelper.unwrapObject(result));
  }
  public void complete() {
    ((io.vertx.core.Future) this.delegate).complete();
  }
  public void fail(String failureMessage) {
    ((io.vertx.core.Future) this.delegate).fail(failureMessage);
  }

  static final java.util.function.Function<io.vertx.core.Future, Future> FACTORY = io.vertx.lang.groovy.Factories.createFactory() {
    io.vertx.core.Future arg -> new Future(arg);
  };
}