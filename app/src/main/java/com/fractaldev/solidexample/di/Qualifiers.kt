package com.fractaldev.solidexample.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Database

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Network

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SafeMapper
