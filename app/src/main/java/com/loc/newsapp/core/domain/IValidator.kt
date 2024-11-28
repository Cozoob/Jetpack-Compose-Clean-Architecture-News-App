package com.loc.newsapp.core.domain

interface IValidator<T> {
  fun validate(value: T)
}
