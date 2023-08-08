package com.lzg.jpa.util;

import com.google.common.collect.Maps;
import com.lzg.jpa.annotation.query.*;
import com.lzg.jpa.entity.BaseEntity;
import com.lzg.jpa.function.TiFunction;
import com.lzg.jpa.request.QueryRequest;
import com.wuchuangroup.jpa.annotation.query.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-08-02 17:19
 * @since 1.0
 **/
@Slf4j
public class JpaPredicateUtils {


    public static List<Predicate> getPredicates(QueryRequest queryRequest, Root<?> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

//        predicates.add(criteriaBuilder.ge(root.get("id"), 3));
//        predicates.add(criteriaBuilder.like(root.get("indexField1"), "%" + "indexField1" + "%"));
//        predicates.add(criteriaBuilder.equal(root.get("indexField2"), "indexField2"));
//        predicates.add(criteriaBuilder.equal(root.get("indexField3"), "indexField3"));
////                predicates.add(criteriaBuilder.equal(root.get("logicalDelete"), LogicalDeleteEnum.UN_DELETE));
//        predicates.add(criteriaBuilder.equal(root.get("varcharFiled"), "varcharFiled"));
//        predicates.add(criteriaBuilder.between(root.get("createTime"), LocalDateTime.of(LocalDate.of(2023, 7, 1), LocalTime.MAX), LocalDateTime.of(LocalDate.of(2023, 9, 1), LocalTime.MAX)));
//        predicates.add(criteriaBuilder.greaterThan(root.get("lastModifiedTime"), LocalDateTime.of(LocalDate.of(2023, 7, 2), LocalTime.MAX)));
//        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("lastModifiedTime"), LocalDateTime.of(LocalDate.of(2023, 7, 3), LocalTime.MAX)));

        return Arrays
                .stream(queryRequest.getClass().getDeclaredFields())
                .map(field -> {

                    Annotation[] annotations = field.getDeclaredAnnotations();

                    return Arrays.stream(annotations)
                            .map(annotation -> getPredicate(queryRequest, field, annotation, criteriaBuilder, root))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

    }

    public static List<Order> getOrders(CriteriaBuilder criteriaBuilder, Root<?> root, QueryRequest queryRequest) {

        return Arrays.stream(queryRequest.getClass().getDeclaredFields())
                .map(field -> Arrays
                        .stream(field.getDeclaredAnnotations())
                        .filter(item -> item.annotationType() == OrderBy.class)
                        .distinct()
                        .map(item -> {
                            field.setAccessible(true);
                            OrderBy orderBy = field.getDeclaredAnnotation(OrderBy.class);

                            String targetFieldName = StringUtils.isBlank(orderBy.targetFieldName()) ? field.getName() : orderBy.targetFieldName();

                            int priority = orderBy.priority();

                            OrderBy.DIRECTION direction = orderBy.direction();

                            if (direction == OrderBy.DIRECTION.ASC) {
                                return new OrderObj(priority, criteriaBuilder.asc(root.get(targetFieldName)));
                            } else {
                                return new OrderObj(priority, criteriaBuilder.desc(root.get(targetFieldName)));
                            }
                        }).collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .sorted(Comparator.comparingInt(OrderObj::getPriority))
                .map(OrderObj::getOrder)
                .collect(Collectors.toList());
    }

    /**
     *
     */
    private static Map<Class<? extends Annotation>, TiFunction<QueryRequest, Field, CriteriaBuilder, Root<?>, Predicate>> QUERY_ANNOTATIONS = Maps.newHashMap();

    static {
        QUERY_ANNOTATIONS.put(After.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            LocalDateTime fieldValue = (LocalDateTime) field.get(entity);

            String name = Arrays.stream(field.getDeclaredAnnotations())
                    .filter(annotation -> annotation.annotationType() == After.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(After.class))
                    .map(After::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                return cb.greaterThan(root.get(name), fieldValue);
            }
            return null;
        });

        QUERY_ANNOTATIONS.put(Before.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            LocalDateTime fieldValue = (LocalDateTime) field.get(entity);

            String name = Arrays
                    .stream(field.getDeclaredAnnotations())
                    .filter(annotation -> annotation.annotationType() == Before.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(Before.class))
                    .map(Before::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                return cb.lessThan(root.get(name), fieldValue);
            }
            return null;
        });

        QUERY_ANNOTATIONS.put(EndingWith.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == EndingWith.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(EndingWith.class))
                    .map(EndingWith::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                return cb.like(root.get(name), StringConstants.PERCENT + fieldValue);
            }
            return null;
        });

        QUERY_ANNOTATIONS.put(StartingWith.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == StartingWith.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(StartingWith.class))
                    .map(StartingWith::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                return cb.like(root.get(name), fieldValue + StringConstants.PERCENT);
            }
            return null;
        });


        QUERY_ANNOTATIONS.put(Containing.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == Containing.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(Containing.class))
                    .map(Containing::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                return cb.like(root.get(name), StringConstants.PERCENT + fieldValue + StringConstants.PERCENT);
            }
            return null;
        });

        QUERY_ANNOTATIONS.put(Equals.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == Equals.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(Equals.class))
                    .map(Equals::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                return cb.equal(root.get(name), fieldValue);
            }
            return null;
        });


        QUERY_ANNOTATIONS.put(GreaterThan.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == GreaterThan.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(GreaterThan.class))
                    .map(GreaterThan::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                //noinspection rawtypes,unchecked
                return cb.greaterThan(root.get(name), (Comparable) fieldValue);
            }
            return null;
        });

        QUERY_ANNOTATIONS.put(GreaterThanEqual.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == GreaterThanEqual.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(GreaterThanEqual.class))
                    .map(GreaterThanEqual::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                //noinspection rawtypes,unchecked
                return cb.greaterThanOrEqualTo(root.get(name), (Comparable) fieldValue);
            }
            return null;
        });


        QUERY_ANNOTATIONS.put(LessThan.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == LessThan.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(LessThan.class))
                    .map(LessThan::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                //noinspection rawtypes,unchecked
                return cb.lessThan(root.get(name), (Comparable) fieldValue);
            }
            return null;
        });

        QUERY_ANNOTATIONS.put(LessThanEqual.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Object fieldValue = field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == LessThanEqual.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(LessThanEqual.class))
                    .map(LessThanEqual::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                //noinspection rawtypes,unchecked
                return cb.lessThanOrEqualTo(root.get(name), (Comparable) fieldValue);
            }
            return null;
        });


        QUERY_ANNOTATIONS.put(IgnoreCase.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            String fieldValue = (String) field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == IgnoreCase.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(IgnoreCase.class))
                    .map(IgnoreCase::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                return cb.equal(cb.upper(root.get(name)), StringUtils.upperCase(fieldValue));
            }
            return null;
        });

        QUERY_ANNOTATIONS.put(In.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Collection<?> fieldValue = (Collection) field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == In.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(In.class))
                    .map(In::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                Path<Object> path = root.get(name);

                CriteriaBuilder.In<Object> in = cb.in(path);
                //存入值
                fieldValue.forEach(in::value);
                return cb.and(in);
            }
            return null;
        });

        QUERY_ANNOTATIONS.put(NotIn.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            Collection<?> fieldValue = (Collection) field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == NotIn.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(NotIn.class))
                    .map(NotIn::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                Path<Object> path = root.get(name);

                CriteriaBuilder.In<Object> in = cb.in(path);
                //存入值
                fieldValue.forEach(in::value);
                return cb.not(in);
            }
            return null;
        });


        QUERY_ANNOTATIONS.put(Like.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            String fieldValue = (String) field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == Like.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(Like.class))
                    .map(Like::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                return cb.like(root.get(name), fieldValue);
            }
            return null;
        });


        QUERY_ANNOTATIONS.put(NotLike.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            String fieldValue = (String) field.get(entity);

            Annotation[] annotations = field.getDeclaredAnnotations();

            String name = Arrays.stream(annotations)
                    .filter(annotation -> annotation.annotationType() == NotLike.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(NotLike.class))
                    .map(NotLike::targetFieldName)
                    .map(item -> StringUtils.isBlank(item) ? field.getName() : item)
                    .orElse(null);

            if (StringUtils.isNotBlank(name)) {
                return cb.notLike(root.get(name), fieldValue);
            }
            return null;
        });


        QUERY_ANNOTATIONS.put(Between.class, (entity, field, cb, root) -> {
            field.setAccessible(true);
            LocalDateTime fieldValue = (LocalDateTime) field.get(entity);

            Between between = Arrays.stream(field.getDeclaredAnnotations())
                    .filter(annotation -> annotation.annotationType() == Between.class)
                    .findFirst()
                    .map(annotation -> field.getDeclaredAnnotation(Between.class))
                    .orElse(null);

            if (between != null) {
                String targetFieldName = between.targetFieldName();

                targetFieldName = StringUtils.isBlank(targetFieldName) ? between.targetFieldName() : targetFieldName;

                Between.Flag flag = between.flag();

                if (flag == Between.Flag.START) {
                    return cb.greaterThan(root.get(targetFieldName), fieldValue);
                } else {
                    return cb.lessThan(root.get(targetFieldName), fieldValue);
                }
            }
            return null;
        });
    }


    public static Predicate getPredicate(QueryRequest request, Field field, Annotation annotation, CriteriaBuilder criteriaBuilder, Root<?> root) {

        TiFunction<QueryRequest, Field, CriteriaBuilder, Root<?>, Predicate> tiFunction = QUERY_ANNOTATIONS.get(annotation.annotationType());

        if (tiFunction != null) {
            try {
                return tiFunction.apply(request, field, criteriaBuilder, root);
            } catch (Exception e) {
                Object value = null;
                try {
                    value = field.get(request);
                } catch (IllegalAccessException ex) {
                    log.info(" =========== 获取属性的value失败 ===========");
                }
                log.info("================= 查询参数异常:{}", field.getName());
                throw new RuntimeException("查询参数异常 : [ " + field.getName() + " : +" + value + " ]");
            }
        }

        return null;
    }

    public static <T extends BaseEntity> Specification<T> getSpecification(QueryRequest queryRequest) {


        return (Specification<T>) (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = getPredicates(queryRequest, root, criteriaQuery, criteriaBuilder);
            List<Order> orders = getOrders(criteriaBuilder, root, queryRequest);

            return criteriaQuery
                    .where(predicates.toArray(new Predicate[0]))
                    .orderBy(orders.toArray(new Order[0]))
                    .getRestriction();
        };
    }

}
