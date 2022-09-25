package indi.qjw.mx.common.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Ints;
import com.sun.istack.internal.Nullable;
import indi.qjw.mx.common.tuple.Tuple;
import indi.qjw.mx.common.tuple.TwoTuple;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
public class Utils {

    /**
     * 返回0~max之间的一个随机数,不包括max
     */
    public static int nextInt(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("max <= 0");
        }
        return ThreadLocalRandom.current().nextInt(max);
    }

    /**
     * 返回0~max之间的一个随机数,不包括max
     */
    public static long nextLong(long max) {
        if (max <= 0L) {
            throw new IllegalArgumentException("max <= 0");
        }
        return ThreadLocalRandom.current().nextLong(max);
    }

    /**
     * a+b 溢出时抛出异常 java.lang.ArithmeticException
     *
     * @param a
     * @param b
     * @return
     */
    public static int sum(int a, int b) {
        BigDecimal bigDecimal = new BigDecimal(a);
        BigDecimal bigDecimal1 = new BigDecimal(b);
        BigDecimal add = bigDecimal.add(bigDecimal1);
        return add.intValueExact();
    }

    /**
     * a-b 溢出时抛出异常 java.lang.ArithmeticException
     *
     * @param a
     * @param b
     * @return
     */
    public static int sub(int a, int b) {
        BigDecimal bigDecimal = new BigDecimal(a);
        BigDecimal bigDecimal1 = new BigDecimal(b);
        BigDecimal sub = bigDecimal.subtract(bigDecimal1);
        return sub.intValueExact();
    }

    /**
     * a*b 溢出时抛出异常 java.lang.ArithmeticException
     *
     * @param a
     * @param b
     * @return
     */
    public static int mul(int a, int b) {
        BigDecimal bigDecimal = new BigDecimal(a);
        BigDecimal bigDecimal1 = new BigDecimal(b);
        BigDecimal mul = bigDecimal.multiply(bigDecimal1);
        return mul.intValueExact();
    }

    /**
     * a/b  直接舍去小数位 溢出时抛出异常 java.lang.ArithmeticException
     *
     * @param a
     * @param b
     * @return
     */
    public static int div(int a, int b) {
        BigDecimal bigDecimal = new BigDecimal(a);
        BigDecimal bigDecimal1 = new BigDecimal(b);
        BigDecimal div = bigDecimal.divide(bigDecimal1, RoundingMode.DOWN);
        return div.intValueExact();
    }

    /**
     * a/b  溢出时抛出异常 java.lang.ArithmeticException
     *
     * @param a
     * @param b
     * @param mode 选择小数位处理方式
     * @return
     */
    public static int div(int a, int b, RoundingMode mode) {
        BigDecimal bigDecimal = new BigDecimal(a);
        BigDecimal bigDecimal1 = new BigDecimal(b);
        BigDecimal div = bigDecimal.divide(bigDecimal1, mode);
        return div.intValueExact();
    }

    /**
     * rate%的概率返回true
     */
    public static boolean isLuck(int rate) {
        return isLuck(rate, 100);
    }

    /**
     * rate%的概率返回true
     */
    public static boolean isLuck(float rate) {
        return ThreadLocalRandom.current().nextFloat() < rate;
    }

    /**
     * rate/base的概率返回true
     */
    public static boolean isLuck(int rate, int base) {
        if (base <= 0) {
            throw new IllegalArgumentException("base < 0");
        }
        return nextInt(base) < rate;
    }

    /**
     * 返回from~to之间的随机数,包括form和to
     */
    public static int random(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from > to");
        }
        int n = to - from + 1;
        return nextInt(n) + from;
    }

    /**
     * 返回from~to之间的随机数,包括form和to
     */
    public static long longRandom(long from, long to) {
        if (from > to) {
            throw new IllegalArgumentException("from > to");
        }
        long n = to - from + 1;
        return nextLong(n) + from;
    }

    /**
     * 返回from~to之间以interval为间隔的随机数,包括form和to
     */
    public static int randomValue(int from, int to, int interval) {
        if (from > to) {
            throw new IllegalArgumentException("from > to");
        }
        if (interval <= 0 || interval >= to) {
            throw new IllegalArgumentException("interval error");
        }
        int n = (to - from) / interval + 1;
        return nextInt(n) * interval + from;
    }

    /**
     * 等概率随机选一个非当前点
     */
    public static <T> T randomChooseNonCurrent(Collection<T> list, T c) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        T r;
        while (true) {
            int index = nextInt(list.size());
            if (!IndexUtil.get(list, index).equals(c)) {
                r = IndexUtil.get(list, index);
                break;
            }
        }
        return r;
    }

    /**
     * 等概率随机选一个
     */
    public static <T> T randomChoose(Collection<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int index = nextInt(list.size());
        return IndexUtil.get(list, index);
    }

    public static <T> Optional<T> randomChooseEx(@Nullable Collection<T> collection) {
        return randomChoose(collection, null);
    }

    /**
     * 等概率随机选一个
     */
    public static <T> T randomChoose(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        int index = nextInt(array.length);
        return array[index];
    }

    /**
     * 等概率随机选一个
     */
    public static int randomChoose(int[] ints) {
        if (ints == null || ints.length == 0) {
            throw new IllegalArgumentException();
        }
        int index = nextInt(ints.length);
        return ints[index];
    }

    /**
     * 等概率随机选择n个
     */
    public static <T> Set<T> randomChooseN(Set<T> set, int n, Set<T> contain) {
        ArrayList list = new ArrayList();
        list.addAll(set);

        HashSet back = new HashSet();
        for (T t : contain) {
            if (n <= 0) {
                break;
            }
            if (set.contains(t)) {
                list.remove(t);
                back.add(t);
                n--;
            }
        }
        back.addAll(randomChooseN(list, n));
        return back;
    }

    public static <T> Optional<T> randomChoose(@Nullable Collection<T> collection, @Nullable ToIntFunction<T> weigher) {
        if (collection == null) {
            return Optional.empty();
        }
        int total = calculateTotalWeight(collection, weigher);
        return randomChoose(collection.stream(), total, weigher);
    }

    private static <T> Optional<T> randomChoose(Stream<T> stream, int total, ToIntFunction<T> weigher) {
        if (stream == null || total <= 0) {
            return Optional.empty();
        }
        int random = nextInt(total);
        if (weigher == null) {
            return stream.skip(random).findFirst();
        }
        TwoTuple<Integer, T> tuple = Tuple.tuple(random, null);
        BiFunction<TwoTuple<Integer, T>, T, TwoTuple<Integer, T>> accumulator = (t, obj) ->
                t.getFirst() < 0 ? t : Tuple.tuple(t.getFirst() - getWeight(weigher, obj), obj);
        tuple = Functions.foldSequential(stream, tuple, accumulator);
        return Optional.ofNullable(tuple.getSecond());
    }

    public static <T> int getWeight(ToIntFunction<T> weigher, T obj) {
        if (obj == null) {
            return 0;
        }
        return weigher == null ? 1 : weigher.applyAsInt(obj);
    }

    private static <T> int calculateTotalWeight(Collection<T> collection, ToIntFunction<T> weigher) {
        if (collection == null) {
            return 0;
        }
        if (weigher == null) {
            return collection.size();
        }
        return calculateTotalWeight(collection.stream(), weigher);
    }

    private static <T> int calculateTotalWeight(Stream<T> stream, ToIntFunction<T> weigher) {
        if (stream == null) {
            return 0;
        }
        if (weigher == null) {
            return Math.toIntExact(stream.count());
        }
        return stream.mapToInt(weigher).sum();
    }

    /**
     * 等概率随机选择n个
     */
    public static <T> List<T> randomChooseN(List<T> list, int n) {
        if (list == null || n <= 0) {
            return Collections.emptyList();
        }
        int size = list.size();
        List<T> result = new ArrayList<>(n);
        for (int i = 0; i < list.size() && n > 0; i++) {
            if (nextInt(size - i) < n) {
                result.add(list.get(i));
                n--;
            }
        }
        return result;
    }

    /**
     * 等概率随机选择n个
     */
    public static <T> List<T> randomChooseN(T[] array, int n) {
        int length;
        if (array == null || (length = array.length) == 0 || n <= 0) {
            return Collections.emptyList();
        }
        if (length <= n) {
            return Arrays.asList(array);
        }
        List<T> result = new ArrayList<>(n);
        for (int i = 0; i < length && n > 0; i++) {
            if (nextInt(length - i) < n) {
                result.add(array[i]);
                n--;
            }
        }
        return result;
    }

    /**
     * 等概率随机选择n个
     */
    public static List<Integer> randomChooseN(int[] ints, int n) {
        int length;
        if (ints == null || (length = ints.length) == 0 || n <= 0) {
            return Collections.emptyList();
        }
        if (length <= n) {
            return Ints.asList(ints);
        }
        List<Integer> result = new ArrayList<>(n);
        for (int i = 0; i < length && n > 0; i++) {
            if (nextInt(length - i) < n) {
                result.add(ints[i]);
                n--;
            }
        }
        return result;
    }

    /**
     * 等概率随机删除一个并返回
     */
    public static <T> T randomRemove(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int index = nextInt(list.size());
        return list.remove(index);
    }

    public static <T> Optional<T> randomRemoveEx(@Nullable List<T> list) {
        if (list == null || list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(list.remove(nextInt(list.size())));
    }

    /**
     * 以对应索引上的概率选择物品
     */
    public static <T> T randomChooseByRate(List<T> items, List<Integer> rates) {
        if (items == null || rates == null) {
            return null;
        }
        int index = randomIndex(rates);
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public static <T> T randomChooseByRate(List<T> items, int[] rates) {
        if (items == null || rates == null) {
            return null;
        }
        int index = randomIndex(rates);
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public static <T> T randomChooseByRate(T[] items, List<Integer> rates) {
        if (items == null || rates == null) {
            return null;
        }
        int index = randomIndex(rates);
        if (index >= 0 && index < items.length) {
            return items[index];
        }
        return null;
    }

    public static <T> T randomChooseByRate(T[] items, int[] rates) {
        if (items == null || rates == null) {
            return null;
        }
        int index = randomIndex(rates);
        if (index >= 0 && index < items.length) {
            return items[index];
        }
        return null;
    }

    public static int randomChooseByRate(int[] ints, int[] rates) {
        if (ints == null || rates == null || rates.length == 0 || ints.length < rates.length) {
            throw new IllegalArgumentException();
        }
        int index = randomIndex(rates);
        return ints[index];
    }

    /**
     * 按概率返回索引
     */
    public static int randomIndex(List<Integer> rates) {
        if (rates == null) {
            return -1;
        }
        int total = 0;
        for (Integer rate : rates) {
            if (rate != null) {
                total += rate;
            }
        }
        if (total == 0) {
            return -1;
        }
        int random = nextInt(total);
        for (int i = 0; i < rates.size(); i++) {
            Integer rate = rates.get(i);
            if (rate != null) {
                random -= rate;
                if (random < 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 按概率返回索引
     */
    public static int randomIndex(int[] rates) {
        if (rates == null) {
            return -1;
        }
        int total = 0;
        for (int i = 0; i < rates.length; i++) {
            total += rates[i];
        }
        if (total == 0) {
            return -1;
        }
        int random = nextInt(total);
        for (int i = 0; i < rates.length; i++) {
            random -= rates[i];
            if (random < 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 将字符串数组转换为int数组
     */
    public static int[] parseToInts(String[] strings) {
        if (strings == null) {
            return new int[0];
        }
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        return ints;
    }

    public static int[] parseToInts(String string, String separator) {
        if (string == null || separator == null) {
            return new int[0];
        }
        return parseToInts(string.trim().split(separator));
    }

    private static Number toNumber(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Number) {
            return (Number) object;
        } else if (object instanceof Boolean) {
            Boolean aBoolean = (Boolean) object;
            return aBoolean ? 1 : 0;
        } else if (object instanceof BigInteger) {
            BigInteger bigInteger = (BigInteger) object;
            return bigInteger.longValue();
        } else if (object instanceof BigDecimal) {
            BigDecimal decimal = (BigDecimal) object;
            return decimal.doubleValue();
        }
        return null;
    }

    public static int parseToInt(@Nullable Object object) {
        return Ints.saturatedCast(parseToLong(object));
    }

    public static int parseToInt(@Nullable String data) {
        return Ints.saturatedCast(parseToLong(data));
    }

    public static long parseToLong(@Nullable Object object) {
        if (object == null) {
            return 0L;
        }
        Number number = toNumber(object);
        if (number != null) {
            return number.longValue();
        }
        return parseToLong(object.toString());
    }

    public static long parseToLong(@Nullable String data) {
        if (data == null) {
            return 0L;
        }
        data = data.trim();
        int length = data.length();
        if (length == 0) {
            return 0L;
        }
        int radix = 10;
        if (data.charAt(0) == '0' && length > 1) {
            char c = data.charAt(1);
            switch (c) {
                case 'x':
                case 'X':
                    if (length > 2) {
                        data = data.substring(2);
                    } else {
                        return 0L;
                    }
                    radix = 16;
                    break;
                case 'b':
                case 'B':
                    if (length > 2) {
                        data = data.substring(2);
                    } else {
                        return 0L;
                    }
                    radix = 2;
                    break;
                default:
                    data = data.substring(1);
                    radix = 8;
                    break;
            }
            if (data.isEmpty()) {
                return 0L;
            }
        }
        Long aLong = null;
        try {
            aLong = Long.parseLong(data, radix);
        } catch (Exception ignore) {
        }
        return aLong == null ? 0L : aLong;
    }

    public static double parseToDouble(@Nullable Object object) {
        if (object == null) {
            return 0.0;
        }
        Number number = toNumber(object);
        if (number != null) {
            return number.doubleValue();
        }
        return parseToDouble(object.toString());
    }

    public static double parseToDouble(@Nullable String data) {
        if (data == null) {
            return 0.0;
        }
        data = data.trim();
        if (data.isEmpty()) {
            return 0.0;
        }
        Double aDouble = null;
        try {
            aDouble = Double.parseDouble(data);
        } catch (Exception ignore) {
        }
        return aDouble == null ? 0.0 : aDouble;
    }

    public static boolean parseToBoolean(@Nullable Object object) {
        if (object == null) {
            return false;
        }
        if (object instanceof Boolean) {
            return (Boolean) object;
        } else {
            Number number = toNumber(object);
            if (number != null) {
                return number.doubleValue() != 0;
            }
        }
        return parseToBoolean(object.toString());
    }

    /**
     * true, yes, on(无视大小写),非0数返回true,其他情况返回false
     *
     * @param data
     * @return
     */
    public static boolean parseToBoolean(@Nullable String data) {
        if (data == null) {
            return false;
        }
        data = data.trim();
        if (data.isEmpty()) {
            return false;
        }
        if (data.equalsIgnoreCase("true") || data.equalsIgnoreCase("yes") ||
                data.equalsIgnoreCase("on")) {
            return true;
        }
        return parseToDouble(data) != 0;
    }

    //是否是数字
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    //=============================================================================================

    /**
     * 打印堆栈
     */
    public static String getStackTrace() {
        return getStackTrace(1, 6, new Exception());
    }

    public static String getStackTrace(int start, int stop) {
        return getStackTrace(start, stop, new Exception());
    }

    public static String getStackTrace(Throwable throwable) {
        return getStackTrace(1, 6, throwable);
    }

    public static String getStackTrace(int start, int stop, Throwable throwable) {
        if (start > stop) {
            throw new IllegalArgumentException("start > stop");
        }
        StringBuilder builder = new StringBuilder((stop - start + 1) * 50);
        StackTraceElement[] stackTrace = throwable.getStackTrace();
        if (stackTrace.length < start + 2) {
            return builder.toString();
        }
        appendElement(builder, stackTrace[start + 1]);
        for (int i = start + 2; i < stop + 3 && i < stackTrace.length; i++) {
            builder.append("<=");
            appendElement(builder, stackTrace[i]);
        }
        return builder.toString();
    }

    private static void appendElement(StringBuilder builder, StackTraceElement element) {
        String className = element.getClassName();
        String methodName = element.getMethodName();
        int index = className.lastIndexOf('.');
        builder.append(className.substring(index + 1))
                .append('.')
                .append(methodName)
                .append(':')
                .append(element.getLineNumber());
    }

    //=============================================================================================

    /**
     * 返回list中第一个小于target的数的索引
     */
    public static int indexLessThan(List<Integer> list, Integer target) {
        return index(list, target, true, false);
    }

    /**
     * 返回list中第一个小于等于target的数的索引
     */
    public static int indexLessThanOrEqualTo(List<Integer> list, Integer target) {
        return index(list, target, true, true);
    }

    /**
     * 返回list中第一个小于target的数的索引
     */
    public static int indexLessThan(int[] list, int target) {
        return index(list, target, true, false);
    }

    /**
     * 返回list中第一个小于等于target的数的索引
     */
    public static int indexLessThanOrEqualTo(int[] list, int target) {
        return index(list, target, true, true);
    }

    /**
     * 返回list中第一个大于target的数的索引
     */
    public static int indexMoreThan(List<Integer> list, Integer target) {
        return index(list, target, false, false);
    }

    /**
     * 返回list中第一个大于等于target的数的索引
     */
    public static int indexMoreThanOrEqualTo(List<Integer> list, Integer target) {
        return index(list, target, false, true);
    }

    /**
     * 返回list中第一个大于target的数的索引
     */
    public static int indexMoreThan(int[] list, int target) {
        return index(list, target, false, false);
    }

    /**
     * 返回list中第一个大于等于target的数的索引
     */
    public static int indexMoreThanOrEqualTo(int[] list, int target) {
        return index(list, target, false, true);
    }


    public static int indexLastLessOrEqualTo(List<Integer> list, Integer target) {
        return lastIndex(list, target, true, true);
    }

    public static int indexLastMoreThanOrEqualTo(List<Integer> list, Integer target) {
        return lastIndex(list, target, false, true);
    }

    private static int lastIndex(List<Integer> list, Integer target, boolean less, boolean equal) {
        if (list == null) {
            return -1;
        }
        int size = list.size();
        for (int i = size - 1; i >= 0; i--) {
            Integer n = list.get(i);
            if (n == null) {
                if (equal && target == null) {
                    return i;
                }
            } else if ((less && target < n) || (!less && target > n) || (equal && n.equals(target))) {
                return i;
            }
        }
        return -1;
    }


    private static int index(List<Integer> list, Integer target, boolean less, boolean equal) {
        if (list == null) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Integer n = list.get(i);
            if (n == null) {
                if (equal && target == null) {
                    return i;
                }
            } else if ((less && target < n) || (!less && target > n) || (equal && n.equals(target))) {
                return i;
            }
        }
        return -1;
    }

    private static int index(int[] list, int target, boolean less, boolean equal) {
        if (list == null) {
            return -1;
        }
        int size = list.length;
        for (int i = 0; i < size; i++) {
            int n = list[i];
            if ((less && target < n) || (!less && target > n) || (equal && target == n)) {
                return i;
            }
        }
        return -1;
    }

    //=============================================================================================

    public static String md5(String input) {
        if (input == null) {
            return "";
        }
        return Hashing.md5().hashString(input, Charsets.UTF_8).toString();
    }

    public static String sha1(String input) {
        if (input == null) {
            return "";
        }
        return Hashing.sha1().hashString(input, Charsets.UTF_8).toString();
    }

    public static String sha256(String input) {
        if (input == null) {
            return "";
        }
        return Hashing.sha256().hashString(input, Charsets.UTF_8).toString();
    }

    /**
     * list数据的去重（用于背包下标这种唯一）
     */
    public static List<Integer> deleteRepeats(List<Integer> bagIndex) {
        List<Integer> result = new ArrayList<>(bagIndex.size());
        for (Integer index : bagIndex) {
            if (!result.contains(index)) {
                result.add(index);
            }
        }
        return result;
    }

    /**
     * 从集合中随机取得一个元素
     */
    public static <E> E getRandomElement(Collection<E> collection) {
        if (collection == null || collection.isEmpty()) throw new IllegalArgumentException("collection==null");
        int rn = nextInt(collection.size());
        int i = 0;
        for (E e : collection) {
            if (i == rn) {
                return e;
            }
            i++;
        }
        return null;
    }

    public static <E> E getRandomElement(Collection<E> collection, Collection<E> filterCollection, Comparator<E> comparator) {
        Collection<E> collect = filterCollection(collection, filterCollection, comparator);
        return getRandomElement(collect);
    }

    public static <E> Set<E> getRandomElement(Collection<E> collection, Collection<E> filterCollection, Comparator<E> comparator, int num) {
        Set<E> set = new HashSet<>();
        Collection<E> collect = filterCollection(collection, filterCollection, comparator);
        Set<E> filterSet = new HashSet<>(collect);
        while (filterSet.size() > 0 && set.size() < num) {
            E e = getRandomElement(filterSet);
            filterSet.remove(e);
            set.add(e);
        }
        return set;
    }

    public static <E> Collection<E> filterCollection(Collection<E> collection, Collection<E> filterCollection, Comparator<E> comparator) {
        if (collection == null || filterCollection == null)
            throw new IllegalArgumentException("set==null||filterSet==null");
        Set<E> set = new HashSet<>(collection);
        Iterator<E> it = set.iterator();
        while (it.hasNext()) {
            E next = it.next();
            for (E e : filterCollection) {
                if (comparator.compare(next, e) == 0) {
                    it.remove();
                }
            }
        }
        return set;
    }

    public static <E> Collection<E> CollectionFilter(Collection<E> collection, Predicate... predicates) {
        if (collection == null || predicates == null) {
            throw new IllegalArgumentException("collection==null||predicate==null");
        }
        Stream<E> stream = collection.stream();
        for (Predicate predicate : predicates) {
            stream = stream.filter(predicate);
        }
        return stream.collect(Collectors.toList());
    }

    public static <E, F> Map<E, F> MapFilter(Map<E, F> map, Predicate... predicates) {
        if (map == null || predicates == null) {
            throw new IllegalArgumentException("map==null||predicate==null");
        }
        Stream<Map.Entry<E, F>> stream = map.entrySet().stream();
        for (Predicate predicate : predicates) {
            stream = stream.filter(predicate);
        }
        return stream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static List<String> stringToList(String str, String separator) {
        return Arrays.asList(str.split(separator));
    }

    /**
     * 解析int[] 为Map[手写,调用请测试] key=array[偶数] value=array[奇数]
     */
    public static Map<Integer, Integer> parseArrayToMap(int[] array) {
        Map<Integer, Integer> map = new LinkedHashMap<>();
        if (array.length % 2 != 0) {
            throw new IllegalArgumentException("参数错误，数组长度不为偶数");
        }
        for (int i = 0; i < array.length; i += 2) {
            map.put(array[i], array[i + 1]);
        }
        return map;
    }

    /**
     * 解析int[] 为Map[手写,调用请测试] key=array[偶数] value=array[奇数]
     */
    public static Set<Integer> parseArrayToSet(int[] array) {
        Set<Integer> set = new HashSet<>();
        for (Integer e : array) {
            set.add(e);
        }
        return set;
    }

    public static <E> Set<E> parseArrayToSet(E[] array) {
        Set<E> set = new HashSet<>();
        Collections.addAll(set, array);
        return set;
    }

    /**
     * 得到位置[用时测试]
     */
    public static Integer getPos(Collection<Integer> collection, int num, boolean sort) {
        List<Integer> list = new ArrayList<>(collection);
        if (sort) {
            Collections.sort(list);
        }
        Integer pos = null;
        for (Integer value : list) {
            if (num >= value) {
                pos = value;
            } else {
                break;
            }
        }
        return pos;
    }

    /**
     * 删除位置list有序[用时测试]
     */
    public static void deletePos(int value, List<Integer> list) {
        if (list == null || list.isEmpty()) return;
        for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext(); ) {
            Integer next = iterator.next();
            if (next != value) {
                iterator.remove();
            } else {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * 得到位置[用时测试]
     */
    public static <E> E getPos(Collection<E> collection, E e, boolean sort, Comparator<E> comparator) {
        if (collection == null || collection.isEmpty()) return null;
        List<E> list = new ArrayList<>(collection);
        if (sort) {
            Collections.sort(list, comparator);
        }
        E pos = null;
        for (E value : list) {
            int compare = comparator.compare(e, value);
            if (compare >= 0) {
                pos = value;
            }
        }
        return pos;
    }

    /**
     * 删除位置[用时测试]
     */
    public static <E> boolean deletePos(Collection<E> collection, E e, boolean sort, Comparator<E> comparator) {
        if (collection == null || collection.isEmpty()) return false;
        List<E> list = new ArrayList<>(collection);
        if (sort) {
            Collections.sort(list, comparator);
        }
        Iterator<E> iterator = collection.iterator();
        while (iterator.hasNext()) {
            E next = iterator.next();
            if (next != e) {
                iterator.remove();
            } else {
                iterator.remove();
                break;
            }
        }
        return true;
    }

    /**
     * 只适合2对应的物品数组
     */
    public static Map<Integer, Integer> arrayToBeMap(int[] array) {
        return arrayToBeMapFromIndex(array, 0);
    }

    /**
     * 从index开始
     */
    public static Map<Integer, Integer> arrayToBeMapFromIndex(int[] array, int index) {
        Map<Integer, Integer> map = new HashMap<>();
        if (array == null) {
            return map;
        }
        int size = (array.length - index) / 2;
        for (int i = index, j = 0; j < size; i += 2, j++) {
            map.put(array[i], array[i + 1]);
        }
        return map;
    }

    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (T[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static int[] concatAll(int[]... rest) {
        int[] first = new int[0];
        int totalLength = first.length;
        for (int[] array : rest) {
            totalLength += array.length;
        }
        int[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (int[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static double formatDouble(double d, int value) {
        String s = String.format("%." + value + "f", d);
        return Double.valueOf(s);
    }

    public static double randomDouble(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("from > to");
        }
        return new Random().nextDouble() * (to - from) + from;
    }

    public static double randomDouble(int from, int to, int value) {
        if (from > to) {
            throw new IllegalArgumentException("from > to");
        }
        double result = new Random().nextDouble() * (to - from) + from;
        return formatDouble(result, value);
    }

    /**
     * 随机枚举 + 排除特定选项
     */
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz, T... exclude) {
        T[] enumConstants = clazz.getEnumConstants();
        if (enumConstants.length <= 0) {
            return null;
        }
        List<T> list = new ArrayList<>();
        if (exclude != null) {
            for (T t : enumConstants) {
                if (!ArrayUtils.contains(exclude, t)) {
                    list.add(t);
                }
            }
            return list.get(new Random().nextInt(list.size()));
        } else {
            return enumConstants[nextInt(enumConstants.length)];
        }
    }

    public static <T> List<T> subList(List<T> list, int startIndex, int endIndex) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (startIndex > endIndex || startIndex > list.size()) {
            return null;
        }
        if (endIndex > list.size()) {
            endIndex = list.size();
        }
        ArrayList<T> listCopy = new ArrayList<>(list);
        return listCopy.subList(startIndex, endIndex);
    }


    public static <T> T weightRandom(Map<T, Integer> map) {
        int total = 0;
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            total += entry.getValue();
        }
        if (total <= 0) {
            return null;
        }
        int key = 0;
        int random = nextInt(total);
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            key += entry.getValue();
            if (key > random) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static <T> ArrayList<T> weightRandomCountWithout(Map<T, Integer> map, int num) {
        ArrayList<T> list = new ArrayList<>();
        int total = 0;
        Map<T, Integer> cache = new HashMap<>();
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            total += entry.getValue();
            cache.put(entry.getKey(), entry.getValue());
        }

        while (num > 0 && cache.size() > 0) {
            if (total <= 0) {
                return list;
            }
            int key = 0;
            int random = nextInt(total);
            for (Map.Entry<T, Integer> entry : cache.entrySet()) {
                key += entry.getValue();
                if (key >= random) {
                    list.add(entry.getKey());
                    total -= entry.getValue();
                    cache.remove(entry.getKey());
                    num--;
                    break;
                }
            }

        }
        return list;
    }

    public static <T> T weightRandom(LinkedHashMap<T, Integer> linkedHashMap) {
        LinkedHashMap<T, Integer> map = new LinkedHashMap<>(linkedHashMap);
        int total = 0;
        for (Iterator<Integer> iterator = map.values().iterator(); iterator.hasNext(); ) {
            Integer next = iterator.next();
            if (next == null || next == 0) {
                iterator.remove();
                continue;
            }
            total += next;
        }

        if (total == 0) {
            return null;
        }

        int randomValue = nextInt(total);
        int variable = 0;
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            Integer value = entry.getValue();
            T key = entry.getKey();
            if (variable <= randomValue && randomValue < variable + value) {
                return key;
            }
            variable += value;
        }
        return null;
    }

    public static int getIndexInArray(int[] array, int number) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == number) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isLongAddOverflow(long... datas) {
        BigDecimal rt = new BigDecimal(0);
        for (long data : datas) {
            BigDecimal add = new BigDecimal(String.valueOf(data));
            rt = rt.add(add);
            BigDecimal subtract = rt.subtract(new BigDecimal(Long.MAX_VALUE));
            if (subtract.compareTo(BigDecimal.ZERO) > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isIntegerMultiplyOverflow(int value, int count) {
        BigInteger v = new BigInteger(String.valueOf(value));
        BigInteger n = new BigInteger(String.valueOf(count));
        BigInteger rt = v.multiply(n);
        if (rt.compareTo(new BigInteger(String.valueOf(Integer.MAX_VALUE))) > 0) {
            return true;
        }
        return false;
    }

    public static int integerMultiply(int value, int count) {
        BigInteger v = new BigInteger(String.valueOf(value));
        BigInteger n = new BigInteger(String.valueOf(count));
        BigInteger rt = v.multiply(n);
        if (rt.compareTo(new BigInteger(String.valueOf(Integer.MAX_VALUE))) > 0) {
            return Integer.MAX_VALUE;
        }
        return rt.intValue();
    }

    //取最后一个 比他小的数
    public static int indexLastLessThanAndEqual(int[] list, int target) {
        if (list == null) {
            return -1;
        }
        int size = list.length;
        int index = -1;
        for (int i = 0; i < size; i++) {
            int n = list[i];
            if (target >= n) {
                if (index == -1 || index < i) {
                    index = i;
                }
            }
        }
        return index;
    }

    /**
     * 2个为一个间隔
     */
    public static int indexTwoSpaceSame(int[] list, int target) {
        if (list == null) {
            return -1;
        }
        int size = list.length;
        for (int i = 0; i < size - 1; i += 2) {
            int n = list[i];
            if (target == n) {
                return i;
            }
        }
        return -1;
    }


    public static List<Integer> random(int from, int to, int count) {
        List<Integer> index = new ArrayList<>();
        if (from > to || count == 0) {
            return index;
        }
        if (to - from < count - 1) {
            return index;
        }
        int max = 0;
        do {
            int random = random(from, to);
            if (!index.contains(random)) {
                index.add(random);
                if (index.size() == count) {
                    break;
                }
            }
            max++;
            if (max > 1000) {
                break;
            }
        } while (true);
        return index;
    }

    public static int indexTwoSpace(int[] list, int target) {
        if (list == null || list.length <= 0) {
            return -1;
        }
        int index = 0;
        int size = list.length;
        for (int i = 0; i < size - 1; i += 2) {
            int n = list[i];
            if (target >= n) {
                index = i;
            } else {
                return index;
            }
        }
        return index;
    }

    public static int indexSpaceByCount(int[] list, int target, int count) {
        if (list == null || list.length <= 0) {
            return -1;
        }
        int index = 0;
        int size = list.length;
        if (size % count != 0) {
            return -1;
        }
        for (int i = 0; i < size - 1; i += count) {
            int n = list[i];
            if (target >= n) {
                index = i;
            } else {
                return index;
            }
        }
        return index;
    }

    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        List<List<T>> listArray = new ArrayList<>();
        List<T> subList = null;
        for (int i = 0; i < list.size(); i++) {
            if (i % pageSize == 0) {
                subList = new ArrayList<>();
                listArray.add(subList);
            }
            subList.add(list.get(i));
        }
        return listArray;
    }

    public static <T> List<Set<T>> splitSet(Set<T> set, int pageSize) {
        List<Set<T>> listArray = new ArrayList<>();
        Set<T> subSet = null;
        int i = 0;
        for (T t : set) {
            if (i % pageSize == 0) {
                subSet = new HashSet<>();
                listArray.add(subSet);
            }
            subSet.add(t);
            i++;
        }
        return listArray;
    }

    public static <T> T getMinElement(Collection<T> collection, Comparator<T> comparator) {
        T min = null;
        for (T t : collection) {
            int compare = comparator.compare(min, t);
            if (compare <= 0) {
                min = t;
            }
        }
        return min;
    }

    public static List<String> regexSearch(String regex, CharSequence source, int groupIndex) {
        ArrayList<String> result = new ArrayList<>();
        if (regex == null || source == null) {
            return result;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result.add(matcher.group(groupIndex));
        }
        return result;
    }

    public static void randomSet(int min, int max, int n, HashSet<Integer> set) {
        if (n > (max - min + 1) || max < min) {
            return;
        }
        for (int i = 0; i < n; i++) {
            // 调用Math.random()方法
            int num = (int) (Math.random() * (max - min)) + min;
            set.add(num);// 将不同的数存入HashSet中
        }
        //        int setSize = set.size();
        //        // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
        //        if (setSize < n) {
        //            randomSet(min, max, n - setSize, set);// 递归
        //        }
    }

    public static HashSet<Integer> randomSet(int min, int max, int n) {
        HashSet<Integer> set = new HashSet<>();
        randomSet(min, max, n, set);
        return set;
    }


    public static <T> T selectKth(List<T> list, Comparator<T> comparator, int k) {
        int index = selectKthIndex(list, comparator, k);
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public static <T> int selectKthIndex(List<T> list, Comparator<T> comparator, int k) {
        if (list == null || list.isEmpty()) {
            return -1;
        }
        return selectIndex(list, comparator, 0, list.size() - 1, k);
    }

    public static <T> int selectIndex(List<T> list, Comparator<T> comparator, int m, int n, int i) {
        if (list == null || list.isEmpty() || comparator == null || n < m || n < 0 || m < 0) {
            return -1;
        }
        if (m == n) {
            return m;
        }
        int len = n - m + 1;
        if (i < 0) {
            i += len;
        }
        if (i < 0) {
            i = 0;
        } else if (i > len) {
            i = len;
        }
        int index = partition(list, comparator, m, n);
        int k = index - m + 1;
        if (k == i) {
            return index;
        } else if (k > i) {
            return selectIndex(list, comparator, m, index - 1, i);
        } else {
            return selectIndex(list, comparator, index + 1, n, i - k);
        }
    }

    private static <T> int partition(List<T> list, Comparator<T> comparator, int m, int n) {
        int r = random(m, n);
        T key = list.get(r);
        swap(list, r, n);
        int index = m;
        for (int i = index + 1; i < n; i++) {
            if (comparator.compare(list.get(i), key) < 0) {
                swap(list, i, index++);
            }
        }
        swap(list, index, n);
        return index;
    }

    private static <T> void swap(List<T> list, int m, int n) {
        T tmp = list.get(m);
        list.set(m, list.get(n));
        list.set(n, tmp);
    }


    public static <T> T selectKth(T[] array, Comparator<T> comparator, int k) {
        int index = selectKthIndex(array, comparator, k);
        if (index >= 0 && index < array.length) {
            return array[index];
        }
        return null;
    }

    public static <T> int selectKthIndex(T[] array, Comparator<T> comparator, int k) {
        if (array == null || array.length == 0) {
            return -1;
        }
        return selectIndex(array, comparator, 0, array.length - 1, k);
    }

    public static <T> int selectIndex(T[] array, Comparator<T> comparator, int m, int n, int i) {
        if (array == null || array.length <= 0 || comparator == null || n < m || n < 0 || m < 0) {
            return -1;
        }
        if (m == n) {
            return m;
        }
        int len = n - m + 1;
        if (i < 0) {
            i += len;
        }
        if (i < 0) {
            i = 0;
        } else if (i > len) {
            i = len;
        }
        int index = partition(array, comparator, m, n);
        int k = index - m + 1;
        if (k == i) {
            return index;
        } else if (k > i) {
            return selectIndex(array, comparator, m, index - 1, i);
        } else {
            return selectIndex(array, comparator, index + 1, n, i - k);
        }
    }

    private static <T> int partition(T[] array, Comparator<T> comparator, int m, int n) {
        int r = random(m, n);
        T key = array[r];
        swap(array, r, n);
        int index = m;
        for (int i = index + 1; i < n; i++) {
            if (comparator.compare(array[i], key) < 0) {
                swap(array, i, index++);
            }
        }
        swap(array, index, n);
        return index;
    }

    private static <T> void swap(T[] array, int m, int n) {
        T tmp = array[m];
        array[m] = array[n];
        array[n] = tmp;
    }

    public static <K, V> Map<K, V> removeMapKey(Map<K, V> map, K remove) {
        HashMap<K, V> copyMap = new HashMap<>(map);
        copyMap.remove(remove);
        return copyMap;
    }

    public static void modifyMapValueIsList(HashMap map, Object key, Object value, boolean add) {
        ArrayList list = (ArrayList) map.get(key);
        if (list == null) {
            list = new ArrayList();
            map.put(key, list);
        }
        if (add) {
            list.add(value);
        } else {
            list.remove(value);
        }
        if (list.isEmpty()) {
            map.remove(key);
        }
    }

    public static void modifyMapValueIsMap(HashMap map, Object key, Object value, Object value2, boolean add) {
        HashMap list = (HashMap) map.get(key);
        if (list == null) {
            list = new HashMap();
            map.put(key, list);
        }
        if (add) {
            list.put(value, value2);
        } else {
            list.remove(value);
        }
    }

    public static long mapToLong(Collection<Long> values) {
        return values.stream().mapToLong(v -> v).sum();
    }

    public static <k, v> List<Map<k, v>> mapChunk(Map<k, v> chunkMap, int chunkNum) {
        if (chunkMap == null || chunkNum <= 0) {
            List<Map<k, v>> list = new ArrayList<>();
            list.add(chunkMap);
            return list;
        }
        Set<k> keySet = chunkMap.keySet();
        Iterator<k> iterator = keySet.iterator();
        int i = 1;
        List<Map<k, v>> total = new ArrayList<>();
        Map<k, v> tem = new HashMap<>();
        while (iterator.hasNext()) {
            k next = iterator.next();
            tem.put(next, chunkMap.get(next));
            if (i == chunkNum) {
                total.add(tem);
                tem = new HashMap<>();
                i = 0;
            }
            i++;
        }
        if (!tem.isEmpty()) {
            total.add(tem);
        }
        return total;
    }

    public static <T> List<List<T>> listChunk(List<T> chunkList, int chunkNum) {
        if (chunkList == null || chunkNum <= 0) {
            List<List<T>> t = new ArrayList<>();
            t.add(chunkList);
            return t;
        }
        Iterator<T> iterator = chunkList.iterator();
        int i = 1;
        List<List<T>> total = new ArrayList<>();
        List<T> tem = new ArrayList<>();
        while (iterator.hasNext()) {
            T next = iterator.next();
            tem.add(next);
            if (i == chunkNum) {
                total.add(tem);
                tem = new ArrayList<>();
                i = 0;
            }
            i++;
        }
        if (!tem.isEmpty()) {
            total.add(tem);
        }
        return total;
    }

    /**
     * 堆栈转String
     * @param stackTraces
     * @return
     */
    public static String stackToString(StackTraceElement[] stackTraces) {
        StringBuilder stack = new StringBuilder();
        for (StackTraceElement stackTrace : stackTraces) {
            stack.append("->").append(stackTrace.toString());
        }
        return stack.toString();
    }

    public static long convertTwoIntToLong(int high32, int low32) {
        return ((long) high32) << 32 | (low32);
    }

    public static int[] convertLongToTwoInt(long data) {
        int[] result = new int[2];
        result[0] = (int) (data >> 32);
        result[1] = (int) (data);
        return result;
    }
}
