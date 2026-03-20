cmake_minimum_required(VERSION 3.20)
project(ticket_driven_development_kata)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_library(ticket_pricing_engine src/ticket_pricing_engine.cpp)
target_include_directories(ticket_pricing_engine PUBLIC include)

enable_testing()
find_package(GTest REQUIRED)

add_executable(ticket_pricing_test
        tests/test_ticket_pricing_engine.cpp
)
target_link_libraries(ticket_pricing_test GTest::gtest_main)
target_link_libraries(ticket_pricing_test ticket_pricing_engine)
add_test(NAME ticket_pricing_test COMMAND ticket_pricing_test)
