package orr.utils;

import java.util.UUID;

public class UUIDSupplierImpl implements UUIDSupplier{
    @Override
    public UUID create() {
        return UUID.randomUUID();
    }
}
