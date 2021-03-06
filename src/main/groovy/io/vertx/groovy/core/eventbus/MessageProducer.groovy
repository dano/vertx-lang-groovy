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

package io.vertx.groovy.core.eventbus;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.core.json.JsonObject
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.groovy.core.streams.WriteStream
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * Represents a stream of message that can be written to.
 * <p>
*/
@CompileStatic
public class MessageProducer<T> implements WriteStream<T> {
  private final def io.vertx.core.eventbus.MessageProducer delegate;
  public MessageProducer(Object delegate) {
    this.delegate = (io.vertx.core.eventbus.MessageProducer) delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * Same as {@link io.vertx.groovy.core.eventbus.MessageProducer#end} but writes some data to the stream before ending.
   * @param t 
   */
  public void end(T t) {
    ((io.vertx.core.streams.WriteStream) delegate).end(t != null ? InternalHelper.unwrapObject(t) : null);
  }
  /**
   * This will return <code>true</code> if there are more bytes in the write queue than the value set using {@link io.vertx.groovy.core.eventbus.MessageProducer#setWriteQueueMaxSize}
   * @return true if write queue is full
   */
  public boolean writeQueueFull() {
    def ret = ((io.vertx.core.streams.WriteStream) delegate).writeQueueFull();
    return ret;
  }
  /**
   * Synonym for {@link io.vertx.groovy.core.eventbus.MessageProducer#write}.
   * @param message the message to send
   * @return reference to this for fluency
   */
  public MessageProducer<T> send(T message) {
    def ret = InternalHelper.safeCreate(delegate.send(message != null ? InternalHelper.unwrapObject(message) : null), io.vertx.groovy.core.eventbus.MessageProducer.class);
    return ret;
  }
  public <R> MessageProducer<T> send(T message, Handler<AsyncResult<Message<R>>> replyHandler) {
    def ret = InternalHelper.safeCreate(delegate.send(message != null ? InternalHelper.unwrapObject(message) : null, replyHandler != null ? new Handler<AsyncResult<io.vertx.core.eventbus.Message<java.lang.Object>>>() {
      public void handle(AsyncResult<io.vertx.core.eventbus.Message<java.lang.Object>> ar) {
        if (ar.succeeded()) {
          replyHandler.handle(io.vertx.core.Future.succeededFuture(InternalHelper.safeCreate(ar.result(), io.vertx.groovy.core.eventbus.Message.class)));
        } else {
          replyHandler.handle(io.vertx.core.Future.failedFuture(ar.cause()));
        }
      }
    } : null), io.vertx.groovy.core.eventbus.MessageProducer.class);
    return ret;
  }
  public MessageProducer<T> exceptionHandler(Handler<Throwable> handler) {
    ((io.vertx.core.streams.WriteStream) delegate).exceptionHandler(handler);
    return this;
  }
  public MessageProducer<T> write(T data) {
    ((io.vertx.core.streams.WriteStream) delegate).write(data != null ? InternalHelper.unwrapObject(data) : null);
    return this;
  }
  public MessageProducer<T> setWriteQueueMaxSize(int maxSize) {
    ((io.vertx.core.streams.WriteStream) delegate).setWriteQueueMaxSize(maxSize);
    return this;
  }
  public MessageProducer<T> drainHandler(Handler<Void> handler) {
    ((io.vertx.core.streams.WriteStream) delegate).drainHandler(handler);
    return this;
  }
  /**
   * Update the delivery options of this producer.
   * @param options the new options (see <a href="../../../../../../../cheatsheet/DeliveryOptions.html">DeliveryOptions</a>)
   * @return this producer object
   */
  public MessageProducer<T> deliveryOptions(Map<String, Object> options = [:]) {
    delegate.deliveryOptions(options != null ? new io.vertx.core.eventbus.DeliveryOptions(new io.vertx.core.json.JsonObject(options)) : null);
    return this;
  }
  /**
   * @return The address to which the producer produces messages.
   * @return 
   */
  public String address() {
    def ret = delegate.address();
    return ret;
  }
  /**
   * Closes the producer, calls {@link io.vertx.groovy.core.eventbus.MessageProducer#close}
   */
  public void end() {
    ((io.vertx.core.streams.WriteStream) delegate).end();
  }
  /**
   * Closes the producer, this method should be called when the message producer is not used anymore.
   */
  public void close() {
    delegate.close();
  }
}
