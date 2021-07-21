package datatransfer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

public class SerialOOS extends ObjectOutputStream implements Serializable {
    public SerialOOS(OutputStream out) throws IOException {
        super(out);
    }
}
