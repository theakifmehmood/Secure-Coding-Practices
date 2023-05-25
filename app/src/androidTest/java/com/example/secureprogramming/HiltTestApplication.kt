package com.example.secureprogramming

import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(MyApplication::class)
interface HiltTestApplication

