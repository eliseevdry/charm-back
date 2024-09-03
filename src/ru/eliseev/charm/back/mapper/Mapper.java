package ru.eliseev.charm.back.mapper;

public interface Mapper<From, To> {
    To map(From from, To to);
}
