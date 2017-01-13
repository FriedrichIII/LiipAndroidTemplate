package ch.template.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.annimon.stream.Collector;
import com.annimon.stream.Stream;
import com.annimon.stream.function.BiConsumer;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Supplier;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;
import java.util.List;

/**
 * To use ParcelablePlease, install the Android Studio plugin (not mandatory)
 */
@ParcelablePlease
public class ShoppingListDto implements Parcelable {

    List<String> items;

    public ShoppingListDto() {
        this.items = new ArrayList<>();
    }

    public void add(String str) {
        this.items.add(str);
    }

    public List<String> getItems() {
        return items;
    }


    @Override
    public String toString() {
        if (items.size()==0) {
            return "[EMPTY]";
        }
        String representation = Stream.of(items)
                .map(item -> String.format("[%s]", item))
                .collect(new MyJoiner());
        return representation;
    }

    private static class MyJoiner implements Collector<String, StringBuilder, String> {

        private boolean first = true;

        @Override
        public Supplier<StringBuilder> supplier() {
            return () -> new StringBuilder();
        }

        @Override
        public BiConsumer<StringBuilder, String> accumulator() {
            return (value1, value2) -> {
                if (first) {
                    first = false;
                } else {
                    value1.append("-");
                }
                value1.append(value2);
            };
        }

        @Override
        public Function<StringBuilder, String> finisher() {
            return stringBuilder -> stringBuilder.toString();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ShoppingListDtoParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<ShoppingListDto> CREATOR = new Creator<ShoppingListDto>() {
        public ShoppingListDto createFromParcel(Parcel source) {
            ShoppingListDto target = new ShoppingListDto();
            ShoppingListDtoParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public ShoppingListDto[] newArray(int size) {
            return new ShoppingListDto[size];
        }
    };
}
