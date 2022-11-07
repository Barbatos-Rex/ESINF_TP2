package barbatos_rex1.structs;

import java.util.function.Predicate;

public abstract class SearchClause<T extends Comparable<T>> implements Predicate<T> {

    public abstract boolean matchCriteria(T element);

    @Override
    public boolean test(T t) {
        return matchCriteria(t);
    }
}
