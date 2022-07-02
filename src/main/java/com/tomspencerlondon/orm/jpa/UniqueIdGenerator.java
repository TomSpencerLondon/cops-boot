package com.tomspencerlondon.orm.jpa;

public interface UniqueIdGenerator<T> {
  T getNextUniqueId();
}
