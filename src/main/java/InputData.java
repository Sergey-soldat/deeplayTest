import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class InputData {
    private String playingFields;
    private Essence essence;
}