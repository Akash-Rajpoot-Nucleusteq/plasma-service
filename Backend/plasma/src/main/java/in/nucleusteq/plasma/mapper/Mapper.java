package in.nucleusteq.plasma.mapper;
/**
 * Mapper.
 */
public interface Mapper {
/**
 * map.
 */
    <T, U> U map(T source, Class<U> destinationType);
}
