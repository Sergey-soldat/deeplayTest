import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Essence {
    private String race;
    private int swamp;
    private int water;
    private int thicket;
    private int plain;
}
